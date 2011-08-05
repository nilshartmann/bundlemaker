package org.bundlemaker.analysis.ui.internal.selection;

import org.bundlemaker.analysis.ui.selection.IArtifactSelection;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionChangedEvent;

/**
 * Default implementation of {@link IArtifactSelectionChangedEvent}
 * 
 * @author Nils Hartmann
 * 
 */
public class ArtifactSelectionEvent implements IArtifactSelectionChangedEvent {

  /**
   * The new selection
   */
  private final IArtifactSelection _selection;

  public ArtifactSelectionEvent(IArtifactSelection artifactSelection) {
    _selection = artifactSelection;
  }

  @Override
  public IArtifactSelection getSelection() {
    return _selection;
  }

}
