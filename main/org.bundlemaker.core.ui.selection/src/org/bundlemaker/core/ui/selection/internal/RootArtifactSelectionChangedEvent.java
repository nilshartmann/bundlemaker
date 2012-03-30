package org.bundlemaker.core.ui.selection.internal;

import org.bundlemaker.core.ui.selection.IRootArtifactSelection;
import org.bundlemaker.core.ui.selection.IRootArtifactSelectionChangedEvent;
import org.eclipse.core.runtime.Assert;

public class RootArtifactSelectionChangedEvent implements IRootArtifactSelectionChangedEvent {

  /** - */
  private IRootArtifactSelection _rootArtifactSelection;

  /**
   * <p>
   * Creates a new instance of type {@link RootArtifactSelectionChangedEvent}.
   * </p>
   * 
   * @param rootArtifactSelection
   */
  public RootArtifactSelectionChangedEvent(IRootArtifactSelection rootArtifactSelection) {
    Assert.isNotNull(rootArtifactSelection);

    _rootArtifactSelection = rootArtifactSelection;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifactSelection getSelection() {
    return _rootArtifactSelection;
  }
}
