package org.bundlemaker.core.ui.editor.dsm;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.ui.editor.dsm.figures.matrix.IMatrixListener;
import org.bundlemaker.core.ui.editor.dsm.figures.matrix.Matrix;
import org.bundlemaker.core.ui.editor.dsm.figures.matrix.MatrixEvent;
import org.bundlemaker.core.ui.editor.dsm.figures.sidemarker.HorizontalSideMarker;
import org.bundlemaker.core.ui.editor.dsm.figures.sidemarker.VerticalSideMarker;
import org.bundlemaker.core.ui.editor.dsm.figures.zoom.ZoomableScrollPane;
import org.bundlemaker.core.ui.editor.dsm.utils.DsmUtils;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DsmViewWidget extends Canvas implements Observer {

  @Deprecated
  float                      _zoom                   = 1.0f;

  /** the {@link DsmViewModel} */
  private IDsmViewModel      _model;

  /** the main figure */
  Figure                     _mainFigure;

  private ZoomableScrollPane _zoomableScrollpane;

  private ZoomableScrollPane _zoomableScrollpaneVerticalBar;

  private ZoomableScrollPane _zoomableScrollpaneHorizontalBar;

  /** - */
  Matrix                     _matrixFigure;

  VerticalSideMarker         _verticalListFigure;

  HorizontalSideMarker       _horizontalListFigure;

  int                        _horizontalFigureHeight = 8;

  int                        _verticalFigureWidth    = -1;

  private int                _x;

  private int                _y;

  private boolean            _drawToolTip            = false;
  
  /**
   * <p>
   * Creates a new instance of type {@link DsmViewWidget}.
   * </p>
   * 
   * @param model
   * @param canvas
   */
  public DsmViewWidget(IDsmViewModel model, Composite parent) {
    super(parent, SWT.NO_REDRAW_RESIZE);

    // assert not null
    Assert.isNotNull(model);
    Assert.isNotNull(parent);

    // set model and canvas
    this._model = model;

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

    LightweightSystem lws = new LightweightSystem(this);

    //
    DsmViewWidgetMouseMotionListener motionListener = new DsmViewWidgetMouseMotionListener(this);

    //
    this.addMouseWheelListener(new MouseWheelListener() {
      @Override
      public void mouseScrolled(org.eclipse.swt.events.MouseEvent e) {
        if (e.count > 0) {
          DsmViewWidget.this.setZoom(getZoom() * 1.05f);
        } else if (e.count < 0) {
          DsmViewWidget.this.setZoom(getZoom() * 0.95f);
        }
      }
    });

    //
    // this.addMouseMoveListener(new MyMouseMoveListener(this));

    _mainFigure = new Figure() {

      @Override
      public void paint(Graphics graphics) {
        super.paint(graphics);

        if (_drawToolTip && _x >= _verticalFigureWidth && _y >= _horizontalFigureHeight) {
          graphics.fillRectangle(_x, _y, 100, 100);
          graphics.drawRectangle(_x, _y, 100, 100);
        }
      }
    };

    _mainFigure.setLayoutManager(new XYLayout());
    _mainFigure.addMouseMotionListener(motionListener);
    lws.setContents(_mainFigure);

    _matrixFigure = new Matrix(_model);
    _matrixFigure.addMouseMotionListener(motionListener);
    _matrixFigure.addMouseListener(motionListener);

    _zoomableScrollpane = new ZoomableScrollPane(_matrixFigure, ScrollPane.ALWAYS, ScrollPane.ALWAYS);

    _verticalListFigure = new VerticalSideMarker(_model);
    _verticalListFigure.addMouseMotionListener(motionListener);
    _zoomableScrollpaneVerticalBar = new ZoomableScrollPane(_verticalListFigure, ScrollPane.NEVER, ScrollPane.NEVER);

    _horizontalListFigure = new HorizontalSideMarker(_model);
    _horizontalListFigure.addMouseMotionListener(motionListener);
    _zoomableScrollpaneHorizontalBar = new ZoomableScrollPane(_horizontalListFigure, ScrollPane.NEVER, ScrollPane.NEVER);

    _matrixFigure.addMouseMotionListener(new MouseMotionListener.Stub() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void mouseExited(org.eclipse.draw2d.MouseEvent me) {
        _drawToolTip = false;
      }
    });

    _matrixFigure.addMatrixListener(new IMatrixListener() {

      @Override
      public void toolTip(MatrixEvent event) {
        _drawToolTip = true;
        _mainFigure.repaint();
      }

      @Override
      public void singleClick(MatrixEvent event) {
        _drawToolTip = false;

        if (_model instanceof DsmViewModel) {
          IDependency dependency = _model.isToggled() ? ((DsmViewModel) _model).getDependency(event.getY(),
              event.getX()) : ((DsmViewModel) _model).getDependency(event.getX(), event.getY());

          Selection.instance().getDependencySelectionService()
              .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID, DSMArtifactModelEditor.DSM_EDITOR_ID, dependency);
        }
        _mainFigure.repaint();
      }

      @Override
      public void doubleClick(MatrixEvent event) {
        _drawToolTip = false;
        _mainFigure.repaint();
      }

      @Override
      public void marked(MatrixEvent event) {
        _mainFigure.repaint();
        _horizontalListFigure.mark(event.getX());
        _verticalListFigure.mark(event.getY());
      }
    });

    // _zoomScrollBar = new ScrollBar();
    // final Label zoomLabel = new Label("Zoom");
    // zoomLabel.setBorder(new SchemeBorder(ButtonBorder.SCHEMES.BUTTON_SCROLLBAR));
    // _zoomScrollBar.setThumb(zoomLabel);
    // _zoomScrollBar.setHorizontal(true);
    // _zoomScrollBar.setMaximum(200);
    // _zoomScrollBar.setMinimum(0);
    // _zoomScrollBar.setExtent(25);
    // _zoomScrollBar.addPropertyChangeListener("value", new PropertyChangeListener() {
    // @Override
    // public void propertyChange(PropertyChangeEvent evt) {
    // float z = (_zoomScrollBar.getValue() + 10) * 0.02f;
    // _zoomableScrollpane.setZoom(z);
    // _zoomableScrollpaneVerticalBar.setZoom(z);
    // _zoomableScrollpaneHorizontalBar.setZoom(z);
    // _zoom = z;
    // }
    // });
    //
    // _useShortendLabelsCheckBox = new CheckBox("Shorten labels");
    // _useShortendLabelsCheckBox.getModel().addChangeListener(new ChangeListener() {
    //
    // @Override
    // public void handleStateChanged(ChangeEvent event) {
    // if ("selected".equals(event.getPropertyName())) {
    // _model.setUseShortendLabels(_useShortendLabelsCheckBox.isSelected());
    // _mainFigure.revalidate();
    // _mainFigure.repaint();
    // }
    // }
    // });

    _zoomableScrollpane.getViewport().addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        Viewport viewport = (Viewport) evt.getSource();
        _zoomableScrollpaneVerticalBar.getViewport().setViewLocation(0, viewport.getViewLocation().y);
        _zoomableScrollpaneHorizontalBar.getViewport().setViewLocation(viewport.getViewLocation().x, 0);
        // _zoomableScrollpaneHorizontalBar.getViewport().setViewLocation(0, 0);
        _zoomableScrollpane.getViewport().setViewLocation(viewport.getViewLocation().x, viewport.getViewLocation().y);

        _mainFigure.revalidate();
        _mainFigure.repaint();
      }
    });

    // _mainFigure.add(_zoomScrollBar);
    // _mainFigure.add(_useShortendLabelsCheckBox);
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

  public void setZoom(float value) {
    // float z = (value + 10) * 0.02f;
    _zoomableScrollpane.setZoom(value);
    _zoomableScrollpaneVerticalBar.setZoom(value);
    _zoomableScrollpaneHorizontalBar.setZoom(value);
    _zoom = value;
    //
    // _mainFigure.repaint();
  }

  /**
   * <p>
   * </p>
   * 
   * @return the zoom
   */
  public float getZoom() {
    return _zoom;
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
        DsmUtils.getLongestString(_model.isUseShortendLabels() ? _model.getShortendLabels() : _model.getLabels()),
        matrixFigure.getFont());
    return (testExtend + 10/* * zoomableScrollpane.getZoom() */);
  }

  private void layoutF(IFigure figure) {

    //
    _model.getConfiguration().setHorizontalBoxSize(computeSize());

    // adjust size
    _matrixFigure.resetSize();
    _horizontalListFigure.resetSize();
    _verticalListFigure.resetSize();

    int horizontalBarHeight = (int) (_horizontalFigureHeight * _zoomableScrollpaneHorizontalBar.getZoom());
    if (_verticalFigureWidth == -1) {
      _verticalFigureWidth = getTextExtend(_matrixFigure, _zoomableScrollpane);
    }
    int verticalBarWidth = (int) (_verticalFigureWidth * _zoomableScrollpaneVerticalBar.getZoom());

    //
    _zoomableScrollpane.setLocation(new Point(verticalBarWidth, horizontalBarHeight));
    _zoomableScrollpane.setSize(_mainFigure.getSize().width - verticalBarWidth,
        (_mainFigure.getSize().height - (horizontalBarHeight)));


    // HACK
    int verticalOffset = 18;
    _zoomableScrollpaneVerticalBar.setLocation(new Point(0, (/* HORIZONTAL_OFFSET + */horizontalBarHeight)));
    _zoomableScrollpaneVerticalBar.setSize(verticalBarWidth,
        (_mainFigure.getSize().height - (horizontalBarHeight + verticalOffset)));

    //
    _zoomableScrollpaneHorizontalBar.setLocation(new Point(verticalBarWidth, 0));
    _zoomableScrollpaneHorizontalBar.setSize((_mainFigure.getSize().width - (verticalBarWidth + 18)),
        horizontalBarHeight);
  }

  private int computeSize() {
    String value = DsmUtils.getLongestString(_model.getValues());
    return FigureUtilities.getTextWidth(value, _matrixFigure.getFont()) + 6;
  }

  public void setModel(IDsmViewModel model) {
    _model = model;

    _matrixFigure.setModel(model);
    _verticalListFigure.setModel(model);
    _horizontalListFigure.setModel(model);

    _verticalFigureWidth = getTextExtend(_matrixFigure, _zoomableScrollpane);

    _mainFigure.revalidate();
    _mainFigure.repaint();

    // _zoomScrollBar.setValue(40);
    // _useShortendLabelsCheckBox.setSelected(_model.isUseShortendLabels());
  }

  /**
   * <p>
   * </p>
   * 
   * @return the model
   */
  public IDsmViewModel getModel() {
    return _model;
  }

  public void addMatrixListener(IMatrixListener listener) {
    _matrixFigure.addMatrixListener(listener);
  }

  public void removeMatrixLIstener(IMatrixListener listener) {
    _matrixFigure.removeMatrixLIstener(listener);
  }
}
