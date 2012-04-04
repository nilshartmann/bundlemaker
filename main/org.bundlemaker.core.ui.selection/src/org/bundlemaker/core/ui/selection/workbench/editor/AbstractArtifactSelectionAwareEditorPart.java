package org.bundlemaker.core.ui.selection.workbench.editor;

import org.bundlemaker.core.ui.selection.IArtifactSelection;
import org.bundlemaker.core.ui.selection.IArtifactSelectionListener;
import org.bundlemaker.core.ui.selection.Selection;
import org.bundlemaker.core.ui.selection.internal.ArtifactSelection;
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
  private IArtifactSelection      _currentArtifactSelection;

  /** - */
  public final IArtifactSelection EMPTY_ARTIFACT_SELECTION = new ArtifactSelection(getArtifactSelectionId(),
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

    // initialize view with current selection from Artifact tree
    initFromArtifactSelectionService();
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

    //
    onArtifactSelectionChanged(selection);
  }

  /**
   * <p>
   * <code>artifactSelectionChanged</code>
   * </p>
   * 
   * @param event
   */
  protected void onArtifactSelectionChanged(IArtifactSelection event) {
    setCurrentArtifactSelection(event);
  }

  /**
   * This method is invoked to set the artifacts that should be visualized when this editor is visible
   * <p>
   * </p>
   * 
   * @param artifacts
   *          The new artifacts. Must not be null but might be empty
   */
  protected final void setCurrentArtifactSelection(IArtifactSelection artifactSelection) {

    // _currentArtifactSelection.addArtifactModelChangedListener();

    _currentArtifactSelection = artifactSelection;

    onSetCurrentArtifactSelection(artifactSelection);
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifactSelection
   */
  protected void onSetCurrentArtifactSelection(IArtifactSelection artifactSelection) {
    //
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getArtifactSelectionId() {
    return Selection.MAIN_ARTIFACT_SELECTION_ID;
  }

  protected abstract String getProviderId();

  private void initFromArtifactSelectionService() {
    IArtifactSelection currentArtifactSelection = Selection.instance().getArtifactSelectionService()
        .getSelection(getArtifactSelectionId());

    if (currentArtifactSelection != null) {
      setCurrentArtifactSelection(currentArtifactSelection);
    }
  }
}
