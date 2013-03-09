package org.bundlemaker.core.osgi.manifest;

import java.util.List;

import org.bundlemaker.core.analysis.IModuleArtifact;

public class CustomManifestCreator extends DefaultManifestCreator {

  /**
   * Packages und Sub-Packages, die <b>nicht</b> per Import-Package oder Require-Bunde im Manifest importiert werden
   * 
   * @see #skipReferencedPackage(String, List)
   **/
  private final static String[] BOOTCLASSPATH_PACKAGES_INCLUDE = new String[] { //
                                                               "sun.", "com.sun." //
                                                               };

  private final static String[] BOOTCLASSPATH_PACKAGES_EXCLUDE = new String[] { //
                                                               "com.sun.jdmk", //
      "com.sun.jdmk.comm", //
      "com.sun.jdmk.defaults", //
      "com.sun.jdmk.trace", //
                                                               };
  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean useImportPackage(String packageName, List<IModuleArtifact> exportingModules) {
    return true;
  }

  @Override
  protected boolean skipReferencedPackage(String packageName, List<IModuleArtifact> exportingModules) {
    for (String includedBootPackage : BOOTCLASSPATH_PACKAGES_INCLUDE) {
      if ((packageName + ".").startsWith(includedBootPackage)) {
        System.out.printf("Potential package from boot classpath '%s' used in '%s'%n", packageName, getResourceModule()
            .getModuleIdentifier());
        for (String excludedBootPackage : BOOTCLASSPATH_PACKAGES_EXCLUDE) {
          if (packageName.equals(excludedBootPackage)) {
            System.out.printf("  Excluded package from boot classpath '%s' used in '%s'%n", packageName,
                getResourceModule().getModuleIdentifier());
            return false;
          }
        }
        System.out.printf("  Skipping package from boot classpath '%s' used in '%s'%n", packageName,
            getResourceModule().getModuleIdentifier());
        return true;
      }
    }
    return false;
  }
}
