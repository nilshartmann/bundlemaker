package org.bundlemaker.analysis.ui.editor;

import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.selection.IArtifactSelection;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionChangedEvent;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionListener;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionService;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * A base class for {@link DependencyPart} implementations that wants to react on {@link IArtifactSelectionChangedEvent}
 * events
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class ArtifactSelectionAwareDependencyPart extends DependencyPart implements IArtifactSelectionListener {

  @Override
  void init(Composite parent) {
    super.init(parent);

    // add listener
    Analysis.instance().getArtifactSelectionService().addArtifactSelectionListener(this);

    // initialize view with current selection from Artifact tree
    IArtifactSelection currentArtifactSelection = getArtifactSelectionService().getSelection(
        Analysis.PROJECT_EXPLORER_ARTIFACT_SELECTION_PROVIDER_ID);

    if (currentArtifactSelection != null) {
      useArtifacts(currentArtifactSelection.getSelectedArtifacts());
    }

  }

  @Override
  void dispose() {
    // Remove ourself from the list of listeners
    Analysis.instance().getArtifactSelectionService().removeArtifactSelectionListener(this);

    // invoke super
    super.dispose();
  }

  /**
   * <p>
   * </p>
   * 
   * @return the IArtifactSelectionService
   */
  protected IArtifactSelectionService getArtifactSelectionService() {
    return Analysis.instance().getArtifactSelectionService();
  }

  @Override
  public abstract void artifactSelectionChanged(IArtifactSelectionChangedEvent event);

}
