package org.mylitespring.aop.config;

import com.test.mylitespring.tx.TransactionManager;
import org.junit.Assert;
import org.junit.Test;
import org.mylitespring.beans.factory.support.DefaultBeanFactory;
import org.mylitespring.beans.factory.xml.XMLBeanDefinitionReader;
import org.mylitespring.core.io.Resource;
import org.mylitespring.core.io.support.ClassPathResource;

import java.lang.reflect.Method;

public class MethodLocatingFactoryTest {
    @Test
    public void testGetMethod() throws Exception{
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(beanFactory);
        Resource resource = new ClassPathResource("petstore-v5.xml");
        reader.loadBeanDefinition(resource);

        MethodLocatingFactory methodLocatingFactory = new MethodLocatingFactory();
        methodLocatingFactory.setTargetBeanName("tx");
        methodLocatingFactory.setMethodName("start");
        methodLocatingFactory.setBeanFactory(beanFactory);
        methodLocatingFactory.parse();

        Method m = methodLocatingFactory.getObject();

        Assert.assertEquals(TransactionManager.class, m.getDeclaringClass());
        Assert.assertEquals(m, TransactionManager.class.getMethod("start"));

    }
}
