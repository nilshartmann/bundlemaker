package org.bundlemaker.core.jdt.internal;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.bundlemaker.core.common.collections.GenericCache;
import org.bundlemaker.core.jdt.content.JdtProjectContentProvider;
import org.eclipse.core.resources.IProject;
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

  private static Activator                                        _instance;

  private IResourceChangeListener                                 _listener;

  private GenericCache<IProject, List<JdtProjectContentProvider>> _project2provider;

  /**
   * <p>
   * </p>
   * 
   * @return the _instance
   */
  public static Activator getInstance() {
    return _instance;
  }

  /**
   * <p>
   * </p>
   * 
   * @return the provider
   */
  public GenericCache<IProject, List<JdtProjectContentProvider>> getProject2ProviderMap() {
    return _project2provider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(BundleContext context) throws Exception {

    //
    _project2provider = new GenericCache<IProject, List<JdtProjectContentProvider>>() {
      @Override
      protected List<JdtProjectContentProvider> create(IProject key) {
        return new LinkedList<JdtProjectContentProvider>();
      }
    };

    //
    _instance = this;

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

            for (Entry<IProject, List<JdtProjectContentProvider>> entry : _project2provider.entrySet()) {
              if (entry.getKey().getFullPath().isPrefixOf(resource.getFullPath())) {
                System.out.println("Project: " + resource.getProject());
                System.out.println("Path: " + resource.getFullPath() + " : " + delta.getKind());
                System.out.println(entry.getValue());
              }
            }

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
