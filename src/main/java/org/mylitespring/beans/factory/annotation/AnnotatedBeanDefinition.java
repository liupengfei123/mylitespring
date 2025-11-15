package org.mylitespring.beans.factory.annotation;

import org.mylitespring.beans.BeanDefinition;
import org.mylitespring.core.type.AnnotationMetadata;

public interface AnnotatedBeanDefinition extends BeanDefinition {

    AnnotationMetadata getMetadata();

}
