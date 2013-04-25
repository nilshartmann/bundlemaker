package org.bundlemaker.core.internal.analysis;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.internal.modules.Module;
import org.bundlemaker.core.internal.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.modules.modifiable.IModifiableModule;
import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.resource.IMovableUnit;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * Helper class that provides several utility methods to manipulate the resource model via {@link IBundleMakerArtifact
 * IBundleMakerArtifacts}. Whenever an add or remove method is called on the artifact tree, the artifact tree
 * manipulates the resource model by using methods provided by this class.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterUtils {

  /**
   * <p>
   * </p>
   * 
   * @param adapterGroup2IArtifact
   * @param name
   */
  public static void changeGroupName(AdapterGroup2IArtifact adapterGroup2IArtifact, String name) {

    // asserts
    Assert.isNotNull(adapterGroup2IArtifact);
    Assert.isNotNull(name);

    // assert group name
    IPath namePath = new Path(name);
    Assert.isTrue(namePath.segmentCount() == 1, String.format("Invalid group name: '%s'.", name));

    // //
    // IBundleMakerArtifact parent = adapterGroup2IArtifact.getParent();
    // if (parent != null && parent instanceof IGroupArtifact) {
    // namePath = ((AdapterGroup2IArtifact) parent).getGroup().getPath().append(namePath);
    // }

    //
    adapterGroup2IArtifact.getAssociatedGroup().setName(name);
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param newPathPrefix
   * @return
   */
  public static boolean addModulesIfNecessaryAndResetClassification(IModuleArtifact artifact, String newPathPrefix) {
    return _addModulesIfNecessaryAndResetClassification(artifact, newPathPrefix);
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param newParent
   */
  public static void addModulesIfNecessaryAndResetClassification(IGroupArtifact artifact,
      IGroupAndModuleContainer newParent) {

    //
    if (newParent instanceof IGroupArtifact) {

      // get the parent group
      Group parent = ((Group) ((IGroupArtifact) newParent)
          .getAssociatedGroup());

      //
      ((Group) artifact.getAssociatedGroup()).setParent(parent);
    }

    //
    else if (newParent instanceof IRootArtifact) {

      //
      ((Group) artifact.getAssociatedGroup()).setRootParent();
    }

    //
    else {

      //
      ((Group) artifact.getAssociatedGroup()).setParent(null);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param newPathPrefix
   * @return
   */
  private static boolean _addModulesIfNecessaryAndResetClassification(IBundleMakerArtifact artifact,
      String newPathPrefix) {

    //
    Assert.isNotNull(artifact);
    Assert.isTrue(artifact instanceof IModuleArtifact || artifact instanceof IGroupArtifact);

    //
    IModifiableModularizedSystem modularizedSystem = (IModifiableModularizedSystem) artifact.getModularizedSystem();

    //
    List<IBundleMakerArtifact> artifacts = getAllContainedModules(artifact);

    //
    for (IBundleMakerArtifact moduleArtifact : artifacts) {

      //
      if (moduleArtifact.getParent() != null && !artifact.isInstanceOf(IModuleArtifact.class)) {

        //
        String newPath = computeRelativeClassification(artifact, moduleArtifact);

        //
        newPathPrefix = newPathPrefix != null ? newPathPrefix + "|" + newPath : newPath;
      }

      if (moduleArtifact instanceof AdapterModule2IArtifact) {
        AdapterModule2IArtifact adapter = (AdapterModule2IArtifact) moduleArtifact;
        Module abstractModule = (Module) adapter.getModule();
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

    while (currentArtifact.getParent() != null && currentArtifact.getParent().isInstanceOf(IGroupArtifact.class)
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
    final ModularizedSystem modularizedSystem = (ModularizedSystem) artifact.getModularizedSystem();
    List<IBundleMakerArtifact> artifacts = getAllContainedModules(artifact);

    //
    if (artifact instanceof IGroupArtifact) {
      IGroupArtifact groupArtifact = (IGroupArtifact) artifact;
      Group group = (Group) groupArtifact.getAssociatedGroup();
      group.setParent(null);

      //
      groupArtifact.accept(new IAnalysisModelVisitor.Adapter() {
        @Override
        public boolean visit(IGroupArtifact artifact) {
          modularizedSystem.internalGroups().remove(artifact.getAssociatedGroup());
          return true;
        }
      });
    }

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
    IModifiableModule resourceModule = (IModifiableModule) adapterPackage2IArtifact
        .getContainingModule();

    //
    addResourcesToModule(resourceModule, getAllMovableUnits(artifact));
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
    Assert.isNotNull(artifactToRemove);
    Assert.isNotNull(artifactToRemoveFrom);

    //
    AdapterResourceModule2IArtifact moduleArtifact = null;

    //
    if (!artifactToRemoveFrom.isInstanceOf(IModuleArtifact.class)) {
      moduleArtifact = (AdapterResourceModule2IArtifact) artifactToRemoveFrom.getParent(IModuleArtifact.class);
    } else {
      moduleArtifact = (AdapterResourceModule2IArtifact) artifactToRemoveFrom;
    }

    //
    if (moduleArtifact != null) {

      //
      removeResourcesFromModule((IModifiableModule) moduleArtifact.getModule(),
          getAllMovableUnits(artifactToRemove));
    }

  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  private static List<IBundleMakerArtifact> getAllContainedModules(IBundleMakerArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);

    // create the result list
    final List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();

    // visit the tree
    artifact.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {
        result.add(moduleArtifact);
        return false;
      }
    });

    // return the result
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  public static List<IMovableUnit> getAllMovableUnits(IBundleMakerArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);

    // create the result list
    final List<IMovableUnit> result = new LinkedList<IMovableUnit>();

    // accept the visitor
    artifact.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean onVisit(IBundleMakerArtifact artifact) {
        // continue the search if artifact is not instance of
        if (!(artifact instanceof IMovableUnit)) {
          return true;
        } else {
          result.add((IMovableUnit) artifact);
          return false;
        }
      }
    });

    // return the result
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param movableUnit
   */
  public static void addResourceToModule(IModifiableModule resourceModule, IMovableUnit movableUnit) {

    //
    Assert.isNotNull(resourceModule);
    Assert.isNotNull(movableUnit);

    //
    IModifiableModule module = (IModifiableModule) movableUnit.getModule();

    if (module != null) {
      module.removeMovableUnit(movableUnit);
    }

    // add the binary resources
    resourceModule.addMovableUnit(movableUnit);
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param resourceHolder
   */
  public static void addResourcesToModule(IModifiableModule resourceModule, List<IMovableUnit> movableUnits) {

    Assert.isNotNull(resourceModule);
    Assert.isNotNull(movableUnits);

    //
    for (IMovableUnit movableUnit : movableUnits) {

      addResourceToModule(resourceModule, movableUnit);
    }
  }

  /**
   * <p>
   * Removes all {@link IMovableUnit IMovableUnits} from the given resource module.
   * </p>
   * 
   * @param resourceModule
   * @param movableUnits
   */
  private static void removeResourcesFromModule(IModifiableModule resourceModule,
      List<IMovableUnit> movableUnits) {

    // asserts
    Assert.isNotNull(resourceModule);
    Assert.isNotNull(movableUnits);

    // remove all units
    for (IMovableUnit resourceHolder : movableUnits) {
      resourceModule.removeMovableUnit(resourceHolder);
    }
  }
}
