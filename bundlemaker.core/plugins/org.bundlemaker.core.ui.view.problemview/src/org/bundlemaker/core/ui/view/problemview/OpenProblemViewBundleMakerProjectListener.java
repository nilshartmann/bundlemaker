package org.bundlemaker.core.ui.view.problemview;

import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.ui.event.IBundleMakerProjectOpenedEvent;
import org.bundlemaker.core.ui.event.IBundleMakerProjectOpenedEventListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * A {@link IBundleMakerProjectOpenedEventListener} that opens the BundleMaker Problem View, if the opened project has
 * {@link IProblem problems}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class OpenProblemViewBundleMakerProjectListener implements IBundleMakerProjectOpenedEventListener {

  @Override
  public void bundleMakerProjectOpened(IBundleMakerProjectOpenedEvent event) {

    if (event.getBundleMakerProject().getProblems().isEmpty()) {
      // no Problems => no need to activate problem view
      return;
    }

    IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    if (workbenchWindow == null) {
      return;
    }

    IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
    if (workbenchPage == null) {
      return;
    }

    try {
      workbenchPage.showView(ProblemView.PROBLEM_VIEW_ID);
    } catch (PartInitException e) {
      e.printStackTrace();
    }
  }

}