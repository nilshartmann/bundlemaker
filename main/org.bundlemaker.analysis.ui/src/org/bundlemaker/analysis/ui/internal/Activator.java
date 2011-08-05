package org.bundlemaker.analysis.ui.internal;

import java.net.MalformedURLException;
import java.net.URL;

import org.bundlemaker.analysis.ui.Analysis;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  // The plug-in ID
  public static final String PLUGIN_ID = "org.bundlemaker.analysis.ui"; //$NON-NLS-1$

  // The shared instance
  private static Activator   plugin;

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

    _projectExplorerSelectionForwarder = new ProjectExplorerSelectionForwarder(Analysis.instance()
        .getArtifactSelectionService());

    getSelectionService().addSelectionListener(Analysis.PROJECT_EXPLORER_VIEW_ID, _projectExplorerSelectionForwarder);
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

  public void addSelectionProjectExplorerListener(BundleContext context) {

    ServiceTracker t = new ServiceTracker(context, ISelectionService.class.getName(), null) {

      @Override
      public Object addingService(ServiceReference reference) {
        ISelectionService selectionService = (ISelectionService) super.addingService(reference);
        System.out.println("adding selection listener!!!");
        selectionService.addSelectionListener(new ISelectionListener() {

          @Override
          public void selectionChanged(IWorkbenchPart part, ISelection selection) {
            System.out.println("changed, part: " + part.getSite().getId());
            System.out.println("changed, selection: " + selection);

          }
        });

        return selectionService;
      }

    };
    t.open();

  }

}
