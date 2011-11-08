package org.bundlemaker.core.testutils;

import java.util.Comparator;
import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactTestUtil {

  /**
   * <p>
   * </p>
   * 
   * @param module
   * @return
   */
  public static String toString(IArtifact root) {

    StringBuilder builder = new StringBuilder();
    toString(root, builder, 0);

    List<IDependency> dependencies = BundleMakerTestUtils.asSortedList(root.getDependencies(),
        new Comparator<IDependency>() {
          @Override
          public int compare(IDependency o1, IDependency o2) {
            String dep1 = dumpDependency(o1);
            String dep2 = dumpDependency(o2);
            return dep1.compareTo(dep2);
          }
        });

    for (IDependency dependency : dependencies) {
      builder.append(dumpDependency(dependency));
      builder.append("\n");
    }

    return builder.toString();
  }

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @param stringBuilder
   */
  private static void toString(IArtifact artifact, StringBuilder builder, int offset) {

    //
    for (int i = 0; i < offset; i++) {
      builder.append(" ");
    }

    //
    builder.append(artifact.getType());
    builder.append(" : ");
    builder.append(artifact.getQualifiedName());
    builder.append("\n");

    //
    List<? extends IArtifact> children = BundleMakerTestUtils.asSortedList((List<IArtifact>)artifact.getChildren(), new Comparator<IArtifact>() {
      @Override
      public int compare(IArtifact o1, IArtifact o2) {
        return o1.getQualifiedName().compareTo(o2.getQualifiedName());
      }
    });
    for (IArtifact child : children) {
      toString(child, builder, offset + 1);
    }
  }

  /**
   * <p>
   * </p>
   */
  public static String dumpDependency(IDependency iDependency) {
    return iDependency.getFrom().getQualifiedName() + " -- " + iDependency.getDependencyKind() + " ["
        + iDependency.getWeight() + "]" + " --> " + iDependency.getTo().getQualifiedName();
  }
}
