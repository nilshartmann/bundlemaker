package org.bundlemaker.core.analysis;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AnalysisModelQueries {

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   * @return
   */
  public static IModuleArtifact getModuleArtifact(IRootArtifact rootArtifact, String moduleName) {

    //
    Assert.isNotNull(rootArtifact);
    Assert.isNotNull(moduleName);

    //
    Collection<IModule> modules = rootArtifact.getModularizedSystem().getModules(moduleName);
    if (modules.size() > 1) {
      throwException(String.format("Found multiple modules with module name '%s'.", moduleName));
    }

    //
    return modules.size() == 1 ? rootArtifact.getModuleArtifact(modules.toArray(new IModule[0])[0]) : null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   * @param moduleName
   * @param version
   * @return
   */
  public static IModuleArtifact getModuleArtifact(IBundleMakerArtifact rootArtifact, String moduleName, String version) {

    //
    Assert.isNotNull(rootArtifact);
    Assert.isNotNull(moduleName);
    Assert.isNotNull(version);

    //
    IModule module = rootArtifact.getModularizedSystem().getModule(moduleName, version);

    //
    return module != null ? rootArtifact.getRoot().getModuleArtifact(module) : null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   * @param identifier
   * @return
   */
  public static IModuleArtifact getModuleArtifact(IRootArtifact rootArtifact, IModuleIdentifier identifier) {

    //
    Assert.isNotNull(rootArtifact);
    Assert.isNotNull(identifier);

    //
    IModule module = rootArtifact.getModularizedSystem().getModule(identifier);

    //
    return module != null ? rootArtifact.getModuleArtifact(module) : null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @return
   */
  public static IModuleArtifact getJreModuleArtifact(IBundleMakerArtifact root) {

    //
    IModule jreModule = root.getModularizedSystem().getExecutionEnvironment();

    //
    return root.getRoot().getModuleArtifact(jreModule);
  }

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @param moduleIdentifier
   * @return
   */
  public static IModuleArtifact getMissingTypesModuleArtifact(IBundleMakerArtifact root) {

    // create the result array
    final IModuleArtifact[] result = new IModuleArtifact[1];

    // visit
    root.getRoot().accept(new IAnalysisModelVisitor.Adapter() {
      @SuppressWarnings("deprecation")
      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {

        //
        if (moduleArtifact.isVirtual() && moduleArtifact.getName().equals("<< Missing Types >>")) {
          result[0] = moduleArtifact;
        }

        //
        return false;
      }
    });

    // return result
    return result[0];
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static Set<IBundleMakerArtifact> getDirectlyReferencedArtifacts(IBundleMakerArtifact artifact) {

    //
    Set<IBundleMakerArtifact> result = new HashSet<IBundleMakerArtifact>();

    //
    for (IDependency dependency : artifact.getDependenciesTo()) {

      result.add(dependency.getTo());
    }

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  public static Set<IBundleMakerArtifact> getIndirectlyReferencedArtifacts(IBundleMakerArtifact artifact) {

    //
    return resolveReferencedArtifacts(artifact, new HashSet<IBundleMakerArtifact>(), true);
  }

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @param fullyQualifiedName
   * @return
   */
  public static IGroupArtifact findGroupArtifactByQualifiedName(IBundleMakerArtifact root,
      final String fullyQualifiedName) {

    // create the result array
    final List<IGroupArtifact> result = new LinkedList<IGroupArtifact>();

    // visit
    root.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IGroupArtifact groupArtifact) {

        //
        if (groupArtifact.getQualifiedName().equals(fullyQualifiedName)) {
          result.add(groupArtifact);
        }

        //
        return true;
      }
    });

    //
    if (result.size() > 1) {
      throwException(String.format("Multiple groups with qualified name '%s' exist.", fullyQualifiedName));
    }

    // return result
    return result.size() > 0 ? result.get(0) : null;
  }

  /**
   * <p>
   * </p>
   */
  public static IResourceArtifact findResourceArtifactByPathName(IBundleMakerArtifact root,
      final String pathName) {

    // create the result array
    final List<IResourceArtifact> result = new LinkedList<IResourceArtifact>();

    // visit
    root.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IResourceArtifact resourceArtifact) {

        //
        if (resourceArtifact.getQualifiedName().equals(pathName)) {
          result.add(resourceArtifact);
        }

        //
        return true;
      }
    });

    //
    if (result.size() > 1) {
      throwException(String.format("Multiple resources with path name '%s' exist.", pathName));
    }

    // return result
    return result.size() > 0 ? result.get(0) : null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param packageArtifact
   * @param string
   */
  public static ITypeArtifact findTypeArtifact(IBundleMakerArtifact root, final String qualifiedName) {

    // create the result array
    final List<ITypeArtifact> result = new LinkedList<ITypeArtifact>();

    // visit
    root.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(ITypeArtifact typeArtifact) {

        //
        if (typeArtifact.getQualifiedName().equals(qualifiedName)) {
          result.add(typeArtifact);
        }

        //
        return true;
      }
    });

    //
    if (result.size() > 1) {
      throwException(String.format("Multiple types with name '%s' exist.", qualifiedName));
    } else if (result.size() == 0) {
      return null;
    }

    // return result
    return result.get(0);
  }

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @param name
   * @return
   */
  public static IPackageArtifact findPackageArtifact(IBundleMakerArtifact root, final String fullyQualifiedName) {

    // create the result array
    final List<IPackageArtifact> result = new LinkedList<IPackageArtifact>();

    // visit
    root.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IPackageArtifact packageArtifact) {

        //
        if (packageArtifact.getQualifiedName().equals(fullyQualifiedName)) {
          result.add(packageArtifact);
        }

        //
        return true;
      }
    });

    //
    if (result.size() > 1) {
      throwException(String.format("Multiple packages with name '%s' exist.", fullyQualifiedName));
    } else if (result.size() == 0) {
      return null;
    }

    // return result
    return result.get(0);
  }

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @param fullyQualifiedName
   * @return
   */
  public static List<IPackageArtifact> findPackageArtifacts(IBundleMakerArtifact root, final String fullyQualifiedName) {

    // create the result array
    final List<IPackageArtifact> result = new LinkedList<IPackageArtifact>();

    // visit
    root.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IPackageArtifact packageArtifact) {

        //
        if (packageArtifact.getQualifiedName().equals(fullyQualifiedName)) {
          result.add(packageArtifact);
        }

        //
        return false;
      }
    });

    // return result
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param artifacts
   * @return
   */
  private static Set<IBundleMakerArtifact> resolveReferencedArtifacts(IBundleMakerArtifact artifact,
      Set<IBundleMakerArtifact> artifacts, boolean transitive) {

    //
    Assert.isNotNull(artifact);
    Assert.isNotNull(artifacts);

    //
    for (IDependency dependency : artifact.getDependenciesTo()) {
      if (artifacts.add(dependency.getTo()) && transitive) {
        resolveReferencedArtifacts(dependency.getTo(), artifacts, transitive);
      }
    }

    //
    return artifacts;
  }

  /**
   * <p>
   * </p>
   * 
   * @param format
   */
  private static void throwException(String format) {
    // TODO
    throw new RuntimeException(format);
  }
}
