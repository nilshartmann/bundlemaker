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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.hook.IBundleMakerProjectHook;
import org.bundlemaker.core.internal.parser.ParserFactoryRegistry;
import org.bundlemaker.core.internal.store.IPersistentDependencyStoreFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * <p>
 * The activator class.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Activator extends Plugin {

  /** -- **/
  public final static boolean                                              ENABLE_HASHVALUES_FOR_COMPARISON = false;

  /** the null progress monitor */
  public static final IProgressMonitor                                     NULL_PROGRESS_MONITOR            = new NullProgressMonitor();

  /** the plug-in id */
  public static final String                                               PLUGIN_ID                        = "org.bundlemaker.core";

  /** the activator instance */
  private static Activator                                                 _activator;

  /** - */
  private static BundleContext                                             _context;

  /** the factory tracker */
  private ServiceTracker                                                   _factoryTracker;

  /** track the bundlemaker hook */
  private ServiceTracker<IBundleMakerProjectHook, IBundleMakerProjectHook> _projectHookTracker;

  /** the project cache */
  private Map<IProject, IBundleMakerProject>                               _projectCache;

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
    _projectCache = new HashMap<IProject, IBundleMakerProject>();
    ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {

      @Override
      public void resourceChanged(IResourceChangeEvent event) {
        if (event.getType() == IResourceChangeEvent.PRE_DELETE && event.getResource() instanceof IProject
            && _projectCache.containsKey(event.getResource())) {

          IBundleMakerProject iBundleMakerProject = _projectCache.get(event.getResource());

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
  public IBundleMakerProject getBundleMakerProject(IProject project) {
    return _projectCache.get(project);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Collection<IBundleMakerProject> getBundleMakerProjects() {

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
  public void cacheBundleMakerProject(IProject project, IBundleMakerProject bundleMakerProject) {
    _projectCache.put(project, bundleMakerProject);
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

      //
      IPersistentDependencyStoreFactory result = (IPersistentDependencyStoreFactory) _factoryTracker
          .waitForService(5000);

      //
      if (result == null) {
        // TODO
        throw new RuntimeException(
            "No IPersistentDependencyStoreFactory available. Please make sure that the following bundles are started: 'com.db4o.osgi' and 'org.eclipse.equinox.ds'.");
      }

      //
      return result;

    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    }
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
