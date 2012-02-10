package org.bundlemaker.analysis.ui.dsmview.figures;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MatrixEvent {

  /** - */
  private int _x;

  /** - */
  private int _y;

  /**
   * <p>
   * Creates a new instance of type {@link MatrixEvent}.
   * </p>
   * 
   * @param x
   * @param y
   */
  public MatrixEvent(int x, int y) {
    super();
    _x = x;
    _y = y;
  }

  // /**
  // * <p>
  // * Creates a new instance of type {@link MatrixEvent}.
  // * </p>
  // *
  // * @param x
  // * @param y
  // */
  // public MatrixEvent(int x, int y) {
  // _x = x;
  // _y = y;
  // }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public int getX() {
    return _x;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public int getY() {
    return _y;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "MatrixEvent [_x=" + _x + ", _y=" + _y + "]";
  }
}
