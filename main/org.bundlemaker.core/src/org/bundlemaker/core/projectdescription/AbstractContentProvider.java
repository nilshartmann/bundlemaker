package org.bundlemaker.core.projectdescription;

import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;

/**
 * <p>
 * Superclass for all implementations of IProjectContentProvider
 * </p>
 * 
 * <p>
 * <b>Implementation note:</b> Implementators should call {@link #fireProjectDescriptionChangedEvent()} after the
 * provider changed.
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractContentProvider implements IProjectContentProvider {

  private BundleMakerProjectDescription _projectDescription;

  /** - */
  private String                        _id;

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getId() {
    return _id;
  }

  /**
   * {@inheritDoc}
   */
  public final void setId(String id) {
    _id = id;
  }

  /**
   * <b>Internal method</b> This method is not intended to be called from outside bundlemaker.core
   * 
   * @param description
   */
  public void setProjectDescription(BundleMakerProjectDescription description) {
    _projectDescription = description;
  }

  /**
   * This method should be called after this provider has been changed.
   */
  protected void fireProjectDescriptionChangedEvent() {
    if (_projectDescription != null) {
      _projectDescription.fireProjectDescriptionChangedEvent();
    }
  }
}
