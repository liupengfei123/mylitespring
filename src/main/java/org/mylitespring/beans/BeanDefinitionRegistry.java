package org.mylitespring.beans;

public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String beanId);

    void registerBeanDefinition(String beanID, BeanDefinition beanDefinition);
}
