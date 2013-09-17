package org.bundlemaker.core.jdt.internal;

import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bundlemaker.core.common.collections.GenericCache;
import org.bundlemaker.core.jdt.content.JdtProjectContentProvider;
import org.bundlemaker.core.project.BundleMakerProjectContentChangedEvent;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.eclipse.core.resources.IFile;
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
  @SuppressWarnings("serial")
  @Override
  public void start(BundleContext context) throws Exception {

    //
    _project2provider = new GenericCache<IProject, List<JdtProjectContentProvider>>() {
      @Override
      protected List<JdtProjectContentProvider> create(IProject key) {
        return new CopyOnWriteArrayList<JdtProjectContentProvider>();
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

            //
            IResource eclipseResource = delta.getResource();

            //
            if (!(eclipseResource instanceof IFile)) {
              return true;
            }

            //
            for (Entry<IProject, List<JdtProjectContentProvider>> entry : _project2provider.entrySet()) {

              //
              if (!entry.getKey().isAccessible()) {
                continue;
              }

              // we can use 'getFullPath' here (getFullPath always contains the project name)
              if (entry.getKey().getFullPath().isPrefixOf(eclipseResource.getFullPath())) {

                for (JdtProjectContentProvider jdtProjectContentProvider : entry.getValue()) {

                  if (delta.getKind() == IResourceDelta.ADDED) {

                    //
                    jdtProjectContentProvider.addEclipseResource(eclipseResource);

                  } else {

                    //
                    IProjectContentResource contentResource = jdtProjectContentProvider
                        .getProjectContentResource(eclipseResource);

                    //
                    if (delta.getKind() == IResourceDelta.CHANGED) {
                      jdtProjectContentProvider
                          .fireProjectContentChangedEvent(new BundleMakerProjectContentChangedEvent(
                              jdtProjectContentProvider.getBundleMakerProject(),
                              BundleMakerProjectContentChangedEvent.Type.MODIFIED, contentResource));
                    }

                    //
                    else if (delta.getKind() == IResourceDelta.REMOVED) {
                      jdtProjectContentProvider
                          .fireProjectContentChangedEvent(new BundleMakerProjectContentChangedEvent(
                              jdtProjectContentProvider.getBundleMakerProject(),
                              BundleMakerProjectContentChangedEvent.Type.REMOVED, contentResource));
                    }

                  }
                }
              }
            }

            return true;
          }
        };

        //
        try {
          if (rootDelta != null) {
            rootDelta.accept(visitor);
          }
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
