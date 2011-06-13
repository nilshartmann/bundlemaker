package org.bundlemaker.core.analysis.ui.commands;

import java.util.List;

import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.eclipse.core.commands.IHandler;

public class ExportHandler extends AbstractBundleMakerHandler implements IHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.ui.commands.AbstractBundleMakerCommand#execute(java.util.List)
   */
  @Override
  protected void execute(List<IArtifact> selectedArtifacts) {

    System.out.println("selected: " + selectedArtifacts);
  }

}
