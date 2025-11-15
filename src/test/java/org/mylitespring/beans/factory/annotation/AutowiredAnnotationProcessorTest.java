package org.mylitespring.beans.factory.annotation;


import java.lang.reflect.Field;
import java.util.List;

import com.test.mylitespring.dao.AccountDaoV4;
import com.test.mylitespring.dao.ItemDaoV4;
import com.test.mylitespring.service.PetStoreServiceV4;
import org.junit.Assert;
import org.junit.Test;
import org.mylitespring.beans.factory.config.DependencyDescriptor;
import org.mylitespring.beans.factory.support.DefaultBeanFactory;


public class AutowiredAnnotationProcessorTest {
    AccountDaoV4 accountDao = new AccountDaoV4();
    ItemDaoV4 itemDao = new ItemDaoV4();
    DefaultBeanFactory beanFactory = new DefaultBeanFactory(){
        public Object resolveDependency(DependencyDescriptor descriptor){
            if(descriptor.getDependencyType().equals(AccountDaoV4.class)){
                return accountDao;
            }
            if(descriptor.getDependencyType().equals(ItemDaoV4.class)){
                return itemDao;
            }
            throw new RuntimeException("can't support types except AccountDao and ItemDao");
        }
    };



    @Test
    public void testGetInjectionMetadata(){

        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(beanFactory);
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetStoreServiceV4.class);
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();
        Assert.assertEquals(2, elements.size());

        assertFieldExists(elements,"accountDao");
        assertFieldExists(elements,"itemDao");

        PetStoreServiceV4 petStore = new PetStoreServiceV4();

        injectionMetadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDaoV4);

        Assert.assertTrue(petStore.getItemDao() instanceof ItemDaoV4);
    }

    private void assertFieldExists(List<InjectionElement> elements ,String fieldName){
        for(InjectionElement ele : elements){
            AutowiredFieldElement fieldEle = (AutowiredFieldElement)ele;
            Field f = fieldEle.getField();
            if(f.getName().equals(fieldName)){
                return;
            }
        }
        Assert.fail(fieldName + "does not exist!");
    }



}