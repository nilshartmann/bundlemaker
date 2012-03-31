package org.bundlemaker.core.ui.selection.workbench;

public interface ILifecycleAwarePart {

  /**
   * <p>
   * </p>
   * 
   */
  void onPartActivated();

  /**
   * <p>
   * </p>
   * 
   */
  void onPartBroughtToTop();

  /**
   * <p>
   * </p>
   * 
   */
  void onPartClosed();

  /**
   * <p>
   * </p>
   * 
   */
  void onPartDeactivated();

  /**
   * <p>
   * </p>
   * 
   */
  void onPartOpened();
}
