package org.bundlemaker.core.ui.projecteditor.dnd;

import org.bundlemaker.core.IBundleMakerProject;
import org.eclipse.swt.dnd.Transfer;

public interface IProjectEditorDropEvent {

  public IBundleMakerProject getBundleMakerProject();

  public Object getTarget();

  public boolean hasTarget();

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