package org.mylitespring.beans.factory;

import com.test.mylitespring.service.PetStoreServiceV1;
import org.junit.Before;
import org.junit.Test;
import org.mylitespring.beans.BeanDefinition;
import org.mylitespring.beans.factory.support.DefaultBeanFactory;
import org.mylitespring.beans.factory.xml.XMLBeanDefinitionReader;
import org.mylitespring.core.io.support.ClassPathResource;

import static org.junit.Assert.*;

public class BeanFactoryTest {

    private DefaultBeanFactory beanFactory;
    private XMLBeanDefinitionReader reader;

    @Before
    public void setUp() {
        beanFactory = new DefaultBeanFactory();
        reader = new XMLBeanDefinitionReader(beanFactory);
    }

    @Test
    public void testGetBean() {

        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        BeanDefinition bd = beanFactory.getBeanDefinition("petStore");

        assertTrue(bd.isSingleton());

        assertFalse(bd.isPrototype());

        assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());

        assertEquals("com.test.mylitespring.service.PetStoreServiceV1", bd.getBeanClassName());

        PetStoreServiceV1 petStore = (PetStoreServiceV1) beanFactory.getBean("petStore");

        assertNotNull(petStore);

        PetStoreServiceV1 petStore1 = (PetStoreServiceV1) beanFactory.getBean("petStore");

        assertEquals(petStore, petStore1);
    }


    @Test
    public void testInvalidBean() {
        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
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
            reader.loadBeanDefinition(new ClassPathResource("InvalidXml.xml"));
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        fail("expect BeanDefinitionStoreException");
    }

}
