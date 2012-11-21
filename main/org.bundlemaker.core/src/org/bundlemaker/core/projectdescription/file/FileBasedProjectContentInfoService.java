package org.bundlemaker.core.projectdescription.file;

import java.io.File;
import java.util.List;

import org.bundlemaker.core.internal.projectdescription.file.FileBasedProjectContentInfoServiceImpl;

public interface FileBasedProjectContentInfoService {

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   */
  public <T> FileBasedProjectContentInfo<T> extractJarInfo(File file);

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   */
  public <T> FileBasedProjectContentInfo<T> getAssociatedFileBasedProjectContent(FileBasedProjectContentInfo<T> info,
      List<FileBasedProjectContentInfo<T>> allInfos);

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static class Factory {

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public static FileBasedProjectContentInfoService getInfoService() {
      return new FileBasedProjectContentInfoServiceImpl();
    }
  }
}
