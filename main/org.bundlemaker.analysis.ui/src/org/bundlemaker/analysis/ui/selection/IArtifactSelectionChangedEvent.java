package org.bundlemaker.analysis.ui.selection;

/**
 * An event describing a changed selection
 * 
 * @author Nils Hartmann
 * 
 * @noimplement This interface should not be implemented by clients
 */
public interface IArtifactSelectionChangedEvent {

  /**
   * Not null but might be empty
   */
  public IArtifactSelection getSelection();

}
