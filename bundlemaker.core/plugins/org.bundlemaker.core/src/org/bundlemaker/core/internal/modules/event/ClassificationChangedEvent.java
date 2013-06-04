package org.bundlemaker.core.internal.modules.event;

import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.resource.IGroup;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ClassificationChangedEvent {

  /** - */
  private IGroup _renamedGroup;

  /** - */
  // TODO: REMOVE (already set on _movedGroup)
  private Group _newParentGroup;

  /** - */
  private Group _movedGroup;

  public ClassificationChangedEvent(IGroup renamedGroup) {
    Assert.isNotNull(renamedGroup);

    _renamedGroup = renamedGroup;
  }

  public ClassificationChangedEvent(Group newParentGroup, Group movedGroup) {

    _newParentGroup = newParentGroup;
    _movedGroup = movedGroup;
  }

  public boolean isGroupRenamed() {
    return _renamedGroup != null;
  }

  public boolean isMovedGroup() {
    return _movedGroup != null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IGroup getRenamedGroup() {
    return _renamedGroup;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Group getNewParentGroup() {
    return _newParentGroup;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Group getMovedGroup() {
    return _movedGroup;
  }
}
