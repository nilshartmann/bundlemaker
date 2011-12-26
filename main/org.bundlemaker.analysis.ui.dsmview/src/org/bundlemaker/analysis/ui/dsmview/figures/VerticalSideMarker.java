package org.bundlemaker.analysis.ui.dsmview.figures;

import org.bundlemaker.analysis.ui.dsmview.AbstractDsmViewModel;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Color;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class VerticalSideMarker extends AbstractSideMarker implements ISideMarker {

  /**
   * <p>
   * Creates a new instance of type {@link VerticalSideMarker}.
   * </p>
   * 
   * @param model
   */
  public VerticalSideMarker(AbstractDsmViewModel model) {
    super(model);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onMark(int oldValue, int newValue) {

    // repaint old
    repaint(0, getHorizontalSliceSize(newValue), getSize().width, getHorizontalSliceSize(newValue + 1)
        - getHorizontalSliceSize(newValue) + 1);

    // repaint new
    repaint(0, getHorizontalSliceSize(oldValue), getSize().width, getHorizontalSliceSize(oldValue + 1)
        - getHorizontalSliceSize(oldValue) + 1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void paintClientArea(Graphics graphics) {
    super.paintClientArea(graphics);

    resetSize();

    // push the state
    graphics.pushState();

    // draw the background
    Color oldColor = graphics.getBackgroundColor();
    graphics.setBackgroundColor(getModel().getConfiguration().getSideMarkerBackgroundColor());
    graphics.fillRectangle(0, 0, getSize().width + 1, getModel().getItemCount()
        * getModel().getConfiguration().getVerticalBoxSize());
    graphics.setBackgroundColor(oldColor);

    int offset = (getModel().getConfiguration().getVerticalBoxSize() - getFontHeight()) / 2;

    boolean useShortendLabels = getModel().isUseShortendLabels();

    // draw the rows/columns
    for (int i = 0; i < getModel().getItemCount(); i++) {

      if (i == getMarkedItem() || i % 2 == 0) {

        // set background color
        oldColor = graphics.getBackgroundColor();

        // set new background color
        if (i == getMarkedItem()) {
          graphics.setBackgroundColor(getModel().getConfiguration().getSideMarkerMarkedColor());
        } else {
          graphics.setBackgroundColor(getModel().getConfiguration().getSideMarkerEvenColor());
        }

        // draw cell
        graphics.fillRectangle(0, getVerticalSliceSize(i), getSize().width + 1, getVerticalSliceSize(i + 1)
            - getVerticalSliceSize(i) + 1);

        // unset background color
        graphics.setBackgroundColor(oldColor);
      }

      // draw text
      graphics.setForegroundColor(getModel().getConfiguration().getSideMarkerTextColor());
      graphics.drawString(useShortendLabels ? getModel().getShortendLabels()[i] : getModel().getLabels()[i], new Point(
          10, (getModel().getConfiguration().getVerticalBoxSize() * i) + offset));

      // draw
      graphics.setForegroundColor(getModel().getConfiguration().getSideMarkerSeparatorColor());
      graphics.drawLine(0, getVerticalSliceSize(i), getSize().width + 1, getVerticalSliceSize(i));

    }

    graphics.drawLine(0, getSize().height, getSize().width, getSize().height);

    // pop the state
    graphics.popState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void resetSize() {

    // compute the size
    Dimension dimension = new Dimension(getSize().width, getModel().getConfiguration().getVerticalBoxSize()
        * getModel().getItemCount());

    // reset the size
    if (!getSize().equals(dimension)) {
      setSize(dimension);
    }
  }
}