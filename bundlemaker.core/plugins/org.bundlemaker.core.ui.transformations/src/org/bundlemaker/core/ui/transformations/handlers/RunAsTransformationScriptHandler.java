package org.bundlemaker.core.ui.transformations.handlers;

import java.util.Collection;

import org.bundlemaker.core.project.BundleMakerCore;
import org.bundlemaker.core.project.BundleMakerProjectState;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModuleAwareBundleMakerProject;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.bundlemaker.core.ui.handler.AbstractBundleMakerHandler;
import org.bundlemaker.core.ui.transformations.runner.UiTransformationScriptRunner;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public class RunAsTransformationScriptHandler extends AbstractBundleMakerHandler {

  @Override
  protected void execute(ExecutionEvent event, ISelection selection) throws Exception {

    IType transformationScriptType = TransformationScriptHandlerUtil.getTransformationScript(event, selection);

    if (transformationScriptType == null) {
      return;
    }

    IProject project = transformationScriptType.getResource().getProject();

    IModuleAwareBundleMakerProject bundleMakerProject = BundleMakerCore.getBundleMakerProject(project).adaptAs(
        IModuleAwareBundleMakerProject.class);

    if (bundleMakerProject.getState() != BundleMakerProjectState.READY) {
      showError(event, "Project " + bundleMakerProject.getName() + " is not parsed. Please open and parse it first");
      return;
    }

    Collection<IModularizedSystem> modularizedSystemWorkingCopies = bundleMakerProject
        .getModularizedSystemWorkingCopies();

    final Shell shell = HandlerUtil.getActiveShell(event);

    IModularizedSystem modularizedSystem = null;

    modularizedSystem = modularizedSystemWorkingCopies.iterator().next();
    if (modularizedSystemWorkingCopies.size() > 1) {
      // User has to selected working copy
      ListDialog listDialog = new ListDialog(shell);
      listDialog.setContentProvider(ArrayContentProvider.getInstance());
      listDialog.setLabelProvider(new ArtifactTreeLabelProvider());
      listDialog.setInput(modularizedSystemWorkingCopies);
      listDialog.setInitialSelections(new IModularizedSystem[] { modularizedSystem });

      listDialog.setTitle("Select Modularized System");

      if (listDialog.open() != Window.OK) {
        return;
      }

      modularizedSystem = (IModularizedSystem) listDialog.getResult()[0];
    }

    // Run the script

    ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(shell);
    progressMonitorDialog.run(true, true, new UiTransformationScriptRunner(shell, modularizedSystem,
        transformationScriptType));

    // refresh the explorer
    CommonNavigatorUtils.refreshProject(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID, bundleMakerProject);

    // List<Object> selectedObject = getSelectedObject(selection, Object.class);
    // for (Object object : selectedObject) {
    // System.out.println("Selected object:  " + object);
    // }

  }

  protected void showError(ExecutionEvent event, String text) {
    MessageDialog.openError(HandlerUtil.getActiveShell(event), "Run as Transformation Script", text);
  }

}
