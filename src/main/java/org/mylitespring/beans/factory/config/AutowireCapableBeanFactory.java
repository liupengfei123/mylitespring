package org.mylitespring.beans.factory.config;

import org.mylitespring.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {
    Object resolveDependency(DependencyDescriptor descriptor);
}
