package org.mylitespring.beans.factory.support;

import org.mylitespring.beans.factory.config.SingletonBeanRegistry;
import org.mylitespring.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName, "beanName must not be null");

        Object value = singletonObjects.putIfAbsent(beanName, singletonObject);

        if (value != null) {
            throw new IllegalStateException("Cloud not register object [" + singletonObject + "] " +
                    "under bean name '" + beanName + "': there is already object [" + value + "]");
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }
}
