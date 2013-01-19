package org.bundlemaker.core.ui.artifact.tree;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.IAnalysisModelModifiedListener;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.artifact.configuration.IArtifactModelConfigurationProvider;
import org.bundlemaker.core.ui.artifact.internal.Activator;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * <p>
 * </p>
 * 
 * @author Kai Lehmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactTreeContentProvider implements ITreeContentProvider, IVirtualRootContentProvider {

  /** EMPTY_OBJECT_ARRAY */
  private static final Object[]                                 EMPTY_OBJECT_ARRAY                    = new Object[0];

  /**
   * Listener used to refresh navigator on artifact model changes
   */
  private final static RefreshArtifactTreeModelModifiedListener ARTIFACT_TREE_MODEL_MODIFIED_LISTENER = new RefreshArtifactTreeModelModifiedListener();

  /** - */
  private boolean                                               _showRoot;

  /** needed to show the root in the tree viewer, see [BM-165 Show Root-Node in Dependency Tree / XRef View] */
  private IRootArtifact                                         _virtualRoot;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactTreeContentProvider}.
   * </p>
   */
  public ArtifactTreeContentProvider() {
    this(false);
  }

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactTreeContentProvider}.
   * </p>
   * 
   * @param _showRoot
   */
  public ArtifactTreeContentProvider(boolean _showRoot) {
    this._showRoot = _showRoot;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getVirtualRoot() {
    return _virtualRoot;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[] getChildren(Object parent) {

    if (parent instanceof IProject) {

      IProject project = (IProject) parent;

      try {

        //
        if (project.hasNature(BundleMakerCore.NATURE_ID)) {

          //
          IBundleMakerProject bundleMakerProject = BundleMakerCore.getBundleMakerProject(project, null);

          //
          Collection<IModularizedSystem> modularizedSystems = bundleMakerProject.getModularizedSystemWorkingCopies();

          List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();
          for (IModularizedSystem modularizedSystem : modularizedSystems) {

            //
            // modularizedSystem.applyTransformations();
            IArtifactModelConfigurationProvider artifactModelConfigurationProvider = Activator.getDefault()
                .getArtifactModelConfigurationProvider();

            IRootArtifact artifact = modularizedSystem.getAnalysisModel(artifactModelConfigurationProvider
                .getArtifactModelConfiguration());

            // // TODO!
            artifact.addAnalysisModelModifiedListener(ARTIFACT_TREE_MODEL_MODIFIED_LISTENER);

            result.add(artifact);
          }

          //
          return result.toArray();
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      //
      return EMPTY_OBJECT_ARRAY;
    } else if (parent instanceof IBundleMakerArtifact) {

      IBundleMakerArtifact parentArtifact = (IBundleMakerArtifact) parent;

      List<IBundleMakerArtifact> artifacts = new ArrayList<IBundleMakerArtifact>();

      for (IBundleMakerArtifact iArtifact : parentArtifact.getChildren()) {
        if (iArtifact.isInstanceOf(IPackageArtifact.class)) {
          if (iArtifact.containsTypesOrResources()) {
            artifacts.add(iArtifact);
          } else {
            //
          }
        } else {
          artifacts.add(iArtifact);
        }
      }

      return artifacts.toArray();
    }
    return EMPTY_OBJECT_ARRAY;
  }

  @Override
  public Object getParent(Object input) {

    if (input instanceof IBundleMakerArtifact) {

      if (_showRoot && ((IBundleMakerArtifact) input).getParent() instanceof IRootArtifact) {
        return _virtualRoot;
      } else {
        return ((IBundleMakerArtifact) input).getParent();
      }
    }

    //
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasChildren(Object parent) {
    return getChildren(parent).length > 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[] getElements(Object parent) {

    if (_showRoot && parent instanceof IRootArtifact) {
      if (_virtualRoot == null) {
        _virtualRoot = newVirtualRoot((IRootArtifact) parent);
      }
      return new Object[] { _virtualRoot };
    } else {
      return getChildren(parent);
    }
  }

  @Override
  public void dispose() {
  }

  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    if (newInput != null && newInput instanceof IRootArtifact) {
      _virtualRoot = newVirtualRoot((IRootArtifact) newInput);
    } else {
      _virtualRoot = null;
    }
  }

  private static IRootArtifact newVirtualRoot(final IRootArtifact rootArtifact) {
    return (IRootArtifact) Proxy.newProxyInstance
        (rootArtifact
            .getClass().getClassLoader(),
            new Class[] { IRootArtifact.class },
            new InvocationHandler() {
              @Override
              public Object invoke(Object proxy, Method method,
                  Object[] args) throws Throwable {
                return method.invoke(rootArtifact, args);
              }
            });
  }

  static class RefreshArtifactTreeModelModifiedListener implements IAnalysisModelModifiedListener {
    @Override
    public void analysisModelModified() {
      //
      CommonNavigatorUtils.update(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);
    }
  }

}
