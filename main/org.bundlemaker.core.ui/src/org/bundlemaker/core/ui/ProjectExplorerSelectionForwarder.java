package org.bundlemaker.core.ui;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.selection.IArtifactSelectionService;
import org.bundlemaker.core.ui.selection.Selection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

/**
 * A {@link ISelectionListener} that forwards the selected artifacts to the {@link IArtifactSelectionService}
 * 
 * @author Nils Hartmann
 * 
 */
public class ProjectExplorerSelectionForwarder implements ISelectionListener {

  private final IArtifactSelectionService _artifactSelectionService;

  public ProjectExplorerSelectionForwarder(IArtifactSelectionService artifactSelectionService) {
    super();
    _artifactSelectionService = artifactSelectionService;
  }

  @Override
  public void selectionChanged(IWorkbenchPart part, ISelection selection) {
    // Get selected artifacts
    List<IBundleMakerArtifact> artifacts = getSelectedObjects(selection, IBundleMakerArtifact.class);

    // notify selection service
    _artifactSelectionService.setSelection(Selection.MAIN_ARTIFACT_SELECTION_ID, Selection.PROJECT_EXPLORER_VIEW_ID,
        artifacts, artifacts.size() == 1);
  }

  /**
   * Returns all elements in the given {@link ISelection} that are of the specified type
   * 
   * <p>
   * Selected objects in the specified selection that are <em>not</em> instances of the specified type are ignored.
   * 
   * @param <T>
   * @param selection
   *          The object containing selected objects
   * @param type
   *          the expected type of the result objects
   * @return
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static <T> List<T> getSelectedObjects(ISelection selection, Class<T> type) {
    final List<T> result = new LinkedList<T>();
    if (selection instanceof IStructuredSelection) {
      IStructuredSelection structuredSelection = (IStructuredSelection) selection;
      Iterator iterator = structuredSelection.iterator();
      while (iterator.hasNext()) {
        Object element = iterator.next();
        if (type.isInstance(element)) {
          result.add((T) element);
        }
      }
    }
    return result;
  }

}
