package org.mylitespring.beans.factory;

import com.test.mylitespring.service.v1.PetStoreService;
import org.junit.Test;
import org.mylitespring.beans.BeanDefinition;
import org.mylitespring.beans.factory.support.DefaultBeanFactory;

import static org.junit.Assert.*;

public class BeanFactoryTest {


    @Test
    public void testGetBean() {
        BeanFactory beanFactory = new DefaultBeanFactory("petstore-v1.xml");

        BeanDefinition definition = beanFactory.getBeanDefinition("petStore");

        assertEquals("com.test.mylitespring.service.v1.PetStoreService", definition.getBeanClassName());

        PetStoreService bean = (PetStoreService) beanFactory.getBean("petStore");

        assertNotNull(bean);
    }


    @Test
    public void testInvalidBean() {
        BeanFactory beanFactory = new DefaultBeanFactory("petstore-v1.xml");

        try {
            beanFactory.getBean("InvalidBean");
        } catch (BeanCreationException e) {
            return;
        }
        fail("expect BeanCreationException");
    }

    @Test
    public void testInvalidXml() {
        try {
             new DefaultBeanFactory("InvalidXml.xml");
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        fail("expect BeanDefinitionStoreException");
    }

}
