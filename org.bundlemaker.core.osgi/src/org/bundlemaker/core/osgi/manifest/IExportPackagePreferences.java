package org.bundlemaker.core.osgi.manifest;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IExportPackagePreferences {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean noUses();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean noExportPackageVersion();
}