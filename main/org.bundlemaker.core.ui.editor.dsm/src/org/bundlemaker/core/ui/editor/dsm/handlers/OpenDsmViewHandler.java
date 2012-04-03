package org.bundlemaker.core.ui.editor.dsm.handlers;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.editor.dsm.DSMArtifactModelEditor;
import org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler;
import org.eclipse.core.commands.ExecutionEvent;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class OpenDsmViewHandler extends AbstractArtifactBasedHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {

    // open the editor
    openEditorAndViews(selectedArtifacts);
  }

  /**
   * <p>
   * </p>
   * 
   * @param selectedArtifacts
   */
  private void openEditorAndViews(List<IBundleMakerArtifact> selectedArtifacts) {
    DSMArtifactModelEditor.openDsmView();
  }
}
