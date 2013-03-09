package org.bundlemaker.core.ui.editor.dsm.figures.matrix;

import org.bundlemaker.core.ui.editor.dsm.IDsmViewModel;
import org.bundlemaker.core.ui.editor.dsm.figures.DefaultMatrixColorScheme;
import org.bundlemaker.core.ui.editor.dsm.figures.IMatrixColorScheme;
import org.bundlemaker.core.ui.editor.dsm.figures.IMatrixContentProvider;
import org.bundlemaker.core.ui.editor.dsm.figures.IMatrixCycleDetector;
import org.bundlemaker.core.ui.editor.dsm.figures.zoom.ZoomContainer;
import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * <p>
 * The {@link Figure} that implements the matrix.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Matrix extends Figure {

  /** the model */
  protected IMatrixContentProvider _model;

  /** - */
  private IMatrixColorScheme       _matrixColorScheme;

  /** - */
  private ILabelProvider           _labelProvider;

  /** - */
  private IMatrixCycleDetector     _matrixCycleDetector;

  /** - */
  private int                      _horizontalBoxSize = -1;

  /**
   * <p>
   * Creates a new instance of type {@link Matrix}.
   * </p>
   * 
   * @param model
   * @param matrixConfiguration
   * @param labelProvider
   * @param matrixCycleDetector
   */
  public Matrix(IMatrixContentProvider model, ILabelProvider labelProvider, IMatrixCycleDetector matrixCycleDetector) {

    //
    Assert.isNotNull(model);
    Assert.isNotNull(labelProvider);
    Assert.isNotNull(matrixCycleDetector);

    // set the model
    _model = model;
    _matrixColorScheme = new DefaultMatrixColorScheme();
    _labelProvider = labelProvider;
    _matrixCycleDetector = matrixCycleDetector;
  }

  /**
   * <p>
   * </p>
   * 
   * @param colorScheme
   */
  protected final void setMatrixColorScheme(IMatrixColorScheme colorScheme) {
    _matrixColorScheme = colorScheme;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IMatrixContentProvider getModel() {
    return _model;
  }

  public void setModel(IMatrixContentProvider model) {

    // TODO
    _model = model;
    _matrixCycleDetector = (IMatrixCycleDetector) model;
    _matrixColorScheme = ((IDsmViewModel) model).getConfiguration();
    _horizontalBoxSize = -1;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final IMatrixColorScheme getMatrixConfiguration() {
    return _matrixColorScheme;
  }

  protected IMatrixCycleDetector getMatrixCycleDetector() {
    return _matrixCycleDetector;
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

    // draw the background for the complete matrix
    graphics.setBackgroundColor(_matrixColorScheme.getMatrixBackgroundColor());
    graphics.fillRectangle(0, 0, getSize().width, getSize().height);

    // draw the diagonal
    graphics.setBackgroundColor(_matrixColorScheme.getMatrixDiagonalColor());
    for (int i = 0; i < _model.getItemCount(); i++) {
      graphics.fillRectangle(getHorizontalSliceSize(i), getVerticalSliceSize(i), getHorizontalSliceSize(i + 1)
          - getHorizontalSliceSize(i) + 1, getVerticalSliceSize(i + 1) - getVerticalSliceSize(i) + 1);
    }

    // draw the cycles
    for (int[] cycle : _matrixCycleDetector.getCycles()) {
      graphics.setBackgroundColor(_matrixColorScheme.getCycleSideMarkerColor());
      int lenght = cycle[cycle.length - 1] - cycle[0] + 1;
      graphics.fillRectangle(getHorizontalSliceSize(cycle[0]), getVerticalSliceSize(cycle[0]),
          getHorizontalSliceSize(lenght) + 1, getVerticalSliceSize(lenght) + 1);

      for (int i = 0; i < cycle.length; i++) {
        graphics.setBackgroundColor(_matrixColorScheme.getCycleMatrixDiagonalColor());
        graphics.fillRectangle(getHorizontalSliceSize(cycle[i]), getVerticalSliceSize(cycle[i]),
            getHorizontalSliceSize(cycle[i] + 1) - getHorizontalSliceSize(cycle[i]) + 1,
            getVerticalSliceSize(cycle[i] + 1) - getVerticalSliceSize(cycle[i]) + 1);
      }

    }

    //
    onPaintClientArea(graphics);

    // draw the text
    graphics.setForegroundColor(_matrixColorScheme.getMatrixTextColor());
    int[] visibleSlices = getVisibleSlices();
    for (int i = visibleSlices[0]; (i <= visibleSlices[1]); i++) {
      for (int j = visibleSlices[2]; j < _model.getItemCount(); j++) {
        if (i != j) {
          String value = _model.isToggled() ? _labelProvider.getText(_model.getDependency(j, i)) : _labelProvider
              .getText(_model.getDependency(i, j));
          if (value != null) {
            graphics.drawString(value, getHorizontalSliceSize(i) + 4, getVerticalSliceSize(j));
          }
        }
      }
    }

    // draw the separator lines
    graphics.setForegroundColor(_matrixColorScheme.getMatrixSeparatorColor());
    for (int i = 0; i <= _model.getItemCount(); i++) {
      graphics.drawLine(new Point(0, getVerticalSliceSize(i)), new Point(
          ((IDsmViewModel)_model).getConfiguration().getHorizontalBoxSize() * _model.getItemCount(), getVerticalSliceSize(i)));
      graphics.drawLine(new Point(getHorizontalSliceSize(i), 0), new Point(getHorizontalSliceSize(i),
          ((IDsmViewModel)_model).getConfiguration().getVerticalBoxSize() * _model.getItemCount()));
    }

    // draw the cycle separator lines
    graphics.setForegroundColor(_matrixColorScheme.getCycleSideMarkerSeparatorColor());
    for (int[] cycle : _matrixCycleDetector.getCycles()) {
      int current = 0;
      for (int i : cycle) {
        current = i;
        graphics.drawLine(getHorizontalSliceSize(i), getVerticalSliceSize(cycle[0]), getHorizontalSliceSize(i),
            getVerticalSliceSize(cycle[cycle.length - 1] + 1));
        graphics.drawLine(getHorizontalSliceSize(cycle[0]), getVerticalSliceSize(i),
            getHorizontalSliceSize(cycle[cycle.length - 1] + 1), getVerticalSliceSize(i));
      }
      current++;
      graphics.drawLine(getHorizontalSliceSize(current), getVerticalSliceSize(cycle[0]),
          getHorizontalSliceSize(current), getVerticalSliceSize(cycle[cycle.length - 1] + 1));
      graphics.drawLine(getHorizontalSliceSize(cycle[0]), getVerticalSliceSize(current),
          getHorizontalSliceSize(cycle[cycle.length - 1] + 1), getVerticalSliceSize(current));
    }

    // restore state
    graphics.popState();
  }

  /**
   * <p>
   * </p>
   * 
   * @param graphics
   */
  protected void onPaintClientArea(Graphics graphics) {
    //
  }

  private int[] getVisibleSlices() {

    if (!(this.getParent() instanceof ZoomContainer)) {
      return new int[] { 0, _model.getItemCount() - 1, 0, _model.getItemCount() - 1 };
    }

    ZoomContainer zoomContainer = (ZoomContainer) this.getParent();
    Viewport viewport = ((Viewport) this.getParent().getParent());

    int horMin = (int) (viewport.getViewLocation().x / (((IDsmViewModel)_model).getConfiguration().getHorizontalBoxSize() * zoomContainer.zoom));
    int horVisibleSlicesCount = (int) (viewport.getSize().width / (((IDsmViewModel)_model).getConfiguration().getHorizontalBoxSize() * zoomContainer.zoom));

    //
    int verMin = (int) (viewport.getViewLocation().y / (((IDsmViewModel)_model).getConfiguration().getVerticalBoxSize() * zoomContainer.zoom));
    int verVisibleSlicesCount = (int) (viewport.getSize().height / (((IDsmViewModel)_model).getConfiguration().getVerticalBoxSize() * zoomContainer.zoom));

    return new int[] {
        horMin,
        horMin + horVisibleSlicesCount > _model.getItemCount() - 1 ? _model.getItemCount() - 1 : horMin
            + horVisibleSlicesCount,
        verMin,
        verMin + verVisibleSlicesCount > _model.getItemCount() - 1 ? _model.getItemCount() - 1 : verMin
            + verVisibleSlicesCount };
  }

  /**
   * <p>
   * </p>
   */
  public void resetSize() {

    //
    Dimension dimension = new Dimension(((IDsmViewModel)_model).getConfiguration().getHorizontalBoxSize() * _model.getItemCount() + 1,
        ((IDsmViewModel)_model).getConfiguration().getVerticalBoxSize() * _model.getItemCount() + 1);

    //
    if (!getSize().equals(dimension)) {
      setSize(dimension);
    }
  }

  protected final int getHorizontalSliceSize(int count) {
    return (((IDsmViewModel)_model).getConfiguration().getHorizontalBoxSize() * count);
  }

  /**
   * <p>
   * </p>
   * 
   * @param count
   * @return
   */
  protected final int getVerticalSliceSize(int count) {
    return (((IDsmViewModel)_model).getConfiguration().getVerticalBoxSize() * count);
  }
}