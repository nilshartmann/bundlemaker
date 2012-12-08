package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifactContainer;
import org.bundlemaker.core.transformation.CreateGroupTransformation;
import org.bundlemaker.core.transformation.CreateModuleTransformation;
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

    // create the transformation
    CreateModuleTransformation.Configuration configuration = new CreateModuleTransformation.Configuration(
        _groupAndModuleContainer,
        qualifiedModuleName, moduleVersion);

    //
    CreateModuleTransformation transformation = new CreateModuleTransformation(configuration.toJsonTree());

    //
    _groupAndModuleContainer.getModularizedSystem().applyTransformations(null, transformation);

    //
    return transformation.getModuleArtifact();
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
    CreateGroupTransformation transformation = new CreateGroupTransformation(_groupAndModuleContainer, path);

    //
    _groupAndModuleContainer.getRoot().getModularizedSystem().applyTransformations(null,
        transformation);

    //
    return transformation.getGroupArtifact();
  }
}
