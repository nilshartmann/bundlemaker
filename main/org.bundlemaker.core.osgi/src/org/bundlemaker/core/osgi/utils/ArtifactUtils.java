package org.bundlemaker.core.osgi.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.modules.IModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactUtils {

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param module
   * @return
   */
  public static IModuleArtifact getAssociatedModuleArtifact(final IGroupAndModuleContainer artifact,
      final IModule module) {

    //
    Assert.isNotNull(artifact);
    Assert.isNotNull(module);

    //
    final IModuleArtifact[] result = new IModuleArtifact[1];

    //
    artifact.accept(new IArtifactTreeVisitor.Adapter() {

      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {

        //
        if (module.equals(moduleArtifact.getAssociatedModule())) {
          result[0] = moduleArtifact;
        }

        //
        return false;
      }
    });

    //
    return result[0];
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  public static List<IModuleArtifact> getAllChildModules(final IGroupAndModuleContainer artifact) {

    //
    Assert.isNotNull(artifact);

    //
    final List<IModuleArtifact> result = new LinkedList<IModuleArtifact>();

    //
    artifact.accept(new IArtifactTreeVisitor.Adapter() {

      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {

        //
        result.add(moduleArtifact);

        //
        return false;
      }
    });

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
  public static List<IPackageArtifact> getAllChildPackages(final IBundleMakerArtifact artifact) {

    //
    Assert.isNotNull(artifact);

    //
    final List<IPackageArtifact> result = new LinkedList<IPackageArtifact>();

    //
    artifact.accept(new IArtifactTreeVisitor.Adapter() {

      @Override
      public boolean visit(IPackageArtifact packageArtifact) {

        //
        result.add(packageArtifact);

        //
        return true;
      }
    });

    Collections.sort(result, new Comparator<IPackageArtifact>() {

      @Override
      public int compare(IPackageArtifact package1, IPackageArtifact package2) {
        return package1.getQualifiedName().compareTo(package2.getQualifiedName());
      }
    });

    //
    return result;
  }
}