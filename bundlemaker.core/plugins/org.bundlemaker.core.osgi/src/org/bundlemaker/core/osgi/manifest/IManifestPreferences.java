package org.bundlemaker.core.osgi.manifest;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IManifestPreferences {

  /**
   * <p>
   * </p>
   *
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static enum DependencyStyle {
    STRICT_IMPORT_PACKAGE, STRICT_REQUIRE_BUNDLE, PREFER_IMPORT_PACKAGE
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  DependencyStyle getDependencyStyle();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isSourceManifest();

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

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isReexportRequiredBundles();

  /**
   * <p>
   * </p>
   * 
   * @param reexportRequiredBundles
   */
  void setReexportRequiredBundles(boolean reexportRequiredBundles);

  /**
   * <p>
   * </p>
   * 
   * @param sourceManifest
   */
  void setSourceManifest(boolean sourceManifest);
}
