package org.bundlemaker.analysis.ui.view.table.actions;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.ui.IAnalysisContext;
import org.bundlemaker.analysis.ui.view.table.DependencyTreeTableContextMenuAction;
import org.eclipse.swt.widgets.TreeItem;

public class IgnoreDependencyContextMenuAction extends DependencyTreeTableContextMenuAction {

  public static final String ACTION_ID = "org.bundlemaker.analysis.ui.view.table.actions.IgnoreDependencyContextMenuAction";

  public IgnoreDependencyContextMenuAction() {
    super("Ignore");
  }

  @Override
  public String getId() {
    return ACTION_ID;
  }

  @Override
  public void execute(IAnalysisContext analysisContext, TreeItem[] selectedItems) throws Exception {
    System.out.println("Ignore selected");

    for (TreeItem item : selectedItems) {

      IDependency dependency = (IDependency) item.getData();

      if (dependency != null) {
        dependency.setTaggedIgnore(true);
      }
    }
    analysisContext.getDependencyGraph().setInvalid(true);
  }

}
