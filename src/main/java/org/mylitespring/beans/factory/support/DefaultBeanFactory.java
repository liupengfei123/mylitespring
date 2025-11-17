package org.mylitespring.beans.factory.support;

import org.mylitespring.beans.BeanDefinition;
import org.mylitespring.beans.BeanDefinitionRegistry;
import org.mylitespring.beans.PropertyValue;
import org.mylitespring.beans.SimpleTypeConverter;
import org.mylitespring.beans.factory.BeanCreationException;
import org.mylitespring.beans.factory.NoSuchBeanDefinitionException;
import org.mylitespring.beans.factory.config.BeanPostProcessor;
import org.mylitespring.beans.factory.config.ConfigurableBeanFactory;
import org.mylitespring.beans.factory.config.DependencyDescriptor;
import org.mylitespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.mylitespring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    private ClassLoader beanClassLoader;

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(64);

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return beanDefinitionMap.get(beanId);
    }

    @Override
    public Object getBean(String beanId) {
        BeanDefinition bd = getBeanDefinition(beanId);
        if (bd == null) {
            throw new BeanCreationException("Bean Definition does not exit");
        }

        Object bean;
        if (bd.isSingleton()) {
            bean = super.getSingleton(beanId);
            if (bean == null) {
                bean = createBean(bd);
                super.registerSingleton(beanId, bean);
            }
        } else {
            bean = createBean(bd);
        }

        return bean;
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        BeanDefinition bd = getBeanDefinition(name);
        if(bd == null){
            throw new NoSuchBeanDefinitionException(name);
        }
        this.resolveBeanClass(bd);
        return bd.getBeanClass();
    }

    @Override
    public void registerBeanDefinition(String beanID, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanID, beanDefinition);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return beanClassLoader == null ? ClassUtils.getDefaultClassLoader() : beanClassLoader;
    }

    @Override
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        beanPostProcessors.add(postProcessor);
    }

    private Object createBean(BeanDefinition bd) {
        Object bean = instantiateBean(bd);

        populateBean(bd, bean);

        return bean;
    }


    private Object instantiateBean(BeanDefinition bd) {
        if (bd.hasConstructorArgument()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        } else {
            ClassLoader cl = this.getBeanClassLoader();
            String beanClassName = bd.getBeanClassName();
            try {
                Class<?> clazz = cl.loadClass(beanClassName);
                return clazz.newInstance();
            } catch (Exception e) {
                throw new BeanCreationException("create bean for " + beanClassName + " fail", e);
            }
        }
    }

    private void populateBean(BeanDefinition bd, Object bean) {
        for(BeanPostProcessor processor : this.getBeanPostProcessors()){
            if(processor instanceof InstantiationAwareBeanPostProcessor){
                ((InstantiationAwareBeanPostProcessor)processor).postProcessPropertyValues(bean, bd.getID());
            }
        }


        List<PropertyValue> pvs = bd.getPropertyValues();

        if (pvs == null || pvs.isEmpty()) {
            return;
        }

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();

                Object resolvedValue = resolver.resolveValueIfNecessary(originalValue);

                for (PropertyDescriptor pd : pds) {
                    if (pd.getName().equals(propertyName)) {
                        resolvedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, resolvedValue);
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", ex);
        }
    }

    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMatch = descriptor.getDependencyType();
        for(BeanDefinition bd: this.beanDefinitionMap.values()){
            //确保BeanDefinition 有Class对象
            resolveBeanClass(bd);
            Class<?> beanClass = bd.getBeanClass();
            if(typeToMatch.isAssignableFrom(beanClass)){
                return this.getBean(bd.getID());
            }
        }
        return null;
    }
    public void resolveBeanClass(BeanDefinition bd) {
        if(!bd.hasBeanClass()){
            try {
                bd.resolveBeanClass(this.getBeanClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("can't load class:"+bd.getBeanClassName());
            }
        }
    }
}
