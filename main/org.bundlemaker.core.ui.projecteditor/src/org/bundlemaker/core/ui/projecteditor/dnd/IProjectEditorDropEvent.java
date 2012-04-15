package org.bundlemaker.core.ui.projecteditor.dnd;

import org.bundlemaker.core.IBundleMakerProject;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;

public interface IProjectEditorDropEvent {

  public Shell getShell();

  public IBundleMakerProject getBundleMakerProject();

  public Object getTarget();

  public boolean hasTarget();

  public TransferData getTransferData();

  /**
   * Data is only available in performDrop() otherwise null is returned !!!
   * 
   * @return
   */
  public Object getData();

  public DropLocation getDropLocation();

  public boolean isTransferType(Transfer transfer);

  public <T> T getData(Class<T> type);
}