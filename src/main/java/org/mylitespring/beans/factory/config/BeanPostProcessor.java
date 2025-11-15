package org.mylitespring.beans.factory.config;

import org.mylitespring.beans.BeansException;

public interface BeanPostProcessor {
    Object beforeInitialization(Object bean, String beanName) throws BeansException;


    Object afterInitialization(Object bean, String beanName) throws BeansException;
}
