package org.bundlemaker.core.analysis;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Assert;

public class ArtifactHelper {

  /**
   * {@inheritDoc}
   */
  public static <T extends IBundleMakerArtifact> List<T> findChildren(IBundleMakerArtifact artifact,
      final String regexp, final Class<T> clazz) {

    Assert.isNotNull(regexp);
    Assert.isNotNull(clazz);

    //
    final List<T> result = new LinkedList<T>();

    //
    IArtifactTreeVisitor visitor = new IArtifactTreeVisitor() {

      @Override
      public boolean visit(IPackageArtifact artifact) {
        check(regexp, clazz, result, artifact);
        return true;
      }

      @Override
      public boolean visit(ITypeArtifact artifact) {
        check(regexp, clazz, result, artifact);
        return true;
      }

      @Override
      public boolean visit(IResourceArtifact artifact) {
        check(regexp, clazz, result, artifact);
        return true;
      }

      @Override
      public boolean visit(IModuleArtifact artifact) {
        check(regexp, clazz, result, artifact);
        return true;
      }

      @Override
      public boolean visit(IGroupArtifact artifact) {
        check(regexp, clazz, result, artifact);
        return true;
      }

      @Override
      public boolean visit(IRootArtifact artifact) {
        check(regexp, clazz, result, artifact);
        return true;
      }

      private <T> void check(final String value, final Class<T> clazz, final List<T> result,
          IBundleMakerArtifact artifact) {

        //
        if (!clazz.isAssignableFrom(artifact.getClass())) {
          return;
        }

        //
        if (artifact.getQualifiedName().equals(value)) {
          result.add((T) artifact);
        } else if (artifact.getName().equals(value)) {
          result.add((T) artifact);
        } else if (artifact instanceof IModuleArtifact && ((IModuleArtifact) artifact).getModuleName().equals(value)) {
          result.add((T) artifact);
        } else if (artifact.getQualifiedName().matches(value)) {
          result.add((T) artifact);
        } else if (artifact.getName().matches(value)) {
          result.add((T) artifact);
        }
      }
    };

    artifact.accept(visitor);

    //
    return result;
  }
}
