package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
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
    Assert.isTrue(groupAndModuleContainer instanceof AbstractBundleMakerArtifactContainer);

    _groupAndModuleContainer = groupAndModuleContainer;
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
          .getOrCreateGroup(new Path(qualifiedModuleName.substring(0, index)));

      moduleName = qualifiedModuleName.substring(index + 1);
    }

    // create the module
    IModuleIdentifier moduleIdentifier = new ModuleIdentifier(moduleName, moduleVersion);
    IModuleArtifact moduleArtifact = (IModuleArtifact) parent.getChild(moduleIdentifier.toString());

    //
    if (moduleArtifact == null) {

      //
      IModifiableResourceModule resourceModule = ((IModifiableModularizedSystem) _groupAndModuleContainer.getRoot()
          .getModularizedSystem()).createResourceModule(moduleIdentifier);

      resourceModule.setClassification(this._groupAndModuleContainer.getFullPath().removeFirstSegments(1));

      //
      moduleArtifact = ((AdapterRoot2IArtifact) _groupAndModuleContainer.getRoot()).getArtifactCache()
          .getModuleArtifact(resourceModule);
    }

    //
    return moduleArtifact;
  }

  /**
   * {@inheritDoc}
   */
  public IGroupArtifact getOrCreateGroup(IPath path) {

    Assert.isNotNull(path);

    System.out.println("getOrCreateGroup " + path);

    // normalize
    // // split
    // String[] segments = path.split("/");

    // add children
    IGroupAndModuleContainer currentArtifact = _groupAndModuleContainer;

    for (int i = 0; i < path.segmentCount(); i++) {

      // try to get the child
      IGroupAndModuleContainer newArtifact = (IGroupAndModuleContainer) currentArtifact.getChild(path.segment(i));

      //
      if (newArtifact == null) {

        // create new
        newArtifact = ((AdapterRoot2IArtifact) _groupAndModuleContainer.getRoot())
            .getArtifactCache()
            .getGroupCache()
            .getOrCreate(
                currentArtifact.getFullPath().removeFirstSegments(1)
                    .append(path.removeLastSegments(path.segmentCount() - (i + 1))));
      }

      currentArtifact = newArtifact;
    }

    return (IGroupArtifact) currentArtifact;
  }
}
