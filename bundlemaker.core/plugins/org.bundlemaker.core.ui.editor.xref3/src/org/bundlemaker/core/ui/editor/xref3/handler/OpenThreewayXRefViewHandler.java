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
package org.bundlemaker.core.ui.editor.xref3.handler;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.editor.xref3.ThreewayXRefView;
import org.bundlemaker.core.ui.handler.AbstractOpenEditorHandler;
import org.eclipse.ui.IEditorPart;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class OpenThreewayXRefViewHandler extends AbstractOpenEditorHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.handler.AbstractOpenEditorHandler#getEditorId()
   */
  @Override
  protected String getEditorId() {
    return ThreewayXRefView.XREF_ID;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.handler.AbstractOpenEditorHandler#editorOpened(org.eclipse.ui.IEditorPart)
   */
  @Override
  protected void editorOpened(IEditorPart editor, List<IBundleMakerArtifact> selectedArtifacts) {
    ThreewayXRefView threewayXRefView = (ThreewayXRefView) editor;
    threewayXRefView.refreshFromCurrentArtifactSelection(selectedArtifacts);
  }

}
