package org.bundlemaker.core.ui.commands;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.artifact.configuration.ArtifactModelConfigurationDialog;
import org.bundlemaker.core.ui.artifact.configuration.ArtifactModelConfigurationProvider;
import org.bundlemaker.core.ui.artifact.configuration.IArtifactModelConfigurationProvider;
import org.bundlemaker.core.ui.handler.AbstractBundleMakerHandler;
import org.bundlemaker.core.ui.internal.Activator;
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
    IArtifactModelConfigurationProvider artifactModelConfigurationProvider = Activator.getDefault()
        .getArtifactModelConfigurationProvider();
    ArtifactModelConfiguration currentArtifactModelConfiguration = artifactModelConfigurationProvider
        .getArtifactModelConfiguration();
    ArtifactModelConfigurationDialog dialog = new ArtifactModelConfigurationDialog(shell,
        currentArtifactModelConfiguration);
    if (dialog.open() != Dialog.OK) {
      return;
    }

    // Persist new settings
    if (artifactModelConfigurationProvider instanceof ArtifactModelConfigurationProvider) {
      ((ArtifactModelConfigurationProvider) artifactModelConfigurationProvider).store();
    } else {
      // "Should not happen"...
      System.err.printf(
          "ArtifactModelConfigurationProvider '%s' is not instanceof IArtifactModelConfigurationProvider?!%n",
          artifactModelConfigurationProvider);
    }

    // update navigator
    CommonNavigatorUtils.update("org.eclipse.ui.navigator.ProjectExplorer");
  }
}
