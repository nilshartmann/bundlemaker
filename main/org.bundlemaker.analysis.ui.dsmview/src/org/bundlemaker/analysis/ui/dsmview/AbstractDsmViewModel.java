package org.bundlemaker.analysis.ui.dsmview;

import java.util.Observable;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractDsmViewModel extends Observable implements IDsmViewModel {

  private String[]              _labels;

  private String[][]            _values;

  private IDsmViewConfiguration _configuration;

  private String[]              _shortendLabels;

  private boolean               _useShortendLabels;

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
   * @return the 'shortend' versions of a label (i.e. o.e.jdt instead of org.eclipse.jdt)
   */
  public final String[] getShortendLabels() {
    if (_shortendLabels == null) {
      String[] labels = createLabels();
      String[] shortendLabels = new String[labels.length];
      for (int i = 0; i < labels.length; i++) {
        shortendLabels[i] = getShortendLabel(labels[i]);
      }

      _shortendLabels = shortendLabels;
    }

    return _shortendLabels;
  }

  public void setUseShortendLabels(boolean useShortendLabels) {
    if (useShortendLabels != _useShortendLabels) {
      _useShortendLabels = useShortendLabels;
      notifyObservers();
    }
  }

  public boolean isUseShortendLabels() {
    return _useShortendLabels;
  }

  /**
   * Returns the shortend version of a label
   * 
   * @param label
   * @return
   */
  protected String getShortendLabel(String label) {
    if (label == null || label.trim().isEmpty()) {
      return "";
    }

    // TODO this is a very quick'n'dirty way to detect a
    // module name that should not be shortended (i.e. jre_1.3.6)
    if (label.indexOf("_") != -1) {
      return label;
    }

    boolean lastPart = true;

    String shortendLabel = "";
    label = "." + label;
    char previousChar = label.charAt(label.length() - 1);
    for (int i = label.length() - 1; i >= 0; i--) {
      char c = label.charAt(i);
      if (c == '.' || c == '/') {
        if (!lastPart) {
          shortendLabel = previousChar + shortendLabel;
        }
        if (i > 0) {
          shortendLabel = c + shortendLabel;
        }
        lastPart = false;
      } else {
        if (lastPart) {
          shortendLabel = c + shortendLabel;
        }

      }

      previousChar = c;
    }
    return shortendLabel;
    // if (label.indexOf(".") > 0) {
    // separator = "\\.";
    // } else {
    // separator = "/";
    // }

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
