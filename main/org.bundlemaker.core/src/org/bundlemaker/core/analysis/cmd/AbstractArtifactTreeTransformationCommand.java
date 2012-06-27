package org.bundlemaker.core.analysis.cmd;

import org.bundlemaker.core.analysis.IRootArtifact;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractArtifactTreeTransformationCommand implements IArtifactTreeTransformationCommand {

  /** is executed */
  private boolean       _isExecuted;

  /** root artifact */
  private IRootArtifact _rootArtifact;

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRootArtifact(IRootArtifact rootArtifact) {
    Assert.isNotNull(rootArtifact);
    Assert.isTrue(!_isExecuted, String.format("Command '' already has been executed.", getClass().getName()));

    _rootArtifact = rootArtifact;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute() {

    _isExecuted = true;

    try {
      onExecute();
    } catch (Exception e) {
      // TODO
      e.printStackTrace();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isExecuted() {
    return _isExecuted;
  }

  @Override
  public void undo() {

    try {
      onUndo();
    } catch (Exception e) {
      // TODO
      e.printStackTrace();
    }

    _isExecuted = false;
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  protected abstract void onUndo() throws Exception;

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  protected abstract void onExecute() throws Exception;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected IRootArtifact getRootArtifact() {
    return _rootArtifact;
  }

  @Override
  public boolean isUndoable() {
    return true;
  }
}
