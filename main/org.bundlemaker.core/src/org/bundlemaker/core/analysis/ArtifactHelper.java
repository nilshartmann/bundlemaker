package org.bundlemaker.core.analysis;

import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.types.selectors.SelectorUtils;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * @deprecated we need a common place for all visitor utilities... see
 *             org.bundlemaker.core.itest.analysis.ArtifactVisitorUtils
 */
@Deprecated
public class ArtifactHelper {

  /**
   * <p>
   * </p>
   * 
   * @param <T>
   * @param artifact
   * @param regexp
   * @param clazz
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T extends IBundleMakerArtifact> T getChildByPath(IBundleMakerArtifact artifact, final IPath path,
      final Class<T> clazz) {

    //
    if (artifact == null) {
      return null;
    }

    Assert.isNotNull(clazz);

    if (path.segmentCount() > 1) {
      return getChildByPath(artifact.getChild(path.segments()[0]), path.removeFirstSegments(1), clazz);
    } else {

      //
      IBundleMakerArtifact result = artifact.getChild(path.segments()[0]);

      //
      if (result != null && !(clazz.isAssignableFrom(result.getClass()))) {
        throw new RuntimeException();
      }

      //
      return (T) result;
    }
  }

  public static <T extends IBundleMakerArtifact> T findChild(IBundleMakerArtifact artifact, final String regexp,
      final Class<T> clazz) {

    List<T> children = findChildren(artifact, regexp, clazz);

    if (children.size() == 0) {
      return null;
    } else if (children.size() == 1) {
      return children.get(0);
    } else {
      // TODO
      throw new RuntimeException();
    }
  }

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

      private <T> void check(String value, final Class<T> clazz, final List<T> result, IBundleMakerArtifact artifact) {

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
        } else {
          String artifactName = artifact.getName();
          String artifactQualifiedName = artifact.getQualifiedName();
          if (clazz.isAssignableFrom(IPackageArtifact.class) || clazz.isAssignableFrom(ITypeArtifact.class)) {
            artifactName = artifactName.replace('.', '/');
            artifactQualifiedName = artifactQualifiedName.replace('.', '/');
            value = value.replace('.', '/');
          }
          artifactName = FileUtils.normalize(artifactName);
          artifactQualifiedName = FileUtils.normalize(artifactQualifiedName);
          value = FileUtils.normalize(value);

          //
          if (SelectorUtils.matchPath(value, artifactName, false)
              || SelectorUtils.matchPath(value, artifactQualifiedName, false))
            result.add((T) artifact);
        }
      }
    };

    artifact.accept(visitor);

    //
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public static <T extends IBundleMakerArtifact> List<T> findChildren(IBundleMakerArtifact artifact,
      final Class<T> clazz) {

    Assert.isNotNull(clazz);

    //
    final List<T> result = new LinkedList<T>();

    //
    IArtifactTreeVisitor visitor = new IArtifactTreeVisitor() {

      @Override
      public boolean visit(IPackageArtifact artifact) {
        check(clazz, result, artifact);
        return true;
      }

      @Override
      public boolean visit(ITypeArtifact artifact) {
        check(clazz, result, artifact);
        return true;
      }

      @Override
      public boolean visit(IResourceArtifact artifact) {
        check(clazz, result, artifact);
        return true;
      }

      @Override
      public boolean visit(IModuleArtifact artifact) {
        check(clazz, result, artifact);
        return true;
      }

      @Override
      public boolean visit(IGroupArtifact artifact) {
        check(clazz, result, artifact);
        return true;
      }

      @Override
      public boolean visit(IRootArtifact artifact) {
        check(clazz, result, artifact);
        return true;
      }

      private <T> void check(final Class<T> clazz, final List<T> result, IBundleMakerArtifact artifact) {

        //
        if (!clazz.isAssignableFrom(artifact.getClass())) {
          return;
        }

        //
        result.add((T) artifact);
      }
    };

    artifact.accept(visitor);

    //
    return result;
  }
}
