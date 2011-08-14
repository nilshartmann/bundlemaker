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
package org.bundlemaker.analysis.ui.xref.handler;

import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.handlers.AbstractArtifactBasedHandler;
import org.bundlemaker.analysis.ui.xref.views.XRefView;
import org.eclipse.core.commands.ExecutionEvent;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class OpenXRefViewHandler extends AbstractArtifactBasedHandler {

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.analysis.ui.handlers.AbstractArtifactBasedHandler#execute(org.eclipse.core.commands.ExecutionEvent,
   * java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IArtifact> selectedArtifacts) throws Exception {
    // Open the 'GenericEditor'
    Analysis.instance().openGenericEditor();

    // Make sure, DSMView is visible on GenericEditor
    XRefView.updateAndShow(selectedArtifacts);
  }

}
