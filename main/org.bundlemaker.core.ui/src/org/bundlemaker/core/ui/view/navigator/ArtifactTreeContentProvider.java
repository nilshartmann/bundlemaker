package org.bundlemaker.core.ui.view.navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.ui.Activator;
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
public class ArtifactTreeContentProvider implements ITreeContentProvider {

  /** EMPTY_OBJECT_ARRAY */
  private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

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

          List<IArtifact> result = new LinkedList<IArtifact>();
          for (IModularizedSystem modularizedSystem : modularizedSystems) {

            //
            // modularizedSystem.applyTransformations();
            IArtifactModelConfigurationProvider artifactModelConfigurationProvider = Activator.getDefault()
                .getArtifactModelConfigurationProvider();

            IArtifact artifact = modularizedSystem.getArtifactModel(artifactModelConfigurationProvider
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
    } else if (parent instanceof IArtifact) {

      IArtifact parentArtifact = (IArtifact) parent;

      List<IArtifact> artifacts = new ArrayList<IArtifact>();

      for (IArtifact iArtifact : parentArtifact.getChildren()) {
        if (iArtifact.getType().equals(ArtifactType.Package)) {
          if (((IAdvancedArtifact) iArtifact).containsTypesOrResources()) {
            // System.out.println("ADDING " + iArtifact + " : " + iArtifact.getChildren());
            artifacts.add(iArtifact);
          } else {
            // System.out.println("SKIPPING " + iArtifact);
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

    if (input instanceof IArtifact) {
      return ((IArtifact) input).getParent();
    }
    return null;
  }

  @Override
  public boolean hasChildren(Object parent) {

    return getChildren(parent).length > 0;
  }

  @Override
  public Object[] getElements(Object parent) {

    return getChildren(parent);
  }

  @Override
  public void dispose() {

  }

  @Override
  public void inputChanged(Viewer arg0, Object arg1, Object arg2) {

  }

}
