package org.bundlemaker.analysis.ui.dsmview.figures;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IMatrixListener {

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  public void singleClick(MatrixEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  public void doubleClick(MatrixEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  public void toolTip(MatrixEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  public void marked(MatrixEvent event);

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static class Adapter implements IMatrixListener {

    @Override
    public void singleClick(MatrixEvent event) {
    }

    @Override
    public void doubleClick(MatrixEvent event) {
    }

    @Override
    public void toolTip(MatrixEvent event) {
    }

    @Override
    public void marked(MatrixEvent event) {
    }
  }
}
