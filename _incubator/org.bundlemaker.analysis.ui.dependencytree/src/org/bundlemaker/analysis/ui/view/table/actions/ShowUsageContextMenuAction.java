package org.bundlemaker.analysis.ui.view.table.actions;

import java.util.ArrayList;
import java.util.Collection;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.dependencies.DependencyGraph;
import org.bundlemaker.analysis.model.dependencies.IDependencyGraph;
import org.bundlemaker.analysis.ui.IAnalysisContext;
import org.bundlemaker.analysis.ui.view.table.DependencyTreeTableContextMenuAction;
import org.eclipse.swt.widgets.TreeItem;

/**
 * The "Show From/To Usage" action for the DependencyTreeTableView
 * 
 * @author Nils Hartmann
 * 
 */
public class ShowUsageContextMenuAction extends DependencyTreeTableContextMenuAction {

  public final static String ACTION_ID = "org.bundlemaker.analysis.ui.view.table.actions.ShowUsageContextMenuAction";

  private final boolean      _showFrom;

  /**
   * @param showFrom
   *          determine if 'from' or 'to' usage should be displayed
   */
  public ShowUsageContextMenuAction(boolean showFrom) {
    super(showFrom ? "Show From Usage" : "Show To Usage");
    _showFrom = showFrom;
  }

  @Override
  public String getId() {
    return ACTION_ID;
  }

  @Override
  public void execute(IAnalysisContext analysisContext, TreeItem[] selectedItems) throws Exception {
    IDependency dependency = (IDependency) selectedItems[0].getData();
    IArtifact singleClassArtifact = _showFrom ? dependency.getFrom() : dependency.getTo();
    Collection<IArtifact> artifacts = new ArrayList<IArtifact>();
    IArtifact root = singleClassArtifact.getParent(ArtifactType.Root);
    if ((singleClassArtifact != null) && (root != null)) {
      artifacts.add(singleClassArtifact);
      artifacts.add(root);

      IDependencyGraph newGraph = DependencyGraph.calculateDependencyGraph(artifacts);
      analysisContext.setDependencyGraph(newGraph);
    }
  }

}
