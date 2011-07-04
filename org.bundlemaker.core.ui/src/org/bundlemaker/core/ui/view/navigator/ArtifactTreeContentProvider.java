package org.bundlemaker.core.ui.view.navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
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
  private static final Object[] EMPTY_OBJECT_ARRAY   = new Object[0];

  private Comparator<IArtifact> alphabeticComparator = new Comparator<IArtifact>() {
                                                       @Override
                                                       public int compare(IArtifact o1, IArtifact o2) {
                                                         return o1.getName().compareTo(o2.getName());
                                                       }
                                                     };

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
//            modularizedSystem.applyTransformations();

            try {
              IArtifact artifact = ModelTransformer.transform((IModifiableModularizedSystem) modularizedSystem);
              // ModelTransformer.dumpArtifact(artifact);
              result.add(artifact);
            } catch (CoreException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }

          }

          //
          return result.toArray();
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      //
      return EMPTY_OBJECT_ARRAY;
    }
    // if (parent instanceof List<?>) {
    // List<IArtifact> artifacts = (List<IArtifact>) parent;
    //
    // Collections.sort(artifacts, alphabeticComparator);
    // return artifacts.toArray();
    // }
    else if (parent instanceof IArtifact) {
      IArtifact parentArtifact = (IArtifact) parent;
      List<IArtifact> artifacts = new ArrayList<IArtifact>();
      artifacts.addAll(parentArtifact.getChildren());
      Collections.sort(artifacts, alphabeticComparator);

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
