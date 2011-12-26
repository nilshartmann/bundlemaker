package org.bundlemaker.analysis.ui.dsmview.figures;

import org.bundlemaker.analysis.ui.dsmview.AbstractDsmViewModel;
import org.bundlemaker.analysis.ui.dsmview.DsmViewModel;
import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.swt.graphics.FontMetrics;

/**
 * <p>
 * Abstract base class for all side marker implementations.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractSideMarker extends Figure implements ISideMarker {

  /** the model */
  private AbstractDsmViewModel _model;

  /** the marked item */
  private int                  _markedItem = -1;

  /** - */
  private FontMetrics          _fontMetrics;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractSideMarker}.
   * </p>
   * 
   * @param model
   *          the model
   */
  public AbstractSideMarker(AbstractDsmViewModel model) {

    //
    Assert.isNotNull(model);

    //
    _model = model;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void mark(int i) {

    int oldValue = _markedItem;
    _markedItem = i;

    onMark(oldValue, _markedItem);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected AbstractDsmViewModel getModel() {
    return _model;
  }

  /**
   * <p>
   * </p>
   * 
   * @param oldValue
   * @param x
   */
  protected abstract void onMark(int oldValue, int x);

  /**
   * <p>
   * </p>
   * 
   */
  protected abstract void resetSize();

  /**
   * <p>
   * </p>
   * 
   * @param count
   * @return
   */
  protected final int getHorizontalSliceSize(int count) {
    return (getModel().getConfiguration().getHorizontalBoxSize() * count);
  }

  /**
   * <p>
   * </p>
   * 
   * @param count
   * @return
   */
  protected final int getVerticalSliceSize(int count) {
    return (getModel().getConfiguration().getVerticalBoxSize() * count);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final int getMarkedItem() {
    return _markedItem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public int getFontHeight() {
    if (_fontMetrics == null) {
      _fontMetrics = FigureUtilities.getFontMetrics(getFont());
    }
    return _fontMetrics.getHeight();
  }

  public void setModel(DsmViewModel model) {
    _model = model;
  }
}