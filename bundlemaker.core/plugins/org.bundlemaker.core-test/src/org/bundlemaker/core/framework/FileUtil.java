package org.bundlemaker.core.framework;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @param timestamp
   */
  public static void touch(File file, long timestamp) {

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
