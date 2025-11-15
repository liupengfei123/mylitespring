package org.mylitespring.beans.factory.annotation;


import java.util.List;

public class InjectionMetadata {

    private final Class<?> targetClass;
    private final List<InjectionElement> injectionElements;

    public InjectionMetadata(Class<?> clz, List<InjectionElement> injectionElements) {
        this.targetClass = clz;
        this.injectionElements = injectionElements;
    }

    public List<InjectionElement> getInjectionElements() {
        return injectionElements;
    }

    public void inject(Object target) {
        if (injectionElements == null || injectionElements.isEmpty()) {
            return;
        }
        for (InjectionElement element : injectionElements) {
            element.inject(target);
        }
    }
}
