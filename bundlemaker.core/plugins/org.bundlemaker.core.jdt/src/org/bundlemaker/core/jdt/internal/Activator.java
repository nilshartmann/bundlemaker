package org.bundlemaker.core.jdt.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

  private IResourceChangeListener _listener;

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(BundleContext context) throws Exception {

    //
    _listener = new IResourceChangeListener() {

      //
      public void resourceChanged(IResourceChangeEvent event) {

        // // we are only interested in POST_CHANGE events
        // if (event.getType() != IResourceChangeEvent.POST_CHANGE)
        // return;

        IResourceDelta rootDelta = event.getDelta();
        // get the delta, if any, for the documentation directory

        IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
          public boolean visit(IResourceDelta delta) {
            // // only interested in changed resources (not added or removed)
            // if (delta.getKind() != IResourceDelta.CHANGED)
            // return true;
            // // only interested in content changes
            // if ((delta.getFlags() & IResourceDelta.CONTENT) == 0)
            // return true;
            IResource resource = delta.getResource();

            System.out.println("Project: " + resource.getProject());

            // only interested in files with the "txt" extension
            System.out.println("Path: " + resource.getFullPath() + " : " + delta.getKind());
            return true;
          }
        };

        //
        try {
          System.out.println("** START ***************************************************");
          if (rootDelta != null) {
            rootDelta.accept(visitor);
          }
          System.out.println("** STOP ***************************************************");
        } catch (CoreException e) {
          // open error dialog with syncExec or print to plugin log file
        }
      }
    };
    ResourcesPlugin.getWorkspace().addResourceChangeListener(_listener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    ResourcesPlugin.getWorkspace().removeResourceChangeListener(_listener);
  }
}
