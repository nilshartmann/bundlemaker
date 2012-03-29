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
package org.bundlemaker.core.ui.selection.view;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.ui.selection.IDependencySelection;
import org.bundlemaker.core.ui.selection.IDependencySelectionChangedEvent;
import org.bundlemaker.core.ui.selection.IDependencySelectionListener;
import org.bundlemaker.core.ui.selection.Selection;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractDependencySelectionAwareViewPart extends ViewPart implements IDependencySelectionListener {

  /** - */
  private IDependency _currentDependency;

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(IViewSite site) throws PartInitException {
    super.init(site);
    
    //
    Selection.instance().getDependencySelectionService().addDependencySelectionListener(this);
  }

  @Override
  public void dispose() {

    //
    Selection.instance().getDependencySelectionService().removeDependencySelectionListener(this);

    // call super
    super.dispose();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    pullDependency();
  }
  
  

  private void pullDependency() {
    //
    IDependencySelection dependencySelection = Selection.instance().getDependencySelectionService()
        .getSelection(Selection.MAIN_SELECTION_PROVIDER_ID);

    //
    _currentDependency = dependencySelection != null ? dependencySelection.getFirstDependency() : null;
    
    //
    updateDependencies();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void dependencySelectionChanged(IDependencySelectionChangedEvent event) {

    //
    _currentDependency = event.getSelection().getFirstDependency();

    //
    updateDependencies();
  }

  /**
   * <p>
   * </p>
   * 
   * @return the currentDependency
   */
  public IDependency getCurrentDependency() {
    return _currentDependency;
  }

  /**
   * <p>
   * </p>
   * 
   */
  protected abstract void updateDependencies();
}
