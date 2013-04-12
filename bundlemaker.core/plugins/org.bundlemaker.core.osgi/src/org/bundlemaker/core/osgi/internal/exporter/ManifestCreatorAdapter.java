package org.bundlemaker.core.osgi.internal.exporter;

import java.io.IOException;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ITemplateProvider;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.osgi.manifest.DefaultManifestPreferences;
import org.bundlemaker.core.osgi.manifest.IBundleManifestCreator;
import org.bundlemaker.core.osgi.manifest.IManifestPreferences;
import org.bundlemaker.core.osgi.utils.ManifestUtils;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.virgo.bundlor.util.SimpleManifestContents;
import org.eclipse.virgo.util.osgi.manifest.BundleManifestFactory;
import org.eclipse.virgo.util.parser.manifest.ManifestContents;

/**
 */
public class ManifestCreatorAdapter {

  /** the modularized system */
  private IModularizedSystem                  _modularizedSystem;

  /** the current module */
  private IModule                     _module;

  /** - */
  private ManifestContents                    _manifestContents;

  /** - */
  private ManifestContents                    _originalManifestContents;

  /** - */
  private ManifestContents                    _hostManifestContents;

  /** the manifest template contents */
  private ManifestContents                    _manifestTemplateContents;

  /** the current context */
  private IModuleExporterContext              _context;

  /** - */
  private ITemplateProvider<ManifestContents> _templateProvider;

  /** - */
  private IBundleManifestCreator              _manifestCreator;

  /** - */
  private IManifestPreferences                _manifestPreferences;

  /**
   * <p>
   * Creates a new instance of type {@link ManifestCreatorAdapter}.
   * </p>
   * 
   * @param modularizedSystem
   * @param module
   * @param context
   * @param moduleTemplateDirectory
   * @param hostManifestContents
   */
  public ManifestCreatorAdapter(IModularizedSystem modularizedSystem, IModule module,
      IModuleExporterContext context, ITemplateProvider templateProvider, ManifestContents hostManifestContents,
      IBundleManifestCreator bundleManifestCreator, IManifestPreferences manifestPreferences) {

    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(module);
    Assert.isNotNull(context);

    // set attributes
    _modularizedSystem = modularizedSystem;
    _context = context;
    _module = module;

    //
    _templateProvider = templateProvider;

    // get the template manifest
    _manifestTemplateContents = createManifestTemplate();

    _hostManifestContents = hostManifestContents;

    //
    Assert.isNotNull(bundleManifestCreator);

    //
    _manifestPreferences = manifestPreferences != null ? manifestPreferences : new DefaultManifestPreferences(false);

    //
    _manifestCreator = bundleManifestCreator;
  }

  /**
   * <p>
   * Creates a new instance of type {@link ManifestCreatorAdapter}.
   * </p>
   * 
   * @param modularizedSystem
   * @param module
   * @param context
   */
  public ManifestCreatorAdapter(IModularizedSystem modularizedSystem, IModule module,
      IModuleExporterContext context, IBundleManifestCreator bundleManifestCreator) {

    //
    this(modularizedSystem, module, context, null, null, bundleManifestCreator, null);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final boolean isFragment() {
    return _hostManifestContents != null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected ManifestContents getCurrentManifest() {
    Assert.isNotNull(_manifestContents, String.format("No manifest set. The method createManifest(IModularizedSystem, "
        + "IResourceModule, IModuleExporterContext) of class " + "'%s' has not been called yet.", this.getClass()
        .getName()));

    return _manifestContents;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected ManifestContents getOriginalManifest() {

    // the original manifest contents
    if (_originalManifestContents == null) {

      // the existing bundle manifest resource
      IResource existingManifestResource = getModule().getResource("META-INF/MANIFEST.MF", ProjectContentType.BINARY);

      // create default manifest
      if (existingManifestResource == null) {
        _originalManifestContents = ManifestUtils.toManifestContents(BundleManifestFactory.createBundleManifest());
      }

      // the existing bundle manifest
      try {
        _originalManifestContents = ManifestUtils.readManifestContents(existingManifestResource);
      } catch (IOException exception) {
        exception.printStackTrace();
        _originalManifestContents = ManifestUtils.toManifestContents(BundleManifestFactory.createBundleManifest());

      }
    }

    //
    return _originalManifestContents;
  }

  /**
   * <p>
   * Returns the {@link IModularizedSystem}.
   * </p>
   * 
   * @return the {@link IModularizedSystem}
   */
  protected IModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  /**
   * <p>
   * Returns the {@link IResourceModule}.
   * </p>
   * 
   * @return the {@link IResourceModule}
   */
  protected IModule getModule() {
    return _module;
  }

  /**
   * <p>
   * Returns the current {@link IModuleExporterContext}.
   * </p>
   * 
   * @return the current {@link IModuleExporterContext}
   */
  protected IModuleExporterContext getContext() {
    return _context;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final ManifestContents getManifestTemplate() {
    return _manifestTemplateContents;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected ManifestContents createManifestTemplate() {

    //
    ManifestContents templateManifestContents = _templateProvider.getTemplate(getModule(),
        getModularizedSystem(), getContext());

    //
    return templateManifestContents != null ? templateManifestContents : createDefaultManifestTemplate();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected ManifestContents createDefaultManifestTemplate() {
    return new SimpleManifestContents();
  }

  /**
   * {@inheritDoc}
   */
  public ManifestContents createManifest() {

    //
    return _manifestCreator.createManifest(getModularizedSystem(), getModule(), getManifestTemplate(),
        getOriginalManifest(), _manifestPreferences);
  }
}
