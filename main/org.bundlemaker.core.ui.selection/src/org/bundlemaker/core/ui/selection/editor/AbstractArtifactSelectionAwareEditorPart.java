package org.bundlemaker.core.ui.selection.editor;

import java.util.Collections;
import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.ui.selection.IArtifactSelection;
import org.bundlemaker.core.ui.selection.IArtifactSelectionChangedEvent;
import org.bundlemaker.core.ui.selection.IArtifactSelectionListener;
import org.bundlemaker.core.ui.selection.IArtifactSelectionService;
import org.bundlemaker.core.ui.selection.Selection;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractArtifactSelectionAwareEditorPart extends EditorPart implements IArtifactSelectionListener {

  /**
   * The current artifacts (contents) of this dependency part
   */
  private List<IArtifact> _currentArtifacts;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifactSelectionAwareEditorPart}.
   * </p>
   */
  public AbstractArtifactSelectionAwareEditorPart() {
    _currentArtifacts = Collections.emptyList();
  }

  /**
   * This method is invoked to set the artifacts that should be visualized when this editor is visible
   * <p>
   * </p>
   * 
   * @param artifacts
   *          The new artifacts. Must not be null but might be empty
   */
  protected void useArtifacts(List<IArtifact> artifacts) {
    _currentArtifacts = artifacts;
  }

  /**
   * <p>
   * Returns the {@link IArtifact} instances that should be visualized
   * </p>
   * 
   * @return
   */
  public List<IArtifact> getCurrentArtifacts() {
    return _currentArtifacts;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDirty() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSaveAsAllowed() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doSave(IProgressMonitor monitor) {
    // nothing to do here
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doSaveAs() {
    // nothing to do here
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    setInput(input);
    setSite(site);

    // add listener
    Selection.instance().getArtifactSelectionService().addArtifactSelectionListener(getProviderId(), this);

    // initialize view with current selection from Artifact tree
    IArtifactSelection currentArtifactSelection = getArtifactSelectionService().getSelection(getProviderId());

    if (currentArtifactSelection != null) {
      useArtifacts(currentArtifactSelection.getSelectedArtifacts());
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
   * <p>
   * </p>
   * 
   * @return the IArtifactSelectionService
   */
  protected IArtifactSelectionService getArtifactSelectionService() {
    return Selection.instance().getArtifactSelectionService();
  }

  @Override
  public abstract void artifactSelectionChanged(IArtifactSelectionChangedEvent event);

  protected String getProviderId() {
    return Selection.MAIN_ARTIFACT_SELECTION_PROVIDER_ID;
  }
}
