package org.mylitespring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mylitespring.beans.BeanDefinition;
import org.mylitespring.beans.factory.BeanCreationException;
import org.mylitespring.beans.factory.BeanDefinitionStoreException;
import org.mylitespring.beans.factory.BeanFactory;
import org.mylitespring.util.ClassUtils;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory {

    private final static String ID_ATTRIBUTE = "id";
    private final static String CLASS_ATTRIBUTE = "class";

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(64);


    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

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

        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clazz = cl.loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " fail", e);
        }
    }


    private void loadBeanDefinition(String configFile) {
        ClassLoader loader = ClassUtils.getDefaultClassLoader();

        try (InputStream is = loader.getResourceAsStream(configFile)) {
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);

            Element root = document.getRootElement();            //<beans>
            Iterator iterator = root.elementIterator();

            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();

                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);

                BeanDefinition beanDefinition = new GenericBeanDefinition(id, beanClassName);
                beanDefinitionMap.put(id, beanDefinition);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("parse xml fail", e);
        }
    }


}
