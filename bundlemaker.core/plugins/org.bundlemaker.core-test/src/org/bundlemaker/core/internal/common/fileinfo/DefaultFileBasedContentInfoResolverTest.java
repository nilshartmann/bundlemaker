package org.bundlemaker.core.internal.common.fileinfo;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

public class DefaultFileBasedContentInfoResolverTest {

  @Test
  public void test() throws CoreException {

    //
    File testFile = new File(System.getProperty("user.dir"),
        "test-data/fileinfo/org.eclipse.equinox.p2.artifact.repository.source_1.1.200.v20120430-1959.jar");

    DefaultFileBasedContentInfoResolver resolver = new DefaultFileBasedContentInfoResolver();
    resolver.resolve(testFile);
    System.out.println(resolver.getName());
    System.out.println(resolver.getBinaryName());
    System.out.println(resolver.getVersion());
    System.out.println(resolver.isSource());
  }
}
