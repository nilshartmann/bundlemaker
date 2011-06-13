package org.bundlemaker.core.analysis.ui.commands;

import java.util.List;

import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class DemoHandler extends AbstractBundleMakerHandler implements IHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.ui.commands.AbstractBundleMakerCommand#execute(java.util.List)
   */
  @Override
  protected void execute(List<IArtifact> selectedArtifacts) {
    MessageDialog.openInformation(new Shell(), "Artifact selected", "Selected artifacts: " + selectedArtifacts);
  }

}
