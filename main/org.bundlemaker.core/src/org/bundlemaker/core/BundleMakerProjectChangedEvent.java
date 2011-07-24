package org.bundlemaker.core;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProjectChangedEvent {

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   * 
   */
  public enum Type {
    PROJECT_DESCRIPTION_CHANGED, PROJECT_STATE_CHANGED;
  }

  /** - */
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
   * </p>
   * 
   * @return
   */
  public Type getType() {
    return _type;
  }
}
