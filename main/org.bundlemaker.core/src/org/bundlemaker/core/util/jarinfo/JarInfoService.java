package org.bundlemaker.core.util.jarinfo;

import java.io.File;

public interface JarInfoService {

  public JarInfo extractJarInfo(File file);

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static class Factory {

    public static JarInfoService getJarInfoService() {
      return new JarInfoServiceImpl();
    }
  }
}
