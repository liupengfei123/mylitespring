package org.mylitespring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mylitespring.beans.factory.annotation.AutowiredAnnotationProcessorTest;
import org.mylitespring.beans.factory.annotation.InjectionMetadataTest;
import org.mylitespring.beans.factory.config.DependencyDescriptorTest;
import org.mylitespring.beans.factory.xml.XMLBeanDefinitionReaderTest;
import org.mylitespring.context.annotation.ClassPathBeanDefinitionScannerTest;
import org.mylitespring.core.io.support.PackageResourceLoaderTest;
import org.mylitespring.core.type.ClassReaderTest;
import org.mylitespring.core.type.MetadataReaderTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ClassReaderTest.class,
        MetadataReaderTest.class,
        PackageResourceLoaderTest.class,
        XMLBeanDefinitionReaderTest.class,
        ClassPathBeanDefinitionScannerTest.class,
        DependencyDescriptorTest.class,
        InjectionMetadataTest.class,
        AutowiredAnnotationProcessorTest.class,
})
public class V4AllTests {

}