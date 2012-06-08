/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.event.selection.workbench.view;

import org.bundlemaker.core.analysis.IArtifactModelModifiedListener;
import org.bundlemaker.core.ui.event.selection.IDependencySelection;
import org.bundlemaker.core.ui.event.selection.IDependencySelectionChangedEvent;
import org.bundlemaker.core.ui.event.selection.IDependencySelectionListener;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractDependencySelectionAwareViewPart extends AbstractPartLifecycleAwareViewPart implements
    IDependencySelectionListener, IArtifactModelModifiedListener {

  /** - */
  private IDependencySelection _currentDependencySelection;

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(IViewSite site) throws PartInitException {
    super.init(site);

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

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public void onPartBroughtToTop() {
  // super.onPartBroughtToTop();
  // initDependencySelectionFromSelectionService();
  // }

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
   * @return
   */
  protected String getSelectionId() {
    return Selection.MAIN_DEPENDENCY_SELECTION_ID;
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
   * @param dependencySelection
   */
  protected void onSetDependencySelection(IDependencySelection dependencySelection) {
    // empty body
  }

  private void unregisterArtifactModelChangedListener() {
    if (_currentDependencySelection != null && _currentDependencySelection.hasDependencies()) {
      _currentDependencySelection.getFirstDependency().getFrom().getRoot().removeArtifactModelChangedListener(this);
    }
  }

  private void registerArtifactModelChangedListener() {
    if (_currentDependencySelection != null && _currentDependencySelection.hasDependencies()) {
      _currentDependencySelection.getFirstDependency().getFrom().getRoot().addArtifactModelChangedListener(this);
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
