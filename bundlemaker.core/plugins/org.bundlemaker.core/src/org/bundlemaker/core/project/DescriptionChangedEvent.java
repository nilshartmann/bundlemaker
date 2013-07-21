package org.bundlemaker.core.project;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DescriptionChangedEvent {

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
   * Creates a new instance of type {@link DescriptionChangedEvent}.
   * </p>
   * 
   * @param type
   */
  public DescriptionChangedEvent(Type type) {
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
