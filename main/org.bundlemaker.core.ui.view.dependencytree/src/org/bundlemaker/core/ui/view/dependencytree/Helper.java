package org.bundlemaker.core.ui.view.dependencytree;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;

public class Helper {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static List<IDependency> getAllLeafDependencies(Collection<IDependency> dependencies) {

    //
    final List<IDependency> result = new LinkedList<IDependency>();

    for (IDependency dependency : dependencies) {
      for (IDependency leafDependency : dependency.getLeafDependencies()) {
        result.add(leafDependency);
      }
    }

    return result;
  }

  /**
   * <p>
   * </p>
   *
   * @param bundleMakerArtifact
   * @return
   */
  public static Collection<IBundleMakerArtifact> getSelfAndAllChildren(IBundleMakerArtifact bundleMakerArtifact) {

    //
    final Set<IBundleMakerArtifact> result = new HashSet<IBundleMakerArtifact>();

    result.add(bundleMakerArtifact);
    
    //
    bundleMakerArtifact.accept(new IArtifactTreeVisitor.Adapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public boolean visit(IResourceArtifact resourceArtifact) {
        result.add(resourceArtifact);
        return super.visit(resourceArtifact);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public boolean visit(ITypeArtifact typeArtifact) {
        result.add(typeArtifact);
        return super.visit(typeArtifact);
      }
    });

    return result;
  }
}
