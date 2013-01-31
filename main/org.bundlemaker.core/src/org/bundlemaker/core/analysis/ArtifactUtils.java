package org.bundlemaker.core.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * @deprecated In a long term all methods that are provided by this class should move to {@link AnalysisModelQueries}.
 *             We have to review if the methods that still reside in this class are really needed..
 */
@Deprecated
public class ArtifactUtils {

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
    bundleMakerArtifact.accept(new IAnalysisModelVisitor.Adapter() {

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
