package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifactContainer;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
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

    CreateModuleTransformation transformation = new CreateModuleTransformation(configuration.toJsonTree());

    // normalize the qualified module name
    qualifiedModuleName = qualifiedModuleName.replace('\\', '/');

    // the root container is 'this' GroupAndModuleContainer
    IBundleMakerArtifact rootContainer = _groupAndModuleContainer;

    // if the qualified name starts with '/', it is an absolute path
    if (qualifiedModuleName.startsWith("/")) {
      qualifiedModuleName = qualifiedModuleName.substring(1);
      rootContainer = rootContainer.getRoot();
    }

    //
    String moduleName = qualifiedModuleName;
    AbstractArtifactContainer moduleParent = (AbstractArtifactContainer) rootContainer;

    // if the qualified name contains groups, we have to create the groups first
    int index = moduleName.lastIndexOf('/');
    if (index != -1) {
      // create the group
      moduleParent = (AbstractArtifactContainer) ((IGroupAndModuleContainer) rootContainer)
          .getOrCreateGroup(new Path(qualifiedModuleName.substring(0, index)));
      // set the simple module name
      moduleName = qualifiedModuleName.substring(index + 1);
    }

    // try to get the module...
    IModuleIdentifier moduleIdentifier = new ModuleIdentifier(moduleName, moduleVersion);
    IModuleArtifact moduleArtifact = (IModuleArtifact) moduleParent.getChild(moduleIdentifier.toString());

    // ...and create it if necessary
    if (moduleArtifact == null) {

      // create the module
      IModifiableResourceModule resourceModule = ((IModifiableModularizedSystem) _groupAndModuleContainer.getRoot()
          .getModularizedSystem()).createResourceModule(moduleIdentifier);

      // get the
      IPath classification = moduleParent.getFullPath();
      if (!".".equals(classification.toOSString())) {
        resourceModule.setClassification(classification);
      }

      //
      moduleArtifact = ((AdapterRoot2IArtifact) _groupAndModuleContainer.getRoot()).getArtifactCache()
          .getModuleArtifact(resourceModule);
    }

    //
    _groupAndModuleContainer.getRoot().getModularizedSystem().getTransformations().add(transformation);

    //
    return moduleArtifact;
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
    CreateGroupTransformation.Configuration configuration = new CreateGroupTransformation.Configuration(
        _groupAndModuleContainer, path);

    CreateGroupTransformation transformation = new CreateGroupTransformation(configuration.toJsonTree());

    //
    _groupAndModuleContainer.getRoot().getModularizedSystem().applyTransformations(null,
        transformation);

    //
    return transformation.getGroupArtifact();
  }
}
