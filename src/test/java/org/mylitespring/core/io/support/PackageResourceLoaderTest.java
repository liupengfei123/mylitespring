package org.mylitespring.core.io.support;

import org.junit.Assert;
import org.junit.Test;
import org.mylitespring.core.io.Resource;

import java.io.IOException;

public class PackageResourceLoaderTest {
    @Test
    public void testGetResources() throws IOException {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("com.test.mylitespring");
        Assert.assertEquals(8, resources.length);
    }
}
