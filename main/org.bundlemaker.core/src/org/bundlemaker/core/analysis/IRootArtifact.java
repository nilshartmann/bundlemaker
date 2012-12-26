package org.bundlemaker.core.analysis;

import java.util.concurrent.Callable;

import org.bundlemaker.core.modules.IGroup;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;

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
   * </p>
   */
  void resetTransformations();

  /**
   * <p>
   * Adds the specified {@link IAnalysisModelModifiedListener} to the artifact tree.
   * </p>
   * 
   * @param listener
   *          the listener to add (must not be null)
   */
  void addAnalysisModelModifiedListener(IAnalysisModelModifiedListener listener);

  /**
   * <p>
   * Removes the specified {@link IAnalysisModelModifiedListener} from the artifact tree.
   * </p>
   * 
   * @param listener
   *          the listener to remove (must not be null)
   */
  void removeAnalysisModelModifiedListener(IAnalysisModelModifiedListener listener);

  /**
   * <p>
   * </p>
   * 
   * @param isDisabled
   */
  void disableModelModifiedNotification(boolean isDisabled);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isModelModifiedNotificationDisabled();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean areCachesInitialized();

  /**
   * <p>
   * </p>
   * 
   * @param progressMonitor
   */
  void initializeCaches(IProgressMonitor progressMonitor);

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

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static class Factory {

    /**
     * <p>
     * </p>
     * 
     * @param callable
     * @throws Exception
     */
    public static <T> T executeWithoutNotification(IRootArtifact rootArtifact, Callable<T> callable) throws Exception {

      //
      Assert.isNotNull(rootArtifact);
      Assert.isNotNull(callable);

      //
      boolean modelModifiedNotificationDisabled = rootArtifact.isModelModifiedNotificationDisabled();

      // Run the script
      try {

        //
        rootArtifact.disableModelModifiedNotification(true);

        //
        return callable.call();
      }

      //
      finally {
        rootArtifact.disableModelModifiedNotification(modelModifiedNotificationDisabled);
      }
    }
  }
}
