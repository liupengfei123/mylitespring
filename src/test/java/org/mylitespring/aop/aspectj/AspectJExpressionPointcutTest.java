package org.mylitespring.aop.aspectj;

import com.test.mylitespring.dao.AccountDaoV4;
import com.test.mylitespring.service.PetStoreServiceV5;
import org.junit.Assert;
import org.junit.Test;
import org.mylitespring.aop.MethodMatcher;

import java.lang.reflect.Method;

public class AspectJExpressionPointcutTest {
    @Test
    public void testPointcut() throws Exception{

        String expression = "execution(* com.test.mylitespring.service.*.placeOrder(..))";

        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);

        MethodMatcher mm = pc.getMethodMatcher();

        {
            Class<?> targetClass = PetStoreServiceV5.class;

            Method method1 = targetClass.getMethod("placeOrder");
            Assert.assertTrue(mm.matches(method1));

            Method method2 = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method2));
        }

        {
            Class<?> targetClass = AccountDaoV4.class;

            Method method = targetClass.getMethod("toString");
            Assert.assertFalse(mm.matches(method));
        }

    }
}
