package org.mylitespring.beans;

import java.util.List;

public interface BeanDefinition {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    String SCOPE_DEFAULT = "";

    String getID();

    boolean isSingleton();
    boolean isPrototype();
    String getScope();
    void setScope(String scope);

    String getBeanClassName();

    Class<?> resolveBeanClass(ClassLoader classLoader)  throws ClassNotFoundException;

    Class<?> getBeanClass() throws IllegalStateException;

    boolean hasBeanClass();

    List<PropertyValue> getPropertyValues();

    ConstructorArgument getConstructorArgument();

    boolean hasConstructorArgument();

}
