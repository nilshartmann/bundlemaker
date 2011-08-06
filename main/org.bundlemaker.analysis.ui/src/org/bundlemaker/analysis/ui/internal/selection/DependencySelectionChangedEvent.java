package org.bundlemaker.analysis.ui.internal.selection;

import org.bundlemaker.analysis.ui.selection.IDependencySelection;
import org.bundlemaker.analysis.ui.selection.IDependencySelectionChangedEvent;
import org.eclipse.core.runtime.Assert;

/**
 * @author Nils Hartmann
 * 
 */
public class DependencySelectionChangedEvent implements IDependencySelectionChangedEvent {

  /**
   * the new selection
   */
  private final IDependencySelection _selection;

  public DependencySelectionChangedEvent(IDependencySelection selection) {
    Assert.isNotNull(selection, "The parameter 'selection' must not be null");

    _selection = selection;
  }

  @Override
  public IDependencySelection getSelection() {
    return _selection;
  }

}
