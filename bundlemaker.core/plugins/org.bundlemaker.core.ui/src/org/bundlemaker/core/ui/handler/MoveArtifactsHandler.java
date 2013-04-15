package org.bundlemaker.core.ui.handler;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.operations.MoveArtifactsOperation;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * <p>
 * Handler that implements the "Move..." command in an artifact tree.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MoveArtifactsHandler extends AbstractArtifactBasedHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {

    // retrieve shell from event
    Shell shell = HandlerUtil.getActiveShell(event);

    MoveArtifactsOperation operation = new MoveArtifactsOperation(shell, selectedArtifacts);
    operation.run();
  }
}