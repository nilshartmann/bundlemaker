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
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependencyTreeView extends AbstractDependencySelectionAwareViewPart {

  /** - */
  public static final String              ID = DependencyTreeView.class.getName();

  /** Id used to store view settings in preferences */
  private static final String        VIEW_SETTINGS_SECTION = "DependencyTreeView";

  
  /** - */
  private CropableDependencyTreeComposite _composite;
  
  /**
   * Turn on/off the display of reference count on each node. Warning: might be expensive.
   */
  private boolean _showReferenceCount = false;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createPartControl(Composite parent) {

    //
    _composite = new CropableDependencyTreeComposite(parent, ID, isShowReferenceCount(), true);
    
    // Restore User-made settings
    loadViewSettings();
    
    // Setup Actions and Menu in Action Bar
    contributeToActionBars();

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
  
  public boolean isShowReferenceCount() {
    return _showReferenceCount;
  }
  
  public void setShowReferenceCount(boolean showReferenceCount) {
    _showReferenceCount = showReferenceCount;
      _composite.setShowReferenceCount(this._showReferenceCount);
      saveViewSettings();
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
  
  private void contributeToActionBars() {
    IActionBars bars = getViewSite().getActionBars();
    
    // Pull Down Menu
    fillLocalPullDown(bars.getMenuManager());
  }
  
  private void fillLocalPullDown(IMenuManager menuManager) {
    menuManager.add(new ShowReferenceCountAction());
  }
  
  private IDialogSettings getViewSettings() {
    IDialogSettings settings = Activator.getDefault().getDialogSettings();
    IDialogSettings section = settings.getSection(VIEW_SETTINGS_SECTION);
    if (section == null) {
      section = settings.addNewSection(VIEW_SETTINGS_SECTION);
    }
    return section;
  }
  
  private void saveViewSettings() {
    IDialogSettings dialogSettings = getViewSettings();

    dialogSettings.put("showReferenceCount", isShowReferenceCount());
  }
  
  private void loadViewSettings() {
    IDialogSettings dialogSettings = getViewSettings();

    this._showReferenceCount = dialogSettings.getBoolean("showReferenceCount");
    
    _composite.setShowReferenceCount(_showReferenceCount);

  }


  class ShowReferenceCountAction extends Action {

    public ShowReferenceCountAction() {
      super("Show Reference Count", IAction.AS_CHECK_BOX);
      setToolTipText("Show Reference Count on each Node. Warning: Might slow down the Tree.");
      setChecked(isShowReferenceCount());
    }
    
    @Override
    public void run() {
      setShowReferenceCount(isChecked());
    }
  }  
  

}
