/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.osgi.internal;

import org.bundlemaker.core.osgi.manifest.IBundleManifestCreator;
import org.eclipse.pde.core.project.IBundleProjectService;
import org.eclipse.pde.internal.core.target.provisional.ITargetPlatformService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

@SuppressWarnings("restriction")
public class Activator implements BundleActivator {

  /** - */
  private static BundleContext  _context;

  /** - */
  private static ServiceTracker _bundleProjectServiceTracker;

  /** - */
  private static ServiceTracker _targetPlatformServiceTracker;

  /** - */
  private static ServiceTracker _bundleManifestCreatorServiceTracker;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static BundleContext getContext() {

    if (_context == null) {
      throw new RuntimeException("Bundle 'org.bundlemaker.core.osgi' has to be started.");
    }

    return _context;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static IBundleProjectService getBundleProjectService() {
    return (IBundleProjectService) _bundleProjectServiceTracker.getService();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static ITargetPlatformService getTargetPlatformService() {
    return (ITargetPlatformService) _targetPlatformServiceTracker.getService();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static IBundleManifestCreator getBundleManifestCreatorService() {
    return (IBundleManifestCreator) _bundleManifestCreatorServiceTracker.getService();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext )
   */
  public void start(BundleContext bundleContext) throws Exception {

    //
    Activator._context = bundleContext;

    //
    _bundleProjectServiceTracker = new ServiceTracker(bundleContext, IBundleProjectService.class.getName(), null);

    //
    _targetPlatformServiceTracker = new ServiceTracker(bundleContext, ITargetPlatformService.class.getName(), null);

    //
    _bundleManifestCreatorServiceTracker = new ServiceTracker(Activator.getContext(),
        IBundleManifestCreator.class.getName(), null);

    //
    _bundleProjectServiceTracker.open();
    _targetPlatformServiceTracker.open();
    _bundleManifestCreatorServiceTracker.open();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext bundleContext) throws Exception {
    Activator._context = null;

    //
    _bundleProjectServiceTracker.close();
  }

}
