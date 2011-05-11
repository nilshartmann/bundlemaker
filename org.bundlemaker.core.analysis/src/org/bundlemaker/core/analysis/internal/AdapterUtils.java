package org.bundlemaker.core.analysis.internal;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Path;

public class AdapterUtils {

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  public static void addResourceModuleToModularizedSystem(IArtifact artifact) {

    //
    IModifiableModularizedSystem modularizedSystem = AdapterUtils.getModularizedSystem(artifact);

    //
    for (IArtifact moduleArtifact : getAllResourceModules(artifact)) {
      // TODO
      AdapterUtils.addResourceModuleToModularizedSystem((AdapterResourceModule2IArtifact) moduleArtifact,
          modularizedSystem);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  public static void removeResourceModuleFromModularizedSystem(IArtifact artifact) {

    //
    IModifiableModularizedSystem modularizedSystem = AdapterUtils.getModularizedSystem(artifact);

    //
    for (IArtifact moduleArtifact : getAllResourceModules(artifact)) {
      // TODO
      AdapterUtils.removedResourceModuleFromModularizedSystem((AdapterResourceModule2IArtifact) moduleArtifact,
          modularizedSystem);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static IModifiableModularizedSystem getModularizedSystem(IArtifact artifact) {

    IArtifact root = artifact.getParent(ArtifactType.Root);

    Assert.isNotNull(root);
    Assert.isTrue(root instanceof AdapterModularizedSystem2IArtifact);

    AdapterModularizedSystem2IArtifact modularizedSystem2IArtifact = (AdapterModularizedSystem2IArtifact) root;

    return modularizedSystem2IArtifact.getModularizedSystem();
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  private static List<IArtifact> getAllResourceModules(IArtifact artifact) {

    //
    List<IArtifact> result = new LinkedList<IArtifact>();

    //
    if (artifact.getType().equals(ArtifactType.Module)) {
      result.add(artifact);
    } else if (artifact.getType().equals(ArtifactType.Group) || artifact.getType().equals(ArtifactType.Root)) {
      for (IArtifact child : artifact.getChildren()) {
        result.addAll(getAllResourceModules(child));
      }
    }

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param modularizedSystem
   */
  private static void addResourceModuleToModularizedSystem(AdapterResourceModule2IArtifact artifact,
      IModifiableModularizedSystem modularizedSystem) {

    IModifiableResourceModule resourceModule = (IModifiableResourceModule) artifact.getModule();
    Assert.isNotNull(resourceModule);

    if (artifact.getParent().getType().equals(ArtifactType.Group)) {
      resourceModule.setClassification(new Path(artifact.getParent().getQualifiedName()));
    }

    modularizedSystem.getModifiableResourceModulesMap().put(resourceModule.getModuleIdentifier(), resourceModule);
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  private static void removedResourceModuleFromModularizedSystem(AdapterResourceModule2IArtifact artifact,
      IModifiableModularizedSystem modularizedSystem) {

    AdapterResourceModule2IArtifact module2IArtifact = (AdapterResourceModule2IArtifact) artifact;
    modularizedSystem.getModifiableResourceModulesMap().remove(module2IArtifact.getModule().getModuleIdentifier());
  }
}
