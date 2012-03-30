package org.bundlemaker.core.ui.selection;

public interface IRootArtifactSelectionService {

  /**
   * Return the current selection of the specified provider.
   * 
   * @param selectionProviderId
   *          the provider id. must not be null
   * @return the selection, or null if there is no selection for the specified provider
   */
  public IRootArtifactSelection getRootArtifactSelection(String selectionProviderId);

  /**
   * Registers an {@link IRootArtifactSelectionListener} for the specified providerId.
   * 
   * <p>
   * The listener is invoked if the provider's selection changes. If the providerId is null, the listener is invoked for
   * selection change on any provider
   * 
   * @param providerId
   *          the providerId or null
   * @param listener
   *          the listener. Must not be null
   */
  public void addRootArtifactSelectionListener(String providerId, IRootArtifactSelectionListener listener);

  // /**
  // * Registers an {@link IRootArtifactSelectionListener} for all providers.
  // *
  // * <p>
  // * Same as calling <tt>addRootArtifactSelectionListener(null, listener)</tt>
  // *
  // * @param listener
  // * the listener. Must not be null
  // */
  //
  // public void addRootArtifactSelectionListener(IRootArtifactSelectionListener listener);

  /**
   * Removes the specified listener
   * 
   * @param listener
   */
  public void removeRootArtifactSelectionListener(IRootArtifactSelectionListener listener);
}
