package org.bundlemaker.core.project;

import org.eclipse.core.runtime.Assert;

public class ContentChangedEvent {

  public enum Type {
    ADDED, REMOVED, MODIFIED;
  }

  /** - */
  private Type                    _type;

  /** - */
  private IProjectContentResource _affectedResource;

  /** - */
  private IProjectContentResource _contentResource;

  /**
   * <p>
   * </p>
   * 
   * @param type
   */
  public ContentChangedEvent(Type type, IProjectContentResource contentResource) {
    Assert.isNotNull(type);
    Assert.isNotNull(contentResource);

    _type = type;
    _contentResource = contentResource;
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
}
