package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.eclipse.core.runtime.Assert;

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
    Assert.isTrue(groupAndModuleContainer instanceof IAdvancedArtifact);

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

    // normalize
    qualifiedModuleName = qualifiedModuleName.replace('\\', '/');

    // check if absolute
    IAdvancedArtifact rootContainer = getAdvancedArtifact();
    if (qualifiedModuleName.startsWith("/")) {
      qualifiedModuleName = qualifiedModuleName.substring(1);
      rootContainer = rootContainer.getRoot();
    }

    //
    String moduleName = qualifiedModuleName;
    IAdvancedArtifact parent = rootContainer;

    //
    int index = qualifiedModuleName.lastIndexOf('/');

    //
    if (index != -1) {
      // create the group
      parent = ((IGroupAndModuleContainer) rootContainer).getOrCreateGroup(qualifiedModuleName.substring(0, index));
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

      //
      parent.addArtifact(moduleArtifact);
    }

    //
    return moduleArtifact;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IGroupArtifact getOrCreateGroup(String path) {

    Assert.isNotNull(path);

    // normalize
    path = path.replace('\\', '/');

    // split
    String[] segments = path.split("/");

    // add children
    IAdvancedArtifact currentArtifact = getAdvancedArtifact();

    //
    for (String segment : segments) {

      // try to get the child
      IAdvancedArtifact newArtifact = (IAdvancedArtifact) currentArtifact.getChild(segment);

      if (newArtifact == null) {

        // create new
        newArtifact = (IAdvancedArtifact) getAdvancedArtifact().getDependencyModel().createArtifactContainer(segment,
            "", ArtifactType.Group);

        // add to parent
        currentArtifact.addArtifact(newArtifact);
      }

      currentArtifact = newArtifact;
    }

    return (IGroupArtifact) currentArtifact;
  }

  public IAdvancedArtifact getAdvancedArtifact() {
    return (IAdvancedArtifact) _groupAndModuleContainer;
  }
}
