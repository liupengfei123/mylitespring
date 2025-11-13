package org.mylitespring.beans;

import org.junit.Assert;
import org.junit.Test;
import org.mylitespring.beans.factory.config.RuntimeBeanReference;
import org.mylitespring.beans.factory.support.DefaultBeanFactory;
import org.mylitespring.beans.factory.xml.XMLBeanDefinitionReader;
import org.mylitespring.core.io.support.ClassPathResource;

import java.util.List;

public class BeanDefinitionTest {


    @Test
    public void testGetBeanDefinition() {

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);

        reader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        List<PropertyValue> pvs = bd.getPropertyValues();

        Assert.assertEquals(4, pvs.size());
        {
            PropertyValue pv = this.getPropertyValue("accountDao", pvs);
            Assert.assertNotNull(pv);
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }

        {
            PropertyValue pv = this.getPropertyValue("itemDao", pvs);
            Assert.assertNotNull(pv);
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }

    }

    private PropertyValue getPropertyValue(String name,List<PropertyValue> pvs){
        for(PropertyValue pv : pvs){
            if(pv.getName().equals(name)){
                return pv;
            }
        }
        return null;
    }

}