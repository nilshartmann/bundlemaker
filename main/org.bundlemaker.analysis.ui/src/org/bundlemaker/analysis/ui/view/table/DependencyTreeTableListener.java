package org.bundlemaker.analysis.ui.view.table;

/**
 * A listener that can be registered by clients to react on events in the {@link DependencyTreeTableView}
 * 
 * @author Nils Hartmann
 * 
 */
public class DependencyTreeTableListener {

  /**
   * Will be invoked <b>before</b> the content of the DependencyTreeTable will change
   * 
   * @param e
   */
  public void onContentChange(DependencyTreeTableContentChangeEvent e) {
    // no-op
  }

  /**
   * Will be invoked <b>before</b> the specified action will be executed
   * 
   * @param actionId
   *          the actionId of the Action that will be executed
   */
  public void beforeActionExecution(String actionId) {
    // no-op
  }

}
