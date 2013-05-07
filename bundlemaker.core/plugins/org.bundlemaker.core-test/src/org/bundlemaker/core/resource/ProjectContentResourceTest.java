package org.bundlemaker.core.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.bundlemaker.core.framework.FileUtil;
import org.bundlemaker.core.internal.resource.DefaultProjectContentResource;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProjectContentResourceTest {

  @Test
  public void test() throws IOException {

    File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));

    DefaultProjectContentResource resource = new DefaultProjectContentResource("123", tempFile.getParentFile()
        .getAbsolutePath(), tempFile.getName());

    FileUtil.touch(tempFile, 123456);
    assertThat(resource.getCurrentTimestamp(), is(123456l));
    FileUtil.touch(tempFile, 987654);
    assertThat(resource.getCurrentTimestamp(), is(987654l));
    FileUtil.touch(tempFile, 123456);
    assertThat(resource.getCurrentTimestamp(), is(123456l));
  }


}
