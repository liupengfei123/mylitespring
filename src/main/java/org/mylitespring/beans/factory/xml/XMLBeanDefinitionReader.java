package org.mylitespring.beans.factory.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mylitespring.beans.BeanDefinition;
import org.mylitespring.beans.BeanDefinitionRegistry;
import org.mylitespring.beans.ConstructorArgument;
import org.mylitespring.beans.PropertyValue;
import org.mylitespring.beans.factory.BeanDefinitionStoreException;
import org.mylitespring.beans.factory.config.RuntimeBeanReference;
import org.mylitespring.beans.factory.config.TypedStringValue;
import org.mylitespring.beans.factory.support.GenericBeanDefinition;
import org.mylitespring.context.annotation.ClassPathBeanDefinitionScanner;
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

    private final static String CONSTRUCTOR_ELEMENT = "constructor-arg";

    public static final String TYPE_ATTRIBUTE = "type";

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";

    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    protected final Log logger = LogFactory.getLog(getClass());

    private final BeanDefinitionRegistry registry;

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
                String namespaceUri = element.getNamespaceURI();

                if(this.isDefaultNamespace(namespaceUri)){
                    parseDefaultElement(element); //普通的bean
                } else if(this.isContextNamespace(namespaceUri)){
                    parseComponentElement(element); //例如<context:component-scan>
                }
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("parse xml fail", e);
        }
    }

    private void parseComponentElement(Element element) {
        String basePackages = element.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.doScan(basePackages);
    }

    private void parseDefaultElement(Element element) {
        String id = element.attributeValue(ID_ATTRIBUTE);
        String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);

        BeanDefinition beanDefinition = new GenericBeanDefinition(id, beanClassName);

        String scope = element.attributeValue(SCOPE_ATTRIBUTE);
        if (scope != null) {
            beanDefinition.setScope(scope);
        }

        parsePropertyElement(element, beanDefinition);
        parseConstructorElement(element, beanDefinition);

        registry.registerBeanDefinition(id, beanDefinition);
    }

    private boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }
    private boolean isContextNamespace(String namespaceUri){
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
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

    private void parseConstructorElement(Element beanElem, BeanDefinition bd) {
        Iterator iterator = beanElem.elementIterator(CONSTRUCTOR_ELEMENT);

        ConstructorArgument constructorArgument = bd.getConstructorArgument();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();

            Object value = parsePropertyValue(element, null);
            ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);

            String typeAttr = element.attributeValue(TYPE_ATTRIBUTE);
            if (StringUtils.hasLength(typeAttr)) {
                valueHolder.setValue(typeAttr);
            }

            String nameAttr = element.attributeValue(NAME_ATTRIBUTE);
            if (StringUtils.hasLength(nameAttr)) {
                valueHolder.setName(nameAttr);
            }

            constructorArgument.addArgumentValue(valueHolder);
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
