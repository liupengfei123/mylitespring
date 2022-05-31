package org.mylitespring.v1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mylitespring.beans.factory.BeanFactoryTest;
import org.mylitespring.context.ApplicationContextTest;
import org.mylitespring.core.io.ResourceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTest.class,
        BeanFactoryTest.class ,
        ResourceTest.class})
public class V1AllTests {

}