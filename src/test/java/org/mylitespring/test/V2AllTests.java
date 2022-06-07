package org.mylitespring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mylitespring.beans.BeanDefinitionTestV2;
import org.mylitespring.beans.TypeConverterTestV2;
import org.mylitespring.beans.factory.support.BeanDefinitionValueResolverTestV2;
import org.mylitespring.beans.propertyeditors.CustomBooleanEditorTestV2;
import org.mylitespring.beans.propertyeditors.CustomNumberEditorTestV2;
import org.mylitespring.context.ApplicationContextTestV2;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTestV2.class,
        BeanDefinitionTestV2.class,
        TypeConverterTestV2.class,
        CustomBooleanEditorTestV2.class,
        CustomNumberEditorTestV2.class,
        BeanDefinitionValueResolverTestV2.class,
})
public class V2AllTests {

}