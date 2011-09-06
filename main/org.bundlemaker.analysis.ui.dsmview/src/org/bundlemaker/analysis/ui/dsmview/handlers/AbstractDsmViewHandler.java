package org.bundlemaker.analysis.ui.dsmview.handlers;

import java.util.List;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.dsmview.DSMView;
import org.bundlemaker.analysis.ui.handlers.AbstractArtifactBasedHandler;
import org.eclipse.core.commands.ExecutionEvent;

public abstract class AbstractDsmViewHandler extends AbstractArtifactBasedHandler {

  @Override
  protected void execute(ExecutionEvent event, List<IArtifact> selectedArtifacts) throws Exception {

    // // Special handling if a single artifact of type class is selected
    // if (isSingleClassArtifactSelected(selectedArtifacts)) {
    // IArtifact singleClassArtifact = selectedArtifacts.get(0);
    // JavaEditor.openTypeInEditor(singleClassArtifact.getQualifiedName());
    // List<IArtifact> artifacts = new ArrayList<IArtifact>();
    // artifacts.add(singleClassArtifact);
    // artifacts.add(singleClassArtifact.getParent(ArtifactType.Root));
    // openEditorAndViews(artifacts);
    // return;
    // }

    // get the artifacts that should be displayed in DSM View
    List<IArtifact> artifactsForDsmView = getArtifactsForDsmView(selectedArtifacts);

    // make sure the editor and views are visible
    openEditorAndViews(artifactsForDsmView);
  }

  /**
   * Return the IArtifacts from the list of selected artifacts that should be added to the DSM view.
   * 
   * @param selectedArtifacts
   * @return
   */
  protected abstract List<IArtifact> getArtifactsForDsmView(List<IArtifact> selectedArtifacts);

  private boolean isSingleClassArtifactSelected(List<IArtifact> selectedArtifacts) {
    return (selectedArtifacts.size() == 1 && selectedArtifacts.get(0).getType().equals(ArtifactType.Type));
  }

  private void openEditorAndViews(List<IArtifact> selectedArtifacts) {
    Analysis.instance().showInGenericEditor(DSMView.class.getName(), selectedArtifacts);
    // Open the DependencyTreeTableView
    // Analysis.instance().openDependencyTreeTableView();
  }
}
