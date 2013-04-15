package org.bundlemaker.core.ui.editor.dsm.widget.internal.util;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BoxSize {

  private int _horizontalBoxSize = 20;

  public int getVerticalBoxSize() {
    return 20;
  }

  public int getHorizontalBoxSize() {
    return _horizontalBoxSize;
  }

  public void setHorizontalBoxSize(int size) {
    _horizontalBoxSize = size > getVerticalBoxSize() ? size : getVerticalBoxSize();
  }
}
