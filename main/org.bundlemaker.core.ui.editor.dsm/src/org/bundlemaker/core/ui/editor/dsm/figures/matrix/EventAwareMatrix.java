package org.bundlemaker.core.ui.editor.dsm.figures.matrix;

import java.util.concurrent.CopyOnWriteArrayList;

import org.bundlemaker.core.ui.editor.dsm.IDsmViewModel;
import org.bundlemaker.core.ui.editor.dsm.figures.IMatrixContentProvider;
import org.bundlemaker.core.ui.editor.dsm.figures.IMatrixCycleDetector;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Display;

/**
 * <p>
 * The {@link Figure} that implements the matrix.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class EventAwareMatrix extends Matrix {

  /** - */
  private CopyOnWriteArrayList<IMatrixListener> _matrixListeners;

  /** - */
  private int                                   _x = -1;

  /** - */
  private int                                   _y = -1;

  /**
   * <p>
   * Creates a new instance of type {@link EventAwareMatrix}.
   * </p>
   * 
   * @param model
   */
  public EventAwareMatrix(IMatrixContentProvider model, ILabelProvider labelProvider,
      IMatrixCycleDetector matrixCycleDetector) {
    super(model, labelProvider, matrixCycleDetector);

    //
    _matrixListeners = new CopyOnWriteArrayList<IMatrixListener>();

    //
    addMouseListener(new MatrixMouseListener());
    addMouseMotionListener(new MatrixMouseMotionListener());
  }

  /**
   * <p>
   * </p>
   * 
   * @param listener
   */
  public void addMatrixListener(IMatrixListener listener) {
    _matrixListeners.addIfAbsent(listener);
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
   * {@inheritDoc}
   */
  /**
   * {@inheritDoc}
   */
  @Override
  protected void onPaintClientArea(Graphics graphics) {

    // draw marked rows and columns
    if (_x != -1 && _y != -1) {

      // draw column
      if (getMatrixCycleDetector().isInCycle(_x, _y)) {
        graphics.setBackgroundColor(getMatrixConfiguration().getCycleMatrixMarkedColumnRowColor());
      } else {
        graphics.setBackgroundColor(getMatrixConfiguration().getMatrixMarkedColumnRowColor());
      }
      graphics.fillRectangle(0, getVerticalSliceSize(_y), getHorizontalSliceSize(_x + 1) + 1,
          getVerticalSliceSize(_y + 1) - getVerticalSliceSize(_y) + 1);

      // draw row
      graphics.fillRectangle(getHorizontalSliceSize(_x), 0, getHorizontalSliceSize(_x + 1) - getHorizontalSliceSize(_x)
          + 1, getVerticalSliceSize(_y + 1) + 1);

      // // draw square
      // if (true) {
      // graphics.fillRectangle(getHorizontalSliceSize(_x), 0, getHorizontalSliceSize(_x + 1)
      // - getHorizontalSliceSize(_x) + 1, getVerticalSliceSize(_x + 1) + 1);
      // graphics.fillRectangle(getHorizontalSliceSize(_x), getVerticalSliceSize(_y), getHorizontalSliceSize(_y + 1)
      // - getHorizontalSliceSize(_x), getVerticalSliceSize(1));
      // graphics.fillRectangle(getHorizontalSliceSize(_x), getVerticalSliceSize(_x), getHorizontalSliceSize(_y)
      // - getHorizontalSliceSize(_x), getVerticalSliceSize(1));
      // graphics.fillRectangle(getHorizontalSliceSize(_y), getVerticalSliceSize(_y), getHorizontalSliceSize(1),
      // getVerticalSliceSize(_x) - getVerticalSliceSize(_y));
      // }

      // draw marked cell
      if (getMatrixCycleDetector().isInCycle(_x, _y)) {
        graphics.setBackgroundColor(getMatrixConfiguration().getCycleMatrixMarkedCellColor());
      } else {
        graphics.setBackgroundColor(getMatrixConfiguration().getMatrixMarkedCellColor());
      }
      graphics.fillRectangle(getHorizontalSliceSize(_x), getVerticalSliceSize(_y), getHorizontalSliceSize(_x + 1)
          - getHorizontalSliceSize(_x) + 1, getVerticalSliceSize(_y + 1) - getVerticalSliceSize(_y) + 1);
      graphics.fillRectangle(getHorizontalSliceSize(_y), getVerticalSliceSize(_x), getHorizontalSliceSize(_y + 1)
          - getHorizontalSliceSize(_y) + 1, getVerticalSliceSize(_x + 1) - getVerticalSliceSize(_x) + 1);
    }
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
      final Point location = me.getLocation();

      //
      final int x = (location.x / ((IDsmViewModel) _model).getConfiguration().getHorizontalBoxSize());
      final int y = (location.y / ((IDsmViewModel) _model).getConfiguration().getVerticalBoxSize());

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
        Display.getCurrent().timerExec(1000, new Runnable() {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseDoubleClicked(MouseEvent me) {

      // notify listener
      MatrixEvent event = new MatrixEvent(_x, _y);
      for (IMatrixListener listener : _matrixListeners) {
        listener.doubleClick(event);
      }
    }

    @Override
    public synchronized void mouseReleased(MouseEvent me) {

      // notify listener
      MatrixEvent event = new MatrixEvent(_x, _y);
      for (IMatrixListener listener : _matrixListeners) {
        listener.singleClick(event);
      }
    }
  }
}