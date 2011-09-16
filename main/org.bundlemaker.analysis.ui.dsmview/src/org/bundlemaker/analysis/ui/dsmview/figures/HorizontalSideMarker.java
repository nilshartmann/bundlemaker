package org.bundlemaker.analysis.ui.dsmview.figures;

import org.bundlemaker.analysis.ui.dsmview.DsmViewModel;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class HorizontalSideMarker extends AbstractSideMarker implements ISideMarker {

  /**
   * <p>
   * Creates a new instance of type {@link HorizontalSideMarker}.
   * </p>
   * 
   * @param model
   */
  public HorizontalSideMarker(DsmViewModel model) {
    super(model);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void paintClientArea(Graphics graphics) {
    super.paintClientArea(graphics);

    // reset the size
    resetSize();

    // store the state
    graphics.pushState();

    // draw the background ("odd marker")
    graphics.setBackgroundColor(getModel().getConfiguration().getSideMarkerBackgroundColor());

    graphics.fillRectangle(0, 0, getModel().getItemCount() * getModel().getConfiguration().getHorizontalBoxSize(),
        getSize().height + 1);

    // draw the makers
    for (int i = 0; i < getModel().getItemCount(); i++) {

      // draw the "even" marker
      if (getMarkedItem() == i || i % 2 == 0) {

        // set the background
        if (getMarkedItem() == i) {
          graphics.setBackgroundColor(getModel().getConfiguration().getSideMarkerMarkedColor());
        } else {
          graphics.setBackgroundColor(getModel().getConfiguration().getSideMarkerEvenColor());
        }

        // draw the background
        graphics.fillRectangle(getHorizontalSliceSize(i), 0, getHorizontalSliceSize(i + 1) - getHorizontalSliceSize(i)
            + 1, getSize().height + 1);
      }

      // draw the separator lines
      graphics.setForegroundColor(getModel().getConfiguration().getSideMarkerSeparatorColor());
      graphics.drawLine(getHorizontalSliceSize(i), 0, getHorizontalSliceSize(i), getSize().height);

    }

    // draw the last line
    graphics.drawLine(getSize().width, 0, getSize().width, getSize().height);
    graphics.translate(getModel().getItemCount() * getModel().getConfiguration().getHorizontalBoxSize(), 0);
    graphics.rotate(90f);

    // compute the text offset (to make the text centered)
    int offset = (getModel().getConfiguration().getHorizontalBoxSize() - getFontHeight()) / 2;

    boolean useShortendLabels = getModel().isUseShortendLabels();

    for (int i = 0; i < getModel().getItemCount(); i++) {

      graphics.setForegroundColor(getModel().getConfiguration().getSideMarkerTextColor());

      graphics.drawString(useShortendLabels ? getModel().getShortendLabels()[i] : getModel().getLabels()[i], new Point(
          10, (((getModel().getItemCount() - (i)) * getModel().getConfiguration().getHorizontalBoxSize()) - 22)
              - offset));
    }

    // restore the state
    graphics.popState();
  }

  /**
   * <p>
   * Resizes this side marker
   * </p>
   */
  @Override
  public void resetSize() {

    Dimension dimension = new Dimension(getModel().getConfiguration().getHorizontalBoxSize()
        * getModel().getItemCount(), getSize().height);

    // reset the size
    if (!getSize().equals(dimension)) {
      setSize(dimension);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onMark(int oldValue, int x) {

    // repaint the old section
    repaint(getHorizontalSliceSize(oldValue), 0, getHorizontalSliceSize(oldValue + 1)
        - getHorizontalSliceSize(oldValue) + 1, getSize().height);

    // repaint the new section
    repaint(getHorizontalSliceSize(x), 0, getHorizontalSliceSize(x + 1) - getHorizontalSliceSize(x) + 1,
        getSize().height);
  }
}