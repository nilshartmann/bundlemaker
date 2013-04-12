package org.bundlemaker.core.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceKeyTest {

  @Test
  public void test() {

    File testDir = new File(System.getProperty("user.dir"), "test-data/com.example/classes");
    assertTrue(testDir.isDirectory());

    File testFile = new File(testDir, "com/ClassInCom.class");
    assertTrue(testFile.isFile());

    ResourceKey resourceKey = new ResourceKey("123", testDir.getAbsolutePath(), "com/ClassInCom.class");

    assertThat(resourceKey.getRoot(), is(testDir.getAbsolutePath().replace('\\', '/')));
    assertThat(resourceKey.getDirectory(), is("com"));

    // TODO
    // touch(testFile, 123456);
    // assertThat(resourceKey.getTimestamp(), is(123456l));
    // touch(testFile, 987654);
    // assertThat(resourceKey.getTimestamp(), is(987654l));
    // touch(testFile, 123456);
    // assertThat(resourceKey.getTimestamp(), is(123456l));
  }

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @param timestamp
   */
  private static void touch(File file, long timestamp) {

    //
    try {

      //
      if (!file.exists()) {
        new FileOutputStream(file).close();
      }
      file.setLastModified(timestamp);
    } catch (IOException exception) {
      //
    }
  }
}
