package org.bundlemaker.core.ui.artifact.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.ui.artifact.configuration.IArtifactModelConfigurationProvider;
import org.bundlemaker.core.ui.artifact.internal.Activator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * <p>
 * </p>
 * 
 * @author Kai Lehmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactTreeContentProvider implements ITreeContentProvider {

  /** EMPTY_OBJECT_ARRAY */
  private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

  /** - */
  private boolean               _showRoot;

  /** needed to show the root in the tree viewer, see [BM-165 Show Root-Node in Dependency Tree / XRef View] */
  private VirtualRoot           _virtualRoot;

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

            IBundleMakerArtifact artifact = modularizedSystem.getArtifactModel(artifactModelConfigurationProvider
                .getArtifactModelConfiguration());

            // ModelTransformer.dumpArtifact(artifact);

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
    } else if (parent instanceof VirtualRoot) {
      return ((VirtualRoot) parent).getChildren();
    } else if (parent instanceof IBundleMakerArtifact) {

      IBundleMakerArtifact parentArtifact = (IBundleMakerArtifact) parent;

      List<IBundleMakerArtifact> artifacts = new ArrayList<IBundleMakerArtifact>();

      for (IBundleMakerArtifact iArtifact : parentArtifact.getChildren()) {
        if (iArtifact.getType().equals(ArtifactType.Package)) {
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
        _virtualRoot = new VirtualRoot((IRootArtifact) parent);
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
  public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public class VirtualRoot {

    private IRootArtifact _rootArtifact;

    /**
     * <p>
     * Creates a new instance of type {@link VirtualRoot}.
     * </p>
     * 
     * @param rootArtifact
     */
    public VirtualRoot(IRootArtifact rootArtifact) {
      Assert.isNotNull(rootArtifact);

      _rootArtifact = rootArtifact;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public String getName() {
      return _rootArtifact.getName();
    }

    /** - */
    public Object[] getChildren() {
      //
      List<Object> artifacts = new ArrayList<Object>();

      for (IBundleMakerArtifact iArtifact : _rootArtifact.getChildren()) {
        artifacts.add(iArtifact);
      }

      return artifacts.toArray();
    }
  }
}
