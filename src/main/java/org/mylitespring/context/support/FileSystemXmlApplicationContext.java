package org.mylitespring.context.support;

import org.mylitespring.beans.factory.support.DefaultBeanFactory;
import org.mylitespring.core.io.Resource;
import org.mylitespring.core.io.support.FileSystemResource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext {
    private DefaultBeanFactory beanFactory;

    public FileSystemXmlApplicationContext(String path) {
        super(path);
    }


    @Override
    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }
}
