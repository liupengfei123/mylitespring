package org.mylitespring.core.io;

import org.junit.Assert;
import org.junit.Test;
import org.mylitespring.core.io.support.ClassPathResource;
import org.mylitespring.core.io.support.FileSystemResource;

import java.io.File;
import java.io.InputStream;

public class ResourceTest {

    @Test
    public void testClassPathResource() throws Exception {
        Resource r = new ClassPathResource("petstore-v1.xml");
        try (InputStream is = r.getInputStream()){
            Assert.assertNotNull(is);
        }
    }

    @Test
    public void testFileSystemResource() throws Exception {
        String path = new File("./").getAbsolutePath() + "\\src\\test\\resources\\petstore-v1.xml";
		Resource r = new FileSystemResource(path);
		try (InputStream is = r.getInputStream()) {
			// 注意：这个测试其实并不充分！！
			Assert.assertNotNull(is);
		}
    }
}
