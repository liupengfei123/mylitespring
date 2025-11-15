package org.mylitespring.core.io.support;

import org.mylitespring.core.io.Resource;
import org.mylitespring.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FileSystemResource implements Resource {

    private final String path;
    private final File file;

    public FileSystemResource(File file) {
        Assert.notNull(file, "file must not be null");
        this.path = file.getPath();
        this.file = file;
    }

    public FileSystemResource(String path) {
        Assert.notNull(path, "Path must not be null");
        this.path = path;
        this.file = new File(path);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return Files.newInputStream(file.toPath());
    }

    @Override
    public String getDescription() {
        return "File [" + this.file.getAbsolutePath() + "]";
    }
}
