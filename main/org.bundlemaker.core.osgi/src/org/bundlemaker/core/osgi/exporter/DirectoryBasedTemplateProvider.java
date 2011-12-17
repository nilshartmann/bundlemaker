package org.bundlemaker.core.osgi.exporter;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.utils.ManifestUtils;
import org.bundlemaker.core.resource.IReadableResource;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DirectoryBasedTemplateProvider implements ITemplateProvider {

  /** the root directory for all templates */
  private File _templateRootDirectory;

  /**
   * <p>
   * Creates a new instance of type {@link DirectoryBasedTemplateProvider}.
   * </p>
   * 
   * @param templateRootDirectory
   */
  public DirectoryBasedTemplateProvider(File templateRootDirectory) {

    Assert.isNotNull(templateRootDirectory);

    // assert directory
    Assert.isTrue(templateRootDirectory.isDirectory(),
        String.format("Template directory '%s' has to be a directory.", templateRootDirectory.getAbsolutePath()));

    // set
    _templateRootDirectory = templateRootDirectory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ManifestContents getManifestTemplate(IResourceModule module, IModularizedSystem modularizedSystem,
      IModuleExporterContext context) {

    //
    File templateFile = getManifestTemplateFile(module);

    //
    if (templateFile == null) {
      return null;
    }

    //
    ManifestContents templateManifestContents = ManifestUtils.readManifestContents(templateFile);

    //
    return templateManifestContents;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IReadableResource> getAdditionalResources(IResourceModule resourceModule,
      IModularizedSystem currentModularizedSystem, IModuleExporterContext currentContext) {

    // step 1a: get the current root directory
    File rootDirectory = getModuleTemplateDirectory(resourceModule);

    //
    return getAdditionalResources(rootDirectory);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final File getTemplateRootDirectory() {
    return _templateRootDirectory;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private File getManifestTemplateFile(IResourceModule resourceModule) {

    Assert.isNotNull(resourceModule);

    //
    File rootDirectory = null;

    // step 1: does a module template directory exists (e.g.
    // '<root>/x.y.z_1.2.3/' or '<root>/x.y.z/')?
    if (hasModuleTemplateDirectory(resourceModule)) {

      // step 1a: get the current root directory
      rootDirectory = getModuleTemplateDirectory(resourceModule);

      // step 1b: try '<root>/<module-root>/x.y.z_1.2.3.template'
      File templateFile = new File(rootDirectory, resourceModule.getModuleIdentifier().toString() + ".template");

      // step 1c: try '<root>/<module-root>/x.y.z.template'
      if (!templateFile.exists()) {
        templateFile = new File(rootDirectory, resourceModule.getModuleIdentifier().getName() + ".template");
      }

      // step 1d: try '<root>/<module-root>/manifest.template'
      if (!templateFile.exists()) {
        templateFile = new File(rootDirectory, "manifest.template");
      }

      // step 1e: try '<root>/<module-root>/manifest.properties'
      // DON'T USE - JUST FOR BACKWARD COMPATIBILITY
      if (!templateFile.exists()) {
        templateFile = new File(rootDirectory, "manifest.properties");
      }

      //
      return templateFile.exists() ? templateFile : null;
    }

    // step 2: try the root template directory
    else {

      // step 1b: try '<root>/x.y.z_1.2.3.template'
      File templateFile = new File(getTemplateRootDirectory(), resourceModule.getModuleIdentifier().toString()
          + ".template");

      // step 1c: try '<root>/x.y.z.template'
      if (!templateFile.exists()) {
        templateFile = new File(rootDirectory, resourceModule.getModuleIdentifier().getName() + ".template");
      }

      //
      return templateFile.exists() ? templateFile : null;
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final boolean hasModuleTemplateDirectory(IResourceModule resourceModule) {

    Assert.isNotNull(resourceModule);

    return getModuleTemplateDirectory(resourceModule) != null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final File getModuleTemplateDirectory(IResourceModule resourceModule) {

    //
    Assert.isNotNull(resourceModule);

    //
    File result = new File(_templateRootDirectory, resourceModule.getModuleIdentifier().toString());

    if (result.exists()) {
      return result;
    }

    //
    result = new File(_templateRootDirectory, resourceModule.getModuleIdentifier().getName());

    //
    if (result.exists()) {
      return result;
    }

    //
    return null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param templateDirectory
   * @return
   */
  private Set<IReadableResource> getAdditionalResources(File templateDirectory) {

    //
    Set<IReadableResource> result = new HashSet<IReadableResource>();

    if (templateDirectory == null) {
      return result;
    }

    try {
      //
      for (String child : FileUtils.getAllChildren(templateDirectory)) {

        // create the resource standin
        ResourceKey resourceKey = new ResourceKey("ADDITIONAL_CONTENT_DUMMY_ID", templateDirectory.getAbsolutePath(),
            child);

        // add the resource
        result.add(resourceKey);
      }
    } catch (CoreException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // return the result
    return filterAdditionalResources(result);
  }

  /**
   * <p>
   * </p>
   * 
   * @param contentProvider
   * @return
   */
  protected Set<IReadableResource> filterAdditionalResources(Set<IReadableResource> contentProvider) {

    //
    Set<IReadableResource> filteredResult = new HashSet<IReadableResource>();

    //
    for (IReadableResource resourceKey : contentProvider) {
      if (!"manifest.properties".equals(resourceKey.getName())) {
        filteredResult.add(resourceKey);
      }
    }

    //
    return filteredResult;
  }
}
