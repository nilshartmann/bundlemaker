package org.bundlemaker.analysis.ui.dsmview;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;

final class DsmViewWidgetMouseMotionListener extends MouseMotionListener.Stub {

  /** - */
  final DsmViewWidget _dsmViewWidget;

  /**
   * <p>
   * Creates a new instance of type {@link DsmViewWidgetMouseMotionListener}.
   * </p>
   * 
   * @param dsmViewWidget
   */
  DsmViewWidgetMouseMotionListener(DsmViewWidget dsmViewWidget) {
    _dsmViewWidget = dsmViewWidget;
  }

  /** - */
  private static final int HORIZONTAL   = 1;

  /** - */
  private static final int VERTICAL     = 2;

  /** - */
  private static final int DIAGONAL     = 3;

  /** - */
  private static final int RANGE        = 10;

  /** - */
  private int              _currentDrag = -1;

  @Override
  public void mouseMoved(MouseEvent me) {
    handle(me, false);
  }

  @Override
  public void mouseDragged(MouseEvent me) {
    handle(me, true);
  }

  /**
   * <p>
   * </p>
   * 
   * @param me
   * @param isDragged
   */
  private void handle(MouseEvent me, boolean isDragged) {

    if (!isDragged) {
      _currentDrag = -1;
    }

    //
    // if (me.getSource() instanceof Figure) {

    // switch (isInRange(me)) {
    // case HORIZONTAL:
    // ((Figure) me.getSource()).setCursor(Cursors.SIZENS);
    // break;
    // case VERTICAL:
    // ((Figure) me.getSource()).setCursor(Cursors.SIZEWE);
    // break;
    // case DIAGONAL:
    // ((Figure) me.getSource()).setCursor(Cursors.SIZENWSE);
    // break;
    // default:
    // ((Figure) me.getSource()).setCursor(Cursors.ARROW);
    // break;
    // }
    // }

    //
    if ((me.getState() & MouseEvent.BUTTON1) != 0 && isDragged) {

      //
      if (_currentDrag == -1) {
        _currentDrag = isInRange(me);
      }

      if ((me.getState() & MouseEvent.SHIFT) != 0) {

        //
        if (me.getSource().equals(_dsmViewWidget._matrixFigure)) {
          System.out.println("location: " + me.getLocation().x);
          float newZoom = me.getLocation().x / (float) _dsmViewWidget._verticalFigureWidth;
          System.out.println("new zoom: " + newZoom);
          System.out.println("zoomed location: " + newZoom);
          System.out.println("width: " + _dsmViewWidget._verticalFigureWidth);
        }
        //
        else if (me.getSource().equals(_dsmViewWidget._mainFigure)) {
          System.out.println("location: " + me.getLocation().x);
          System.out.println("zoom: " + _dsmViewWidget._zoom);
          float newZoom = me.getLocation().x / (float) _dsmViewWidget._verticalFigureWidth;
          System.out.println("new zoom: " + newZoom);
          System.out.println("zoomed location: " + me.getLocation().x / _dsmViewWidget._zoom);
          System.out.println("width: " + _dsmViewWidget._verticalFigureWidth);

          _dsmViewWidget.setZoom(newZoom);

          // _verticalFigureWidth = location / zoom
          // zoom = location / _verticalFigureWidth
        }

        // _dsmViewWidget.setZoom(12);

      } else {

        //
        if ((_currentDrag == HORIZONTAL || _currentDrag == DIAGONAL)
            && me.getSource().equals(_dsmViewWidget._matrixFigure)) {
          _dsmViewWidget._horizontalFigureHeight = _dsmViewWidget._horizontalFigureHeight + me.getLocation().y;
        }
        //
        else if ((_currentDrag == HORIZONTAL || _currentDrag == DIAGONAL)
            && me.getSource().equals(_dsmViewWidget._horizontalListFigure)) {
          _dsmViewWidget._horizontalFigureHeight = me.getLocation().y;
        }
        //
        else if ((_currentDrag == HORIZONTAL || _currentDrag == DIAGONAL)
            && me.getSource().equals(_dsmViewWidget._verticalListFigure)) {
          _dsmViewWidget._horizontalFigureHeight = _dsmViewWidget._horizontalFigureHeight + me.getLocation().y;
        }
        //
        else if ((_currentDrag == HORIZONTAL || _currentDrag == DIAGONAL)
            && me.getSource().equals(_dsmViewWidget._mainFigure)) {
          _dsmViewWidget._horizontalFigureHeight = (int) ((me.getLocation().y / _dsmViewWidget._zoom));
        }
        //
        if ((_currentDrag == VERTICAL || _currentDrag == DIAGONAL)
            && me.getSource().equals(_dsmViewWidget._matrixFigure)) {
          _dsmViewWidget._verticalFigureWidth = _dsmViewWidget._verticalFigureWidth + me.getLocation().x;
        }
        //
        else if ((_currentDrag == VERTICAL || _currentDrag == DIAGONAL)
            && me.getSource().equals(_dsmViewWidget._verticalListFigure)) {
          _dsmViewWidget._verticalFigureWidth = me.getLocation().x;
        }
        // TODO
        else if ((_currentDrag == VERTICAL || _currentDrag == DIAGONAL)
            && me.getSource().equals(_dsmViewWidget._horizontalListFigure)) {
          _dsmViewWidget._verticalFigureWidth = _dsmViewWidget._verticalFigureWidth + me.getLocation().x;
        }
        //
        else if ((_currentDrag == VERTICAL || _currentDrag == DIAGONAL)
            && me.getSource().equals(_dsmViewWidget._mainFigure)) {
          _dsmViewWidget._verticalFigureWidth = (int) (me.getLocation().x / _dsmViewWidget._zoom);
        }
      }
      // DsmViewWidget.this._horizontalWidth = me.y;
      _dsmViewWidget._mainFigure.revalidate();
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param me
   * @return
   */
  private int isInRange(MouseEvent me) {

    //
    if (me.getSource().equals(_dsmViewWidget._matrixFigure)) {

      if (Math.abs(me.getLocation().x) < (RANGE * _dsmViewWidget._zoom)
          && Math.abs(me.getLocation().y) < (RANGE * _dsmViewWidget._zoom)) {
        return DIAGONAL;
      }

      if (Math.abs(me.getLocation().x) < (RANGE * _dsmViewWidget._zoom)) {
        return VERTICAL;
      }

      if (Math.abs(me.getLocation().y) < (RANGE * _dsmViewWidget._zoom)) {
        return HORIZONTAL;
      }
    }

    else if (me.getSource().equals(_dsmViewWidget._horizontalListFigure)) {

      if (Math.abs(me.getLocation().x) < (RANGE * _dsmViewWidget._zoom)
          && _dsmViewWidget._horizontalListFigure.getSize().height - Math.abs(me.getLocation().y) < (RANGE * _dsmViewWidget._zoom)) {
        return DIAGONAL;
      }

      if (Math.abs(me.getLocation().x) < (RANGE * _dsmViewWidget._zoom)) {
        return VERTICAL;
      }

      if (_dsmViewWidget._horizontalListFigure.getSize().height - Math.abs(me.getLocation().y) < (RANGE * _dsmViewWidget._zoom)) {
        return HORIZONTAL;
      }
    }

    else if (me.getSource().equals(_dsmViewWidget._verticalListFigure)) {

      if (Math.abs(me.getLocation().y) < (RANGE * _dsmViewWidget._zoom)
          && _dsmViewWidget._verticalListFigure.getSize().width - Math.abs(me.getLocation().x) < (RANGE * _dsmViewWidget._zoom)) {
        return HORIZONTAL;
      }

      if (Math.abs(me.getLocation().y) < (RANGE * _dsmViewWidget._zoom)) {
        return HORIZONTAL;
      }

      if (_dsmViewWidget._verticalListFigure.getSize().width - Math.abs(me.getLocation().x) < (RANGE * _dsmViewWidget._zoom)) {
        return VERTICAL;
      }
    }

    //
    return -1;
  }
}