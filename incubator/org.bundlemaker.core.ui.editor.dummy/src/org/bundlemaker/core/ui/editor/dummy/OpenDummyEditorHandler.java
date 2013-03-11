/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.editor.dummy;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.part.NullEditorInput;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class OpenDummyEditorHandler extends AbstractArtifactBasedHandler {

  private IEditorInput nullInputEditor = new NullEditorInput();

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.analysis.ui.handlers.AbstractArtifactBasedHandler#execute(org.eclipse.core.commands.ExecutionEvent,
   * java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {
    IWorkbenchPage page = getActiveWorkbenchPage();
    if (page != null) {
      try {
        IEditorPart editorPart = page.openEditor(nullInputEditor, DummyEditor.DummyEditor_ID);
        if (!(editorPart instanceof DummyEditor)) {
          System.err.println("EditorPart " + editorPart + " is not a XRefView?");
          return;
        }
      } catch (PartInitException e) {
        e.printStackTrace();
      }
    }
  }

  private IWorkbenchPage getActiveWorkbenchPage() {
    IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    if (workbenchWindow != null) {
      IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
      if (workbenchPage != null) {
        return workbenchPage;
      }
    }
    return null;
  }
}
