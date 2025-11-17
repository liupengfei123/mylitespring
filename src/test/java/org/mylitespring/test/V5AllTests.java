package org.mylitespring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mylitespring.aop.aspectj.AspectJExpressionPointcutTest;
import org.mylitespring.aop.config.MethodLocatingFactoryTest;
import org.mylitespring.context.ApplicationContextAOPTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MethodLocatingFactoryTest.class,
        ApplicationContextAOPTest.class,
        AspectJExpressionPointcutTest.class,
})
public class V5AllTests {

}