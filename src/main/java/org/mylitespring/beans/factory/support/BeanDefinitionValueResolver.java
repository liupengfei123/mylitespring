package org.mylitespring.beans.factory.support;

import org.mylitespring.beans.factory.BeanFactory;
import org.mylitespring.beans.factory.config.RuntimeBeanReference;
import org.mylitespring.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {
    private final BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            String beanName = ((RuntimeBeanReference) value).getBeanName();

            return this.beanFactory.getBean(beanName);
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            //TODO
            throw new RuntimeException("the value " + value +" has not implemented");
        }
    }
}
