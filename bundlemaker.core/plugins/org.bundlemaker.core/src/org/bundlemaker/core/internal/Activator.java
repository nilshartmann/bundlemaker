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
package org.bundlemaker.core.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.internal.parser.ParserFactoryRegistry;
import org.bundlemaker.core.parser.IParserAwareBundleMakerProject;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.resource.IBundleMakerProjectHook;
import org.bundlemaker.core.spi.store.IPersistentDependencyStoreFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
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
import org.osgi.util.tracker.ServiceTracker;

/**
 * <p>
 * The activator class.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Activator extends Plugin {

  /** the null progress monitor */
  public static final IProgressMonitor                                     NULL_PROGRESS_MONITOR   = new NullProgressMonitor();

  /** the plug-in id */
  public static final String                                               PLUGIN_ID               = "org.bundlemaker.core";

  /** Bundles that need to be in ACTIVE state in order to make Dependency Store work correctly */
  public static final String[]                                             REQUIRED_ACTIVE_BUNDLES = new String[] {
      "org.eclipse.equinox.ds",
      "org.bundlemaker.com.db4o.osgi"

                                                                                                   };

  /** the activator instance */
  private static Activator                                                 _activator;

  /** - */
  private static BundleContext                                             _context;

  /** the factory tracker */
  private ServiceTracker                                                   _factoryTracker;

  /** track the bundlemaker hook */
  private ServiceTracker<IBundleMakerProjectHook, IBundleMakerProjectHook> _projectHookTracker;

  /** the project cache */
  private Map<IProject, IParserAwareBundleMakerProject>                    _projectCache;

  /** - */
  private ParserFactoryRegistry                                            _parserFactoryRegistry;

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

    // create the factory tracker
    _factoryTracker = new ServiceTracker(context, IPersistentDependencyStoreFactory.class.getName(), null);
    _factoryTracker.open();

    // create hook tracker
    _projectHookTracker = new ServiceTracker<IBundleMakerProjectHook, IBundleMakerProjectHook>(context,
        IBundleMakerProjectHook.class, null);
    _projectHookTracker.open();

    // create the maps and caches
    _projectCache = new HashMap<IProject, IParserAwareBundleMakerProject>();
    ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {

      @Override
      public void resourceChanged(IResourceChangeEvent event) {
        if (event.getType() == IResourceChangeEvent.PRE_DELETE && event.getResource() instanceof IProject
            && _projectCache.containsKey(event.getResource())) {

          IProjectDescriptionAwareBundleMakerProject iBundleMakerProject = _projectCache.get(event.getResource());

          // notifies listeners and removes itself from the cache
          iBundleMakerProject.dispose();

        }
      }
    }, IResourceChangeEvent.PRE_DELETE);

    //
    _parserFactoryRegistry = new ParserFactoryRegistry();

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
    _factoryTracker.close();

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

  public static BundleContext getContext() {
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

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ParserFactoryRegistry getParserFactoryRegistry() {

    // lazy initialization to prevent alzheimer's workspace [https://bundlemaker.jira.com/browse/BM-246]
    if (!_parserFactoryRegistry.isInitalized()) {
      _parserFactoryRegistry.initialize();
    }

    // return the parser factory
    return _parserFactoryRegistry;
  }

  /**
   * Returns an instance of {@link IBundleMakerProjectHook} or null if no hook is registered
   * 
   * @return
   */
  public IBundleMakerProjectHook getBundleMakerProjectHook() {

    IBundleMakerProjectHook hook = _projectHookTracker.getService();

    return hook;

  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @return
   */
  public IParserAwareBundleMakerProject getBundleMakerProject(IProject project) {
    return _projectCache.get(project);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Collection<? extends IParserAwareBundleMakerProject> getBundleMakerProjects() {

    //
    return Collections.unmodifiableCollection(_projectCache.values());
  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @param bundleMakerProject
   */
  public void cacheBundleMakerProject(IProject project, IProjectDescriptionAwareBundleMakerProject bundleMakerProject) {
    _projectCache.put(project, bundleMakerProject.adaptAs(IParserAwareBundleMakerProject.class));
  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   */
  public void removeCachedBundleMakerProject(IProject project) {
    _projectCache.remove(project);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IPersistentDependencyStoreFactory getPersistentDependencyStoreFactory() {

    try {

      // Make sure required bundles are ACTIVE
      for (String bundleName : REQUIRED_ACTIVE_BUNDLES) {
        activateBundleIfNeeded(bundleName);
      }

      //
      IPersistentDependencyStoreFactory result = (IPersistentDependencyStoreFactory) _factoryTracker
          .waitForService(5000);

      //
      if (result == null) {
        // TODO
        throw new RuntimeException(
            "No IPersistentDependencyStoreFactory available. Please make sure that the following bundles are active: "
                + Arrays.asList(REQUIRED_ACTIVE_BUNDLES));
      }

      //
      return result;

    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    }
  }

  protected void log(int status, String msg) {
    log(status, msg, null);
  }

  protected void log(int status, String msg, Throwable t) {
    ILog log = getLog();
    if (log != null) {
      log.log(new Status(status, PLUGIN_ID, msg, t));

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
  protected void activateBundleIfNeeded(String symbolicName) {

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
  protected Bundle findBundle(String symbolicName) {
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
