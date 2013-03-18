package org.bundlemaker.core.ui.event.selection.internal.lifecycle;

import org.bundlemaker.core.ui.event.selection.workbench.ILifecycleAwarePart;
import org.eclipse.core.runtime.Assert;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

public class LifecycleAwarePartListener implements IPartListener {

  /** - */
  private ILifecycleAwarePart _owner;

  /**
   * <p>
   * Creates a new instance of type {@link LifecycleAwarePartListener}.
   * </p>
   * 
   * @param owner
   */
  public LifecycleAwarePartListener(ILifecycleAwarePart owner) {
    Assert.isNotNull(owner);

    // set owner
    _owner = owner;
  }

  @Override
  public void partOpened(IWorkbenchPart part) {

    //
    if (!isOwner(part)) {
      return;
    }

    _owner.onPartOpened();
  }

  @Override
  public void partDeactivated(IWorkbenchPart part) {

    //
    if (!isOwner(part)) {
      return;
    }

    _owner.onPartDeactivated();
  }

  @Override
  public void partClosed(IWorkbenchPart part) {

    //
    if (!isOwner(part)) {
      return;
    }

    _owner.onPartClosed();
  }

  @Override
  public void partBroughtToTop(IWorkbenchPart part) {

    //
    if (!isOwner(part)) {
      return;
    }

    _owner.onPartBroughtToTop();
  }

  @Override
  public void partActivated(IWorkbenchPart part) {

    //
    if (!isOwner(part)) {
      return;
    }

    _owner.onPartActivated();
  }

  /**
   * <p>
   * </p>
   * 
   * @param part
   * @return
   */
  private boolean isOwner(IWorkbenchPart part) {
    return _owner.equals(part);
  }
}