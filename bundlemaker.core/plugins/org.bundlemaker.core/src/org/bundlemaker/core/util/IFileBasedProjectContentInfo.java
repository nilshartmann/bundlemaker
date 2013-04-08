package org.bundlemaker.core.util;

import java.io.File;

import org.bundlemaker.core.internal.util.fileinfo.FileBasedProjectContentInfoServiceImpl;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IFileBasedProjectContentInfo {

  /**
   * <p>
   * Returns the (guessed) name of a file based project content entry.
   * </p>
   * 
   * @return the (guessed) name of a file based project content entry.
   */
  public String getName();

  /**
   * <p>
   * Returns the (guessed) version of a file based project content entry.
   * </p>
   * 
   * @return the (guessed) version of a file based project content entry.
   */
  public String getVersion();

  /**
   * <p>
   * Returns <code>true</code> if this project content entry is a (guessed) source entry, <code>false</code> otherwise.
   * </p>
   * 
   * @return <code>true</code> if this project content entry is a (guessed) source entry, <code>false</code> otherwise.
   */
  public boolean isSource();

  /**
   * <p>
   * If this project content entry is a (guessed) source entry, this method returns the (guessed) name of the associated
   * binary file.
   * </p>
   * 
   * 
   * @return the binaryName
   */
  public String getBinaryName();

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   * 
   */
  public static class Factory {

    /**
     * <p>
     * </p>
     * 
     * @param file
     * @return
     */
    public static IFileBasedProjectContentInfo extractFileBasedProjectContentInfo(File file) {
      return new FileBasedProjectContentInfoServiceImpl().extractJarInfo(file);
    }
  }
}
