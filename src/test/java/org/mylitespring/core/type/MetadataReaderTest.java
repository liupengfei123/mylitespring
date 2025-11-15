package org.mylitespring.core.type;

import org.junit.Assert;
import org.junit.Test;
import org.mylitespring.core.annotation.AnnotationAttributes;
import org.mylitespring.core.io.support.ClassPathResource;
import org.mylitespring.core.type.classreading.MetadataReader;
import org.mylitespring.core.type.classreading.SimpleMetadataReader;
import org.mylitespring.stereotype.Component;

import java.io.IOException;

public class MetadataReaderTest {
    @Test
    public void testGetMetadata() throws IOException {
        ClassPathResource resource = new ClassPathResource("com/test/mylitespring/service/PetStoreServiceV4.class");

        MetadataReader reader = new SimpleMetadataReader(resource);
        //注意：不需要单独使用ClassMetadata
        //ClassMetadata clzMetadata = reader.getClassMetadata();
        AnnotationMetadata amd = reader.getAnnotationMetadata();

        String annotation = Component.class.getName();

        Assert.assertTrue(amd.hasAnnotation(annotation));
        AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attributes.get("value"));

        //注：下面对class metadata的测试并不充分
        Assert.assertFalse(amd.isAbstract());
        Assert.assertFalse(amd.isFinal());
        Assert.assertEquals("com.test.mylitespring.service.PetStoreServiceV4", amd.getClassName());

    }
}
