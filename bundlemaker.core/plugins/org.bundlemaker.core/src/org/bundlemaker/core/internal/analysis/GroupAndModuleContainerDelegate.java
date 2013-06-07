package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.AnalysisModelException;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifactContainer;
import org.bundlemaker.core.internal.api.resource.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.transformation.CreateGroupTransformation;
import org.bundlemaker.core.internal.transformation.CreateModuleTransformation;
import org.bundlemaker.core.resource.IModule;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class GroupAndModuleContainerDelegate /** implements IGroupAndModuleContainer **/
{

  /** - */
  private final IGroupAndModuleContainer _groupAndModuleContainer;

  /**
   * <p>
   * Creates a new instance of type {@link GroupAndModuleContainerDelegate}.
   * </p>
   * 
   * @param groupAndModuleContainer
   */
  public GroupAndModuleContainerDelegate(IGroupAndModuleContainer groupAndModuleContainer) {
    Assert.isNotNull(groupAndModuleContainer);
    Assert.isTrue(groupAndModuleContainer instanceof AbstractArtifactContainer);

    _groupAndModuleContainer = groupAndModuleContainer;
  }

  /**
   * {@inheritDoc}
   */
  public IModuleArtifact getOrCreateModule(String qualifiedModuleName, String moduleVersion) {

    // asserts
    Assert.isNotNull(qualifiedModuleName);
    Assert.isNotNull(moduleVersion);

    //
    IBundleMakerArtifact rootArtifact = qualifiedModuleName.startsWith("/") ? _groupAndModuleContainer.getRoot()
        : _groupAndModuleContainer;

    //
    IModuleArtifact moduleArtifact = AnalysisModelQueries.getChildByPath(rootArtifact, new Path(
        qualifiedModuleName + "_" + moduleVersion),
        IModuleArtifact.class);

    // does it already exist?
    if (moduleArtifact != null) {
      return moduleArtifact;
    }

    // if not, we have to create it...
    else {

      // check if module already exists
      IPath simpleName = new Path(qualifiedModuleName);
      IModule resourceModule = _groupAndModuleContainer.getModularizedSystem().getModule(
          simpleName.lastSegment(), moduleVersion);

      //
      if (resourceModule != null) {

        //
        String classification = resourceModule.hasClassification() ? resourceModule.getClassification()
            .toPortableString() : "";

        //
        throw new AnalysisModelException(String.format(
            "Cannot create new Module '%s_%s' in group '%s'. Module already exists within a different group ('%s').",
            qualifiedModuleName, moduleVersion, this._groupAndModuleContainer.getFullPath(), classification));
      }

      // create the transformation
      CreateModuleTransformation.Configuration configuration = new CreateModuleTransformation.Configuration(
          _groupAndModuleContainer,
          qualifiedModuleName, moduleVersion);

      //
      CreateModuleTransformation transformation = new CreateModuleTransformation(configuration.toJsonTree());

      //
      ((IModifiableModularizedSystem) _groupAndModuleContainer.getModularizedSystem()).applyTransformations(null,
          transformation);

      //
      return transformation.getModuleArtifact();
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @return
   */
  public IGroupArtifact getOrCreateGroup(String path) {
    Assert.isNotNull(path);

    //
    return getOrCreateGroup(new Path(path));
  }

  /**
   * {@inheritDoc}
   */
  public IGroupArtifact getOrCreateGroup(IPath path) {
    Assert.isNotNull(path);

    //
    IGroupArtifact groupArtifact = AnalysisModelQueries.getChildByPath(_groupAndModuleContainer, path,
        IGroupArtifact.class);

    // does it already exist?
    if (groupArtifact != null) {
      return groupArtifact;
    }

    // if not, we have to create it...
    else {

      //
      CreateGroupTransformation transformation = new CreateGroupTransformation(_groupAndModuleContainer, path);

      //
      ((IModifiableModularizedSystem) _groupAndModuleContainer.getRoot().getModularizedSystem()).applyTransformations(
          null,
          transformation);

      //
      return transformation.getGroupArtifact();
    }
  }
}
