package org.bundlemaker.core.ui.artifact;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.eclipse.ui.IPageLayout;
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

  /** the id of the Eclipse project explorer */
  public static final String PROJECT_EXPLORER_VIEW_ID = IPageLayout.ID_PROJECT_EXPLORER;

  /**
   * <p>
   * </p>
   * 
   * @param identifier
   */
  public static void update(String identifier) {
    CommonNavigator commonNavigator = findCommonNavigator(identifier);
    if (commonNavigator != null) {
      commonNavigator.getNavigatorContentService().update();
    }
  }

  /**
   * <p>
   * </p>
   */
  public static void refresh(String identifier, IBundleMakerArtifact... artifacts) {

    //
    CommonNavigator commonNavigator = findCommonNavigator(identifier);

    if (commonNavigator == null) {
      return;
    }

    //
    for (IBundleMakerArtifact iArtifact : artifacts) {
      commonNavigator.getCommonViewer().refresh(iArtifact);
    }
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

  public static void activateCommonNavigator(String navigatorViewId) {
    CommonNavigator navigator = findCommonNavigator(navigatorViewId);
    if (navigator == null) {
      return;
    }
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    page.activate(navigator);
  }
}
