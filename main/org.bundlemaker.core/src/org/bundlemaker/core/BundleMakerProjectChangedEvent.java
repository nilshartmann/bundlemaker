package org.bundlemaker.core;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Represents an event that is fired when the BundleMaker project has changed. This is the case if the project
 * description has been modified {{@link BundleMakerProjectChangedEvent.Type#PROJECT_DESCRIPTION_CHANGED}) <i>or</i> it
 * the project state has changed {{@link BundleMakerProjectChangedEvent.Type#PROJECT_STATE_CHANGED}).
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProjectChangedEvent {

  /**
   * <p>
   * The cause for this event.
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public enum Type {
    PROJECT_DESCRIPTION_CHANGED, PROJECT_STATE_CHANGED;
  }

  /** the cause for this event */
  private Type _type;

  /**
   * <p>
   * Creates a new instance of type {@link BundleMakerProjectChangedEvent}.
   * </p>
   * 
   * @param type
   */
  public BundleMakerProjectChangedEvent(Type type) {
    Assert.isNotNull(type);

    _type = type;
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
}
