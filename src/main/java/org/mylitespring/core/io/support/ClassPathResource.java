package org.mylitespring.core.io.support;

import org.mylitespring.core.io.Resource;
import org.mylitespring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {
    private final String path;
    private final ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);

    }
    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader == null ? ClassUtils.getDefaultClassLoader() : classLoader;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = this.classLoader.getResourceAsStream(this.path);

        if (is == null) {
            throw new FileNotFoundException(this.path + " cannot be opened");
        }
        return is;
    }

    public String getDescription() {
        return this.path;
    }
}
