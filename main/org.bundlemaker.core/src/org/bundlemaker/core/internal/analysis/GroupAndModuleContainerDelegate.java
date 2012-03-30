package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
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
  private final AbstractBundleMakerArtifactContainer _groupAndModuleContainer;

  // TODO: remove this shit...
  private ModelTransformer                           _dependencyModel;

  /**
   * <p>
   * Creates a new instance of type {@link GroupAndModuleContainerDelegate}.
   * </p>
   * 
   * @param groupAndModuleContainer
   */
  public GroupAndModuleContainerDelegate(IGroupAndModuleContainer groupAndModuleContainer) {
    Assert.isNotNull(groupAndModuleContainer);
    Assert.isTrue(groupAndModuleContainer instanceof AbstractBundleMakerArtifactContainer);

    _groupAndModuleContainer = (AbstractBundleMakerArtifactContainer) groupAndModuleContainer;
  }

  /**
   * {@inheritDoc}
   */
  public IModuleArtifact getOrCreateModule(String qualifiedModuleName, String moduleVersion) {

    // not null assert
    Assert.isNotNull(qualifiedModuleName);
    Assert.isNotNull(moduleVersion);

    // /G1/m1
    // G1/m1
    // /m1
    // m1

    // step 1: normalize the qualified module name
    qualifiedModuleName = qualifiedModuleName.replace('\\', '/');

    // step 2: check if absolute
    IBundleMakerArtifact rootContainer = _groupAndModuleContainer;

    if (qualifiedModuleName.startsWith("/")) {
      qualifiedModuleName = qualifiedModuleName.substring(1);
      rootContainer = rootContainer.getRoot();
    }

    //
    String moduleName = qualifiedModuleName;
    AbstractBundleMakerArtifactContainer parent = (AbstractBundleMakerArtifactContainer) rootContainer;

    //
    int index = qualifiedModuleName.lastIndexOf('/');

    //
    if (index != -1) {

      // create the group
      parent = (AbstractBundleMakerArtifactContainer) ((IGroupAndModuleContainer) rootContainer)
          .getOrCreateGroup(qualifiedModuleName.substring(0, index));

      moduleName = qualifiedModuleName.substring(index + 1);
    }

    // create the module
    IModuleIdentifier moduleIdentifier = new ModuleIdentifier(moduleName, moduleVersion);
    IModuleArtifact moduleArtifact = (IModuleArtifact) parent.getChild(moduleIdentifier.toString());

    //
    if (moduleArtifact == null) {

      //
      moduleArtifact = getDependencyModel().createModule(moduleIdentifier);

      //
      parent.addArtifact(moduleArtifact);
    }

    //
    return moduleArtifact;
  }

  /**
   * {@inheritDoc}
   */
  public IGroupArtifact getOrCreateGroup(String path) {

    Assert.isNotNull(path);

    System.out.println("getOrCreateGroup " + path);

    // normalize
    path = path.replace('\\', '/');

    // // split
    // String[] segments = path.split("/");

    // add children
    AbstractBundleMakerArtifactContainer currentArtifact = _groupAndModuleContainer;

    IPath iPath = new Path(path);

    for (int i = 0; i < iPath.segmentCount(); i++) {

      // try to get the child
      AbstractBundleMakerArtifactContainer newArtifact = (AbstractBundleMakerArtifactContainer) currentArtifact
          .getChild(iPath.segment(i));

      //
      if (newArtifact == null) {

        // create new
        newArtifact = getDependencyModel().createGroup(
            currentArtifact.getFullPath().removeFirstSegments(1)
                .append(iPath.removeLastSegments(iPath.segmentCount() - (i + 1))));

        // add to parent
        if (newArtifact.getParent() != currentArtifact) {
          currentArtifact.addArtifact(newArtifact);
        }
      }

      currentArtifact = newArtifact;
    }

    return (IGroupArtifact) currentArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private ModelTransformer getDependencyModel() {

    //
    if (_dependencyModel == null) {
      _dependencyModel = ((AdapterModularizedSystem2IArtifact) _groupAndModuleContainer.getRoot()).getDependencyModel();
      Assert.isNotNull(_dependencyModel);
    }

    //
    return _dependencyModel;
  }
}
