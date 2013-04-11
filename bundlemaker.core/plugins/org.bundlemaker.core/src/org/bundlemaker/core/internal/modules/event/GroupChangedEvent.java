package org.bundlemaker.core.internal.modules.event;

import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.modules.ChangeAction;
import org.bundlemaker.core.modules.IGroup;
import org.eclipse.core.runtime.IPath;

public class GroupChangedEvent {

  /** - */
  private IGroup       _group;

  /** - */
  private ChangeAction _changeAction;

  /**
   * <p>
   * Creates a new instance of type {@link GroupChangedEvent}.
   * </p>
   * 
   * @param group
   * @param changeAction
   */
  public GroupChangedEvent(IGroup group, ChangeAction changeAction) {
    super();
    _group = group;
    _changeAction = changeAction;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ChangeAction getChangeAction() {
    return _changeAction;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IPath getPath() {
    return ((Group) _group).getPath();
  }

  public IGroup getGroup() {
    return _group;
  }
}
