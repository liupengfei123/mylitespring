package org.mylitespring.beans.factory;

import org.mylitespring.beans.BeanDefinition;

public interface BeanFactory {

    BeanDefinition getBeanDefinition(String beanId);

    Object getBean(String beanId);

}
