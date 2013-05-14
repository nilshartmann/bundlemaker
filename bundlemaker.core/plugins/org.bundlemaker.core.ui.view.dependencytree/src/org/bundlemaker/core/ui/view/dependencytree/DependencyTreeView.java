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
package org.bundlemaker.core.ui.view.dependencytree;

import org.bundlemaker.core.selection.IDependencySelection;
import org.bundlemaker.core.ui.event.selection.workbench.view.AbstractDependencySelectionAwareViewPart;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependencyTreeView extends AbstractDependencySelectionAwareViewPart {

  /** - */
  public static final String              ID = DependencyTreeView.class.getName();

  /** - */
  private CropableDependencyTreeComposite _composite;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createPartControl(Composite parent) {

    //
    _composite = new CropableDependencyTreeComposite(parent, ID, false, true);

    //
    initDependencies(getCurrentDependencySelection());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocus() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onSetDependencySelection(IDependencySelection selection) {

    if (selection.getProviderId().equals(ID)) {
      return;
    }

    // init dependencies
    initDependencies(selection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void analysisModelModified() {
    
    //
    initDependencies(getCurrentDependencySelection());
  }

  /**
   * <p>
   * </p>
   * 
   * @param selection
   */
  private void initDependencies(IDependencySelection selection) {

    //
    if (_composite != null && selection != null) {
      _composite.setDependencies(selection.getSelectedDependencies());
    }
  }

}
