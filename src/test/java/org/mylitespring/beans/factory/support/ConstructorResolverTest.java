package org.mylitespring.beans.factory.support;

import com.test.mylitespring.service.PetStoreServiceV3;
import org.junit.Assert;
import org.junit.Test;
import org.mylitespring.beans.BeanDefinition;
import org.mylitespring.core.io.Resource;
import org.mylitespring.core.io.support.ClassPathResource;
import org.mylitespring.beans.factory.xml.XMLBeanDefinitionReader;

public class ConstructorResolverTest {

    @Test
    public void testAutowireConstructor1() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v3.xml");
        reader.loadBeanDefinition(resource);

        BeanDefinition bd = factory.getBeanDefinition("petStore1");

        ConstructorResolver resolver = new ConstructorResolver(factory);

        PetStoreServiceV3 petStore = (PetStoreServiceV3)resolver.autowireConstructor(bd);

        Assert.assertEquals(2, petStore.getVersion());

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
    }

    @Test
    public void testAutowireConstructor2() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v3.xml");
        reader.loadBeanDefinition(resource);

        BeanDefinition bd = factory.getBeanDefinition("petStore2");

        ConstructorResolver resolver = new ConstructorResolver(factory);

        PetStoreServiceV3 petStore = (PetStoreServiceV3)resolver.autowireConstructor(bd);

        Assert.assertEquals(-1, petStore.getVersion());

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
    }
}
