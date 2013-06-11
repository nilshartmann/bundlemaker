/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.ui.handler.selector;

import java.util.List;

import org.bundlemaker.core._type.analysis.selectors.SubtypesAndImplementorsSelector;
import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.handler.AbstractArtifactSelectorHandler;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class SubtypesAndImplementorsSelectorHandler extends AbstractArtifactSelectorHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.handler.AbstractArtifactSelectorHandler#createArtifactSelector(java.util.List)
   */
  @Override
  protected IArtifactSelector createArtifactSelector(List<IBundleMakerArtifact> selectedArtifacts) throws Exception {

    IBundleMakerArtifact artifact = selectedArtifacts.get(0);

    return new SubtypesAndImplementorsSelector(artifact);
  }

}
