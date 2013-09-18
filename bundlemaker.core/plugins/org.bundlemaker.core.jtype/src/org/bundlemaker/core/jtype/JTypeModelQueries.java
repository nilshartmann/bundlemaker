package org.bundlemaker.core.jtype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.types.selectors.SelectorUtils;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact.IResourceArtifactContent;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.common.utils.FileUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Helper class that provides several static methods useful when working with analysis models.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JTypeModelQueries {

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @param moduleIdentifier
   * @return
   */
  public static IModuleArtifact getMissingTypesModuleArtifact(IBundleMakerArtifact root) {

    // create the result array
    final IModuleArtifact[] result = new IModuleArtifact[1];

    // visit
    root.getRoot().accept(new IAnalysisModelVisitor.Adapter() {
      @SuppressWarnings("deprecation")
      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {

        //
        if (moduleArtifact.isVirtual() && moduleArtifact.getName().equals("<< Missing Types >>")) {
          result[0] = moduleArtifact;
        }

        //
        return false;
      }
    });

    // return result
    return result[0];
  }

  /**
   * <p>
   * </p>
   * 
   * @param packageArtifact
   * @param string
   */
  public static ITypeArtifact findTypeArtifactByQualifiedName(IBundleMakerArtifact root, final String qualifiedName) {

    // create the result array
    final List<ITypeArtifact> result = new LinkedList<ITypeArtifact>();

    // visit
    root.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IResourceArtifactContent resourceArtifact) {

        //
        if (resourceArtifact instanceof ITypeArtifact) {
          if (((ITypeArtifact) resourceArtifact).getAssociatedType().getFullyQualifiedName().equals(qualifiedName)) {
            result.add((ITypeArtifact) resourceArtifact);
          }
        }

        //
        return true;
      }
    });

    //
    if (result.size() > 1) {
      throwException(String.format("Multiple types with name '%s' exist.", qualifiedName));
    } else if (result.size() == 0) {
      return null;
    }

    // return result
    return result.get(0);
  }

  public static List<ITypeArtifact> findTypeArtifactsByQualifiedName(IBundleMakerArtifact root,
      final String qualifiedName) {

    // create the result array
    final List<ITypeArtifact> result = new LinkedList<ITypeArtifact>();

    // visit
    root.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IResourceArtifact resourceArtifact) {

        //
        if (resourceArtifact instanceof ITypeArtifact) {
          if (((ITypeArtifact) resourceArtifact).getAssociatedType().getFullyQualifiedName().equals(qualifiedName)) {
            result.add((ITypeArtifact) resourceArtifact);
          }
        }
        //
        return true;
      }
    });

    // return result
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @param name
   * @return
   */
  public static IPackageArtifact findPackageArtifactByQualifiedName(IBundleMakerArtifact root,
      final String fullyQualifiedName) {

    // create the result array
    final List<IPackageArtifact> result = findPackageArtifactsByQualifiedName(root, fullyQualifiedName);

    //
    if (result.size() > 1) {
      throwException(String.format("Multiple packages with name '%s' exist.", fullyQualifiedName));
    } else if (result.size() == 0) {
      return null;
    }

    // return result
    return result.get(0);
  }

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @param fullyQualifiedName
   * @return
   */
  public static List<IPackageArtifact> findPackageArtifactsByQualifiedName(IBundleMakerArtifact root,
      final String fullyQualifiedName) {

    // create the result array
    final List<IPackageArtifact> result = new LinkedList<IPackageArtifact>();

    // visit
    root.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IPackageArtifact packageArtifact) {

        //
        if (packageArtifact.getQualifiedName().equals(fullyQualifiedName)) {
          result.add(packageArtifact);
        }

        //
        return true;
      }
    });

    // return result
    return result;
  }

  public static List<IPackageArtifact> findPackageArtifacts(IBundleMakerArtifact root,
      final Matcher<IPackageArtifact> matcher) {
    // create the result array
    final List<IPackageArtifact> result = new LinkedList<IPackageArtifact>();

    // visit
    root.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IPackageArtifact packageArtifact) {

        //
        if (matcher.matches(packageArtifact)) {
          result.add(packageArtifact);
        }

        //
        return true;
      }
    });

    // return result
    return result;

  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param artifacts
   * @return
   */
  private static Set<IBundleMakerArtifact> resolveReferencedArtifacts(IBundleMakerArtifact artifact,
      Set<IBundleMakerArtifact> artifacts, boolean transitive) {

    //
    Assert.isNotNull(artifact);
    Assert.isNotNull(artifacts);

    //
    for (IDependency dependency : artifact.getDependenciesTo()) {
      if (artifacts.add(dependency.getTo()) && transitive) {
        resolveReferencedArtifacts(dependency.getTo(), artifacts, transitive);
      }
    }

    //
    return artifacts;
  }

  /**
   * <p>
   * </p>
   * 
   * @param format
   */
  private static void throwException(String format) {
    // TODO
    throw new RuntimeException(format);
  }

  /************************************************************************************************************/
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
    IAnalysisModelVisitor visitor = new IAnalysisModelVisitor() {

      @Override
      public boolean visit(IPackageArtifact artifact) {
        check(regexp, clazz, result, artifact);
        return true;
      }

      @Override
      public boolean visit(IResourceArtifactContent artifact) {
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
    IAnalysisModelVisitor visitor = new IAnalysisModelVisitor() {

      @Override
      public boolean visit(IPackageArtifact artifact) {
        check(clazz, result, artifact);
        return true;
      }

      @Override
      public boolean visit(IResourceArtifactContent artifact) {
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
      public boolean visit(IResourceArtifactContent typeArtifact) {
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

  public static interface Matcher<T extends IBundleMakerArtifact> {

    public boolean matches(T artifact);

  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private static class GenericAnalysisModelVisitor<T> implements IAnalysisModelVisitor {

    /** the class object */
    private Class<T> _clazz;

    /**
     * <p>
     * Creates a new instance of type {@link GenericAnalysisModelVisitor}.
     * </p>
     * 
     * @param clazz
     */
    public GenericAnalysisModelVisitor(Class<T> clazz) {
      Assert.isNotNull(clazz);

      //
      _clazz = clazz;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean visit(IPackageArtifact artifact) {
      handleNode(artifact);
      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean visit(IResourceArtifactContent artifact) {
      handleNode(artifact);
      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean visit(IResourceArtifact artifact) {
      handleNode(artifact);
      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean visit(IModuleArtifact artifact) {
      handleNode(artifact);
      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean visit(IGroupArtifact artifact) {
      handleNode(artifact);
      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean visit(IRootArtifact artifact) {
      handleNode(artifact);
      return true;
    }

    /**
     * <p>
     * </p>
     * 
     * @param artifact
     */
    @SuppressWarnings("unchecked")
    private void handleNode(IBundleMakerArtifact artifact) {
      if (_clazz.isAssignableFrom(artifact.getClass())) {
        onHandle((T) artifact);
      }
    }

    /**
     * <p>
     * </p>
     * 
     * @param t
     */
    protected void onHandle(T t) {
      //
    }
  }
}
