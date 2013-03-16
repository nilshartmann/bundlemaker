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

package org.bundlemaker.core.ui.stage.actions;

import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class StageGroupsAction extends AbstractStageByTypeAction {

  public StageGroupsAction() {
    super("Group children", IGroupArtifact.class, IModuleArtifact.class, IPackageArtifact.class,
        IResourceArtifact.class, ITypeArtifact.class);
  }

}
