package org.bundlemaker.core.osgi.manifest;

import org.bundlemaker.core.osgi.internal.manifest.DroolsBasedBundleManifestCreator;
import org.bundlemaker.core.osgi.internal.manifest.ExportPackagePreferences;
import org.bundlemaker.core.osgi.internal.manifest.PackageWiringPreferences;

public class Factory {

  public static IBundleManifestCreator createBundleManifestCreator() {
    return new DroolsBasedBundleManifestCreator();
  }

  public static IExportPackagePreferences createExportPackagePreferences() {
    return new ExportPackagePreferences();
  }

  public static IPackageWiringPreferences createPackageWiringPreferences() {
    return new PackageWiringPreferences();
  }
}
