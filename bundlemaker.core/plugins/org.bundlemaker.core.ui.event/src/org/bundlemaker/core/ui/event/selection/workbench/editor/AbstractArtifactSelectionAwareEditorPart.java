package org.bundlemaker.core.ui.event.selection.workbench.editor;

import org.bundlemaker.core.analysis.IAnalysisModelModifiedListener;
import org.bundlemaker.core.selection.IArtifactSelection;
import org.bundlemaker.core.selection.IArtifactSelectionListener;
import org.bundlemaker.core.selection.Selection;
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
    IArtifactSelectionListener, IAnalysisModelModifiedListener {

  /**
   * The current artifacts (contents) of this dependency part
   */
  private IArtifactSelection      _currentArtifactSelection;

  /** - */
  public final IArtifactSelection EMPTY_ARTIFACT_SELECTION = Selection.emptyArtifactSelection(getArtifactSelectionId(),
                                                               getProviderId());

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifactSelectionAwareEditorPart}.
   * </p>
   */
  public AbstractArtifactSelectionAwareEditorPart() {
    _currentArtifactSelection = EMPTY_ARTIFACT_SELECTION;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IArtifactSelection getCurrentArtifactSelection() {
    return _currentArtifactSelection;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {

    // call super
    super.init(site, input);

    // add listener
    Selection.instance().getArtifactSelectionService().addArtifactSelectionListener(getArtifactSelectionId(), this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    // Remove ourself from the list of listeners
    Selection.instance().getArtifactSelectionService().removeArtifactSelectionListener(this);

    //
    unregisterArtifactModelChangedListener();

    // invoke super
    super.dispose();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void artifactSelectionChanged(IArtifactSelection selection) {

    //
    if (!isActive()) {
      return;
    }

    // // skip self
    // if (selection.getProviderId().equals(getProviderId())) {
    // return;
    // }

    // skip already set
    if (getCurrentArtifactSelection() != null && getCurrentArtifactSelection().equals(selection)) {
      return;
    }

    setCurrentArtifactSelection(selection);
  }

  /**
   * This method is invoked to set the artifacts that should be visualized when this editor is visible
   * <p>
   * </p>
   * 
   * @param artifacts
   *          The new artifacts. Must not be null but might be empty
   */
  protected void setCurrentArtifactSelection(IArtifactSelection artifactSelection) {

    // remove ArtifactModelChangedListener from 'old' model
    unregisterArtifactModelChangedListener();

    _currentArtifactSelection = artifactSelection;

    registerArtifactModelChangedListener();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPartBroughtToTop() {

    // initialize view with current selection from Artifact stage
    initFromArtifactSelectionService();

  }

  private void unregisterArtifactModelChangedListener() {
    if (_currentArtifactSelection != null && _currentArtifactSelection.hasSelectedArtifacts()) {
      _currentArtifactSelection.getRootArtifact().removeAnalysisModelModifiedListener(this);
    }
  }

  private void registerArtifactModelChangedListener() {
    if (_currentArtifactSelection != null && _currentArtifactSelection.hasSelectedArtifacts()) {
      _currentArtifactSelection.getRootArtifact().addAnalysisModelModifiedListener(this);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getArtifactSelectionId() {
    return Selection.ARTIFACT_STAGE_SELECTION_ID;
  }

  protected abstract String getProviderId();

  /**
   * <p>
   * </p>
   * 
   */
  protected void initFromArtifactSelectionService() {
    IArtifactSelection currentArtifactSelection = Selection.instance().getArtifactSelectionService()
        .getSelection(getArtifactSelectionId());

    if (currentArtifactSelection != null) {
      setCurrentArtifactSelection(currentArtifactSelection);
    }
  }
}
