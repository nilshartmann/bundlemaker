package org.bundlemaker.core.project;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProjectDescriptionChangedEvent {

  /** the bundle maker project */
  private IProjectDescriptionAwareBundleMakerProject _bundleMakerProject;

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public enum Type {

    /** - */
    PROJECT_DESCRIPTION_RELOADED,

    /**
     * The project description has been modified via the api. This means the project description is 'dirty', i.e. not
     * written to disc
     */
    PROJECT_DESCRIPTION_MODIFIED,

    /** The project description has been saved to disc */
    PROJECT_DESCRIPTION_SAVED,

    /** the project description has been recomputed **/
    PROJECT_DESCRIPTION_RECOMPUTED;
  }

  /** the cause for this event */
  private Type _type;

  /**
   * <p>
   * Creates a new instance of type {@link BundleMakerProjectDescriptionChangedEvent}.
   * </p>
   * 
   * @param type
   */
  public BundleMakerProjectDescriptionChangedEvent(IProjectDescriptionAwareBundleMakerProject project, Type type) {
    Assert.isNotNull(project, "Parameter 'project' must not be null.");
    Assert.isNotNull(type, "Parameter 'type' must not be null.");

    _type = type;
    _bundleMakerProject = project;
  }

  /**
   * <p>
   * Returns the cause for this event.
   * </p>
   * 
   * @return the cause for this event.
   */
  public Type getType() {
    return _type;
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
