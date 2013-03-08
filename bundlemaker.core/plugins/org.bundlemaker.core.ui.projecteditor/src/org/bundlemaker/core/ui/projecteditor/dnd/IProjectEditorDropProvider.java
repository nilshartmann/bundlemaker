package org.bundlemaker.core.ui.projecteditor.dnd;

import org.eclipse.swt.dnd.Transfer;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public interface IProjectEditorDropProvider {
  public Transfer[] getSupportedDropTypes();

  public boolean canDrop(IProjectEditorDropEvent dropEvent) throws Exception;

  public boolean performDrop(IProjectEditorDropEvent dropEvent) throws Exception;
}
