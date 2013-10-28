package org.bundlemaker.core.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.bundlemaker.core.common.DefaultResource;
import org.bundlemaker.core.framework.FileUtil;
import org.bundlemaker.core.project.internal.DefaultProjectContentResource;
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

    DefaultResource resource = new DefaultResource(tempFile.getParentFile()
        .getAbsolutePath(), tempFile.getName());

    // FileUtil.touch(tempFile, 1234);
    // assertThat(resource.getCurrentTimestamp(), is(1234l));
    // FileUtil.touch(tempFile, 9876);
    // assertThat(resource.getCurrentTimestamp(), is(9876l));
    // FileUtil.touch(tempFile, 1234);
    // assertThat(resource.getCurrentTimestamp(), is(1234l));
  }
}
