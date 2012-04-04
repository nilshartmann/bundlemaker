package org.bundlemaker.core.ui.view.dependencytree;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;

public class Helper {

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerArtifacts
   * @return
   */
  public static IBundleMakerArtifact[] getChildrenOfCommonParent(Collection<IBundleMakerArtifact> bundleMakerArtifacts) {

    //
    IBundleMakerArtifact commonParent = getCommonParent(bundleMakerArtifacts);

    //
    if (commonParent == null) {
      return new IBundleMakerArtifact[0];
    }

    //
    return commonParent.getChildren().size() == 0 ? new IBundleMakerArtifact[] { commonParent } : commonParent
        .getChildren().toArray(new IBundleMakerArtifact[0]);
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerArtifacts
   * @return
   */
  public static IBundleMakerArtifact getCommonParent(Collection<IBundleMakerArtifact> bundleMakerArtifacts) {

    //
    if (bundleMakerArtifacts == null || bundleMakerArtifacts.isEmpty()) {
      return null;
    }

    // initial parent list
    List<IBundleMakerArtifact> parents = new LinkedList<IBundleMakerArtifact>();
    IBundleMakerArtifact currentArtifact = bundleMakerArtifacts.toArray(new IBundleMakerArtifact[0])[0];
    parents.add(currentArtifact);
    while (currentArtifact != null && !currentArtifact.getType().equals(ArtifactType.Root)) {
      currentArtifact = currentArtifact.getParent();
      parents.add(currentArtifact);
    }

    //
    for (IBundleMakerArtifact artifact : bundleMakerArtifacts) {
      List<IBundleMakerArtifact> commonParents = new LinkedList<IBundleMakerArtifact>();
      if (parents.contains(artifact)) {
        commonParents.add(artifact);
      }
      while (artifact != null && !artifact.getType().equals(ArtifactType.Root)) {
        artifact = artifact.getParent();
        if (parents.contains(artifact)) {
          commonParents.add(artifact);
        }
      }
      parents = commonParents;
    }

    // return null
    return parents.get(0);
  }

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
