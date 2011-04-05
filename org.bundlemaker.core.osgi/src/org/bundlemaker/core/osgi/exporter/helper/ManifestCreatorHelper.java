package org.bundlemaker.core.osgi.exporter.helper;

import java.io.File;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.internal.manifest.ManifestPreferences;
import org.bundlemaker.core.osgi.manifest.IBundleManifestCreator;
import org.bundlemaker.core.osgi.manifest.IManifestPreferences;
import org.eclipse.core.runtime.Assert;

import com.springsource.bundlor.util.BundleManifestUtils;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 */
public class ManifestCreatorHelper extends AbstractManifestAwareExporterHelper {

  /** - */
  private IBundleManifestCreator _manifestCreator;

  /** - */
  private IManifestPreferences   _manifestPreferences;

  /**
   * <p>
   * Creates a new instance of type {@link ManifestCreatorHelper}.
   * </p>
   * 
   * @param modularizedSystem
   * @param module
   * @param context
   * @param moduleTemplateDirectory
   * @param hostManifestContents
   */
  public ManifestCreatorHelper(IModularizedSystem modularizedSystem, IResourceModule module,
      IModuleExporterContext context, File templateRootDirectory, ManifestContents hostManifestContents,
      IBundleManifestCreator bundleManifestCreator, IManifestPreferences manifestPreferences) {

    super(modularizedSystem, module, context, templateRootDirectory, hostManifestContents);

    //
    Assert.isNotNull(bundleManifestCreator);

    //
    _manifestPreferences = manifestPreferences != null ? manifestPreferences : new ManifestPreferences(false);

    //
    _manifestCreator = bundleManifestCreator;
  }

  /**
   * <p>
   * Creates a new instance of type {@link ManifestCreatorHelper}.
   * </p>
   * 
   * @param modularizedSystem
   * @param module
   * @param context
   */
  public ManifestCreatorHelper(IModularizedSystem modularizedSystem, IResourceModule module,
      IModuleExporterContext context, IBundleManifestCreator bundleManifestCreator) {

    //
    this(modularizedSystem, module, context, null, null, bundleManifestCreator, null);
  }

  /**
   * {@inheritDoc}
   */
  public ManifestContents createManifest() {

    //
    return _manifestCreator.createManifest(getModularizedSystem(), getModule(),
        BundleManifestUtils.createBundleManifest(getManifestTemplate()),
        BundleManifestUtils.createBundleManifest(getOriginalManifest()), _manifestPreferences);
  }
}
