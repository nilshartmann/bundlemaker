package org.bundlemaker.core.ui.projecteditor;

import java.util.Set;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.ui.projecteditor.dnd.DropLocation;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropEvent;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropProvider;
import org.bundlemaker.core.ui.projecteditor.dnd.internal.ProjectEditorDropEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;

public class ProjectEditorTreeViewerDropAdapter extends ViewerDropAdapter {

  private final IBundleMakerProject             _bundleMakerProject;

  private final Set<IProjectEditorDropProvider> _dndProviders;

  /**
   * Will be set in validateDrop and invalidated in performDrop
   */
  private ProjectEditorDropEvent                _currentDropEvent;

  private IProjectEditorDropProvider            _providerCandidate;

  public ProjectEditorTreeViewerDropAdapter(Viewer viewer, IBundleMakerProject bundleMakerProject,

  Set<IProjectEditorDropProvider> dndProviders) {
    super(viewer);
    _bundleMakerProject = bundleMakerProject;
    _dndProviders = dndProviders;
  }

  private void resetState() {
    _providerCandidate = null;
    _currentDropEvent = null;
  }

  @Override
  public boolean validateDrop(Object target, int operation, TransferData transferType) {

    // reset the state
    resetState();

    // create a new drop event
    ProjectEditorDropEvent dropEvent = createDropEvent(target);

    // try to find a provider for the drop data
    for (IProjectEditorDropProvider dndProvider : _dndProviders) {

      if (canHandleTransferType(dndProvider, transferType)) {
        if (canDrop(dndProvider, dropEvent)) {
          // TODO support multiple providers
          _providerCandidate = dndProvider;
          break;
        }
      }
    }

    // at least one provider found: save event and return
    if (_providerCandidate != null) {
      // Save drop event for use in performDrop()
      _currentDropEvent = dropEvent;

      // can drop
      return true;
    }
    // no handler found => drop not possible
    return false;
  }

  protected boolean canDrop(IProjectEditorDropProvider provider, IProjectEditorDropEvent event) {
    try {
      return provider.canDrop(event);
    } catch (Exception ex) {
      Activator.logError("canDrop failed: " + ex, ex);
    }
    return false;
  }

  @Override
  public boolean performDrop(Object data) {

    try {
      // try to do the actual drop
      boolean result = performDropInternal(data);

      // reset the state (event and drop provider)
      resetState();

      // return the result
      return result;
    } catch (RuntimeException ex) {
      // in case of an error at least reset the state
      resetState();

      // re-throw
      throw ex;
    }
  }

  private boolean performDropInternal(Object data) {

    if (_providerCandidate == null || _currentDropEvent == null) {
      // this really should not happen. anyway: in case it does, do nothing
      return false;
    }

    // update the current event with the data to be dropped
    _currentDropEvent.setData(data);

    // let the provider do the drop
    try {
      boolean dropped = _providerCandidate.performDrop(_currentDropEvent);

      if (dropped) {
        afterDrop(getCurrentTarget());
      }
      return dropped;

    } catch (Exception ex) {
      // log exception
      Activator.logError("Cannot drop: " + ex, ex);
    }

    return false;
  }

  /**
   * Will be invoked after the drop has been done.
   * 
   * @TODO replace with listener?!
   */
  protected void afterDrop(Object target) {

  }

  private ProjectEditorDropEvent createDropEvent(Object target) {

    if (target instanceof ProjectEditorTreeViewerElement) {
      target = ((ProjectEditorTreeViewerElement) target).getElement();
    }

    int location = this.determineLocation(getCurrentEvent());
    DropLocation dropLocation = DropLocation.getDropLocation(location);
    ProjectEditorDropEvent projectEditorDropEvent = new ProjectEditorDropEvent(getViewer().getControl().getShell(),
        _bundleMakerProject, target, dropLocation, getCurrentEvent().currentDataType);

    return projectEditorDropEvent;

  }

  /**
   * Checks whether the given dndprovider can handle the given Transfer type
   * 
   * @param dndProvider
   * @param transferType
   * @return
   */
  private boolean canHandleTransferType(IProjectEditorDropProvider dndProvider, TransferData transferType) {

    Transfer[] supportedTypes = dndProvider.getSupportedDropTypes();
    for (Transfer transfer : supportedTypes) {
      if (transfer.isSupportedType(transferType)) {
        return true;
      }
    }

    return false;
  }
}
