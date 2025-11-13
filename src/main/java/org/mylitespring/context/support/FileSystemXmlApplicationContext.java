package org.mylitespring.context.support;

import org.mylitespring.core.io.Resource;
import org.mylitespring.core.io.support.FileSystemResource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

    public FileSystemXmlApplicationContext(String path) {
        super(path);
    }


    @Override
    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }
}
