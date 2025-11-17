package org.mylitespring.context;

import com.test.mylitespring.service.PetStoreServiceV5;
import org.junit.Before;
import org.junit.Test;
import org.mylitespring.context.support.ClassPathXmlApplicationContext;
import org.mylitespring.utils.MessageTracker;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ApplicationContextAOPTest {


    @Before
    public void setUp() {
        MessageTracker.clear();
    }

    @Test
    public void testContextAOP() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v5.xml");
        PetStoreServiceV5 petStore = (PetStoreServiceV5)ctx.getBean("petStore");

        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());

        petStore.placeOrder();

        List<String> msgs = MessageTracker.getMessages();

        assertEquals(3, msgs.size());
        assertEquals("start tx", msgs.get(0));
        assertEquals("place order", msgs.get(1));
        assertEquals("commit tx", msgs.get(2));
    }



}
