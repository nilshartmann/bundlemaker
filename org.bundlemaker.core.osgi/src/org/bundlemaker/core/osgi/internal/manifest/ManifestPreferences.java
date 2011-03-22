package org.bundlemaker.core.osgi.internal.manifest;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.osgi.manifest.DependencyStyle;
import org.bundlemaker.core.osgi.manifest.IManifestPreferences;

public class ManifestPreferences implements IManifestPreferences {

  private boolean _sourceManifest;
  
  public ManifestPreferences(boolean sourceManifest) {
    _sourceManifest = sourceManifest;
  }

  @Override
  public Set<String> importsToIgnore() {
    return new HashSet<String>();
  }

  @Override
  public DependencyStyle getDependencyStyle() {
    return DependencyStyle.STRICT_REQUIRE_BUNDLE;
  }

  @Override
  public boolean noUses() {
    return true;
  }

  @Override
  public boolean noExportPackageVersion() {
    return false;
  }

  @Override
  public boolean isSourceManifest() {
    return _sourceManifest;
  }
}
