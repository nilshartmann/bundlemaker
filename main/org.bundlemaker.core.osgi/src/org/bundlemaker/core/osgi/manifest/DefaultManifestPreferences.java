package org.bundlemaker.core.osgi.manifest;

import org.eclipse.core.runtime.Assert;

public class DefaultManifestPreferences implements IManifestPreferences {

  /** - */
  private boolean         _sourceManifest;

  /** - */
  private boolean         _reexportRequiredBundles;

  /** - */
  private DependencyStyle _dependencyStyle = DependencyStyle.PREFER_IMPORT_PACKAGE;

  /**
   * <p>
   * Creates a new instance of type {@link DefaultManifestPreferences}.
   * </p>
   * 
   * @param sourceManifest
   */
  public DefaultManifestPreferences(boolean sourceManifest) {

    //
    _sourceManifest = sourceManifest;
  }

  /**
   * <p>
   * Creates a new instance of type {@link DefaultManifestPreferences}.
   * </p>
   * 
   * @param sourceManifest
   * @param style
   */
  public DefaultManifestPreferences(boolean sourceManifest, DependencyStyle style) {

    Assert.isNotNull(style);

    //
    _sourceManifest = sourceManifest;
    _dependencyStyle = style;
  }

  // @Override
  // public Set<String> importsToIgnore() {
  // return new HashSet<String>();
  // }

  @Override
  public DependencyStyle getDependencyStyle() {
    return _dependencyStyle;
  }

  @Override
  public boolean noUses() {
    return true;
  }

  @Override
  public boolean noExportPackageVersion() {
    return true;
  }

  @Override
  public boolean isSourceManifest() {
    return _sourceManifest;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isReexportRequiredBundles() {
    return _reexportRequiredBundles;
  }

  /**
   * <p>
   * </p>
   * 
   * @param dependencyStyle
   */
  public void setDependencyStyle(DependencyStyle dependencyStyle) {
    Assert.isNotNull(dependencyStyle);

    _dependencyStyle = dependencyStyle;
  }

  /**
   * {@inheritDoc}
   */
  public void setReexportRequiredBundles(boolean reexportRequiredBundles) {
    _reexportRequiredBundles = reexportRequiredBundles;
  }

  /**
   * {@inheritDoc}
   */
  public void setSourceManifest(boolean sourceManifest) {
    _sourceManifest = sourceManifest;
  }
}
