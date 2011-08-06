package org.bundlemaker.core.ui.view.navigator;

import org.bundlemaker.analysis.model.IArtifact;
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
public class CommonNavigatorUtils {

  /**
   * <p>
   * </p>
   * 
   * @param identifier
   */
  public static void update(String identifier) {
    CommonNavigator commonNavigator = findCommonNavigator(identifier);
    commonNavigator.getNavigatorContentService().update();
  }

  /**
   * <p>
   * </p>
   */
  public static void refresh(String identifier, IArtifact artifact) {

    CommonNavigator commonNavigator = findCommonNavigator(identifier);
    commonNavigator.getCommonViewer().refresh(artifact);
  }

  /**
   * <p>
   * </p>
   * 
   * @param navigatorViewId
   * @return
   */
  private static CommonNavigator findCommonNavigator(String navigatorViewId) {
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    if (page != null) {
      IViewPart view = page.findView(navigatorViewId);
      if (view != null && view instanceof CommonNavigator)
        return ((CommonNavigator) view);
    }
    return null;
  }
}
