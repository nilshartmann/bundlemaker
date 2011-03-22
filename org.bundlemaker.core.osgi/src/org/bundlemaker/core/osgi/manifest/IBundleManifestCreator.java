package org.bundlemaker.core.osgi.manifest;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;

import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IBundleManifestCreator {

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @param resourceModule
   * @param manifestTemplate
   * @param exportPackagePreferences
   * @param packageWiringPreferences
   * @return
   */
  public ManifestContents createManifest(IModularizedSystem modularizedSystem, IResourceModule resourceModule,
      BundleManifest manifestTemplate, BundleManifest originalManifest, IManifestPreferences manifestPreferences);
}
