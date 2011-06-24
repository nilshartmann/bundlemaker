package org.bundlemaker.core.ui.commands;

import java.util.List;

import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class DemoHandler extends AbstractArtifactBasedHandler implements IHandler {

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.commands.AbstractBundleMakerHandler#execute(org.eclipse.core.commands.ExecutionEvent
   * , java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IArtifact> selectedArtifacts) throws Exception {
    MessageDialog.openInformation(new Shell(), "Artifact selected", "Selected artifacts: " + selectedArtifacts);
  }

}
