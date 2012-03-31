package org.bundlemaker.core.ui.selection.workbench.editor;

import java.util.Collections;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.selection.IArtifactSelection;
import org.bundlemaker.core.ui.selection.IArtifactSelectionChangedEvent;
import org.bundlemaker.core.ui.selection.IArtifactSelectionListener;
import org.bundlemaker.core.ui.selection.Selection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractArtifactSelectionAwareEditorPart extends AbstractPartLifecycleAwareEditorPart implements
    IArtifactSelectionListener {

  /**
   * The current artifacts (contents) of this dependency part
   */
  private List<IBundleMakerArtifact> _currentArtifacts;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifactSelectionAwareEditorPart}.
   * </p>
   */
  public AbstractArtifactSelectionAwareEditorPart() {
    _currentArtifacts = Collections.emptyList();
  }

  /**
   * <p>
   * Returns the {@link IBundleMakerArtifact} instances that should be visualized
   * </p>
   * 
   * @return
   */
  public List<IBundleMakerArtifact> getCurrentArtifacts() {
    return _currentArtifacts;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {

    // call super
    super.init(site, input);

    // add listener
    Selection.instance().getArtifactSelectionService().addArtifactSelectionListener(getProviderId(), this);

    // initialize view with current selection from Artifact tree
    IArtifactSelection currentArtifactSelection = Selection.instance().getArtifactSelectionService()
        .getSelection(getProviderId());

    if (currentArtifactSelection != null) {
      setCurrentArtifacts(currentArtifactSelection.getSelectedArtifacts());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    // Remove ourself from the list of listeners
    Selection.instance().getArtifactSelectionService().removeArtifactSelectionListener(this);

    // invoke super
    super.dispose();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void artifactSelectionChanged(IArtifactSelectionChangedEvent event) {

    //
    if (!isActive()) {
      return;
    }

    System.out.println("onArtifactSelectionChanged " + event.getSelection().getSelectedArtifacts());

    //
    onArtifactSelectionChanged(event);
  }

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  protected abstract void onArtifactSelectionChanged(IArtifactSelectionChangedEvent event);

  /**
   * This method is invoked to set the artifacts that should be visualized when this editor is visible
   * <p>
   * </p>
   * 
   * @param artifacts
   *          The new artifacts. Must not be null but might be empty
   */
  // TODO RENAME
  protected void setCurrentArtifacts(List<IBundleMakerArtifact> artifacts) {
    _currentArtifacts = artifacts;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getProviderId() {
    return Selection.MAIN_ARTIFACT_SELECTION_ID;
  }
}
