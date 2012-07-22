package org.bundlemaker.core.transformation;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IUndoableTransformation extends ITransformation {

  /**
   * <p>
   * </p>
   */
  void undo();
}
