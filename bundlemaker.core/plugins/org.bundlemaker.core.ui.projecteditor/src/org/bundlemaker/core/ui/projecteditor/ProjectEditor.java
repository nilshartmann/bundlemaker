/**
 * 
 */
package org.bundlemaker.core.ui.projecteditor;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.BundleMakerProjectChangedEvent;
import org.bundlemaker.core.BundleMakerProjectChangedEvent.Type;
import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IBundleMakerProjectChangedListener;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

/**
 * <p>
 * Provides a graphical editor for BundleMaker's project artifacts
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ProjectEditor extends FormEditor {

  private final BundleMakerProjectDirtyListener _bundleMakerProjectDirtyListener = new BundleMakerProjectDirtyListener();

  private IBundleMakerProject                   _bundleMakerProject;

  private boolean                               _projectDirty                    = false;

  public ProjectEditor() {
    super();
  }

  @Override
  protected void addPages() {
    try {
      addPage(new ProjectEditorPage(this));
    } catch (Exception ex) {
      Activator.logError("Could not add page to editor", ex);
    }

  }

  @Override
  public void doSave(IProgressMonitor monitor) {
    // commit all editor pages
    commitPages(true);

    // save the project
    try {
      _bundleMakerProject.getModifiableProjectDescription().save();

    } catch (Exception ex) {
      Activator.logError("Error while saving project: " + ex, ex);
    }

  }

  @Override
  public void doSaveAs() {
    // "Save As" is not allowed.
  }

  @Override
  public boolean isSaveAsAllowed() {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.forms.editor.FormEditor#isDirty()
   */
  @Override
  public boolean isDirty() {
    return _projectDirty;
  }

  protected void setProjectDirty(boolean dirty) {
    boolean oldValue = _projectDirty;
    _projectDirty = dirty;

    if (_projectDirty != oldValue) {
      editorDirtyStateChanged();
    }
  }

  /**
   * Opens the {@link IBundleMakerProject}
   */
  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    super.init(site, input);

    IFileEditorInput adapter = (IFileEditorInput) input.getAdapter(IFileEditorInput.class);

    if (adapter == null) {
      Activator.logErrorMessage("Unsupported EditorInput '%s' cannot be adapted to '%s'", input,
          IFileEditorInput.class.getName());
      return;
    }

    // Open the BundleMaker project
    IProject project = adapter.getFile().getProject();

    setPartName(project.getName());
    try {
      // TODO use ProgressMonitor
      IBundleMakerProject bundleMakerProject = BundleMakerCore
          .getBundleMakerProject(project);

      _bundleMakerProject = bundleMakerProject;

      // add listener
      _bundleMakerProject.addBundleMakerProjectChangedListener(this._bundleMakerProjectDirtyListener);

    } catch (Exception ex) {
      throw new PartInitException("Could not open BundleMaker project", ex);
    }

  }

  /**
   * Returns the {@link IBundleMakerProject} that this editor is working on.
   * <p>
   * </p>
   * 
   * @return The IBundleMakerProject instance. Never null.
   */
  public IBundleMakerProject getBundleMakerProject() {
    Assert.isNotNull(_bundleMakerProject);
    return this._bundleMakerProject;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.forms.editor.FormEditor#dispose()
   */
  @Override
  public void dispose() {
    // remove dirty listener
    if (_bundleMakerProject != null) {
      _bundleMakerProject.removeBundleMakerProjectChangedListener(_bundleMakerProjectDirtyListener);

      boolean d = isDirty();
      if (d) {
        // window is closed, changes already made to the model must be discarded by re-loading the bundlemaker.json from
        // storage
        try {
          _bundleMakerProject.reloadProjectDescription();
        } catch (CoreException e) {
          Activator.logError("Error while re-reading project description from disc: " + e, e);
        }
      }
    }

    // dispose
    super.dispose();
  }

  /**
   * A Listener that tracks configuration state of the project that is edited in this editor.
   * 
   * @author Nils Hartmann (nils@nilshartmann.net)
   * 
   */
  class BundleMakerProjectDirtyListener implements IBundleMakerProjectChangedListener {

    /*
     * (non-Javadoc)
     * 
     * @see org.bundlemaker.core.IBundleMakerProjectChangedListener#bundleMakerProjectChanged(org.bundlemaker.core.
     * BundleMakerProjectChangedEvent)
     */
    @Override
    public void bundleMakerProjectChanged(BundleMakerProjectChangedEvent event) {
      if (event.getType() == Type.PROJECT_DESCRIPTION_CHANGED) {
        setProjectDirty(true);
      }

      if (event.getType() == Type.PROJECT_DESCRIPTION_SAVED) {
        setProjectDirty(false);
      }

      if (event.getType() == Type.PROJECT_STATE_CHANGED
          && _bundleMakerProject.getState() == BundleMakerProjectState.DISPOSED) {

        // Project has been disposed => close editor
        getSite().getShell().getDisplay().asyncExec(new Runnable() {

          @Override
          public void run() {
            getSite().getWorkbenchWindow().getActivePage().closeEditor(ProjectEditor.this, false);
          }
        });
      }
    }

  }

}
