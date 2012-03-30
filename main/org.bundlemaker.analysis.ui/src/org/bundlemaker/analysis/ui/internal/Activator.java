package org.bundlemaker.analysis.ui.internal;

import java.net.MalformedURLException;
import java.net.URL;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.selection.Selection;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  // The plug-in ID
  public static final String                PLUGIN_ID = "org.bundlemaker.analysis.ui"; //$NON-NLS-1$

  // The shared instance
  private static Activator                  plugin;

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

    registerProjectExplorerSelectionForwarder();

    // CommonNavigatorUtils.findCommonNavigator()
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    CommonNavigator commonNavigator = (CommonNavigator) page.findView(IPageLayout.ID_PROJECT_EXPLORER);
    commonNavigator.getCommonViewer().addDoubleClickListener(new IDoubleClickListener() {

      @Override
      public void doubleClick(DoubleClickEvent event) {
        System.out.println(event.getSelection());
      }
    });

    PlatformUI.getWorkbench().addWindowListener(new WindowListener());

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

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    plugin = null;
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

  public String getFile(String path) {
    Bundle bundle = Platform.getBundle(PLUGIN_ID);
    Object file = FileLocator.find(bundle, new Path(path), null);
    if (file != null) {
      return file.toString();
    } else {
      return null;
    }
  }

  /**
   * Returns the Icon-Image that should be used for the given artifact.
   * 
   * <p>
   * If no artifact is given, <tt>null</tt> is returned.
   * 
   * @param artifact
   * @return
   */
  public Image getIconForArtifact(IBundleMakerArtifact artifact) {
    if (artifact == null) {
      return null;
    }

    return Activator.getDefault().getIcon(artifact.getType().getKuerzel());
  }

  public Image getIcon(String icon) {
    ImageRegistry registry = getImageRegistry();
    Image image = registry.get(icon);

    if (image != null) {
      return image;
    }
    URL url = null;
    try {
      url = new URL(getFile("icons/" + icon + ".gif"));
    } catch (MalformedURLException e) {
    }

    ImageDescriptor myImage = ImageDescriptor.createFromURL(url);
    image = myImage.createImage();
    getImageRegistry().put(icon, image);

    return image;
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
