package org.bundlemaker.core.internal.analysis;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.internal.modules.AbstractModule;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
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
  public static IModifiableModularizedSystem getModularizedSystem(IBundleMakerArtifact artifact) {

    //
    IBundleMakerArtifact root = ((IBundleMakerArtifact) artifact).getRoot();

    // check (performance!)
    if (root == null) {
      Assert.isNotNull(root, "No root for :" + ArtifactUtils.artifactToString(artifact));
    }
    Assert.isTrue(root instanceof AdapterRoot2IArtifact);

    //
    return ((AdapterRoot2IArtifact) root).getModularizedSystem();
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param newPathPrefix
   * @return
   */
  public static boolean addModulesIfNecessaryAndResetClassification(IBundleMakerArtifact artifact, String newPathPrefix) {

    //
    Assert.isNotNull(artifact);
    Assert.isTrue(artifact instanceof IModuleArtifact || artifact instanceof IGroupArtifact);

    //
    IModifiableModularizedSystem modularizedSystem = AdapterUtils.getModularizedSystem(artifact);

    //
    List<IBundleMakerArtifact> artifacts = getAllContainedResourceModules(artifact);

    //
    for (IBundleMakerArtifact moduleArtifact : artifacts) {

      //
      if (moduleArtifact.getParent() != null && !artifact.getType().equals(ArtifactType.Module)) {

        //
        String newPath = computeRelativeClassification(artifact, moduleArtifact);

        //
        newPathPrefix = newPathPrefix != null ? newPathPrefix + "|" + newPath : newPath;
      }

      // TODO
      AdapterModule2IArtifact adapter = (AdapterModule2IArtifact) moduleArtifact;
      AbstractModule<?, ?> abstractModule = (AbstractModule<?, ?>) adapter.getModule();
      Assert.isNotNull(abstractModule);

      //
      if (!abstractModule.hasModularizedSystem()) {
        modularizedSystem.addModule(abstractModule);
      }

      if (newPathPrefix != null) {
        newPathPrefix = newPathPrefix.replace('|', '/');
        abstractModule.setClassification(new Path(newPathPrefix));
      } else {
        abstractModule.setClassification(null);
      }

    }

    return !artifacts.isEmpty();
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param moduleArtifact
   * @return
   */
  private static String computeRelativeClassification(IBundleMakerArtifact artifact, IBundleMakerArtifact moduleArtifact) {

    //
    List<IBundleMakerArtifact> groups = new LinkedList<IBundleMakerArtifact>();

    //
    IBundleMakerArtifact currentArtifact = moduleArtifact;

    boolean relativeArtifactFound = false;

    while (currentArtifact.getParent() != null && currentArtifact.getParent().getType().equals(ArtifactType.Group)
        && !relativeArtifactFound) {

      currentArtifact = currentArtifact.getParent();
      groups.add(currentArtifact);

      relativeArtifactFound = artifact.equals(currentArtifact);
    }

    //
    Collections.reverse(groups);

    //
    StringBuilder builder = new StringBuilder();
    for (Iterator<IBundleMakerArtifact> iterator = groups.iterator(); iterator.hasNext();) {
      IBundleMakerArtifact iArtifact = iterator.next();
      builder.append(iArtifact.getName());
      if (iterator.hasNext()) {
        builder.append("|");
      }
    }

    //
    return builder.toString();
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  public static boolean removeResourceModuleFromModularizedSystem(IBundleMakerArtifact artifact) {

    //
    IModifiableModularizedSystem modularizedSystem = AdapterUtils.getModularizedSystem(artifact);

    //
    List<IBundleMakerArtifact> artifacts = getAllContainedResourceModules(artifact);

    //
    for (IBundleMakerArtifact moduleArtifact : artifacts) {

      // TODO
      AdapterModule2IArtifact module2IArtifact = ((AdapterModule2IArtifact) moduleArtifact);
      modularizedSystem.removeModule(module2IArtifact.getModule().getModuleIdentifier());
    }

    return !artifacts.isEmpty();
  }

  /**
   * <p>
   * </p>
   * 
   * @param adapterPackage2IArtifact
   * @param artifact
   */
  public static void addArtifactToPackage(AdapterPackage2IArtifact adapterPackage2IArtifact,
      IBundleMakerArtifact artifact) {

    Assert.isNotNull(adapterPackage2IArtifact);
    Assert.isNotNull(artifact);

    //
    IModifiableResourceModule resourceModule = (IModifiableResourceModule) adapterPackage2IArtifact
        .getContainingModule();

    //
    addResourcesToModule(resourceModule, getAllMovableUnits(artifact));

    // addTypesToModule((IModifiableResourceModule) resourceModule2Artifact.getModule(),
    // getAllContainedTypeHolder(artifact));
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifactToRemove
   * @param adapterPackage2IArtifact
   */
  public static void removeArtifact(IBundleMakerArtifact artifactToRemove, IBundleMakerArtifact artifactToRemoveFrom) {

    //
    AdapterResourceModule2IArtifact moduleArtifact = null;

    //
    if (!artifactToRemoveFrom.getType().equals(ArtifactType.Module)) {
      moduleArtifact = (AdapterResourceModule2IArtifact) artifactToRemoveFrom.getParent(ArtifactType.Module);
    } else {
      moduleArtifact = (AdapterResourceModule2IArtifact) artifactToRemoveFrom;
    }

    //
    if (moduleArtifact != null) {

      //
      removeResourcesFromModule((IModifiableResourceModule) moduleArtifact.getModule(),
          getAllMovableUnits(artifactToRemove));
    }

  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @param packageArtifact
  // * @param moduleArtifact
  // */
  // public static void removePackageFromModule(IBundleMakerArtifact packageArtifact, IBundleMakerArtifact
  // moduleArtifact) {
  //
  // Assert.isTrue(packageArtifact instanceof AdapterPackage2IArtifact);
  // Assert.isTrue(moduleArtifact instanceof AdapterResourceModule2IArtifact);
  //
  // // down cast
  // AdapterPackage2IArtifact package2Artifact = (AdapterPackage2IArtifact) packageArtifact;
  // AdapterResourceModule2IArtifact adapterResourceModule2IArtifact = (AdapterResourceModule2IArtifact) moduleArtifact;
  //
  // //
  // removeResourcesFromModule((IModifiableResourceModule) adapterResourceModule2IArtifact.getModule(),
  // getAllMovableUnits(package2Artifact));
  // // removeTypesFromModule((IModifiableResourceModule) adapterResourceModule2IArtifact.getModule(),
  // // getAllContainedTypeHolder(package2Artifact));
  // }

  /**
   * <p>
   * </p>
   * 
   * @param packageArtifact
   * @param moduleArtifact
   */
  public static void addPackageToModule(IBundleMakerArtifact packageArtifact, IBundleMakerArtifact moduleArtifact) {

    Assert.isTrue(packageArtifact instanceof AdapterPackage2IArtifact);
    Assert.isTrue(moduleArtifact instanceof AdapterResourceModule2IArtifact);

    // down cast
    AdapterPackage2IArtifact package2Artifact = (AdapterPackage2IArtifact) packageArtifact;
    AdapterResourceModule2IArtifact adapterResourceModule2IArtifact = (AdapterResourceModule2IArtifact) moduleArtifact;

    //
    addResourcesToModule((IModifiableResourceModule) adapterResourceModule2IArtifact.getModule(),
        getAllMovableUnits(package2Artifact));
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
  private static List<IBundleMakerArtifact> getAllContainedResourceModules(IBundleMakerArtifact artifact) {

    //
    List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();

    //
    if (artifact.getType().equals(ArtifactType.Module)) {
      result.add(artifact);
    } else if (artifact.getType().equals(ArtifactType.Group) || artifact.getType().equals(ArtifactType.Root)) {
      for (IBundleMakerArtifact child : artifact.getChildren()) {
        result.addAll(getAllContainedResourceModules(child));
      }
    }

    //
    return result;
  }

  public static List<IMovableUnit> getAllMovableUnits(IBundleMakerArtifact artifact) {

    //
    List<IMovableUnit> result = new LinkedList<IMovableUnit>();

    //
    if (artifact instanceof IMovableUnit) {

      result.add((IMovableUnit) artifact);

      // DO NOT ADD THE CHILDREN OF 'IMovableUnit'

    } else {
      for (IBundleMakerArtifact child : artifact.getChildren()) {
        result.addAll(getAllMovableUnits(child));
      }
    }

    //
    return result;
  }

  private static List<ITypeArtifact> getAllContainedTypeHolder(IBundleMakerArtifact artifact) {

    List<ITypeArtifact> result = new LinkedList<ITypeArtifact>();

    //
    if (artifact instanceof ITypeArtifact) {

      result.add((ITypeArtifact) artifact);

      // DO NOT ADD THE CHILDREN OF 'IResourceHolder'

    } else {
      for (IBundleMakerArtifact child : artifact.getChildren()) {
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
  public static void addResourcesToModule(IModifiableResourceModule resourceModule, List<IMovableUnit> movableUnits) {

    //
    for (IMovableUnit movableUnit : movableUnits) {

      addResourceToModule(resourceModule, movableUnit);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param movableUnit
   */
  public static void addResourceToModule(IModifiableResourceModule resourceModule, IMovableUnit movableUnit) {

    //
    IModifiableResourceModule module = (IModifiableResourceModule) movableUnit.getContainingResourceModule();

    if (module != null) {
      module.getModifiableSelfResourceContainer().removeMovableUnit(movableUnit);
    }
    // add the binary resources
    resourceModule.getModifiableSelfResourceContainer().addMovableUnit(movableUnit);
  }

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
      resourceModule.getModifiableSelfResourceContainer().removeMovableUnit(resourceHolder);
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
