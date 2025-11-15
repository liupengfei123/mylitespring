package org.mylitespring.context.support;

import org.mylitespring.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.mylitespring.beans.factory.config.ConfigurableBeanFactory;
import org.mylitespring.beans.factory.support.DefaultBeanFactory;
import org.mylitespring.beans.factory.xml.XMLBeanDefinitionReader;
import org.mylitespring.context.ApplicationContext;
import org.mylitespring.core.io.Resource;

public abstract class AbstractApplicationContext implements ApplicationContext {

    private final DefaultBeanFactory beanFactory;

    public AbstractApplicationContext(String path) {
        this.beanFactory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(beanFactory);

        Resource resource = getResourceByPath(path);

        reader.loadBeanDefinition(resource);

        registerBeanPostProcessors(beanFactory);
    }

    protected abstract Resource getResourceByPath(String path);

    @Override
    public Object getBean(String beanId) {
        return beanFactory.getBean(beanId);
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        beanFactory.setBeanClassLoader(classLoader);
    }

    public ClassLoader getBeanClassLoader() {
        return beanFactory.getBeanClassLoader();
    }

    protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {
        AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
        postProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(postProcessor);
    }
}
