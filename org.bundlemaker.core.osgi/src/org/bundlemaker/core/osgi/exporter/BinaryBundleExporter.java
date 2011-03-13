package org.bundlemaker.core.osgi.exporter;

import org.bundlemaker.core.osgi.manifest.BundleManifestCreator;
import org.bundlemaker.core.osgi.manifest.DependencyStyle;
import org.eclipse.core.runtime.CoreException;

import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class BinaryBundleExporter extends AbstractJarFileBundleExporter {

  /** - */
  private DependencyStyle _dependencyStyle = DependencyStyle.PREFER_IMPORT_PACKAGE;

  /**
   * <p>
   * </p>
   * 
   * @param useRequireBundle
   */
  public void setDependencyStyle(DependencyStyle dependencyStyle) {
    _dependencyStyle = dependencyStyle;
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @param module
   * @param context
   * @return
   * @throws Exception
   */
  protected ManifestContents createManifest() throws CoreException {

    // create the manifest
    BundleManifestCreator creator = new BundleManifestCreator(getCurrentModularizedSystem(), getCurrentModule(),
        getCurrentContext(), getCurrentManifestTemplate());

    // set useRequireBundle
    creator.setDependencyStyle(_dependencyStyle);

    // create manifest
    return creator.createManifest();
  }
}
