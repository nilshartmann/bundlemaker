package org.bundlemaker.analysis.ui.selection;

/**
 * An event describing a changed dependency
 * 
 * @author Nils Hartmann
 * 
 * @noimplement This interface should not be implemented by clients
 */
public interface IDependencySelectionChangedEvent {

  /**
   * Not null but might be empty
   */
  public IDependencySelection getSelection();

}
