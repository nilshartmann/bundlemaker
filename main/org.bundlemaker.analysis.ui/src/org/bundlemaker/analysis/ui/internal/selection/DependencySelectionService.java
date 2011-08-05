package org.bundlemaker.analysis.ui.internal.selection;

import java.util.Collection;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.ui.selection.IDependencySelection;
import org.bundlemaker.analysis.ui.selection.IDependencySelectionChangedEvent;
import org.bundlemaker.analysis.ui.selection.IDependencySelectionListener;
import org.bundlemaker.analysis.ui.selection.IDependencySelectionService;

public class DependencySelectionService extends
    AbstractSelectionService<IDependencySelectionListener, IDependencySelectionChangedEvent> implements
    IDependencySelectionService {

  @Override
  public IDependencySelection getSelection(String selectionProviderId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setSelection(String selectionProviderId, Collection<IDependency> selectedArtifacts) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setSelection(String selectionProviderId, IDependency dependency) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addDependencySelectionListener(String providerId, IDependencySelectionListener listener) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addDependencySelectionListener(IDependencySelectionListener listener) {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeDependencySelectionListener(IDependencySelectionListener listener) {
    // TODO Auto-generated method stub

  }

  @Override
  protected void invokeListener(IDependencySelectionListener listener, IDependencySelectionChangedEvent event) {
    // TODO Auto-generated method stub

  }

}
