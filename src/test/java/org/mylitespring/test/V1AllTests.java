package org.mylitespring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mylitespring.beans.factory.BeanFactoryTest;
import org.mylitespring.context.ApplicationContextTestV1;
import org.mylitespring.core.io.ResourceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTestV1.class,
        BeanFactoryTest.class ,
        ResourceTest.class})
public class V1AllTests {

}