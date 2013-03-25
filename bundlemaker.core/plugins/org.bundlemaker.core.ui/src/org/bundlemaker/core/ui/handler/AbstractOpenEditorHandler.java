/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.handler;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.utils.EditorHelper;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

/**
 * An abstract base class for Handlers that open an Editor. Only overriding the {@link #getEditorId()} Method is
 * neccessary
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractOpenEditorHandler extends AbstractArtifactBasedHandler {

  private IEditorInput _editorInput;

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler#execute(org.eclipse.core.commands.ExecutionEvent,
   * java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {

    String editorId = getEditorId();
    IEditorInput editorInput = getEditorInput();

    IEditorPart editor = EditorHelper.openEditor(editorId, editorInput);

    if (editor != null) {
      editorOpened(editor);
    }

  }

  /**
   * @param editor
   */
  protected void editorOpened(IEditorPart editor) {
    // override in subclasses to perfom additional actions after the editor has been opened
  }

  protected abstract String getEditorId();

  protected IEditorInput getEditorInput() {
    if (_editorInput == null) {
      _editorInput = createEditorInput();
    }

    return _editorInput;

  }

  /**
   * @return
   */
  protected IEditorInput createEditorInput() {
    return EditorHelper.newNullEditorInput();
  }

}
