package org.bundlemaker.core.ui.app.perspective;

import org.bundlemaker.core.ui.view.dependencytable.DependencyTableView;
import org.bundlemaker.core.ui.view.dependencytree.DependencyTreeView;
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
    layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.LEFT, 0.25f, editorArea);

    IFolderLayout middle = layout.createFolder("middle", IPageLayout.BOTTOM, 0.5f, editorArea);
    middle.addView(DependencyTreeView.ID);

    IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.5f, "middle");
    bottom.addView(DependencyTableView.ID);
    bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
  }
}
