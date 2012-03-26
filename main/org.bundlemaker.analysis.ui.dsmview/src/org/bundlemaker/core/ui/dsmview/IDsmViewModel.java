package org.bundlemaker.core.ui.dsmview;

import java.util.Observer;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IDsmViewModel {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IDsmViewConfiguration getConfiguration();

  int getItemCount();

  boolean isUseShortendLabels();

  String[] getShortendLabels();

  String[] getLabels();

  String[][] getValues();

  void addObserver(Observer observer);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  int[][] getCycles();

  boolean isInCycle(int i);

  boolean isInCycle(int i, int j);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isToggled();
}
