package org.mylitespring.beans.factory.support;

import com.test.mylitespring.dao.v2.AccountDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mylitespring.beans.factory.BeanFactory;
import org.mylitespring.beans.factory.config.RuntimeBeanReference;
import org.mylitespring.beans.factory.config.TypedStringValue;
import org.mylitespring.beans.factory.xml.XMLBeanDefinitionReader;
import org.mylitespring.core.io.support.ClassPathResource;

public class BeanDefinitionValueResolverTest {

    private BeanFactory factory;

    @Before
    public void setup() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));

        this.factory = factory;
    }


    @Test
    public void testResolveRuntimeBeanReference() {
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);

        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference);

        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void testResolveTypedStringValue() {
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);

        TypedStringValue stringValue = new TypedStringValue("test");
        Object value = resolver.resolveValueIfNecessary(stringValue);
        Assert.assertNotNull(value);
        Assert.assertEquals("test", value);
    }
}
