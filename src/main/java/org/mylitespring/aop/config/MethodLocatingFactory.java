package org.mylitespring.aop.config;

import org.mylitespring.beans.factory.BeanFactory;
import org.mylitespring.util.Assert;
import org.mylitespring.beans.BeanUtils;

import java.lang.reflect.Method;

public class MethodLocatingFactory {

    private BeanFactory beanFactory;
    private String targetBeanName;
    private String methodName;

    private Method method;


    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


    public void parse() {
        Assert.notNull(beanFactory, "BeanFactory is required");
        Assert.notBlank(targetBeanName, "TargetBeanName is required");
        Assert.notBlank(methodName, "MethodName is required");

        Class<?> beanClass = beanFactory.getType(targetBeanName);


        this.method = BeanUtils.resolveSignature(this.methodName, beanClass);
        if (this.method == null) {
            throw new IllegalArgumentException("Unable to locate method [" + this.methodName + "] on bean [" + this.targetBeanName + "]");
        }
    }

    public Method getObject() {
        return this.method;
    }


}
