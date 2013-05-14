package org.bundlemaker.core.ui.view.dependencytable;


/**
 * Determines the presentation of the bundlemaker artifacts in a DependencyTable row 
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public enum LabelPresentationMode {
  
  fullPath("Full Path", "Show the whole path of an Artifact"), 
  
  qualifiedTypeName("Qualified Type name", "Show only the qualified Type name of an Artifact"), 
  
  unqualifiedTypeName("Simple Type name", "Show only the simple Type name of an Artifact");

  private final String _label;
  private final String _tooltip;

  private LabelPresentationMode(String label, String tooltip) {
    this._label = label;
    this._tooltip = tooltip;
  }

  @Override
  public String toString() {
    return this._label;
  }
  
  public String getLabel() {
    return _label;
  }
  
  public String getTooltip() {
    return _tooltip;
  }

  
}
