package org.mylitespring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mylitespring.beans.BeanDefinition;
import org.mylitespring.beans.BeanDefinitionRegistry;
import org.mylitespring.beans.factory.BeanDefinitionStoreException;
import org.mylitespring.beans.factory.support.GenericBeanDefinition;
import org.mylitespring.core.io.Resource;

import java.io.InputStream;
import java.util.Iterator;

public class XMLBeanDefinitionReader {
    private final static String ID_ATTRIBUTE = "id";
    private final static String CLASS_ATTRIBUTE = "class";
    private final static String SCOPE_ATTRIBUTE = "scope";

    private BeanDefinitionRegistry registry;

    public XMLBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.registry = beanDefinitionRegistry;
    }

    public void loadBeanDefinition(Resource resource) {
        try (InputStream is = resource.getInputStream()) {
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);

            Element root = document.getRootElement();            //<beans>
            Iterator iterator = root.elementIterator();

            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();

                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);

                BeanDefinition beanDefinition = new GenericBeanDefinition(id, beanClassName);

                String scope = element.attributeValue(SCOPE_ATTRIBUTE);
                if (scope != null) {
                    beanDefinition.setScope(scope);
                }

                registry.registerBeanDefinition(id, beanDefinition);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("parse xml fail", e);
        }
    }


}
