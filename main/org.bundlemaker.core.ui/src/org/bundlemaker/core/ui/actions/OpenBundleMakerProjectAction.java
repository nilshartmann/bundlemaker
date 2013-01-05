package org.bundlemaker.core.ui.actions;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.event.Events;
import org.bundlemaker.core.ui.utils.EditorHelper;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

public class OpenBundleMakerProjectAction extends Action implements ISelectionChangedListener {

  private IBundleMakerProject  _bundleMakerProject;

  private IStructuredSelection _structuredSelection;

  /**
   * {@inheritDoc}
   */
  @Override
  public void selectionChanged(SelectionChangedEvent event) {
    _structuredSelection = (IStructuredSelection) event.getSelection();

    if (_structuredSelection.getFirstElement() instanceof IBundleMakerArtifact) {

      IBundleMakerArtifact makerArtifact = (IBundleMakerArtifact) _structuredSelection.getFirstElement();

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

      // TODO
      if (_structuredSelection.size() == 1 && _structuredSelection.getFirstElement() instanceof IBundleMakerArtifact) {

        //
        IBundleMakerArtifact bundleMakerArtifact = (IBundleMakerArtifact) _structuredSelection.getFirstElement();

        //
        IResourceArtifact resourceArtifact = bundleMakerArtifact instanceof IResourceArtifact ? (IResourceArtifact) bundleMakerArtifact
            : bundleMakerArtifact.getParent(IResourceArtifact.class);

        //
        // ((IBundleMakerArtifact) ).getType().equals(ArtifactType.Resource)

        if (resourceArtifact != null) {

          try {
            EditorHelper.openArtifactInEditor((IBundleMakerArtifact) _structuredSelection.getFirstElement(), null);
          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }

        //
        return;
      }

      // TODO: RENAME
      Events.instance().fireProjectOpened(_bundleMakerProject);
      // Re-activate common navigator make selections via context menu work
      CommonNavigatorUtils.activateCommonNavigator(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);
    }
  }
}
