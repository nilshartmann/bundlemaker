package org.bundlemaker.core.internal.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.bundlemaker.core.framework.FileUtil;
import org.junit.Test;

public class ResourceTest {

  @Test
  public void test() throws IOException {

    File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));

    Resource resource = new Resource("123", tempFile.getParentFile().getAbsolutePath(), tempFile.getName());

    FileUtil.touch(tempFile, 1234);
    assertThat(resource.getCurrentTimestamp(), is(1234l));

    resource.storeCurrentTimestamp();

    FileUtil.touch(tempFile, 9876);
    assertThat(resource.getCurrentTimestamp(), is(9876l));
    assertThat(resource.getParsedTimestamp(), is(1234l));
  }
}
