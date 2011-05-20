package org.bundlemaker.core.osgi.manifest;

import java.util.Set;

public interface IManifestPreferences {

  /**
   * <p>
   * E.g. boot delegated packages.
   * </p>
   * 
   * @return
   */
  @Deprecated
  Set<String> importsToIgnore();

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
  boolean isConsiderIndirectReferences();

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
   * @param considerIndirectReferences
   */
  void setConsiderIndirectReferences(boolean considerIndirectReferences);

  /**
   * <p>
   * </p>
   * 
   * @param sourceManifest
   */
  void setSourceManifest(boolean sourceManifest);
}
