/*******************************************************************************
 * Copyright (c) 2013 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.handler;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.operations.CreateModuleWithArtifactsOperation;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * 
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class CreateModuleFromPackageSelectionHandler extends AbstractArtifactBasedHandler {

  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {
    // Retrieve shell from Event
    Shell shell = HandlerUtil.getActiveShell(event);

    CreateModuleWithArtifactsOperation operation = new CreateModuleWithArtifactsOperation(shell, selectedArtifacts);
    operation.run();

  }

}
