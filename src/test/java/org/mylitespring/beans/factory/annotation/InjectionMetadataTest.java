package org.mylitespring.beans.factory.annotation;

import com.test.mylitespring.dao.AccountDaoV4;
import com.test.mylitespring.dao.ItemDaoV4;
import com.test.mylitespring.service.PetStoreServiceV4;
import org.junit.Assert;
import org.junit.Test;
import org.mylitespring.beans.factory.support.DefaultBeanFactory;
import org.mylitespring.beans.factory.xml.XMLBeanDefinitionReader;
import org.mylitespring.core.io.Resource;
import org.mylitespring.core.io.support.ClassPathResource;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class InjectionMetadataTest {
    @Test
    public void testInjection() throws Exception {

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinition(resource);

        Class<?> clz = PetStoreServiceV4.class;
        LinkedList<InjectionElement> elements = new LinkedList<>();

        {
            Field f = PetStoreServiceV4.class.getDeclaredField("accountDao");
            InjectionElement injectionElem = new AutowiredFieldElement(f, true, factory);
            elements.add(injectionElem);
        }
        {
            Field f = PetStoreServiceV4.class.getDeclaredField("itemDao");
            InjectionElement injectionElem = new AutowiredFieldElement(f, true, factory);
            elements.add(injectionElem);
        }

        InjectionMetadata metadata = new InjectionMetadata(clz, elements);

        PetStoreServiceV4 petStore = new PetStoreServiceV4();

        metadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDaoV4);

        Assert.assertTrue(petStore.getItemDao() instanceof ItemDaoV4);

    }
}
