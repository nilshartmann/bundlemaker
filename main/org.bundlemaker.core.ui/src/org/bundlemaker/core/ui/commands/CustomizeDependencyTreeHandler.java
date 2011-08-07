package org.bundlemaker.core.ui.commands;

import org.bundlemaker.analysis.ui.handlers.AbstractBundleMakerHandler;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.ui.view.navigator.ArtifactModelConfigurationDialog;
import org.bundlemaker.core.ui.view.navigator.ArtifactModelConfigurationProvider;
import org.bundlemaker.core.ui.view.navigator.CommonNavigatorUtils;
import org.bundlemaker.core.ui.view.navigator.IArtifactModelConfigurationProvider;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

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

    // open dialog
    Shell shell = HandlerUtil.getActiveShell(event);
    IArtifactModelConfigurationProvider artifactModelConfigurationProvider = ArtifactModelConfigurationProvider
        .instance();
    ArtifactModelConfiguration currentArtifactModelConfiguration = artifactModelConfigurationProvider
        .getCurrentArtifactModelConfiguration();
    ArtifactModelConfigurationDialog dialog = new ArtifactModelConfigurationDialog(shell,
        currentArtifactModelConfiguration);
    if (dialog.open() != Dialog.OK) {
      return;
    }

    System.out.println("customize tree....");

    // update navigator
    CommonNavigatorUtils.update("org.eclipse.ui.navigator.ProjectExplorer");
  }
}
