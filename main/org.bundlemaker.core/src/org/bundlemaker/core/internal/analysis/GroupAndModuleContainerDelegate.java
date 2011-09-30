package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.analysis.model.ArtifactType;
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
public class GroupAndModuleContainerDelegate implements IGroupAndModuleContainer {

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
    Assert.isTrue(groupAndModuleContainer instanceof IBundleMakerArtifact);

    _groupAndModuleContainer = groupAndModuleContainer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
    IBundleMakerArtifact rootContainer = getAdvancedArtifact();
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
      moduleArtifact = (IModuleArtifact) getAdvancedArtifact().getDependencyModel().createArtifactContainer(
          moduleIdentifier.toString(), moduleIdentifier.toString(), ArtifactType.Module);

      // TEST
      rootContainer.getRoot().getChild("groupTest1");

      //
      parent.addArtifact(moduleArtifact);
    }

    // TEST
    rootContainer.getRoot().getChild("groupTest1");

    //
    return moduleArtifact;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IGroupArtifact getOrCreateGroup(String path) {

    Assert.isNotNull(path);

    System.out.println("getOrCreateGroup " + path);

    // normalize
    path = path.replace('\\', '/');

    // // split
    // String[] segments = path.split("/");

    // add children
    AbstractBundleMakerArtifactContainer currentArtifact = getAdvancedArtifact();

    IPath iPath = new Path(path);

    for (int i = 0; i < iPath.segmentCount(); i++) {

      // try to get the child
      AbstractBundleMakerArtifactContainer newArtifact = (AbstractBundleMakerArtifactContainer) currentArtifact.getChild(iPath.segment(i));

      //
      if (newArtifact == null) {

        // create new
        newArtifact = (AbstractBundleMakerArtifactContainer) getAdvancedArtifact().getDependencyModel().createArtifactContainer(
            iPath.segment(i), iPath.removeLastSegments(iPath.segmentCount() - (i + 1)).toString(), ArtifactType.Group);

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
  public AbstractBundleMakerArtifactContainer getAdvancedArtifact() {
    return (AbstractBundleMakerArtifactContainer) _groupAndModuleContainer;
  }
}
