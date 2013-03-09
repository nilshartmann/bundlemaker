package org.bundlemaker.core.ui.editor.dsm.figures.zoom;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScrollPane;

/**
 * <p>
 * Implements a zoomable {@link ScrollPane}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ZoomableScrollPane extends ScrollPane implements IZoomAware {

  /** the internal zoom container */
  private ZoomContainer _zoomContainer;

  /**
   * <p>
   * Creates a new instance of type {@link ZoomableScrollPane}.
   * </p>
   * 
   * @param figure
   * @param horizontalScrollBarVisibility
   * @param verticalScrollBarVisibility
   */
  public ZoomableScrollPane(IFigure figure, int horizontalScrollBarVisibility, int verticalScrollBarVisibility) {

    // assert not null
    Assert.isNotNull(figure);

    // set the zoom container
    _zoomContainer = new ZoomContainer();
    _zoomContainer.add(figure);
    _zoomContainer.setZoom(1.0f);
    setContents(_zoomContainer);

    // set up the scroll pane
    getVerticalScrollBar().setExtent(25);
    getHorizontalScrollBar().setExtent(25);
    setHorizontalScrollBarVisibility(horizontalScrollBarVisibility);
    setVerticalScrollBarVisibility(verticalScrollBarVisibility);
    getViewport().setContentsTracksWidth(true);
    getViewport().setContentsTracksHeight(true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setZoom(float zoom) {
    _zoomContainer.setZoom(zoom);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public float getZoom() {
    return _zoomContainer.zoom;
  }
}
