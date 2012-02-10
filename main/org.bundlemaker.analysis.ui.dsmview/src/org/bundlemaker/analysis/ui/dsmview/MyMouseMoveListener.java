package org.bundlemaker.analysis.ui.dsmview;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.Cursors;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.widgets.Canvas;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MyMouseMoveListener implements MouseMoveListener, MouseListener {

  /** - */
  private static final int HORIZONTAL = 1;

  /** - */
  private static final int VERTICAL   = 2;

  /** - */
  private static final int DIAGONAL   = 3;

  /** - */
  private static final int RANGE      = 7;

  /** - */
  final DsmViewWidget      _dsmViewWidget;

  /**
   * <p>
   * Creates a new instance of type {@link MyMouseMoveListener}.
   * </p>
   * 
   * @param dsmViewWidget
   */
  MyMouseMoveListener(DsmViewWidget dsmViewWidget) {
    Assert.isNotNull(dsmViewWidget);

    _dsmViewWidget = dsmViewWidget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseDoubleClick(MouseEvent e) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseDown(MouseEvent e) {
    //
    _dsmViewWidget._x = -1;
    _dsmViewWidget._y = -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseUp(MouseEvent e) {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseMove(MouseEvent e) {
    System.out.println(isInRange(e) + " mouseMove " + e);

    //
    _dsmViewWidget._x = e.x;
    _dsmViewWidget._y = e.y;
    System.out.println(e.x);
    System.out.println(e.y);

    switch (isInRange(e)) {
    case HORIZONTAL:
      ((Canvas) e.getSource()).setCursor(Cursors.SIZENS);
      break;
    case VERTICAL:
      ((Canvas) e.getSource()).setCursor(Cursors.SIZEWE);
      break;
    case DIAGONAL:
      ((Canvas) e.getSource()).setCursor(Cursors.SIZENWSE);
      break;
    default:
      ((Canvas) e.getSource()).setCursor(Cursors.ARROW);
      break;
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

    // //
    // if (me.getSource().equals(_dsmViewWidget._matrixFigure)) {

    float x = Math.abs(me.x - (_dsmViewWidget._verticalFigureWidth * _dsmViewWidget.getZoom()));
    float y = Math.abs(me.y - (_dsmViewWidget._horizontalFigureHeight * _dsmViewWidget.getZoom()));

    float range = RANGE * _dsmViewWidget.getZoom();

    //
    if (x < range && y < range) {
      return DIAGONAL;
    } else if (x < range) {
      return VERTICAL;
    } else if (y < range) {
      return HORIZONTAL;
    }

    // }
    //
    // else if (me.getSource().equals(_dsmViewWidget._horizontalListFigure)) {
    //
    // if (Math.abs(me.x) < RANGE && _dsmViewWidget._horizontalListFigure.getSize().height - Math.abs(me.y) < RANGE) {
    // return DIAGONAL;
    // }
    //
    // if (Math.abs(me.x) < RANGE) {
    // return VERTICAL;
    // }
    //
    // if (_dsmViewWidget._horizontalListFigure.getSize().height - Math.abs(me.y) < RANGE) {
    // return HORIZONTAL;
    // }
    // }
    //
    // else if (me.getSource().equals(_dsmViewWidget._verticalListFigure)) {
    //
    // if (Math.abs(me.y) < RANGE && _dsmViewWidget._verticalListFigure.getSize().width - Math.abs(me.x) < RANGE) {
    // return HORIZONTAL;
    // }
    //
    // if (Math.abs(me.y) < RANGE) {
    // return HORIZONTAL;
    // }
    //
    // if (_dsmViewWidget._verticalListFigure.getSize().width - Math.abs(me.x) < RANGE) {
    // return VERTICAL;
    // }
    // }

    //
    return -1;
  }
}
