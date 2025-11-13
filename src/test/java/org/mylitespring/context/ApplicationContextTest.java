package org.mylitespring.context;

import com.test.mylitespring.dao.v2.AccountDao;
import com.test.mylitespring.dao.v2.ItemDao;
import com.test.mylitespring.service.PetStoreServiceV1;
import com.test.mylitespring.service.PetStoreServiceV2;
import org.junit.Test;
import org.mylitespring.context.support.ClassPathXmlApplicationContext;
import org.mylitespring.context.support.FileSystemXmlApplicationContext;

import java.io.File;

import static org.junit.Assert.*;

public class ApplicationContextTest {


    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreServiceV1 petStore = (PetStoreServiceV1)ctx.getBean("petStore");
        assertNotNull(petStore);
    }
    @Test
    public void testGetBeanFromFileSystemContext(){
        String path = new File("./").getAbsolutePath() + "\\src\\test\\resources\\petstore-v1.xml";
		ApplicationContext ctx = new FileSystemXmlApplicationContext(path);
		PetStoreServiceV1 petStore = (PetStoreServiceV1)ctx.getBean("petStore");
        assertNotNull(petStore);
    }

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreServiceV2 petStore = (PetStoreServiceV2) ctx.getBean("petStore");

        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());

        assertTrue(petStore.getAccountDao() instanceof AccountDao);
        assertTrue(petStore.getItemDao() instanceof ItemDao);

        assertEquals("liuxin", petStore.getOwner());

        assertEquals(2, petStore.getVersion());
    }

}
