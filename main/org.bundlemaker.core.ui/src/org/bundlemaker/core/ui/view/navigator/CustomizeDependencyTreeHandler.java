package org.bundlemaker.core.ui.view.navigator;

import org.bundlemaker.analysis.ui.handlers.AbstractBundleMakerHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class CustomizeDependencyTreeHandler extends AbstractBundleMakerHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void execute(ExecutionEvent event, ISelection selection) throws Exception {

    // TODO: open dialog
    //

    CommonNavigator commonNavigator = findCommonNavigator("org.eclipse.ui.navigator.ProjectExplorer");
    commonNavigator.getNavigatorContentService().update();
  }

  /**
   * <p>
   * </p>
   * 
   * @param navigatorViewId
   * @return
   */
  public static CommonNavigator findCommonNavigator(String navigatorViewId) {
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    if (page != null) {
      IViewPart view = page.findView(navigatorViewId);
      if (view != null && view instanceof CommonNavigator)
        return ((CommonNavigator) view);
    }
    return null;
  }
}
