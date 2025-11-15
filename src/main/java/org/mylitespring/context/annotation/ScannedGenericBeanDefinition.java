package org.mylitespring.context.annotation;

import org.mylitespring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.mylitespring.beans.factory.support.GenericBeanDefinition;
import org.mylitespring.core.type.AnnotationMetadata;

public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

    private final AnnotationMetadata metadata;

    public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
        super();

        this.metadata = metadata;

        setBeanClassName(this.metadata.getClassName());
    }

    @Override
    public AnnotationMetadata getMetadata() {
        return metadata;
    }
}
