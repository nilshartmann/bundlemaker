/*******************************************************************************
 * Copyright (c) 2013 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.ui.stage.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactStageActionProvider extends CommonActionProvider {

  private MenuManager _submenuManager;

  private MyAction    _myAction = new MyAction();

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
   */
  @Override
  public void init(ICommonActionExtensionSite aSite) {
    super.init(aSite);

    _submenuManager = new MenuManager("Stage", "org.bundlemaker.core.ui.stage");
    _myAction = new MyAction();

    _submenuManager.add(_myAction);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu(IMenuManager menu) {
    super.fillContextMenu(menu);

    menu.appendToGroup("org.bundlemaker.core.ui.stage.group", _submenuManager);

  }

  class MyAction extends Action {
    public MyAction() {
      super("Select all Packages");
    }

  }

}
