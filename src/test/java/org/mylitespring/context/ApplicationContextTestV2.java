package org.mylitespring.context;

import com.test.mylitespring.dao.v2.AccountDao;
import com.test.mylitespring.dao.v2.ItemDao;
import com.test.mylitespring.service.v2.PetStoreService;
import org.junit.Test;
import org.mylitespring.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class ApplicationContextTestV2 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());

        assertTrue(petStore.getAccountDao() instanceof AccountDao);
        assertTrue(petStore.getItemDao() instanceof ItemDao);

        assertEquals("liuxin", petStore.getOwner());

        assertEquals(2, petStore.getVersion());
    }
}
