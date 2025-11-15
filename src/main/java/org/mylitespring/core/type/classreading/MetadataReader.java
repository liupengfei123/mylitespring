package org.mylitespring.core.type.classreading;

import org.mylitespring.core.io.Resource;
import org.mylitespring.core.type.AnnotationMetadata;
import org.mylitespring.core.type.ClassMetadata;

public interface MetadataReader {

    /**
     * Return the resource reference for the class file.
     */
    Resource getResource();

    ClassMetadata getClassMetadata();


    AnnotationMetadata getAnnotationMetadata();

}
