package org.bundlemaker.analysis.ui.selection;

/**
 * A listener that is invoked when a selection changed.
 * 
 * <p>
 * This method must be implemented by clients
 * 
 * @author Nils Hartmann
 * 
 */
public interface IArtifactSelectionListener {

  /**
   * Is invoked when a selection has been changed
   * 
   * @param event
   *          the event describing the selection. Never null.
   */
  public void artifactSelectionChanged(IArtifactSelectionChangedEvent event);

}
