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
package org.bundlemaker.core.ui.view.transformationhistory.handler;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler;
import org.eclipse.core.commands.ExecutionEvent;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class OpenHistoryViewHandler extends AbstractArtifactBasedHandler {

  /* (non-Javadoc)
   * @see org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler#execute(org.eclipse.core.commands.ExecutionEvent, java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {
    // TODO Auto-generated method stub

  }

}
