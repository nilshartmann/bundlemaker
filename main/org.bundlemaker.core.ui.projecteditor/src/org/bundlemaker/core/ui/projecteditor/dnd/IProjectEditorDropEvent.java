package org.bundlemaker.core.ui.projecteditor.dnd;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;

/**
 * Event passed to a {@link IProjectEditorDropProvider}, describing a Drop operation
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public interface IProjectEditorDropEvent {

  public Shell getShell();

  public IBundleMakerProject getBundleMakerProject();

  /**
   * Returns the project content provider that belongs to the target or null if there is no target
   * 
   * @return
   */
  public IProjectContentProvider getProjectContentProvider();

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