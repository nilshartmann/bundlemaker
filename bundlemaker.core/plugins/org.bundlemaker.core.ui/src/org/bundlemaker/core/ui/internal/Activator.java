/*******************************************************************************
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.internal;

import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.artifact.configuration.IArtifactModelConfigurationProvider;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  // The plug-in ID
  public static final String                PLUGIN_ID                                 = "org.bundlemaker.core.ui"; //$NON-NLS-1$

  /** the bundle maker "artificial project" identifier */
  public static final String                BUNDLEMAKER_ARTIFICIAL_PROJECT_IDENTIFIER = "$bundlemaker";

  // The shared instance
  private static Activator                  plugin;

  private BundleContext                     _bundleContext;

  private ProjectExplorerSelectionForwarder _projectExplorerSelectionForwarder;

  /** - */
  private boolean                           _filterInitialized;

  /**
   * The constructor
   */
  public Activator() {
  }

  /**
   * <p>
   * </p>
   */
  // TODO: As an extension??
  public void initFilters() {

    //
    if (!_filterInitialized) {
      CommonNavigator commonNavigator = CommonNavigatorUtils.findCommonNavigator(IPageLayout.ID_PROJECT_EXPLORER);
      if (commonNavigator != null) {
        commonNavigator.getCommonViewer().addFilter(new BundleMakerProjectViewerFilter());
        _filterInitialized = true;
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    plugin = this;
    _bundleContext = context;

    registerProjectExplorerSelectionForwarder();

    PlatformUI.getWorkbench().addWindowListener(new WindowListener());

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    plugin = null;
    _bundleContext = null;

    //
    super.stop(context);
  }

  /**
   * Returns the shared instance
   * 
   * @return the shared instance
   */
  public static Activator getDefault() {
    return plugin;
  }

  private void registerProjectExplorerSelectionForwarder() {
    if (_projectExplorerSelectionForwarder != null) {
      // already registered
      return;
    }

    // Try to get selection service
    ISelectionService selectionService = getSelectionService();
    if (selectionService != null) {
      // register forwarder
      _projectExplorerSelectionForwarder = new ProjectExplorerSelectionForwarder(Selection.instance()
          .getArtifactSelectionService());
      selectionService.addSelectionListener(Selection.PROJECT_EXPLORER_VIEW_ID, _projectExplorerSelectionForwarder);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IArtifactModelConfigurationProvider getArtifactModelConfigurationProvider() {

    //
    ServiceReference serviceReference = _bundleContext.getServiceReference(IArtifactModelConfigurationProvider.class
        .getName());

    //
    if (serviceReference != null) {

      //
      IArtifactModelConfigurationProvider provider = (IArtifactModelConfigurationProvider) _bundleContext
          .getService(serviceReference);

      //
      return provider;
    }

    //
    return null;
  }

  /**
   * @return the {@link ISelectionService} or null
   */
  private ISelectionService getSelectionService() {

    IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    if (workbenchWindow != null) {
      return workbenchWindow.getSelectionService();
    }
    return null;
  }

  class WindowListener implements IWindowListener {

    @Override
    public void windowActivated(IWorkbenchWindow window) {
      registerProjectExplorerSelectionForwarder();
    }

    @Override
    public void windowDeactivated(IWorkbenchWindow window) {
    }

    @Override
    public void windowClosed(IWorkbenchWindow window) {
    }

    @Override
    public void windowOpened(IWorkbenchWindow window) {
      registerProjectExplorerSelectionForwarder();
    }
  }
}
