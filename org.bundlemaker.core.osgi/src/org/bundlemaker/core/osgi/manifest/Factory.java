package org.bundlemaker.core.osgi.manifest;

import org.bundlemaker.core.osgi.internal.manifest.DroolsBasedBundleManifestCreator;
import org.bundlemaker.core.osgi.internal.manifest.ManifestPreferences;

public class Factory {

  public static IBundleManifestCreator createBundleManifestCreator() {
    return new DroolsBasedBundleManifestCreator();
  }

  public static IManifestPreferences createManifestPreferences(boolean sourceManifest) {
    return new ManifestPreferences(sourceManifest);
  }
}
