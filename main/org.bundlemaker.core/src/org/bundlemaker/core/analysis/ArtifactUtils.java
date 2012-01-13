package org.bundlemaker.core.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArtifactUtils {

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
    stringBuilder.append(artifact.getType());
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
