package org.mylitespring.context;

import com.test.mylitespring.dao.AccountDao;
import com.test.mylitespring.dao.ItemDao;
import com.test.mylitespring.service.PetStoreServiceV1;
import com.test.mylitespring.service.PetStoreServiceV2;
import com.test.mylitespring.service.PetStoreServiceV3;
import com.test.mylitespring.service.PetStoreServiceV4;
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

    @Test
    public void testGetBeanConstructor() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v3.xml");
        PetStoreServiceV3 petStore1 = (PetStoreServiceV3) ctx.getBean("petStore1");

        assertNotNull(petStore1.getAccountDao());
        assertNotNull(petStore1.getItemDao());

        assertTrue(petStore1.getAccountDao() instanceof AccountDao);
        assertTrue(petStore1.getItemDao() instanceof ItemDao);

        assertEquals("liuxin", petStore1.getOwner());

        assertEquals(2, petStore1.getVersion());

        PetStoreServiceV3 petStore2 = (PetStoreServiceV3) ctx.getBean("petStore2");
        assertNotNull(petStore2.getAccountDao());
        assertNotNull(petStore2.getItemDao());

        assertTrue(petStore2.getAccountDao() instanceof AccountDao);
        assertTrue(petStore2.getItemDao() instanceof ItemDao);

        assertEquals("liuxin2", petStore2.getOwner());

        assertEquals(-1, petStore2.getVersion());
    }


    @Test
    public void testGetBeanByAnnotation() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v4.xml");
        PetStoreServiceV4 petStore = (PetStoreServiceV4)ctx.getBean("petStore");

        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());
    }

}
