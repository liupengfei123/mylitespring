package org.mylitespring.beans.factory.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mylitespring.beans.BeanDefinition;
import org.mylitespring.beans.BeanDefinitionRegistry;
import org.mylitespring.beans.PropertyValue;
import org.mylitespring.beans.factory.BeanDefinitionStoreException;
import org.mylitespring.beans.factory.config.RuntimeBeanReference;
import org.mylitespring.beans.factory.config.TypedStringValue;
import org.mylitespring.beans.factory.support.GenericBeanDefinition;
import org.mylitespring.core.io.Resource;
import org.mylitespring.util.StringUtils;

import java.io.InputStream;
import java.util.Iterator;

public class XMLBeanDefinitionReader {
    private final static String ID_ATTRIBUTE = "id";
    private final static String CLASS_ATTRIBUTE = "class";
    private final static String SCOPE_ATTRIBUTE = "scope";
    private final static String PROPERTY_ELEMENT = "property";
    private final static String REF_ATTRIBUTE = "ref";
    private final static String VALUE_ATTRIBUTE = "value";
    private final static String NAME_ATTRIBUTE = "name";


    protected final Log logger = LogFactory.getLog(getClass());

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

                parsePropertyElement(element, beanDefinition);

                registry.registerBeanDefinition(id, beanDefinition);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("parse xml fail", e);
        }
    }

    private void parsePropertyElement(Element beanElem, BeanDefinition bd) {
        Iterator iterator = beanElem.elementIterator(PROPERTY_ELEMENT);

        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();

            String propertyName = element.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propertyName)) {
                logger.fatal("Tag 'property' must have a 'name' attribute");
                return;
            }

            Object value = parsePropertyValue(element, propertyName);

            bd.getPropertyValues().add(new PropertyValue(propertyName, value));
        }
    }

    private Object parsePropertyValue(Element ele, String propertyName) {
        String elementName = (propertyName != null) ?
                "<property> element for property '" + propertyName + "'" :
                "<constructor-arg> element";

        boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE) != null);
        boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) != null);

        if (hasRefAttribute) {
            String refName = ele.attributeValue(REF_ATTRIBUTE);
            if (!StringUtils.hasText(refName)) {
                logger.error(elementName + " contains empty 'ref' attribute");
            }
            return new RuntimeBeanReference(refName);
        } else if (hasValueAttribute) {
            String value = ele.attributeValue(VALUE_ATTRIBUTE);
            return new TypedStringValue(value);
        } else {
            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }

}
