package org.bundlemaker.core.internal.analysis;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Path;

public class AdapterUtils {

  /**
   * <p>
   * Valid artifact types:
   * <ul>
   * <li>{@link ArtifactType#Root}</li>
   * <li>{@link ArtifactType#Group}</li>
   * <li>{@link ArtifactType#Module}</li>
   * <li>{@link ArtifactType#Package}</li>
   * <li>{@link ArtifactType#Resource}</li>
   * <li>{@link ArtifactType#Type}</li>
   * </ul>
   * </p>
   * 
   * @param artifact
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
   */
  public static void addResourceModuleToModularizedSystem(IArtifact artifact) {

    //
    IModifiableModularizedSystem modularizedSystem = AdapterUtils.getModularizedSystem(artifact);

    //
    for (IArtifact moduleArtifact : getAllContainedResourceModules(artifact)) {
      
      // TODO
      AdapterResourceModule2IArtifact artifact1 = (AdapterResourceModule2IArtifact) moduleArtifact;
      IModifiableResourceModule resourceModule = (IModifiableResourceModule) artifact1.getModule();
      Assert.isNotNull(resourceModule);

      if (artifact1.getParent().getType().equals(ArtifactType.Group)) {
        resourceModule.setClassification(new Path(artifact1.getParent().getQualifiedName()));
      }

      modularizedSystem.getModifiableResourceModulesMap().put(resourceModule.getModuleIdentifier(), resourceModule);
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
    for (IArtifact moduleArtifact : getAllContainedResourceModules(artifact)) {
      // TODO
      AdapterResourceModule2IArtifact module2IArtifact = (AdapterResourceModule2IArtifact) ((AdapterResourceModule2IArtifact) moduleArtifact);
      modularizedSystem.getModifiableResourceModulesMap().remove(module2IArtifact.getModule().getModuleIdentifier());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param adapterPackage2IArtifact
   * @param artifact
   */
  public static void addArtifactToPackage(AdapterPackage2IArtifact adapterPackage2IArtifact, IArtifact artifact) {

    Assert.isNotNull(adapterPackage2IArtifact);
    Assert.isNotNull(artifact);

    //
    AdapterResourceModule2IArtifact resourceModule2Artifact = (AdapterResourceModule2IArtifact) adapterPackage2IArtifact
        .getParent(ArtifactType.Module);

    //
    addResourcesToModule((IModifiableResourceModule) resourceModule2Artifact.getModule(),
        getAllContainedResourceHolder(artifact));
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param adapterPackage2IArtifact
   */
  public static void removeArtifactFromPackage(IArtifact artifact, AdapterPackage2IArtifact adapterPackage2IArtifact) {

    //
    AdapterResourceModule2IArtifact resourceModule2Artifact = (AdapterResourceModule2IArtifact) adapterPackage2IArtifact
        .getParent(ArtifactType.Module);

    //
    removeResourcesFromModule((IModifiableResourceModule) resourceModule2Artifact.getModule(),
        getAllContainedResourceHolder(artifact));
  }

  /**
   * <p>
   * </p>
   * 
   * @param packageArtifact
   * @param moduleArtifact
   */
  public static void removePackageFromModule(IArtifact packageArtifact, IArtifact moduleArtifact) {

    Assert.isTrue(packageArtifact instanceof AdapterPackage2IArtifact);
    Assert.isTrue(moduleArtifact instanceof AdapterResourceModule2IArtifact);

    // down cast
    AdapterPackage2IArtifact package2Artifact = (AdapterPackage2IArtifact) packageArtifact;
    AdapterResourceModule2IArtifact adapterResourceModule2IArtifact = (AdapterResourceModule2IArtifact) moduleArtifact;

    //
    removeResourcesFromModule((IModifiableResourceModule) adapterResourceModule2IArtifact.getModule(),
        getAllContainedResourceHolder(package2Artifact));
  }

  /**
   * <p>
   * </p>
   * 
   * @param packageArtifact
   * @param moduleArtifact
   */
  public static void addPackageToModule(IArtifact packageArtifact, IArtifact moduleArtifact) {

    Assert.isTrue(packageArtifact instanceof AdapterPackage2IArtifact);
    Assert.isTrue(moduleArtifact instanceof AdapterResourceModule2IArtifact);

    // down cast
    AdapterPackage2IArtifact package2Artifact = (AdapterPackage2IArtifact) packageArtifact;
    AdapterResourceModule2IArtifact adapterResourceModule2IArtifact = (AdapterResourceModule2IArtifact) moduleArtifact;

    //
    addResourcesToModule((IModifiableResourceModule) adapterResourceModule2IArtifact.getModule(),
        getAllContainedResourceHolder(package2Artifact));
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  private static List<IArtifact> getAllContainedResourceModules(IArtifact artifact) {

    //
    List<IArtifact> result = new LinkedList<IArtifact>();

    //
    if (artifact.getType().equals(ArtifactType.Module)) {
      result.add(artifact);
    } else if (artifact.getType().equals(ArtifactType.Group) || artifact.getType().equals(ArtifactType.Root)) {
      for (IArtifact child : artifact.getChildren()) {
        result.addAll(getAllContainedResourceModules(child));
      }
    }

    //
    return result;
  }

  private static List<IResourceHolder> getAllContainedResourceHolder(IArtifact artifact) {

    //
    List<IResourceHolder> result = new LinkedList<IResourceHolder>();

    //
    if (artifact instanceof IResourceHolder) {

      result.add((IResourceHolder) artifact);

      // DO NOT ADD THE CHILDREN OF 'IResourceHolder'

    } else {
      for (IArtifact child : artifact.getChildren()) {
        result.addAll(getAllContainedResourceHolder(child));
      }
    }

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param resourceHolder
   */
  private static void addResourcesToModule(IModifiableResourceModule resourceModule,
      List<IResourceHolder> resourceHolders) {

    //
    for (IResourceHolder resourceHolder : resourceHolders) {

      // add the binary resources
      resourceModule.getModifiableSelfResourceContainer().getModifiableResourcesSet(ContentType.BINARY)
          .addAll(resourceHolder.getAssociatedBinaryResources());

      // add the source resources
      resourceModule.getModifiableSelfResourceContainer().getModifiableResourcesSet(ContentType.SOURCE)
          .addAll(resourceHolder.getAssociatedSourceResources());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param resourceHolder
   */
  private static void removeResourcesFromModule(IModifiableResourceModule resourceModule,
      List<IResourceHolder> resourceHolders) {

    //
    for (IResourceHolder resourceHolder : resourceHolders) {

      // remove the binary resources
      resourceModule.getModifiableSelfResourceContainer().getModifiableResourcesSet(ContentType.BINARY)
          .removeAll(resourceHolder.getAssociatedBinaryResources());

      // remove the source resources
      resourceModule.getModifiableSelfResourceContainer().getModifiableResourcesSet(ContentType.SOURCE)
          .removeAll(resourceHolder.getAssociatedSourceResources());
    }
  }
}
