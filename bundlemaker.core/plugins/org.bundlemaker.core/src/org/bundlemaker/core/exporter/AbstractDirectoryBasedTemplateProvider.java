package org.bundlemaker.core.exporter;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.resource.DefaultProjectContentResource;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractDirectoryBasedTemplateProvider<T> implements ITemplateProvider<T> {

  /** the root directory for all templates */
  private File _templateRootDirectory;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractDirectoryBasedTemplateProvider}.
   * </p>
   * 
   * @param templateRootDirectory
   */
  public AbstractDirectoryBasedTemplateProvider(File templateRootDirectory) {

    Assert.isNotNull(templateRootDirectory);

    // assert directory
    Assert.isTrue(templateRootDirectory.isDirectory(),
        String.format("Template directory '%s' has to be a directory.", templateRootDirectory.getAbsolutePath()));

    // set the template directory
    _templateRootDirectory = templateRootDirectory;
  }

  /**
   * <p>
   * </p>
   * 
   * @param templateFile
   * @return
   */
  protected abstract T readTemplateFile(File templateFile) throws Exception;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected abstract String getGenericTemplateFileName();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected abstract String getTemplatePostfix();

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
   * {@inheritDoc}
   */
  @Override
  public T getTemplate(IModule module, IModularizedSystem modularizedSystem,
      IModuleExporterContext context) {

    //
    File templateFile = getTemplateFile(module);

    //
    if (templateFile == null) {
      return null;
    }

    //
    try {
      return readTemplateFile(templateFile);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IResource> getAdditionalResources(IModule resourceModule,
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
  private File getTemplateFile(IModule resourceModule) {

    Assert.isNotNull(resourceModule);

    //
    File rootDirectory = null;

    // step 1: does a module template directory exists (e.g.
    // '<root>/x.y.z_1.2.3/' or '<root>/x.y.z/')?
    if (hasModuleTemplateDirectory(resourceModule)) {

      // step 1a: get the current root directory
      rootDirectory = getModuleTemplateDirectory(resourceModule);

      // step 1b: try '<root>/<module-root>/x.y.z_1.2.3.<postfix>'
      File templateFile = new File(rootDirectory, resourceModule.getModuleIdentifier().toString()
          + getTemplatePostfix());

      // step 1c: try '<root>/<module-root>/x.y.z.<postfix>'
      if (!templateFile.exists()) {
        templateFile = new File(rootDirectory, resourceModule.getModuleIdentifier().getName() + getTemplatePostfix());
      }

      // step 1d: try '<root>/<module-root>/manifest.<postfix>'
      if (!templateFile.exists()) {
        templateFile = new File(rootDirectory, getGenericTemplateFileName());
      }

      //
      return templateFile.exists() ? templateFile : null;
    }

    // step 2: try the root template directory
    else {

      // step 1b: try '<root>/x.y.z_1.2.3.<postfix>'
      File templateFile = new File(getTemplateRootDirectory(), resourceModule.getModuleIdentifier().toString()
          + getTemplatePostfix());

      // step 1c: try '<root>/x.y.z.<postfix>'
      if (!templateFile.exists()) {
        templateFile = new File(rootDirectory, resourceModule.getModuleIdentifier().getName() + getTemplatePostfix());
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
  public final boolean hasModuleTemplateDirectory(IModule resourceModule) {

    Assert.isNotNull(resourceModule);

    return getModuleTemplateDirectory(resourceModule) != null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public File getModuleTemplateDirectory(IModule resourceModule) {

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
  private Set<IResource> getAdditionalResources(File templateDirectory) {

    //
    Set<IResource> result = new HashSet<IResource>();

    if (templateDirectory == null) {
      return result;
    }

    try {
      //
      for (String child : FileUtils.getAllChildren(templateDirectory)) {

        // create the resource standin
        IResource resourceKey = new DefaultProjectContentResource("ADDITIONAL_CONTENT_DUMMY_ID",
            templateDirectory.getAbsolutePath(),
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
  protected Set<IResource> filterAdditionalResources(Set<IResource> contentProvider) {

    //
    Set<IResource> filteredResult = new HashSet<IResource>();

    //
    for (IResource resourceKey : contentProvider) {
      if (!"manifest.properties".equals(resourceKey.getName())) {
        filteredResult.add(resourceKey);
      }
    }

    //
    return filteredResult;
  }
}
