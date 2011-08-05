package org.bundlemaker.analysis.ui.internal.selection;

import org.bundlemaker.analysis.ui.selection.IArtifactSelection;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionChangedEvent;
import org.eclipse.core.runtime.Assert;

/**
 * @author Nils Hartmann
 * 
 */
public class ArtifactSelectionChangedEvent implements IArtifactSelectionChangedEvent {

  /**
   * the new selection
   */
  private final IArtifactSelection _selection;

  public ArtifactSelectionChangedEvent(IArtifactSelection selection) {
    Assert.isNotNull(selection, "The parameter 'selection' must not be null");

    _selection = selection;
  }

  @Override
  public IArtifactSelection getSelection() {
    return _selection;
  }

}
