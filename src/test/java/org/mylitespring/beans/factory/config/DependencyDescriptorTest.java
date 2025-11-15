package org.mylitespring.beans.factory.config;

import com.test.mylitespring.dao.AccountDaoV4;
import com.test.mylitespring.service.PetStoreServiceV4;
import org.junit.Assert;
import org.junit.Test;
import org.mylitespring.beans.factory.support.DefaultBeanFactory;
import org.mylitespring.beans.factory.xml.XMLBeanDefinitionReader;
import org.mylitespring.core.io.Resource;
import org.mylitespring.core.io.support.ClassPathResource;

import java.lang.reflect.Field;

public class DependencyDescriptorTest {

    @Test
    public void testResolveDependency() throws Exception{

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinition(resource);

        Field f = PetStoreServiceV4.class.getDeclaredField("accountDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(f, true);
        Object o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof AccountDaoV4);
    }

}
