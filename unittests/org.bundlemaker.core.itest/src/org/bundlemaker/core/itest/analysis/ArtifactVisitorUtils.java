package org.bundlemaker.core.itest.analysis;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.junit.Assert;

public class ArtifactVisitorUtils {

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @return
   */
  public static IModuleArtifact findJreModuleArtifact(IBundleMakerArtifact root) {

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
  public static IModuleArtifact findModuleArtifact(IBundleMakerArtifact root, final String name, final String version) {
    return findModuleArtifact(root, new ModuleIdentifier(name, version));
  }

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @return
   */
  public static IModuleArtifact findModuleArtifact(IBundleMakerArtifact root, final IModuleIdentifier moduleIdentifier) {

    // create the result array
    final IModuleArtifact[] result = new IModuleArtifact[1];

    // visit
    root.accept(new IArtifactTreeVisitor.Adapter() {
      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {

        //
        if (!moduleArtifact.isVirtual()
            && moduleArtifact.getAssociatedModule().getModuleIdentifier().equals(moduleIdentifier)) {
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
   * @param root
   * @param name
   * @return
   */
  public static IModuleArtifact findModuleArtifact(IBundleMakerArtifact root, final String name) {

    // create the result array
    final List<IModuleArtifact> result = new LinkedList<IModuleArtifact>();

    // visit
    root.accept(new IArtifactTreeVisitor.Adapter() {
      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {

        //
        if (moduleArtifact.getAssociatedModule().getModuleIdentifier().getName().equals(name)) {
          result.add(moduleArtifact);
        }

        //
        return false;
      }
    });

    //
    if (result.size() > 1) {
      Assert.fail(String.format("Multiple modules with name '%s' exist.", name));
    }

    // return result
    return result.get(0);
  }

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @param moduleIdentifier
   * @return
   */
  public static IModuleArtifact findMissingTypesModuleArtifact(IBundleMakerArtifact root) {

    // create the result array
    final IModuleArtifact[] result = new IModuleArtifact[1];

    // visit
    root.accept(new IArtifactTreeVisitor.Adapter() {
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
   * @param root
   * @param name
   * @return
   */
  public static IPackageArtifact findPackageArtifact(IBundleMakerArtifact root, final String fullyQualifiedName) {

    // create the result array
    final List<IPackageArtifact> result = new LinkedList<IPackageArtifact>();

    // visit
    root.accept(new IArtifactTreeVisitor.Adapter() {
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
      Assert.fail(String.format("Multiple packages with name '%s' exist.", fullyQualifiedName));
    } else if (result.size() == 0) {
      return null;
    }

    // return result
    return result.get(0);
  }

  public static List<IPackageArtifact> findPackageArtifacts(IBundleMakerArtifact root, final String fullyQualifiedName) {

    // create the result array
    final List<IPackageArtifact> result = new LinkedList<IPackageArtifact>();

    // visit
    root.accept(new IArtifactTreeVisitor.Adapter() {
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
   * @param root
   * @param fullyQualifiedName
   * @return
   */
  public static IGroupArtifact findGroupArtifactByQualifiedName(IBundleMakerArtifact root,
      final String fullyQualifiedName) {

    // create the result array
    final List<IGroupArtifact> result = new LinkedList<IGroupArtifact>();

    // visit
    root.accept(new IArtifactTreeVisitor.Adapter() {
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
      Assert.fail(String.format("Multiple groups with qualified name '%s' exist.", fullyQualifiedName));
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
    root.accept(new IArtifactTreeVisitor.Adapter() {
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
      Assert.fail(String.format("Multiple resources with path name '%s' exist.", pathName));
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
  public static ITypeArtifact findTypeArtifact(IPackageArtifact root, final String qualifiedName) {

    // create the result array
    final List<ITypeArtifact> result = new LinkedList<ITypeArtifact>();

    // visit
    root.accept(new IArtifactTreeVisitor.Adapter() {
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
      Assert.fail(String.format("Multiple types with name '%s' exist.", qualifiedName));
    } else if (result.size() == 0) {
      return null;
    }

    // return result
    return result.get(0);
    
  }
}
