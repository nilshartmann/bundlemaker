package org.bundlemaker.core.internal.analysis;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.projectdescription.ContentType;
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

    IArtifact root = (artifact.getType() == ArtifactType.Root ? artifact : artifact.getParent(ArtifactType.Root));

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
      AdapterResourceModule2IArtifact adapter = (AdapterResourceModule2IArtifact) moduleArtifact;
      IModifiableResourceModule resourceModule = (IModifiableResourceModule) adapter.getModule();
      Assert.isNotNull(resourceModule);

      if (adapter.getParent().getType().equals(ArtifactType.Group)) {

        //
        String path = adapter.getParent().getQualifiedName();
        path = path.replace('|', '/');

        // set the classification
        resourceModule.setClassification(new Path(path));
      }

      //
      modularizedSystem.addModifiableResourceModule(resourceModule);
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
      modularizedSystem.removeModule(module2IArtifact.getModule().getModuleIdentifier());
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
    // addTypesToModule((IModifiableResourceModule) resourceModule2Artifact.getModule(),
    // getAllContainedTypeHolder(artifact));
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
    // removeTypesFromModule((IModifiableResourceModule) resourceModule2Artifact.getModule(),
    // getAllContainedTypeHolder(artifact));

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
    // removeTypesFromModule((IModifiableResourceModule) adapterResourceModule2IArtifact.getModule(),
    // getAllContainedTypeHolder(package2Artifact));
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
    // addTypesToModule((IModifiableResourceModule) adapterResourceModule2IArtifact.getModule(),
    // getAllContainedTypeHolder(package2Artifact));
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

  private static List<IMovableUnit> getAllContainedResourceHolder(IArtifact artifact) {

    //
    List<IMovableUnit> result = new LinkedList<IMovableUnit>();

    //
    if (artifact instanceof IMovableUnit) {

      result.add((IMovableUnit) artifact);

      // DO NOT ADD THE CHILDREN OF 'IResourceHolder'

    } else {
      for (IArtifact child : artifact.getChildren()) {
        result.addAll(getAllContainedResourceHolder(child));
      }
    }

    //
    return result;
  }

  private static List<ITypeArtifact> getAllContainedTypeHolder(IArtifact artifact) {

    List<ITypeArtifact> result = new LinkedList<ITypeArtifact>();

    //
    if (artifact instanceof ITypeArtifact) {

      result.add((ITypeArtifact) artifact);

      // DO NOT ADD THE CHILDREN OF 'IResourceHolder'

    } else {
      for (IArtifact child : artifact.getChildren()) {
        result.addAll(getAllContainedTypeHolder(child));
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
  private static void addResourcesToModule(IModifiableResourceModule resourceModule, List<IMovableUnit> resourceHolders) {

    //
    for (IMovableUnit resourceHolder : resourceHolders) {

      // add the binary resources
      resourceModule.getModifiableSelfResourceContainer().addAll(resourceHolder.getAssociatedBinaryResources(),
          ContentType.BINARY);

      // add the source resources
      resourceModule.getModifiableSelfResourceContainer().add(resourceHolder.getAssociatedSourceResource(),
          ContentType.SOURCE);
    }
  }

  // private static void addTypesToModule(IModifiableResourceModule resourceModule, List<ITypeHolder> typeHolders) {
  // // Get the modifiable type container
  // IModifiableTypeContainer modifiableSelfTypeContainer = resourceModule.getModifiableSelfResourceContainer();
  // Map<String, IType> typesMap = modifiableSelfTypeContainer.getModifiableContainedTypesMap();
  //
  // for (ITypeHolder typeHolder : typeHolders) {
  // IType type = typeHolder.getAssociatedType();
  // typesMap.put(type.getFullyQualifiedName(), type);
  // }
  // }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param resourceHolder
   */
  private static void removeResourcesFromModule(IModifiableResourceModule resourceModule,
      List<IMovableUnit> resourceHolders) {

    //
    for (IMovableUnit resourceHolder : resourceHolders) {

      // remove the binary resources
      resourceModule.getModifiableSelfResourceContainer().removeAll(resourceHolder.getAssociatedBinaryResources(),
          ContentType.BINARY);

      // remove the source resources
      resourceModule.getModifiableSelfResourceContainer().remove(resourceHolder.getAssociatedSourceResource(),
          ContentType.SOURCE);
    }
  }

  // private static void removeTypesFromModule(IModifiableResourceModule resourceModule, List<ITypeHolder> typeHolders)
  // {
  // // Get the modifiable type container
  // IModifiableTypeContainer modifiableSelfTypeContainer = resourceModule.getModifiableSelfResourceContainer();
  // Map<String, IType> typesMap = modifiableSelfTypeContainer.getModifiableContainedTypesMap();
  //
  // // Remove all types from the container
  // for (ITypeHolder typeHolder : typeHolders) {
  // IType type = typeHolder.getAssociatedType();
  // typesMap.remove(type.getFullyQualifiedName());
  // }
  // }
}
