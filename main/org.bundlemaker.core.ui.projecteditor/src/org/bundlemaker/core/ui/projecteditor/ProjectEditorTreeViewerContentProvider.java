package org.bundlemaker.core.ui.projecteditor;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.BundleMakerProjectChangedEvent;
import org.bundlemaker.core.BundleMakerProjectChangedEvent.Type;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IBundleMakerProjectChangedListener;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.bundlemaker.core.ui.projecteditor.provider.internal.ProjectEditorContributionRegistry;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

/**
 * <p>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProjectEditorTreeViewerContentProvider implements ITreeContentProvider {

  /** - */
  private final static Object[]                   EMPTY_RESULT = new Object[0];

  /** - */
  private final ProjectEditorContributionRegistry _projectEditorContributionRegistry;

  /** - */
  private Viewer                                  _viewer;

  /** - */
  private IBundleMakerProjectChangedListener      _listener;

  /**
   * <p>
   * Creates a new instance of type {@link ProjectEditorTreeViewerContentProvider}.
   * </p>
   * 
   * @param projectContentProviderEditorRegistry
   */
  public ProjectEditorTreeViewerContentProvider(
      ProjectEditorContributionRegistry projectContentProviderEditorRegistry) {

    //
    Assert.isNotNull(projectContentProviderEditorRegistry);

    //
    _projectEditorContributionRegistry = projectContentProviderEditorRegistry;

    //
    _listener = new IBundleMakerProjectChangedListener() {
      @Override
      public void bundleMakerProjectChanged(BundleMakerProjectChangedEvent event) {
        if (event.getType().equals(Type.PROJECT_DESCRIPTION_RECOMPUTED) && _viewer != null) {

          // async refresh
          Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
              if (!(_viewer instanceof ColumnViewer) || !((ColumnViewer) _viewer).isBusy()) {
                _viewer.refresh();
              }
            }
          });
        }
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

    // the viewer
    _viewer = viewer;

    //
    if (newInput != null) {
      ((IBundleMakerProject) newInput).addBundleMakerProjectChangedListener(_listener);
    }

    //
    if (oldInput != null) {
      ((IBundleMakerProject) oldInput).removeBundleMakerProjectChangedListener(_listener);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[] getElements(Object inputElement) {

    // cast to IBundleMakerProject
    IBundleMakerProject bundleMakerProject = (IBundleMakerProject) inputElement;

    // create the result
    List<Object> result = new LinkedList<Object>();

    List<? extends IProjectContentProvider> contentProviders = bundleMakerProject.getModifiableProjectDescription()
        .getContentProviders();

    for (IProjectContentProvider iProjectContentProvider : contentProviders) {
      Set<IProjectContentProviderEditor> projectContentProviderEditors = _projectEditorContributionRegistry
          .getContentProviderEditors();
      for (IProjectContentProviderEditor iProjectContentProviderEditor : projectContentProviderEditors) {
        if (iProjectContentProviderEditor.canHandle(iProjectContentProvider)) {
          Object rootElement = iProjectContentProviderEditor
              .getRootElement(bundleMakerProject, iProjectContentProvider);
          if (rootElement != null) {

            ProjectEditorTreeViewerElement treeViewerElement = new ProjectEditorTreeViewerElement(bundleMakerProject,
                iProjectContentProvider, rootElement, iProjectContentProviderEditor);

            result.add(treeViewerElement);
            break;
          }
        }
      }
    }

    return result.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[] getChildren(Object parent) {

    if (!(parent instanceof ProjectEditorTreeViewerElement)) {
      // should not happen. in case it does, simply ignore the entry
      return EMPTY_RESULT;
    }

    ProjectEditorTreeViewerElement parentElement = (ProjectEditorTreeViewerElement) parent;

    List<? extends Object> children = null;

    // Ask provider for children
    try {
      children = parentElement.getProvidingEditor().getChildren(parentElement.getBundleMakerProject(),
          parentElement.getProjectContentProvider(), parentElement.getElement());
    } catch (Exception ex) {
      Activator.logError("Error while retrieving children for project editor: " + ex, ex);
    }

    if (children == null || children.isEmpty()) {
      return EMPTY_RESULT;
    }

    // Wrap returned elements in TreeViewerElement objects
    List<Object> result = new LinkedList<Object>();

    for (Object child : children) {
      result.add(parentElement.deriveChild(child));
    }

    return result.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasChildren(Object element) {
    if (!(element instanceof ProjectEditorTreeViewerElement)) {
      // should not happen. in case it does, simply ignore the entry
      return false;
    }

    ProjectEditorTreeViewerElement parentElement = (ProjectEditorTreeViewerElement) element;
    Object candidate = parentElement.getElement();

    if (candidate instanceof IProjectContentProvider) {
      // Project content provider should always have childs. Don't ask to save performance
      // in cases where getting the actual content is expensive
      return true;
    }

    return getChildren(element).length > 0;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getParent(Object element) {
    return null;
  }
}
