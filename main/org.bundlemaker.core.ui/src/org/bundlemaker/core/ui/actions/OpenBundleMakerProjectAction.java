package org.bundlemaker.core.ui.actions;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.event.Events;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

public class OpenBundleMakerProjectAction extends Action implements ISelectionChangedListener {

  private IBundleMakerProject _bundleMakerProject;

  /**
   * {@inheritDoc}
   */
  @Override
  public void selectionChanged(SelectionChangedEvent event) {
    IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();

    if (structuredSelection.getFirstElement() instanceof IBundleMakerArtifact) {

      IBundleMakerArtifact makerArtifact = (IBundleMakerArtifact) structuredSelection.getFirstElement();

      _bundleMakerProject = makerArtifact.getRoot().getModularizedSystem().getBundleMakerProject();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    super.run();

    //
    if (_bundleMakerProject != null) {
      Events.instance().fireProjectOpened(_bundleMakerProject);
      // Re-activate common navigator make selections via context menu work
      CommonNavigatorUtils.activateCommonNavigator(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);
    }

    //
    // TODO: Handle DoubleCLick here...
  }
}
