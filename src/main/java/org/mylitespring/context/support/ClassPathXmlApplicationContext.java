package org.mylitespring.context.support;

import org.mylitespring.core.io.Resource;
import org.mylitespring.core.io.support.ClassPathResource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String path) {
        super(path);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path, getBeanClassLoader());
    }
}
