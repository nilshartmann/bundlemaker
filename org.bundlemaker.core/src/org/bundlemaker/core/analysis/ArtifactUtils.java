package org.bundlemaker.core.analysis;

import org.bundlemaker.dependencyanalysis.base.model.IArtifact;

public class ArtifactUtils {

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param level
   */
  private static void dumpArtifact(IArtifact artifact, int level) {

    //
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < level; i++) {
      builder.append("  ");
    }

    System.out.println(builder.toString() + artifact.getType() + " : " + artifact.getQualifiedName());

    for (IArtifact child : artifact.getChildren()) {
      dumpArtifact(child, level + 1);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  public static void dumpArtifact(IArtifact artifact) {
    dumpArtifact(artifact, 0);
  }

}
