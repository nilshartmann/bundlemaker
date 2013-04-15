package org.bundlemaker.core.ui.editor.dsm.widget.internal.matrix;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void singleClick(MatrixEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doubleClick(MatrixEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toolTip(MatrixEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void marked(MatrixEvent event) {
    }
  }
}
