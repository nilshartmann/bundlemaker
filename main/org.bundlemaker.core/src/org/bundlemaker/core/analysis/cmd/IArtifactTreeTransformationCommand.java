package org.bundlemaker.core.analysis.cmd;

import org.bundlemaker.core.analysis.IRootArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IArtifactTreeTransformationCommand {

  /**
   * <p>
   * </p>
   */
  void setRootArtifact(IRootArtifact rootArtifact);

  /**
   * <p>
   * </p>
   */
  void execute();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isReadyToExecute();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isExecuted();

  /**
   * <p>
   * </p>
   */
  boolean isUndoable();

  /**
   * <p>
   * </p>
   */
  void undo();
}
