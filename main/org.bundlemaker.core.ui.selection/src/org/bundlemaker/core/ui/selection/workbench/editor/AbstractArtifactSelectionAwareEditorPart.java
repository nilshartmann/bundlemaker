package org.bundlemaker.core.ui.selection.workbench.editor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.selection.IArtifactSelection;
import org.bundlemaker.core.ui.selection.IArtifactSelectionChangedEvent;
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
  public final IArtifactSelection EMPTY_ARTIFACT_SELECTION = new ArtifactSelection(getProviderId(), "");

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
    Selection.instance().getArtifactSelectionService().addArtifactSelectionListener(getProviderId(), this);

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
  public final void artifactSelectionChanged(IArtifactSelectionChangedEvent event) {

    //
    if (!isActive()) {
      return;
    }

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
  protected void setCurrentArtifactSelection(IArtifactSelection artifactSelection) {
    _currentArtifactSelection = artifactSelection;
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
  
  private void initFromArtifactSelectionService() {
    IArtifactSelection currentArtifactSelection = Selection.instance().getArtifactSelectionService()
        .getSelection(getProviderId());

    if (currentArtifactSelection != null) {
      setCurrentArtifactSelection(currentArtifactSelection);
    }
  }
}
