package org.bundlemaker.core.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;

public class ArtifactUtils {

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param level
   * @param stringBuilder
   */
  public static void dumpArtifact(IArtifact artifact, int level, StringBuilder stringBuilder) {

    //
    for (int i = 0; i < level; i++) {
      stringBuilder.append("  ");
    }

    //
    stringBuilder.append(artifact.getType());
    stringBuilder.append(" : ");
    stringBuilder.append(artifact.getQualifiedName());
    stringBuilder.append("\n");

    List<IArtifact> sorted = new ArrayList<IArtifact>(artifact.getChildren());
    Collections.sort(sorted, new Comparator<IArtifact>() {
      @Override
      public int compare(IArtifact o1, IArtifact o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });

    for (IArtifact child : sorted) {
      // if (child.getType().equals(ArtifactType.Root) || child.getType().equals(ArtifactType.Group)
      // || child.getType().equals(ArtifactType.Module)) {
      dumpArtifact(child, level + 1, stringBuilder);
      // }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  public static void dumpArtifact(IArtifact artifact) {
    StringBuilder builder = new StringBuilder();
    dumpArtifact(artifact, 0, builder);
    System.out.println(builder.toString());
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  public static String artifactToString(IArtifact artifact) {
    StringBuilder builder = new StringBuilder();
    dumpArtifact(artifact, 0, builder);
    return builder.toString();
  }

}
