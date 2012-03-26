package org.bundlemaker.core.ui.dsmview.handlers;

import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.handlers.AbstractArtifactBasedHandler;
import org.bundlemaker.core.ui.dsmview.DsmViewWidget;
import org.eclipse.core.commands.ExecutionEvent;

public abstract class AbstractDsmViewHandler extends AbstractArtifactBasedHandler {

  @Override
  protected void execute(ExecutionEvent event, List<IArtifact> selectedArtifacts) throws Exception {

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

  private void openEditorAndViews(List<IArtifact> selectedArtifacts) {
    Analysis.instance().showInGenericEditor(DsmViewWidget.class.getName(), selectedArtifacts);
  }
}
