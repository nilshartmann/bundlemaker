package org.bundlemaker.analysis.ui.dsmview;

import java.util.Observable;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractDsmViewModel extends Observable {

  private String[]              _labels;

  private String[][]            _values;

  private IDsmViewConfiguration _configuration;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final int getItemCount() {
    return getLabels().length;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final String[] getLabels() {

    // create
    if (_labels == null) {
      _labels = createLabels();
    }

    // return values
    return _labels;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final String[][] getValues() {

    // create
    if (_values == null) {
      _values = createValues();
    }

    // return values
    return _values;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IDsmViewConfiguration getConfiguration() {

    // create
    if (_configuration == null) {
      _configuration = createConfiguration();
    }

    // return configuration
    return _configuration;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected abstract IDsmViewConfiguration createConfiguration();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected abstract String[][] createValues();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected abstract String[] createLabels();

  /**
   * <p>
   * </p>
   * 
   * @param x
   * @param y
   * @return
   */
  public abstract String getToolTip(int x, int y);
}
