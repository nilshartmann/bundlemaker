package org.bundlemaker.core.project;

import org.bundlemaker.core.IBundleMakerProject;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * A {@link BundleMakerProjectContentChangedEvent} is thrown if the content (not the description!) of a
 * {@link IBundleMakerProject} has changed.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclasses by clients.
 */
public class BundleMakerProjectContentChangedEvent {

  /**
   * <p>
   * Defined the possible types of a {@link BundleMakerProjectContentChangedEvent}.
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   * 
   * @noextend This class is not intended to be subclasses by clients.
   */
  public enum Type {
    ADDED, REMOVED, MODIFIED;
  }

  /** the type */
  private Type                                       _type;

  /** - */
  private IProjectContentResource                    _contentResource;

  /** the bundle maker project */
  private IProjectDescriptionAwareBundleMakerProject _bundleMakerProject;

  /**
   * <p>
   * </p>
   * 
   * @param type
   */
  public BundleMakerProjectContentChangedEvent(IProjectDescriptionAwareBundleMakerProject project, Type type,
      IProjectContentResource contentResource) {
    Assert.isNotNull(type, "Parameter 'type' must not be null.");
    Assert.isNotNull(contentResource, "Parameter 'contentResource' must not be null.");
    Assert.isNotNull(project, "Parameter 'project' must not be null.");

    _type = type;
    _contentResource = contentResource;
    _bundleMakerProject = project;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
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
  public IProjectContentResource getContentResource() {
    return _contentResource;
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
