package org.bundlemaker.core.osgi.exporter;

import org.bundlemaker.core.osgi.internal.manifest.DroolsBasedBundleManifestCreator;
import org.bundlemaker.core.osgi.internal.manifest.ManifestPreferences;
import org.bundlemaker.core.osgi.manifest.IBundleManifestCreator;
import org.bundlemaker.core.osgi.manifest.IManifestPreferences;
import org.eclipse.core.runtime.CoreException;

import com.springsource.bundlor.util.BundleManifestUtils;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 */
public abstract class AbstractBundleManifestCreatorExporter extends AbstractManifestAwareExporter {

  /** - */
  private IBundleManifestCreator _manifestCreator;

  /**
   * 
   */
  public AbstractBundleManifestCreatorExporter() {
    _manifestCreator = createBundleManifestCreator();
  }

  /**
   * {@inheritDoc}
   */
  protected ManifestContents createManifest() throws CoreException {
    return _manifestCreator.createManifest(getCurrentModularizedSystem(), getCurrentModule(),
        BundleManifestUtils.createBundleManifest(getCurrentManifestTemplate()),
        BundleManifestUtils.createBundleManifest(getOriginalManifest()), createManifestPreferences());
  }

  /**
   * @return
   * @throws CoreException
   */
  protected IBundleManifestCreator createBundleManifestCreator() {
    return new DroolsBasedBundleManifestCreator();
  }

  /**
   * @return
   */
  protected IManifestPreferences createManifestPreferences() {
    return new ManifestPreferences(false);
  }
}
