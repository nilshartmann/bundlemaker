package org.bundlemaker.core.jdt.internal;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bundlemaker.core.jdt.content.JdtProjectContentProvider;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

final class InternalResourceChangedListener implements IResourceChangeListener {

  /** - */
  private final Map<IProject, List<JdtProjectContentProvider>> _project2provider;

  /**
   * <p>
   * </p>
   * 
   * @param activator
   */
  public InternalResourceChangedListener(Map<IProject, List<JdtProjectContentProvider>> project2provider) {
    Assert.isNotNull(project2provider);

    _project2provider = project2provider;
  }

  //
  public void resourceChanged(IResourceChangeEvent event) {

    // // we are only interested in POST_CHANGE events
    // if (event.getType() != IResourceChangeEvent.POST_CHANGE)
    // return;

    IResourceDelta rootDelta = event.getDelta();
    // get the delta, if any, for the documentation directory

    IResourceDeltaVisitor visitor = new InternalResourceDeltaVisitor();

    //
    if (rootDelta != null) {
      try {
        rootDelta.accept(visitor);
      } catch (CoreException e) {
        throw new RuntimeException(e.getMessage(), e);
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class InternalResourceDeltaVisitor implements IResourceDeltaVisitor {

    /**
     * {@inheritDoc}
     */
    public boolean visit(IResourceDelta delta) {

      //
      IResource eclipseResource = delta.getResource();

      //
      if (eclipseResource instanceof IProject
          && Activator.getInstance().getProject2ProviderMap().containsKey(eclipseResource)) {

        if (delta.getKind() == IResourceDelta.REMOVED) {
          Activator.getInstance().getProject2ProviderMap().remove(eclipseResource);
        }

        return true;
      }

      //
      if (!(eclipseResource instanceof IFile)) {
        return true;
      }

      //
      for (Entry<IProject, List<JdtProjectContentProvider>> entry : InternalResourceChangedListener.this._project2provider
          .entrySet()) {

        //
        if (!entry.getKey().isAccessible()) {
          continue;
        }

        // we can use 'getFullPath' here (getFullPath always
        // contains the project name)
        if (entry.getKey().getFullPath().isPrefixOf(eclipseResource.getFullPath())) {

          for (JdtProjectContentProvider jdtProjectContentProvider : entry.getValue()) {

            if (delta.getKind() == IResourceDelta.ADDED) {
              jdtProjectContentProvider.eclipseResourceAdded(eclipseResource);

            } else if (delta.getKind() == IResourceDelta.REMOVED) {
              jdtProjectContentProvider.eclipseResourceRemoved(eclipseResource);

            } else if (delta.getKind() == IResourceDelta.CHANGED) {
              jdtProjectContentProvider.eclipseResourceChanged(eclipseResource);

            }
          }
        }
      }

      return true;
    }
  }
}