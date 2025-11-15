package org.mylitespring.core.type;

import org.junit.Assert;
import org.junit.Test;
import org.mylitespring.core.annotation.AnnotationAttributes;
import org.mylitespring.core.io.support.ClassPathResource;
import org.mylitespring.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.mylitespring.core.type.classreading.ClassMetadataReadingVisitor;
import org.springframework.asm.ClassReader;

import java.io.IOException;

public class ClassReaderTest {


    @Test
    public void testGetClasMetaData() throws IOException {
        ClassPathResource resource = new ClassPathResource("com/test/mylitespring/service/PetStoreServiceV4.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();

        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals("com.test.mylitespring.service.PetStoreServiceV4", visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }

    @Test
    public void testGetAnnonation() throws Exception{
        ClassPathResource resource = new ClassPathResource("com/test/mylitespring/service/PetStoreServiceV4.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();

        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        String annotation = "org.mylitespring.stereotype.Component";
        Assert.assertTrue(visitor.hasAnnotation(annotation));

        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);

        Assert.assertEquals("petStore", attributes.get("value"));

    }
}
