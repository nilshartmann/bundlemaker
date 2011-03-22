package org.bundlemaker.core.osgi.internal.manifest;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.osgi.manifest.DependencyStyle;
import org.bundlemaker.core.osgi.manifest.IPackageWiringPreferences;

public class PackageWiringPreferences implements IPackageWiringPreferences {

  @Override
  public Set<String> importsToIgnore() {
    return new HashSet<String>();
  }

  @Override
  public DependencyStyle getDependencyStyle() {
    return DependencyStyle.STRICT_REQUIRE_BUNDLE;
  }

  @Override
  public boolean includeSourceDependencies() {
    return true;
  }

  @Override
  public boolean includeIndirectlyReferencedClasses() {
    return true;
  }

}
