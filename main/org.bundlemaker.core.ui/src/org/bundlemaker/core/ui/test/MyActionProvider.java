package org.bundlemaker.core.ui.test;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public class MyActionProvider extends CommonActionProvider {

  private MyAction doubleClickAction;

  @Override
  public void init(ICommonActionExtensionSite aSite) {
    super.init(aSite);

    doubleClickAction = new MyAction();

    // only if doubleClickAction must know tree selection:
    // aSite.getStructuredViewer().addSelectionChangedListener(doubleClickAction);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclips e.ui.IActionBars)
   */
  @Override
  public void fillActionBars(IActionBars actionBars) {
    super.fillActionBars(actionBars);
    // forward doubleClick to doubleClickAction
    actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, doubleClickAction);
  }
}