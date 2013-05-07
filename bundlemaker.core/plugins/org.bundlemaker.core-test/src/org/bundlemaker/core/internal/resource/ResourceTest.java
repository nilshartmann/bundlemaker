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

    FileUtil.touch(tempFile, 12345678);
    assertThat(resource.getCurrentTimestamp(), is(12345678l));

    resource.storeCurrentTimestamp();

    FileUtil.touch(tempFile, 987654321);
    assertThat(resource.getCurrentTimestamp(), is(987654321l));
    assertThat(resource.getParsedTimestamp(), is(12345678l));
  }
}
