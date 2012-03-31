package org.bundlemaker.core.ui.selection.internal;

import org.bundlemaker.core.analysis.IRootArtifact;
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

  /**
   * <p>
   * </p>
   * 
   * @return
   * @see org.bundlemaker.core.ui.selection.IProviderSelection#getSelectionId()
   */
  public String getSelectionId() {
    return _rootArtifactSelection.getSelectionId();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @see org.bundlemaker.core.ui.selection.IRootArtifactSelection#getSelectedRootArtifact()
   */
  public IRootArtifact getSelectedRootArtifact() {
    return _rootArtifactSelection.getSelectedRootArtifact();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @see org.bundlemaker.core.ui.selection.IProviderSelection#getProviderId()
   */
  public String getProviderId() {
    return _rootArtifactSelection.getProviderId();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @see org.bundlemaker.core.ui.selection.IRootArtifactSelection#hasSelectedRootArtifact()
   */
  public boolean hasSelectedRootArtifact() {
    return _rootArtifactSelection.hasSelectedRootArtifact();
  }
}
