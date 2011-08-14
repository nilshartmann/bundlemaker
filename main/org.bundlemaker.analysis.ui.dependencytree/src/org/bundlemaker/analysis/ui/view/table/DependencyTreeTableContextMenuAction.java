package org.bundlemaker.analysis.ui.view.table;

import org.bundlemaker.analysis.ui.IAnalysisContext;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Actions that can be executed from the {@link DependencyTreeTableView}
 * 
 * @author Nils Hartmann
 * 
 */
public abstract class DependencyTreeTableContextMenuAction {

  /**
   * The text of this action
   */
  private String _text;

  protected DependencyTreeTableContextMenuAction(String text) {
    _text = text;
  }

  public String getText() {
    return _text;
  }

  /**
   * Returns the unique Id for this action.
   * 
   * 
   * @return the Action-Id. Must not be null.
   */
  public abstract String getId();

  /**
   * Will be invoked when the action is executed.
   * 
   * @param the
   *          current {@link IAnalysisContext}. Never null.
   * @param selectedItems
   *          The selection from the {@link DependencyTreeTableView}. Never null.
   * @throws Exception
   */
  public abstract void execute(IAnalysisContext analysisContext, TreeItem[] selectedItems) throws Exception;

}
