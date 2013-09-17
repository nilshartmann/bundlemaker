package org.bundlemaker.core.project;

import org.bundlemaker.core.IBundleMakerProject;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * A {@link BundleMakerProjectContentChangedEvent} is thrown if the state of an {@link IBundleMakerProject} has changed.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclasses by clients.
 */
public class BundleMakerProjectStateChangedEvent {

  /** the bundle maker project */
  private IProjectDescriptionAwareBundleMakerProject _bundleMakerProject;

  /**
   * <p>
   * Creates a new instance of type {@link BundleMakerProjectStateChangedEvent}.
   * </p>
   * 
   * @param project
   *          the changed project (must not be null)
   */
  public BundleMakerProjectStateChangedEvent(IProjectDescriptionAwareBundleMakerProject project) {
    Assert.isNotNull(project, "Parameter 'project' must not be null.");

    _bundleMakerProject = project;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IProjectDescriptionAwareBundleMakerProject getBundleMakerProject() {
    return _bundleMakerProject;
  }

  /**
   * <p>
   * </p>
   * 
   * @param clazz
   * @return
   */
  public <T extends IProjectDescriptionAwareBundleMakerProject> T getBundleMakerProject(Class<T> clazz) {
    return _bundleMakerProject.adaptAs(clazz);
  }
}
