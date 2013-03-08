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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.ui.event.internal.BundleMakerProjectOpenedEvent;
import org.bundlemaker.core.ui.event.selection.internal.Activator;
import org.eclipse.core.runtime.Assert;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Allows clients to fire several BundleMaker UI Events.
 * 
 * <p>
 * Since we cannot inject DS components into Eclipse components (like Handlers) we need to have a singleton instance
 * that is statically accessible.
 * 
 * <p>
 * To get an instance of this class, use {@link #instance()} method
 * 
 * @noextend This class is not intended to be subclassed by clients. Clients must not create instances of this class
 * 
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class Events {

  /**
   * @return the single instance of this class
   */
  public static Events instance() {
    return Activator.getDefault().getEvents();
  }

  /**
   * Dispatches a {@link IBundleMakerProjectOpenedEvent} to all registered
   * {@link IBundleMakerProjectOpenedEventListener listeners}
   * 
   * @param bundleMakerProject
   *          the project that has been opened. Must not be null.
   */
  public void fireProjectOpened(IBundleMakerProject bundleMakerProject) {
    Assert.isNotNull(bundleMakerProject);

    // get registered listener
    List<IBundleMakerProjectOpenedEventListener> listeners = getServices(IBundleMakerProjectOpenedEventListener.class);

    if (listeners.isEmpty()) {
      // no listener registered
      return;
    }

    // create event
    final BundleMakerProjectOpenedEvent event = new BundleMakerProjectOpenedEvent(bundleMakerProject);

    // notify listeners
    for (IBundleMakerProjectOpenedEventListener listener : listeners) {
      listener.bundleMakerProjectOpened(event);
    }
  }

  @SuppressWarnings("unchecked")
  protected <T> List<T> getServices(Class<T> type) {
    ServiceTracker serviceTracker = new ServiceTracker(getBundleContext(), type.getName(), null);
    serviceTracker.open();

    try {
      Object[] services = serviceTracker.getServices();
      if (services == null) {
        return Collections.emptyList();
      }

      return (List<T>) Arrays.asList(services);

    } finally {
      serviceTracker.close();
    }
  }

  protected abstract BundleContext getBundleContext();

}
