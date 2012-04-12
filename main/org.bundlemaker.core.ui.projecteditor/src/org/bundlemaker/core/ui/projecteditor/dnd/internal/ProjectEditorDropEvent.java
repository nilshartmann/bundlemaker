package org.bundlemaker.core.ui.projecteditor.dnd.internal;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.ui.projecteditor.dnd.DropLocation;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;

public class ProjectEditorDropEvent implements IProjectEditorDropEvent {

  private final Shell               _shell;

  private final IBundleMakerProject _bundleMakerProject;

  private final Object              _target;

  private Object                    _data;

  private final TransferData        _transferData;

  private final DropLocation        _dropLocation;

  public ProjectEditorDropEvent(final Shell shell, IBundleMakerProject bundleMakerProject, Object target,
      DropLocation dropLocation, TransferData transferData) {
    super();
    _shell = shell;
    _bundleMakerProject = bundleMakerProject;
    _target = target;
    _dropLocation = dropLocation;
    this._transferData = transferData;

  }

  public void setData(Object data) {
    this._data = data;
  }

  @Override
  public Shell getShell() {
    return _shell;
  }

  @Override
  public IBundleMakerProject getBundleMakerProject() {
    return _bundleMakerProject;
  }

  @Override
  public Object getTarget() {
    return _target;
  }

  @Override
  public boolean hasTarget() {
    return (_target != null);
  }

  @Override
  public Object getData() {
    return _data;
  }

  @Override
  public DropLocation getDropLocation() {
    return _dropLocation;
  }

  @Override
  public boolean isTransferType(Transfer transfer) {
    if (transfer.isSupportedType(_transferData)) {
      return true;
    }
    return false;
  }

  @Override
  public <T> T getData(Class<T> type) {

    return type.cast(_data);
  }

}
