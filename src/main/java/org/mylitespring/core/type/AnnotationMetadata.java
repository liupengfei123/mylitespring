package org.mylitespring.core.type;

import org.mylitespring.core.annotation.AnnotationAttributes;

import java.util.Set;

public interface AnnotationMetadata extends ClassMetadata {

    Set<String> getAnnotationTypes();


    boolean hasAnnotation(String annotationType);

    AnnotationAttributes getAnnotationAttributes(String annotationType);
}
