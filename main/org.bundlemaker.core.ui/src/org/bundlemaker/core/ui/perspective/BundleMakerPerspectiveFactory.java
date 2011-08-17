package org.bundlemaker.core.ui.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class BundleMakerPerspectiveFactory implements IPerspectiveFactory {

  @Override
  public void createInitialLayout(IPageLayout layout) {

    // add wizard shortcut
    layout.addNewWizardShortcut("org.bundlemaker.core.ui.wizards.newwizard");

    //
    String editorArea = layout.getEditorArea();

    layout.addView(IPageLayout.ID_PROJECT_EXPLORER, IPageLayout.LEFT, 0.25f, editorArea);

    IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.66f, editorArea);
    bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
    bottom.addView("org.bundlemaker.analysis.ui.view.table.DependencyTreeTableView");
    bottom.addView("org.bundlemaker.analysis.ui.dependencyview.DependencyView");
  }
}
