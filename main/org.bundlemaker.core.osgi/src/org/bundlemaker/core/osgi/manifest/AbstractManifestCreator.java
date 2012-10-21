package org.bundlemaker.core.osgi.manifest;

import java.util.Map;

import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.utils.ArtifactUtils;
import org.bundlemaker.core.osgi.utils.ManifestUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.virgo.bundlor.util.BundleManifestUtils;
import org.eclipse.virgo.util.osgi.manifest.BundleManifest;
import org.eclipse.virgo.util.osgi.manifest.BundleManifestFactory;
import org.eclipse.virgo.util.parser.manifest.ManifestContents;

/**
 * <p>
 * Abstract base class for all manifest creators.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractManifestCreator implements IBundleManifestCreator {

  /** the constant for the 'MANIFEST_PER_ENTRY_ATTRIBUTES_NAME' */
  private static final String  MANIFEST_PER_ENTRY_ATTRIBUTES_NAME = "Name";

  /** the bundle manifest */
  private BundleManifest       _bundleManifest;

  /** the manifest template */
  private BundleManifest       _manifestTemplate;

  /** the original manifest */
  private BundleManifest       _originalManifest;

  /** the modularized system */
  private IModularizedSystem   _modularizedSystem;

  /** the resource module */
  private IResourceModule      _resourceModule;

  /** the manifest preferences */
  private IManifestPreferences _manifestPreferences;

  /** the root artifact */
  private IRootArtifact        _rootArtifact;

  /** the module artifact */
  private IModuleArtifact      _moduleArtifact;

  /**
   * {@inheritDoc}
   */
  @Override
  public final ManifestContents createManifest(final IModularizedSystem modularizedSystem,
      final IResourceModule resourceModule, final ManifestContents manifestTemplate,
      final ManifestContents originalManifest, final IManifestPreferences manifestPreferences) {

    // assert not null
    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(resourceModule);
    Assert.isNotNull(manifestTemplate);
    Assert.isNotNull(originalManifest);
    Assert.isNotNull(manifestPreferences);

    System.out.println("Creating manifest for '" + resourceModule.getModuleIdentifier() + "'");

    // set the values
    _modularizedSystem = modularizedSystem;
    _resourceModule = resourceModule;
    _originalManifest = BundleManifestUtils.createBundleManifest(originalManifest);
    _manifestTemplate = BundleManifestUtils.createBundleManifest(manifestTemplate);
    _manifestPreferences = manifestPreferences;

    //
    // TODO: make Configurable
    _rootArtifact = modularizedSystem.getArtifactModel(IAnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

    //
    _moduleArtifact = ArtifactUtils.getAssociatedModuleArtifact(_rootArtifact, resourceModule);

    // create a new bundle manifest
    _bundleManifest = BundleManifestFactory.createBundleManifest();

    // populate the manifest
    onCreateManifest();

    // copy all 'per-entry' attributes from the manifest template to the final manifest
    ManifestContents manifestContents = ManifestUtils.toManifestContents(_bundleManifest);

    // copy all 'per-entry' attributes from the template
    for (String sectionName : manifestTemplate.getSectionNames()) {

      // get the section attributes
      Map<String, String> sectionAttributes = manifestTemplate.getAttributesForSection(sectionName);

      // copy the section attributes
      for (String key : sectionAttributes.keySet()) {
        if (!key.equalsIgnoreCase(MANIFEST_PER_ENTRY_ATTRIBUTES_NAME)) {
          manifestContents.getAttributesForSection(sectionName).put(key, sectionAttributes.get(key));
        }
      }
    }

    // get the new manifest contents
    return manifestContents;
  }

  /**
   * <p>
   * Returns the {@link BundleManifest}.
   * </p>
   * 
   * @return the {@link BundleManifest}.
   */
  protected final BundleManifest getBundleManifest() {
    return _bundleManifest;
  }

  /**
   * <p>
   * Returns the {@link IModularizedSystem}.
   * </p>
   * 
   * @return the {@link IModularizedSystem}.
   */
  protected final IModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  /**
   * <p>
   * Returns the {@link IResourceModule}.
   * </p>
   * 
   * @return the {@link IResourceModule}.
   */
  protected final IResourceModule getResourceModule() {
    return _resourceModule;
  }

  /**
   * <p>
   * Returns the {@link BundleManifest} for the manifest template.
   * </p>
   * 
   * @return the {@link BundleManifest} for the manifest template.
   */
  protected final BundleManifest getManifestTemplate() {
    return _manifestTemplate;
  }

  /**
   * <p>
   * Returns the {@link BundleManifest} for the original manifest.
   * </p>
   * 
   * @return the {@link BundleManifest} for the original manifest.
   */
  protected final BundleManifest getOriginalManifest() {
    return _originalManifest;
  }

  /**
   * <p>
   * Returns the {@link IManifestPreferences}.
   * </p>
   * 
   * @return the {@link IManifestPreferences}.
   */
  protected final IManifestPreferences getManifestPreferences() {
    return _manifestPreferences;
  }

  /**
   * <p>
   * Returns the {@link IRootArtifact}.
   * </p>
   * 
   * @return the {@link IRootArtifact}.
   */
  protected final IRootArtifact getRootArtifact() {
    return _rootArtifact;
  }

  /**
   * <p>
   * Returns the {@link IModuleArtifact}.
   * </p>
   * 
   * @return the {@link IModuleArtifact}.
   */
  protected final IModuleArtifact getModuleArtifact() {
    return _moduleArtifact;
  }

  /**
   * <p>
   * </p>
   */
  protected abstract void onCreateManifest();
}
