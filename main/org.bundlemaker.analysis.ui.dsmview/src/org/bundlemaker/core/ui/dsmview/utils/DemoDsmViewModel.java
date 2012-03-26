package org.bundlemaker.core.ui.dsmview.utils;

import org.bundlemaker.core.ui.dsmview.AbstractDsmViewModel;
import org.bundlemaker.core.ui.dsmview.DefaultDsmViewConfiguration;
import org.bundlemaker.core.ui.dsmview.IDsmViewConfiguration;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DemoDsmViewModel extends AbstractDsmViewModel {

  /** - */
  private String[][] _values;

  /** - */
  private String[]   _labels;

  /**
   * <p>
   * Creates a new instance of type {@link DemoDsmViewModel}.
   * </p>
   * 
   * @param count
   */
  public DemoDsmViewModel(int count) {
    _labels = new String[count];
    _values = new String[count][count];

    //
    for (int i = 0; i < _labels.length; i++) {
      _labels[i] = "example-" + i;
    }

    //
    for (int i = 0; i < _values.length; i++) {
      for (int j = 0; j < _values[i].length; j++) {
        _values[i][j] = i + "" + j;
      }
    }
  }

  @Override
  public String getToolTip(int x, int y) {
    return "same";
  }

  @Override
  protected String[][] createValues() {
    return _values;
  }

  @Override
  protected String[] createLabels() {
    return _labels;
  }

  @Override
  protected IDsmViewConfiguration createConfiguration() {
    return new DefaultDsmViewConfiguration();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInCycle(int i) {
    return isInCycle(i, i);
  }

  @Override
  public boolean isInCycle(int i, int j) {
    return (i >= 0 && i < 2 && j >= 0 && j < 2) || (i > 5 && i < 12 && j > 5 && j < 12)
        || (i > 17 && i < 22 && j > 17 && j < 22);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int[][] getCycles() {
    return new int[][] { { 0, 1 }, { 6, 7, 8, 9, 10, 11 }, { 18, 19, 20, 21 } };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isToggled() {
    return true;
  }
}