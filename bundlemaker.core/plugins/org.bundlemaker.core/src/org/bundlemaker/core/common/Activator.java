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
package org.bundlemaker.core.common;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

/**
 * <p>
 * The activator class.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Activator extends Plugin {

  /** the null progress monitor */
  public static final IProgressMonitor NULL_PROGRESS_MONITOR = new NullProgressMonitor();

  /** the activator instance */
  private static Activator             _activator;

  /** - */
  private BundleContext                _context;

  /**
   * <p>
   * Creates a new instance of type {@link Activator}.
   * </p>
   * 
   */
  public Activator() {
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext context) throws Exception {
    super.start(context);

    //
    _activator = this;

    //
    _context = context;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext context) throws Exception {

    //
    _activator = null;

    //
    _context = null;

    //
    super.stop(context);
  }

  /**
   * <p>
   * Returns the shared instance
   * </p>
   * 
   * @return
   */
  public static Activator getDefault() {

    //
    if (_activator == null) {

      //
      throw new RuntimeException("Bundle 'org.bundlemaker.core' has to be started.");
    }

    // return the activator
    return _activator;
  }

  public BundleContext getContext() {
    return _context;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getBundleVersion() {
    return getContext().getBundle().getVersion().toString();
  }

  public void log(int status, String msg) {
    log(status, msg, null);
  }

  public void log(int status, String msg, Throwable t) {
    ILog log = getLog();
    if (log != null) {
      log.log(new Status(status, Constants.BUNDLE_ID_BUNDLEMAKER_CORE, msg, t));

    } else {
      if (t != null) {
        System.err.println(msg);
        t.printStackTrace();
      } else {
        System.out.println(msg);
      }
    }
  }

  /**
   * Activates the bundle with the specified symbolic name
   * 
   * If the bundle either not exists or cannot be started a message is logged, but the method does not fail
   * 
   * @param symbolicName
   */
  public void activateBundleIfNeeded(String symbolicName) {

    Bundle bundle = findBundle(symbolicName);
    if (bundle == null) {
      log(IStatus.WARNING, "No Bundle '" + symbolicName + "' installed?");
      return;
    }

    if (bundle.getState() != Bundle.ACTIVE) {
      log(IStatus.INFO, "Starting Bundle '" + bundle + "'...");
      try {
        bundle.start();
      } catch (BundleException e) {
        log(IStatus.ERROR, "Unable to Start Bundle '" + bundle + "': " + e, e);
      }
    }
  }

  /**
   * Finds the bundle with the specified symbolic name
   * 
   * @param symbolicName
   * @return the bundle or null if no such bundle is installed
   */
  public Bundle findBundle(String symbolicName) {
    Bundle[] bundles = _context.getBundles();

    for (Bundle bundle : bundles) {
      if (symbolicName.equals(bundle.getSymbolicName())) {
        return bundle;
      }
    }

    return null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param progressMonitor
   * @return
   */
  public IProgressMonitor getProgressMonitor(IProgressMonitor progressMonitor) {
    return progressMonitor != null ? progressMonitor : NULL_PROGRESS_MONITOR;
  }

  public boolean enableAutoBuild(boolean enabled) {
    IWorkspace workspace = ResourcesPlugin.getWorkspace();
    IWorkspaceDescription desc = workspace.getDescription();
    boolean isAutoBuilding = desc.isAutoBuilding();
    if (isAutoBuilding != enabled) {
      desc.setAutoBuilding(enabled);
      try {
        workspace.setDescription(desc);
      } catch (CoreException e) {
        e.printStackTrace();
      }
    }
    return isAutoBuilding;
  }
}
