package org.bundlemaker.core.ui.projecteditor.filebased;

import java.io.File;

import org.eclipse.core.resources.IFile;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FileBasedProjectContentHelper {

  /**
   * <p>
   * </p>
   * 
   * @param object
   * @return
   */
  public static boolean isValidArchive(IFile file) {

    //
    if (file == null) {
      return false;
    }

    //
    if (!isValidFileName(file.getName())) {
      return false;
    }

    //
    return true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   */
  public static boolean isValidArchive(File file) {

    //
    if (file == null) {
      return false;
    }

    //
    if (!isValidFileName(file.getName())) {
      return false;
    }

    //
    return true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param name
   * @return
   */
  public static boolean isValidFileName(String name) {
    return name.endsWith(".zip") || name.endsWith(".jar");
  }
}
