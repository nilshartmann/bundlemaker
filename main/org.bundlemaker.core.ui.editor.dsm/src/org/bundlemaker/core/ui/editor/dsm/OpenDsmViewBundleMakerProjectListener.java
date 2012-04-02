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
package org.bundlemaker.core.ui.editor.dsm;

import org.bundlemaker.core.ui.event.IBundleMakerProjectOpenedEvent;
import org.bundlemaker.core.ui.event.IBundleMakerProjectOpenedEventListener;
import org.osgi.service.component.annotations.Component;

/**
 * Opens the DSM view when a BundleMaker project has been opened
 * 
 * @TODO Refactor into a general 'default action' to allow users to select a default action that should happen,
 * after a project has been opened 
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
@Component
public class OpenDsmViewBundleMakerProjectListener implements IBundleMakerProjectOpenedEventListener {

  /* (non-Javadoc)
   * @see org.bundlemaker.core.ui.event.IBundleMakerProjectOpenedEventListener#bundleMakerProjectOpened(org.bundlemaker.core.ui.event.IBundleMakerProjectOpenedEvent)
   */
  @Override
  public void bundleMakerProjectOpened(IBundleMakerProjectOpenedEvent event) {
    
    System.out.println(" Project opened => opening DSM view");
    
    DSMArtifactModelEditor.openDsmView();
  }

}
