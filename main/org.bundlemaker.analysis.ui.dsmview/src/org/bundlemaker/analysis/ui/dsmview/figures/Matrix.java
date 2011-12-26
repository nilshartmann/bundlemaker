package org.bundlemaker.analysis.ui.dsmview.figures;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.ui.dsmview.AbstractDsmViewModel;
import org.bundlemaker.analysis.ui.dsmview.DsmViewModel;
import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.widgets.Display;

/**
 * <p>
 * The {@link Figure} that implements the matrix.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Matrix extends Figure {

  /** the model */
  protected AbstractDsmViewModel _model;

  /** - */
  int                            _x          = -1;

  /** - */
  int                            _y          = -1;

  /** - */
  int                            _selected_x = -1;

  /** - */
  int                            _selected_y = -1;

  /** - */
  private List<IMatrixListener>  _matrixListeners;

  /**
   * <p>
   * Creates a new instance of type {@link Matrix}.
   * </p>
   * 
   * @param model
   */
  public Matrix(AbstractDsmViewModel model) {

    //
    Assert.isNotNull(model);

    // set the model
    _model = model;

    //
    _matrixListeners = new LinkedList<IMatrixListener>();

    //
    addMouseListener(new MatrixMouseListener());

    //
    addMouseMotionListener(new MatrixMouseMotionListener());
  }

  /**
   * <p>
   * </p>
   * 
   * @param listener
   */
  public void addMatrixListener(IMatrixListener listener) {
    if (!_matrixListeners.contains(listener)) {
      _matrixListeners.add(listener);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param listener
   */
  public void removeMatrixLIstener(IMatrixListener listener) {
    _matrixListeners.remove(listener);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public AbstractDsmViewModel getModel() {
    return _model;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void paintClientArea(Graphics graphics) {
    super.paintClientArea(graphics);

    // reset the size
    resetSize();

    // push the current state
    graphics.pushState();

    // draw the background
    graphics.setBackgroundColor(getModel().getConfiguration().getMatrixBackgroundColor());

    graphics.fillRectangle(0, 0, getSize().width, getSize().height);

    // draw the diagonal
    graphics.setBackgroundColor(getModel().getConfiguration().getMatrixDiagonalColor());

    for (int i = 0; i < _model.getItemCount(); i++) {

      //
      graphics.fillRectangle(getHorizontalSliceSize(i), getVerticalSliceSize(i), getHorizontalSliceSize(i + 1)
          - getHorizontalSliceSize(i) + 1, getVerticalSliceSize(i + 1) - getVerticalSliceSize(i) + 1);
    }

    // draw marked rows and columns
    if (_x != -1 && _y != -1) {

      // draw column
      graphics.setBackgroundColor(_model.getConfiguration().getMatrixMarkedColumnRowColor());
      graphics.fillRectangle(0, getVerticalSliceSize(_y), getHorizontalSliceSize(_x + 1) + 1,
          getVerticalSliceSize(_y + 1) - getVerticalSliceSize(_y) + 1);

      // draw row
      graphics.setBackgroundColor(_model.getConfiguration().getMatrixMarkedColumnRowColor());
      graphics.fillRectangle(getHorizontalSliceSize(_x), 0, getHorizontalSliceSize(_x + 1) - getHorizontalSliceSize(_x)
          + 1, getVerticalSliceSize(_y + 1) + 1);

      // draw marked cell
      graphics.setBackgroundColor(_model.getConfiguration().getMatrixMarkedCellColor());
      graphics.fillRectangle(getHorizontalSliceSize(_x), getVerticalSliceSize(_y), getHorizontalSliceSize(_x + 1)
          - getHorizontalSliceSize(_x) + 1, getVerticalSliceSize(_y + 1) - getVerticalSliceSize(_y) + 1);
    }

    // draw the text
    graphics.setForegroundColor(getModel().getConfiguration().getMatrixTextColor());

    for (int i = 0; i < _model.getItemCount(); i++) {
      for (int j = 0; j < _model.getItemCount(); j++) {

        //
        if (i != j) {

          String value = _model.getValues()[i][j];

          if (value != null) {
            //
            graphics.drawString(value, getHorizontalSliceSize(i) + 4, getVerticalSliceSize(j));
          }
        }
      }
    }

    // draw the separator lines
    graphics.setForegroundColor(_model.getConfiguration().getMatrixSeparatorColor());

    for (int i = 0; i <= _model.getItemCount(); i++) {
      graphics.drawLine(new Point(0, getVerticalSliceSize(i)), new Point(_model.getConfiguration()
          .getHorizontalBoxSize() * _model.getItemCount(), getVerticalSliceSize(i)));
    }

    for (int i = 0; i <= _model.getItemCount(); i++) {
      graphics.drawLine(new Point(getHorizontalSliceSize(i), 0), new Point(getHorizontalSliceSize(i), _model
          .getConfiguration().getVerticalBoxSize() * _model.getItemCount()));
    }

    // restore state
    graphics.popState();
  }

  /**
   * <p>
   * </p>
   */
  public void resetSize() {

    //
    Dimension dimension = new Dimension(_model.getConfiguration().getHorizontalBoxSize() * _model.getItemCount() + 1,
        _model.getConfiguration().getVerticalBoxSize() * _model.getItemCount() + 1);

    //
    if (!getSize().equals(dimension)) {
      setSize(dimension);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param count
   * @return
   */
  private final int getHorizontalSliceSize(int count) {
    return (_model.getConfiguration().getHorizontalBoxSize() * count);
  }

  /**
   * <p>
   * </p>
   * 
   * @param count
   * @return
   */
  private final int getVerticalSliceSize(int count) {
    return (_model.getConfiguration().getVerticalBoxSize() * count);
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class MatrixMouseMotionListener extends MouseMotionListener.Stub {

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseMoved(MouseEvent me) {

      //
      Point location = me.getLocation();

      //
      final int x = (location.x / _model.getConfiguration().getHorizontalBoxSize());
      final int y = (location.y / _model.getConfiguration().getVerticalBoxSize());

      //
      if (x != _x || y != _y) {

        //
        if (x >= _model.getItemCount() || y >= _model.getItemCount()) {

          _x = -1;
          _y = -1;
        }

        //
        else {

          _x = x;
          _y = y;
        }

        // start the tool tip listener
        Display.getCurrent().timerExec(500, new Runnable() {
          @Override
          public void run() {
            // set the new cell
            if (_x == x && _y == y) {

              // notify listener
              MatrixEvent event = new MatrixEvent(_x, _y);
              for (IMatrixListener listener : _matrixListeners.toArray(new IMatrixListener[0])) {
                listener.toolTip(event);
              }
            }
          }
        });

        // repaint
        repaint();

        // notify listener
        MatrixEvent event = new MatrixEvent(_x, _y);
        for (IMatrixListener listener : _matrixListeners.toArray(new IMatrixListener[0])) {
          listener.marked(event);
        }
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseExited(MouseEvent me) {

      //
      _x = -1;
      _y = -1;

      // repaint
      repaint();

      // notify listener
      MatrixEvent event = new MatrixEvent(_x, _y);
      for (IMatrixListener listener : _matrixListeners.toArray(new IMatrixListener[0])) {
        listener.marked(event);
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class MatrixMouseListener extends MouseListener.Stub {

    //
    private Object lock        = new Object();

    //
    int            _clickCount = 0;

    @Override
    public synchronized void mouseReleased(MouseEvent me) {

      synchronized (lock) {
        if (_clickCount == 0) {

          //
          Point location = me.getLocation();
          final int x = (location.x / _model.getConfiguration().getHorizontalBoxSize());
          final int y = (location.y / _model.getConfiguration().getVerticalBoxSize());

          //
          _selected_x = x;
          _selected_y = y;

          //
          Display.getCurrent().timerExec(250, new Runnable() {
            @Override
            public void run() {
              collectResult();
            }
          });
        }

        _clickCount++;
      }
    }

    private void collectResult() {
      synchronized (lock) {

        if (_clickCount == 1) {

          // notify listener
          MatrixEvent event = new MatrixEvent(_selected_x, _selected_y);
          for (IMatrixListener listener : _matrixListeners.toArray(new IMatrixListener[0])) {
            listener.singleClick(event);
          }

        } else {

          // notify listener
          MatrixEvent event = new MatrixEvent(_selected_x, _selected_y);
          for (IMatrixListener listener : _matrixListeners.toArray(new IMatrixListener[0])) {
            listener.doubleClick(event);
          }
        }

        //
        _clickCount = 0;
      }
    }
  }

  public void setModel(DsmViewModel model) {
    _model = model;
  }
}