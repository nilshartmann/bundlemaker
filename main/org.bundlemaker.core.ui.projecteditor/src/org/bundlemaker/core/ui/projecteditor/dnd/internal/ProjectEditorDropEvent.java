package org.bundlemaker.core.ui.projecteditor.dnd.internal;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.ui.projecteditor.dnd.DropLocation;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;

public class ProjectEditorDropEvent implements IProjectEditorDropEvent {

  private final Shell                   _shell;

  private final IBundleMakerProject     _bundleMakerProject;

  private final IProjectContentProvider _projectContentProvider;

  private final Object                  _target;

  private Object                        _data;

  private final TransferData            _transferData;

  private final DropLocation            _dropLocation;

  public ProjectEditorDropEvent(final Shell shell, IBundleMakerProject bundleMakerProject,
      IProjectContentProvider projectContentProvider,
      Object target,
      DropLocation dropLocation, TransferData transferData) {
    super();
    _shell = shell;
    _bundleMakerProject = bundleMakerProject;
    _projectContentProvider = projectContentProvider;
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

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropEvent#getProjectContentProvider()
   */
  @Override
  public IProjectContentProvider getProjectContentProvider() {
    return _projectContentProvider;
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

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropEvent#getTransferData()
   */
  @Override
  public TransferData getTransferData() {
    return this._transferData;
  }

  @Override
  public boolean isTransferType(Transfer transfer) {
    if (transfer.isSupportedType(_transferData)) {
      return true;
    }
    return false;
  }

  // public <T> T getJavaTransferData(Class<T> type, Transfer transfer) {
  // if (!isTransferType(transfer)) {
  // return null;
  // }
  //
  // try {
  // transfer.
  // }
  //
  // }

  @Override
  public <T> T getData(Class<T> type) {

    return type.cast(_data);
  }

}
