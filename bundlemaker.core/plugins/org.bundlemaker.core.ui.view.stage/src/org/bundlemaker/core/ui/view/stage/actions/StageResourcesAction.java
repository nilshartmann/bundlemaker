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

package org.bundlemaker.core.ui.view.stage.actions;

import org.bundlemaker.core._type.ITypeArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class StageResourcesAction extends AbstractStageByTypeAction {

  public StageResourcesAction() {
    super("Add Resource children to Stage", IResourceArtifact.class, ITypeArtifact.class);
  }

}
