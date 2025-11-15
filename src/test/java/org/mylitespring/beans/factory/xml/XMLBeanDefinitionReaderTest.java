package org.mylitespring.beans.factory.xml;

import org.junit.Assert;
import org.junit.Test;
import org.mylitespring.beans.BeanDefinition;
import org.mylitespring.beans.factory.support.DefaultBeanFactory;
import org.mylitespring.context.annotation.ScannedGenericBeanDefinition;
import org.mylitespring.core.annotation.AnnotationAttributes;
import org.mylitespring.core.io.Resource;
import org.mylitespring.core.io.support.ClassPathResource;
import org.mylitespring.core.type.AnnotationMetadata;
import org.mylitespring.stereotype.Component;

public class XMLBeanDefinitionReaderTest {

    @Test
    public void testParseScanedBean(){

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinition(resource);
        String annotation = Component.class.getName();

        //下面的代码和ClassPathBeanDefinitionScannerTest重复，该怎么处理？
        {
            BeanDefinition bd = factory.getBeanDefinition("petStore");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();


            Assert.assertTrue(amd.hasAnnotation(annotation));
            AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
            Assert.assertEquals("petStore", attributes.get("value"));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("accountDaoV4");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("itemDaoV4");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
    }
}
