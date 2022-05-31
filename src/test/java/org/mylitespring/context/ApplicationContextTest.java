package org.mylitespring.context;

import com.test.mylitespring.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Test;
import org.mylitespring.context.support.ClassPathXmlApplicationContext;
import org.mylitespring.context.support.FileSystemXmlApplicationContext;

import java.io.File;

public class ApplicationContextTest {


    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }
    @Test
    public void testGetBeanFromFileSystemContext(){
        String path = new File("./").getAbsolutePath() + "\\src\\test\\resources\\petstore-v1.xml";
		ApplicationContext ctx = new FileSystemXmlApplicationContext(path);
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }


}
