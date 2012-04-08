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

import java.util.Collection;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.ui.event.selection.IDependencySelection;
import org.bundlemaker.core.ui.event.selection.IDependencySelectionChangedEvent;
import org.bundlemaker.core.ui.event.selection.IDependencySelectionListener;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.bundlemaker.core.ui.event.selection.internal.DependencySelection;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractDependencySelectionAwareViewPart extends AbstractPartLifecycleAwareViewPart implements
    IDependencySelectionListener {

  /** - */
  private List<IDependency> _currentDependencies;

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(IViewSite site) throws PartInitException {
    super.init(site);

    //
    Selection.instance().getDependencySelectionService().addDependencySelectionListener(getSelectionId(), this);
  }

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
    _currentDependencies = event.getSelection().getSelectedDependencies();

    //
    onDependencySelectionChanged(event);
  }

  /**
   * <p>
   * </p>
   * 
   * @return the currentDependency
   */
  public List<IDependency> getCurrentDependencies() {
    return _currentDependencies;
  }

  /**
   * <p>
   * </p>
   * 
   * @param currentDependencies
   *          the currentDependencies to set
   */
  public void setCurrentDependencies(List<IDependency> currentDependencies) {
    _currentDependencies = currentDependencies;
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
   * {@inheritDoc}
   */
  @Override
  public void onPartBroughtToTop() {
    super.onPartBroughtToTop();

    IDependencySelection dependencySelection = Selection.instance().getDependencySelectionService()
        .getSelection(getSelectionId());
    
    if (dependencySelection != null) {
      onDependencySelectionChanged(dependencySelection);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param event
   * 
   */
  protected abstract void onDependencySelectionChanged(IDependencySelection event);
}
