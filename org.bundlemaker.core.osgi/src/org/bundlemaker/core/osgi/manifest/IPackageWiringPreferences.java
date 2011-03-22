package org.bundlemaker.core.osgi.manifest;

import java.util.Set;

public interface IPackageWiringPreferences {

  /**
   * <p>
   * E.g. boot delegated packages.
   * </p>
   * 
   * @return
   */
  Set<String> importsToIgnore();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  DependencyStyle getDependencyStyle();

  boolean includeSourceDependencies();

  boolean includeIndirectlyReferencedClasses();
}
