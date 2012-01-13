package org.bundlemaker.analysis.ui.dsmview;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.dsmview.figures.HorizontalSideMarker;
import org.bundlemaker.analysis.ui.dsmview.figures.IMatrixListener;
import org.bundlemaker.analysis.ui.dsmview.figures.Matrix;
import org.bundlemaker.analysis.ui.dsmview.figures.MatrixEvent;
import org.bundlemaker.analysis.ui.dsmview.figures.VerticalSideMarker;
import org.bundlemaker.analysis.ui.dsmview.figures.ZoomableScrollPane;
import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.ButtonBorder;
import org.eclipse.draw2d.ChangeEvent;
import org.eclipse.draw2d.ChangeListener;
import org.eclipse.draw2d.CheckBox;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.SchemeBorder;
import org.eclipse.draw2d.ScrollBar;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DsmViewWidget implements Observer {

  private final class MyMotionListener extends MouseMotionListener.Stub {

    /** - */
    private static final int HORIZONTAL   = 1;

    /** - */
    private static final int VERTICAL     = 2;

    /** - */
    private static final int RANGE        = 10;

    /** - */
    private int              _currentDrag = -1;

    @Override
    public void mouseMoved(MouseEvent me) {
      dump(me, false);
    }

    @Override
    public void mouseDragged(MouseEvent me) {
      dump(me, true);
    }

    public void dump(MouseEvent me, boolean isDragged) {

      if (!isDragged) {
        _currentDrag = -1;
      }

      //
      if (me.getSource() instanceof Figure) {

        switch (isInRange(me)) {
        case HORIZONTAL:
          ((Figure) me.getSource()).setCursor(Cursors.SIZENS);
          break;
        case VERTICAL:
          ((Figure) me.getSource()).setCursor(Cursors.SIZEWE);
          break;
        default:
          ((Figure) me.getSource()).setCursor(Cursors.ARROW);
          break;
        }
      }

      //
      if ((me.getState() & MouseEvent.BUTTON1) != 0 && isDragged) {

        //
        if (_currentDrag == -1) {
          _currentDrag = isInRange(me);
        }

        //
        if (_currentDrag == HORIZONTAL && me.getSource().equals(DsmViewWidget.this._matrixFigure)) {
          DsmViewWidget.this._horizontalWidth = DsmViewWidget.this._horizontalWidth + me.getLocation().y;
        }

        //
        if (_currentDrag == HORIZONTAL && me.getSource().equals(DsmViewWidget.this._horizontalListFigure)) {
          DsmViewWidget.this._horizontalWidth = me.getLocation().y;
        }

        //
        if (_currentDrag == VERTICAL && me.getSource().equals(DsmViewWidget.this._matrixFigure)) {
          DsmViewWidget.this._verticalWidth = DsmViewWidget.this._verticalWidth + me.getLocation().x;
        }

        //
        if (_currentDrag == VERTICAL && me.getSource().equals(DsmViewWidget.this._verticalListFigure)) {
          DsmViewWidget.this._verticalWidth = me.getLocation().x;
        }

        // DsmViewWidget.this._horizontalWidth = me.y;
        DsmViewWidget.this._mainFigure.revalidate();
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
      if (me.getSource().equals(DsmViewWidget.this._matrixFigure)) {
        if (Math.abs(me.getLocation().x) < RANGE) {
          return VERTICAL;
        }

        if (Math.abs(me.getLocation().y) < RANGE) {
          return HORIZONTAL;
        }
      }

      else if (me.getSource().equals(DsmViewWidget.this._horizontalListFigure)) {

        if (Math.abs(me.getLocation().x) < RANGE) {
          return VERTICAL;
        }

        if (DsmViewWidget.this._horizontalListFigure.getSize().height - Math.abs(me.getLocation().y) < RANGE) {
          return HORIZONTAL;
        }
      }

      else if (me.getSource().equals(DsmViewWidget.this._verticalListFigure)) {

        if (Math.abs(me.getLocation().y) < RANGE) {
          return HORIZONTAL;
        }

        if (DsmViewWidget.this._verticalListFigure.getSize().width - Math.abs(me.getLocation().x) < RANGE) {
          return VERTICAL;
        }
      }

      //
      return -1;
    }
  }

  /** the SWT canvas */
  private Canvas               _canvas;

  /** the {@link DsmViewModel} */
  private AbstractDsmViewModel _model;

  /** the main figure */
  private Figure               _mainFigure;

  private ZoomableScrollPane   _zoomableScrollpane;

  private ZoomableScrollPane   _zoomableScrollpaneVerticalBar;

  private ZoomableScrollPane   _zoomableScrollpaneHorizontalBar;

  private ScrollBar            _zoomScrollBar;

  private CheckBox             _useShortendLabelsCheckBox;

  /** - */
  private Matrix               _matrixFigure;

  private VerticalSideMarker   _verticalListFigure;

  private HorizontalSideMarker _horizontalListFigure;

  private int                  _horizontalWidth = 10;

  public int                   _verticalWidth   = -1;

  /**
   * <p>
   * Creates a new instance of type {@link DsmViewWidget}.
   * </p>
   * 
   * @param model
   * @param canvas
   */
  public DsmViewWidget(AbstractDsmViewModel model, Composite parent) {

    // assert not null
    Assert.isNotNull(model);
    Assert.isNotNull(parent);

    // set model and canvas
    this._model = model;
    this._canvas = new Canvas(parent, SWT.NO_REDRAW_RESIZE);

    // set this view as an observer
    this._model.addObserver(this);

    // init
    init();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(Observable o, Object arg) {
    Display.getDefault().asyncExec(new Runnable() {
      @Override
      public void run() {
        _mainFigure.repaint();
      }
    });
  }

  /**
   * <p>
   * Initializes the {@link DsmViewWidget}.
   * </p>
   */
  private void init() {

    LightweightSystem lws = new LightweightSystem(_canvas);
    _mainFigure = new Figure();
    _mainFigure.setLayoutManager(new XYLayout());
    lws.setContents(_mainFigure);

    MyMotionListener motionListener = new MyMotionListener();
    _matrixFigure = new Matrix(_model);
    _matrixFigure.addMouseMotionListener(motionListener);

    _zoomableScrollpane = new ZoomableScrollPane(_matrixFigure, ScrollPane.ALWAYS, ScrollPane.ALWAYS);

    _verticalListFigure = new VerticalSideMarker(_model);
    _verticalListFigure.addMouseMotionListener(motionListener);
    _zoomableScrollpaneVerticalBar = new ZoomableScrollPane(_verticalListFigure, ScrollPane.NEVER, ScrollPane.NEVER);

    _horizontalListFigure = new HorizontalSideMarker(_model);
    _horizontalListFigure.addMouseMotionListener(motionListener);
    _zoomableScrollpaneHorizontalBar = new ZoomableScrollPane(_horizontalListFigure, ScrollPane.NEVER, ScrollPane.NEVER);

    _matrixFigure.addMatrixListener(new IMatrixListener() {

      @Override
      public void toolTip(MatrixEvent event) {
        drawToolTip(event.getX(), event.getY());
      }

      @Override
      public void singleClick(MatrixEvent event) {
        if (_model instanceof DsmViewModel) {
          Analysis.instance().getDependencySelectionService()
              .setSelection(DSMView.ID, ((DsmViewModel) _model).getDependency(event.getX(), event.getY()));
        }
      }

      @Override
      public void doubleClick(MatrixEvent event) {
        // do nothing
      }

      @Override
      public void marked(MatrixEvent event) {
        _horizontalListFigure.mark(event.getX());
        _verticalListFigure.mark(event.getY());
      }
    });

    _zoomScrollBar = new ScrollBar();
    final Label zoomLabel = new Label("Zoom");
    zoomLabel.setBorder(new SchemeBorder(ButtonBorder.SCHEMES.BUTTON_SCROLLBAR));
    _zoomScrollBar.setThumb(zoomLabel);
    _zoomScrollBar.setHorizontal(true);
    _zoomScrollBar.setMaximum(200);
    _zoomScrollBar.setMinimum(0);
    _zoomScrollBar.setExtent(25);
    _zoomScrollBar.addPropertyChangeListener("value", new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        float z = (_zoomScrollBar.getValue() + 10) * 0.02f;
        _zoomableScrollpane.setZoom(z);
        _zoomableScrollpaneVerticalBar.setZoom(z);
        _zoomableScrollpaneHorizontalBar.setZoom(z);
      }
    });

    _useShortendLabelsCheckBox = new CheckBox("Shorten labels");
    _useShortendLabelsCheckBox.getModel().addChangeListener(new ChangeListener() {

      @Override
      public void handleStateChanged(ChangeEvent event) {
        if ("selected".equals(event.getPropertyName())) {
          _model.setUseShortendLabels(_useShortendLabelsCheckBox.isSelected());
          _mainFigure.revalidate();
          _mainFigure.repaint();
        }
      }
    });

    _zoomableScrollpane.getViewport().addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        Viewport viewport = (Viewport) evt.getSource();

        _zoomableScrollpaneVerticalBar.getViewport().setViewLocation(0, viewport.getViewLocation().y);

        _zoomableScrollpaneHorizontalBar.getViewport().setViewLocation(viewport.getViewLocation().x, 0);

        _zoomableScrollpane.getViewport().setViewLocation(viewport.getViewLocation().x, viewport.getViewLocation().y);

        _mainFigure.revalidate();
        _mainFigure.repaint();
      }
    });

    _mainFigure.add(_zoomScrollBar);
    _mainFigure.add(_useShortendLabelsCheckBox);
    _mainFigure.add(_zoomableScrollpane);
    _mainFigure.add(_zoomableScrollpaneVerticalBar);
    _mainFigure.add(_zoomableScrollpaneHorizontalBar);

    _mainFigure.addLayoutListener(new LayoutListener.Stub() {

      @Override
      public boolean layout(IFigure container) {
        layoutF(container);
        return true;
      }
    });
  }

  protected void drawToolTip(int x, int y) {
    // System.out.println("drawToolTip");
  }

  /**
   * <p>
   * Compute the text extend.
   * </p>
   * 
   * @param matrixFigure
   * @param zoomableScrollpane
   * @return
   */
  private int getTextExtend(final Matrix matrixFigure, final ZoomableScrollPane zoomableScrollpane) {

    //
    int testExtend = FigureUtilities.getTextWidth(
        getLongestString(_model.isUseShortendLabels() ? _model.getShortendLabels() : _model.getLabels()),
        _matrixFigure.getFont()) + 15;
    return (int) (testExtend * zoomableScrollpane.getZoom());
  }

  /**
   * <p>
   * Helper method that returns the longest string from the string array.
   * </p>
   * 
   * @param strings
   *          the string array
   * @return the longest string from the string array.
   */
  private String getLongestString(String[] strings) {

    // create the result
    String result = null;

    // iterate over all strings
    for (String string : strings) {
      if (result == null) {
        result = string;
      } else if (result.length() < string.length()) {
        result = string;
      }
    }

    // return the result
    return (result == null ? "" : result);
  }

  private void layoutF(IFigure figure) {

    //
    _model.getConfiguration().setHorizontalBoxSize(computeSize());

    // adjust size
    _matrixFigure.resetSize();
    _horizontalListFigure.resetSize();
    _verticalListFigure.resetSize();

    // fix sized
    _zoomScrollBar.setLocation(new Point(0.0, 0.0));
    int mainFigureHalfWidth = _mainFigure.getSize().width / 2;
    _zoomScrollBar.setSize(mainFigureHalfWidth, 20);

    _useShortendLabelsCheckBox.setLocation(new Point(mainFigureHalfWidth + 1, 0.0));
    _useShortendLabelsCheckBox.setSize(mainFigureHalfWidth, 20);

    int horizontalBarHeight = (int) (_horizontalWidth * _zoomableScrollpaneHorizontalBar.getZoom());
    if (_verticalWidth == -1) {
      _verticalWidth = getTextExtend(_matrixFigure, _zoomableScrollpane);
    }
    int verticalBarWidth = (int) (_verticalWidth * _zoomableScrollpaneVerticalBar.getZoom());

    //
    _zoomableScrollpane.setLocation(new Point(verticalBarWidth, 21.0 + horizontalBarHeight));
    _zoomableScrollpane.setSize(_mainFigure.getSize().width - verticalBarWidth,
        (int) (_mainFigure.getSize().height - (21.0 + horizontalBarHeight)));

    //
    _zoomableScrollpaneVerticalBar.setLocation(new Point(0, (21.0 + horizontalBarHeight)));
    _zoomableScrollpaneVerticalBar.setSize(verticalBarWidth,
        (_mainFigure.getSize().height - (21 + horizontalBarHeight + 17)));

    //
    _zoomableScrollpaneHorizontalBar.setLocation(new Point(verticalBarWidth, 21.0));
    // _zoomableScrollpaneHorizontalBar.setSize((_mainFigure.getSize().width - (textExtend + 17)), textExtend);
    _zoomableScrollpaneHorizontalBar.setSize((_mainFigure.getSize().width - (verticalBarWidth + 17)),
        horizontalBarHeight);
  }

  private int computeSize() {

    //
    String value = getLongestString(_model.getValues());
    return FigureUtilities.getTextWidth(value, _matrixFigure.getFont()) + 6;
  }

  private String getLongestString(String[][] values) {

    // create the result
    String result = "";

    // iterate over all strings
    for (String[] value : values) {
      for (String string : value) {

        if (string != null) {

          if (result == null) {
            result = string;
          } else if (result.length() < string.length()) {
            result = string;
          }
        }
      }
    }

    // return the result
    return result;
  }

  public void setModel(DsmViewModel model) {
    _model = model;

    _matrixFigure.setModel(model);
    _verticalListFigure.setModel(model);
    _horizontalListFigure.setModel(model);

    _mainFigure.revalidate();
    _mainFigure.repaint();

    _zoomScrollBar.setValue(40);
    _useShortendLabelsCheckBox.setSelected(_model.isUseShortendLabels());
  }

  public void addMatrixListener(IMatrixListener listener) {
    _matrixFigure.addMatrixListener(listener);
  }

  public void removeMatrixLIstener(IMatrixListener listener) {
    _matrixFigure.removeMatrixLIstener(listener);
  }

  public static void main(String args[]) {

    Display d = new Display();
    final Shell shell = new Shell(d);
    shell.setSize(800, 800);
    shell.setLayout(new FillLayout());
    final AbstractDsmViewModel model = new AbstractDsmViewModel() {

      @Override
      public String getToolTip(int x, int y) {
        return "same";
      }

      @Override
      protected String[][] createValues() {
        return new String[][] { { "1", "12", "1", "12" }, { "3", "56", "1", "12" }, { "3", "56", "1", "12" },
            { "3", "56", "1", "12" } };
      }

      @Override
      protected String[] createLabels() {
        return new String[] { "Test_1", "Test_2", "Test_3", "Test_4" };
      }

      @Override
      protected IDsmViewConfiguration createConfiguration() {
        return new DefaultDsmViewConfiguration();
      }
    };

    new DsmViewWidget(model, shell);
    shell.open();

    while (!shell.isDisposed())
      while (!d.readAndDispatch())
        d.sleep();
  }
}
