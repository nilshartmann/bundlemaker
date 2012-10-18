package org.bundlemaker.core.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.analysis.model.IDependency;
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
    while (currentArtifact != null && !currentArtifact.isInstanceOf(IRootArtifact.class)) {
      currentArtifact = currentArtifact.getParent();
      parents.add(currentArtifact);
    }

    //
    for (IBundleMakerArtifact artifact : bundleMakerArtifacts) {
      List<IBundleMakerArtifact> commonParents = new LinkedList<IBundleMakerArtifact>();
      if (parents.contains(artifact)) {
        commonParents.add(artifact);
      }
      while (artifact != null && !currentArtifact.isInstanceOf(IRootArtifact.class)) {
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

    if (dependencies == null) {
      return Collections.emptyList();
    }

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
    for (IDependency dependency : artifact.getDependencies()) {

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
    return solveArtifacts(artifact, new HashSet<IBundleMakerArtifact>(), true);
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param artifacts
   * @return
   */
  private static Set<IBundleMakerArtifact> solveArtifacts(IBundleMakerArtifact artifact,
      Set<IBundleMakerArtifact> artifacts, boolean transitive) {

    //
    Assert.isNotNull(artifact);
    Assert.isNotNull(artifacts);

    //
    for (IDependency dependency : artifact.getDependencies()) {
      if (artifacts.add(dependency.getTo()) && transitive) {
        solveArtifacts(dependency.getTo(), artifacts, transitive);
      }
    }

    //
    return artifacts;
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param level
   * @param stringBuilder
   */
  private static void dumpArtifact(IBundleMakerArtifact artifact, int level, StringBuilder stringBuilder, int limit) {

    // limit
    if (limit != -1 && level >= limit) {
      return;
    }

    //
    for (int i = 0; i < level; i++) {
      stringBuilder.append("  ");
    }

    //
    stringBuilder.append(artifact.getClass().getName());
    stringBuilder.append(" : ");
    stringBuilder.append(artifact.getUniquePathIdentifier());
    stringBuilder.append("\n");

    List<IBundleMakerArtifact> sorted = new ArrayList<IBundleMakerArtifact>(artifact.getChildren());
    Collections.sort(sorted, new Comparator<IBundleMakerArtifact>() {
      @Override
      public int compare(IBundleMakerArtifact o1, IBundleMakerArtifact o2) {
        return o1.getUniquePathIdentifier().compareTo(o2.getUniquePathIdentifier());
      }
    });

    for (IBundleMakerArtifact child : sorted) {
      // if (child.getType().equals(ArtifactType.Root) || child.getType().equals(ArtifactType.Group)
      // || child.getType().equals(ArtifactType.Module)) {
      dumpArtifact(child, level + 1, stringBuilder, limit);
      // }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  public static void dumpArtifact(IBundleMakerArtifact artifact) {

    if (artifact == null) {
      System.out.println("Artifact is 'null'.");
      return;
    }

    StringBuilder builder = new StringBuilder();
    dumpArtifact(artifact, 0, builder, -1);
    System.out.println(builder.toString());
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  public static void dumpArtifact(IBundleMakerArtifact artifact, int limit) {
    StringBuilder builder = new StringBuilder();
    dumpArtifact(artifact, 0, builder, limit);
    System.out.println(builder.toString());
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  public static String artifactToString(IBundleMakerArtifact artifact) {
    StringBuilder builder = new StringBuilder();
    dumpArtifact(artifact, 0, builder, -1);
    return builder.toString();
  }
}
