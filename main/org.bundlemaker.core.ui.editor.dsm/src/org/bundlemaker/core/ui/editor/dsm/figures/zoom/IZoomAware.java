package org.bundlemaker.core.ui.editor.dsm.figures.zoom;

/**
 * <p>
 * Defines the interface for zoom aware containers.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IZoomAware {

  /**
   * <p>
   * Sets the zoom.
   * </p>
   * 
   * @param zoom
   *          the zoom
   */
  void setZoom(float zoom);

  /**
   * <p>
   * Returns the current zoom.
   * </p>
   * 
   * @return the current zoom.
   */
  float getZoom();
}
