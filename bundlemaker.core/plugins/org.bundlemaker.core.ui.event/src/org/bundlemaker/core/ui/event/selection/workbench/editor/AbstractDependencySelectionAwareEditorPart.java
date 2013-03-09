package org.bundlemaker.core.ui.event.selection.workbench.editor;

import org.bundlemaker.core.analysis.IAnalysisModelModifiedListener;
import org.bundlemaker.core.ui.event.selection.IDependencySelection;
import org.bundlemaker.core.ui.event.selection.IDependencySelectionChangedEvent;
import org.bundlemaker.core.ui.event.selection.IDependencySelectionListener;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractDependencySelectionAwareEditorPart extends AbstractPartLifecycleAwareEditorPart implements
    IDependencySelectionListener, IAnalysisModelModifiedListener {

  /** the current dependency selection */
  private IDependencySelection _currentDependencySelection;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractDependencySelectionAwareEditorPart}.
   * </p>
   */
  public AbstractDependencySelectionAwareEditorPart() {

    // init the selection
    initDependencySelectionFromSelectionService();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    super.init(site, input);

    //
    Selection.instance().getDependencySelectionService().addDependencySelectionListener(getSelectionId(), this);

    //
    initDependencySelectionFromSelectionService();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    //
    Selection.instance().getDependencySelectionService().removeDependencySelectionListener(this);

    // call super
    super.dispose();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void dependencySelectionChanged(IDependencySelectionChangedEvent event) {

    //
    setDependencySelection(event.getSelection());
  }

  /**
   * <p>
   * </p>
   * 
   * @return the currentDependency
   */
  public IDependencySelection getCurrentDependencySelection() {
    return _currentDependencySelection;
  }

  /**
   * <p>
   * </p>
   * 
   * @param dependencySelection
   *          the currentDependencies to set
   */
  public void setCurrentDependencies(IDependencySelection dependencySelection) {

    // remove ArtifactModelChangedListener from 'old' model
    unregisterArtifactModelChangedListener();

    _currentDependencySelection = dependencySelection;

    registerArtifactModelChangedListener();
  }

  /**
   * <p>
   * </p>
   * 
   * @param dependencySelection
   */
  protected void onSetDependencySelection(IDependencySelection dependencySelection) {
    // empty body
  }

  /**
   * <p>
   * </p>
   * 
   * @param dependencySelection
   * 
   */
  protected final void setDependencySelection(final IDependencySelection dependencySelection) {
    _currentDependencySelection = dependencySelection;

    // init the dependencies
    Display.getCurrent().asyncExec(new Runnable() {
      @Override
      public void run() {
        onSetDependencySelection(dependencySelection);
      }

    });
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getSelectionId() {
    return Selection.MAIN_DEPENDENCY_SELECTION_ID;
  }

  /**
   * <p>
   * </p>
   */
  private void unregisterArtifactModelChangedListener() {
    if (_currentDependencySelection != null && _currentDependencySelection.hasDependencies()) {
      _currentDependencySelection.getFirstDependency().getFrom().getRoot().removeAnalysisModelModifiedListener(this);
    }
  }

  /**
   * <p>
   * </p>
   */
  private void registerArtifactModelChangedListener() {
    if (_currentDependencySelection != null && _currentDependencySelection.hasDependencies()) {
      _currentDependencySelection.getFirstDependency().getFrom().getRoot().addAnalysisModelModifiedListener(this);
    }
  }

  /**
   * <p>
   * </p>
   * 
   */
  private void initDependencySelectionFromSelectionService() {
    IDependencySelection dependencySelection = Selection.instance().getDependencySelectionService()
        .getSelection(getSelectionId());

    if (dependencySelection != null) {
      setDependencySelection(dependencySelection);
    }
  }
}
