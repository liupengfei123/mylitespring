package org.mylitespring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mylitespring.beans.BeanDefinitionTest;
import org.mylitespring.beans.TypeConverterTest;
import org.mylitespring.beans.factory.support.BeanDefinitionValueResolverTest;
import org.mylitespring.beans.propertyeditors.CustomBooleanEditorTest;
import org.mylitespring.beans.propertyeditors.CustomNumberEditorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BeanDefinitionTest.class,
        TypeConverterTest.class,
        CustomBooleanEditorTest.class,
        CustomNumberEditorTest.class,
        BeanDefinitionValueResolverTest.class,
})
public class V2AllTests {

}