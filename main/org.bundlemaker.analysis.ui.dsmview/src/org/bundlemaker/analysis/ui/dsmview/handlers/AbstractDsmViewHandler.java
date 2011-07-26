package org.bundlemaker.analysis.ui.dsmview.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.dependencies.DependencyGraph;
import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.dsmview.DSMView;
import org.bundlemaker.analysis.ui.handlers.AbstractArtifactBasedHandler;
import org.bundlemaker.analysis.ui.view.table.JavaEditor;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.internal.part.NullEditorInput;

@SuppressWarnings("restriction")
public abstract class AbstractDsmViewHandler extends AbstractArtifactBasedHandler {

  private IEditorInput nullInputEditor = new NullEditorInput();

  @Override
  protected void execute(ExecutionEvent event, List<IArtifact> selectedArtifacts) throws Exception {

    // Special handling if a single artifact of type class is selected
    if (isSingleClassArtifactSelected(selectedArtifacts)) {
      IArtifact singleClassArtifact = selectedArtifacts.get(0);
      JavaEditor.openTypeInEditor(singleClassArtifact.getQualifiedName());
      Collection<IArtifact> artifacts = new ArrayList<IArtifact>();
      artifacts.add(singleClassArtifact);
      artifacts.add(singleClassArtifact.getParent(ArtifactType.Root));
      setNewDependencyGraph(artifacts);
      openEditorAndViews(event);
      return;
    }

    // get the artifacts that should be displayed in DSM View
    Set<IArtifact> artifactsForDsmView = getArtifactsForDsmView(selectedArtifacts);

    // Create and set the dependencyGraph for the selected artifacts
    setNewDependencyGraph(new HashSet<IArtifact>(artifactsForDsmView));

    // make sure the editor and views are visible
    openEditorAndViews(event);
  }

  /**
   * Return the IArtifacts from the list of selected artifacts that should be added to the DSM view.
   * 
   * @param selectedArtifacts
   * @return
   */
  protected abstract Set<IArtifact> getArtifactsForDsmView(List<IArtifact> selectedArtifacts);

  private void setNewDependencyGraph(Collection<IArtifact> artifacts) {
    DependencyGraph dependencyGraph = DependencyGraph.calculateDependencyGraph(artifacts);
    Analysis.instance().getContext().setDependencyGraph(dependencyGraph);
  }

  private boolean isSingleClassArtifactSelected(List<IArtifact> selectedArtifacts) {
    return (selectedArtifacts.size() == 1 && selectedArtifacts.get(0).getType().equals(ArtifactType.Type));
  }

  private void openEditorAndViews(ExecutionEvent event) {
      // Open the 'GenericEditor'
    Analysis.instance().openGenericEditor();

      // Make sure, DSMView is visible on GenericEditor
      DSMView.showTab();

      // Open the DependencyTreeTableView
      Analysis.instance().openDependencyTreeTableView();
  }
}
