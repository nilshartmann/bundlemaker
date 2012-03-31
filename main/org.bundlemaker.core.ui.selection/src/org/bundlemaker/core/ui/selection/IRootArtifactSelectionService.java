package org.bundlemaker.core.ui.selection;

public interface IRootArtifactSelectionService {

  /**
   * Return the current selection of the specified provider.
   * 
   * @param artifactSelectionId
   *          the artifact selection id. must not be null
   * @return the selection, or null if there is no selection for the specified provider
   */
  public IRootArtifactSelection getRootArtifactSelection(String artifactSelectionId);

  /**
   * Registers an {@link IRootArtifactSelectionListener} for the specified artifactSelectionId.
   * 
   * <p>
   * The listener is invoked if the selection with the given artifactSelectionId changes.
   * 
   * @param artifactSelectionId
   *          the artifactSelectionId
   * @param listener
   *          the listener. Must not be null
   */
  public void addRootArtifactSelectionListener(String artifactSelectionId, IRootArtifactSelectionListener listener);

  /**
   * Removes the specified listener
   * 
   * @param listener
   */
  public void removeRootArtifactSelectionListener(IRootArtifactSelectionListener listener);
}
