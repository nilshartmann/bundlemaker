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

import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;

/**
 * A Listener for {@link IBundleMakerProjectOpenedEvent IBundleMakerProjectOpenedEvents}
 * 
 * <p>Instances of {@link IBundleMakerProjectOpenedEventListener} must be registered as a Service at the OSGi Service Registry
 * in order to get informed about IBundleMakerProjectOpenedEvents.
 * 
 * @see IBundleMakerProjectOpenedEvent
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public interface IBundleMakerProjectOpenedEventListener {
  public void bundleMakerProjectOpened(IBundleMakerProjectOpenedEvent event);

}
