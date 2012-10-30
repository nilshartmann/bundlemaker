package org.bundlemaker.core.analysis;

import org.bundlemaker.core.modules.IGroup;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.resource.IResource;

/**
 * <p>
 * Defines the root {@link IBundleMakerArtifact} that holds the {@link IModularizedSystem} instance. The root artifact
 * can contain group and modules and therefore extends the interface {@link IGroupAndModuleContainer}.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IRootArtifact extends IBundleMakerArtifact, IGroupAndModuleContainer {

  /**
   * <p>
   * Adds the specified {@link IAnalysisModelModifiedListener} to the artifact tree.
   * </p>
   * 
   * @param listener
   *          the listener to add (must not be null)
   */
  void addArtifactModelChangedListener(IAnalysisModelModifiedListener listener);

  /**
   * <p>
   * Removes the specified {@link IAnalysisModelModifiedListener} from the artifact tree.
   * </p>
   * 
   * @param listener
   *          the listener to remove (must not be null)
   */
  void removeArtifactModelChangedListener(IAnalysisModelModifiedListener listener);

  /**
   * <p>
   * Returns the {@link IGroupArtifact} for the given {@link IGroup}.
   * </p>
   * 
   * @param group
   *          (must not be null)
   * @return the {@link IGroupArtifact} for the given {@link IGroup} (maybe null)
   */
  IGroupArtifact getGroupArtifact(IGroup group);

  /**
   * <p>
   * Returns the {@link IModuleArtifact} for the given {@link IModule}.
   * </p>
   * 
   * @param module
   *          (must not be null)
   * @return the {@link IModuleArtifact} for the given {@link IModule} (maybe null)
   */
  IModuleArtifact getModuleArtifact(IModule module);

  /**
   * <p>
   * Returns the {@link IResourceArtifact} for the given {@link IResource}.
   * </p>
   * 
   * @param resource
   *          (must not be null)
   * @return the {@link IResourceArtifact} for the given {@link IResource} (maybe null)
   */
  IResourceArtifact getResourceArtifact(IResource resource);
}
