/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui;

import org.bundlemaker.core.ui.artifact.configuration.IArtifactModelConfigurationProvider;
import org.bundlemaker.core.ui.editor.adapter.ProjectDescriptionAdapterFactory;
import org.bundlemaker.core.ui.selection.Selection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  // The plug-in ID
  public static final String                PLUGIN_ID = "org.bundlemaker.core.ui"; //$NON-NLS-1$

  // The shared instance
  private static Activator                  plugin;

  private BundleContext                     _bundleContext;

  private ProjectExplorerSelectionForwarder _projectExplorerSelectionForwarder;

  /**
   * The constructor
   */
  public Activator() {
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

    ProjectDescriptionAdapterFactory.register();

    // CommonNavigatorUtils.findCommonNavigator()
    // IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    // CommonNavigator commonNavigator = (CommonNavigator) page.findView(IPageLayout.ID_PROJECT_EXPLORER);
    // commonNavigator.getCommonViewer().addDoubleClickListener(new IDoubleClickListener() {
    // @Override
    // public void doubleClick(DoubleClickEvent event) {
    // System.out.println(event.getSelection());
    // }
    // });

    // /** -- */
    // IPartListener partListener = new IPartListener() {
    //
    // @Override
    // public void partOpened(IWorkbenchPart part) {
    // System.out.println("partOpened: " + part);
    // }
    //
    // @Override
    // public void partDeactivated(IWorkbenchPart part) {
    // System.out.println("partDeactivated: " + part);
    // }
    //
    // @Override
    // public void partClosed(IWorkbenchPart part) {
    // System.out.println("partClosed: " + part);
    // }
    //
    // @Override
    // public void partBroughtToTop(IWorkbenchPart part) {
    // System.out.println("partBroughtToTop: " + part);
    // }
    //
    // @Override
    // public void partActivated(IWorkbenchPart part) {
    // System.out.println("partActivated: " + part);
    // }
    // };
    //
    // page.addPartListener(partListener);
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

    ProjectDescriptionAdapterFactory.unregister();

    if (_projectExplorerSelectionForwarder != null) {
      ISelectionService selectionService = getSelectionService();
      if (selectionService != null) {
        selectionService.removeSelectionListener(_projectExplorerSelectionForwarder);
      }
    }

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
      System.out.println("Register ProjectExplorerSelectionForwarder");
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
