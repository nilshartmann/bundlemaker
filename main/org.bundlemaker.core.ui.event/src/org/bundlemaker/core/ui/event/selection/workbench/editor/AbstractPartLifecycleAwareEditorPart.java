package org.bundlemaker.core.ui.event.selection.workbench.editor;

import org.bundlemaker.core.ui.event.selection.internal.lifecycle.LifecycleAwarePartListener;
import org.bundlemaker.core.ui.event.selection.workbench.ILifecycleAwarePart;
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
public abstract class AbstractPartLifecycleAwareEditorPart extends EditorPart implements ILifecycleAwarePart {

  /** - */
  private LifecycleAwarePartListener _partListener;

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

    //
    setInput(input);
    setSite(site);

    // register part listener
    _partListener = new LifecycleAwarePartListener(this);
    this.getSite().getPage().addPartListener(_partListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    // register part listener
    this.getSite().getPage().removePartListener(_partListener);

    super.dispose();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPartActivated() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPartBroughtToTop() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPartClosed() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPartDeactivated() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPartOpened() {
    //
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final boolean isActive() {
    return getSite().getPage().getActiveEditor() == this;
  }
}
