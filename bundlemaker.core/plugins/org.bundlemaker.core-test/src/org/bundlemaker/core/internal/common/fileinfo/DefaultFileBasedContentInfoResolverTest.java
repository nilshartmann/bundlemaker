package org.bundlemaker.core.internal.common.fileinfo;

import java.io.File;

import org.bundlemaker.core.common.internal.fileinfo.DefaultFileBasedContentInfoResolver;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class DefaultFileBasedContentInfoResolverTest {

  @Test
  public void test() throws CoreException {

    //
    File testFile = new File(System.getProperty("user.dir"),
        "test-data/fileinfo/org.eclipse.equinox.p2.artifact.repository.source_1.1.200.v20120430-1959.jar");

    DefaultFileBasedContentInfoResolver resolver = new DefaultFileBasedContentInfoResolver();
    resolver.resolve(testFile);
    
    Assert.assertEquals("org.eclipse.equinox.p2.artifact.repository.source", resolver.getName());
    Assert.assertEquals("org.eclipse.equinox.p2.artifact.repository", resolver.getBinaryName());
    Assert.assertEquals("1.1.200.v20120430-1959", resolver.getVersion());
    Assert.assertTrue(resolver.isSource());
  }
  
  @Test
  @Ignore
  public void test_2() throws CoreException {

    //
    File testFile = new File(System.getProperty("user.dir"),
        "test-data/fileinfo/test-src.jar");

    DefaultFileBasedContentInfoResolver resolver = new DefaultFileBasedContentInfoResolver();
    resolver.resolve(testFile);
    
    Assert.assertEquals("test-src", resolver.getName());
    Assert.assertEquals("test", resolver.getBinaryName());
    Assert.assertEquals("0.0.0", resolver.getVersion());
    Assert.assertTrue(resolver.isSource());
  }
}
