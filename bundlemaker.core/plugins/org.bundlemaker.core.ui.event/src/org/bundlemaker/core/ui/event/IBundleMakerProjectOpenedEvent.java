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
package org.bundlemaker.core.ui.event;

import org.bundlemaker.core.IBundleMakerProject;

/**
 * This is event is fired when a BundleMaker project is opened via the 'Parse and open' context menu.
 * <p>It is <b>not</b> fired in case the 'Parse' button on the Project Editor is pressed.
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public interface IBundleMakerProjectOpenedEvent {
  
  public IBundleMakerProject getBundleMakerProject();
  

}
