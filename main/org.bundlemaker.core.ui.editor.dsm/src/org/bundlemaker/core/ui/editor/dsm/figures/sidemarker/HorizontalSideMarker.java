package org.bundlemaker.core.ui.editor.dsm.figures.sidemarker;

import org.bundlemaker.core.ui.editor.dsm.IDsmViewModel;
import org.eclipse.draw2d.FigureUtilities;
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

  private boolean _rotateText = true;

  /**
   * <p>
   * Creates a new instance of type {@link HorizontalSideMarker}.
   * </p>
   * 
   * @param model
   */
  public HorizontalSideMarker(IDsmViewModel model) {
    super(model);
  }

  /**
   * <p>
   * </p>
   * 
   * @param rotateText
   *          the rotateText to set
   */
  public final void setRotateText(boolean rotateText) {
    _rotateText = rotateText;
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

      //
      boolean isInCycle = getModel().isInCycle(i);

      // draw the "even" marker
      if (isInCycle || getMarkedItem() == i || i % 2 == 0) {

        // set the background
        if (i == getMarkedItem()) {
          if (isInCycle) {
            graphics.setBackgroundColor(getModel().getConfiguration().getCycleSideMarkerMarkedColor());
          } else {
            graphics.setBackgroundColor(getModel().getConfiguration().getSideMarkerMarkedColor());
          }
        } else {
          if (isInCycle) {
            graphics.setBackgroundColor(getModel().getConfiguration().getCycleSideMarkerColor());
          } else {
            graphics.setBackgroundColor(getModel().getConfiguration().getSideMarkerEvenColor());
          }
        }

        // draw the background
        graphics.fillRectangle(getHorizontalSliceSize(i), 0, getHorizontalSliceSize(i + 1) - getHorizontalSliceSize(i)
            + 1, getSize().height + 1);
      }

      if (isInCycle && getModel().isInCycle(i - 1)) {
        graphics.setForegroundColor(getModel().getConfiguration().getCycleSideMarkerSeparatorColor());
      } else {
        // draw the separator lines
        graphics.setForegroundColor(getModel().getConfiguration().getSideMarkerSeparatorColor());
      }
      graphics.drawLine(getHorizontalSliceSize(i), 0, getHorizontalSliceSize(i), getSize().height);

    }

    // draw the last line
    graphics.drawLine(getSize().width, 0, getSize().width, getSize().height);

    boolean useShortendLabels = getModel().isUseShortendLabels();

    // rotate
    if (_rotateText) {

      graphics.translate(getModel().getItemCount() * getModel().getConfiguration().getHorizontalBoxSize(), 0);
      graphics.rotate(90f);
      // compute the text offset (to make the text centered)
      int offset = (getModel().getConfiguration().getHorizontalBoxSize() - getFontHeight()) / 2;
      for (int i = 0; i < getModel().getItemCount(); i++) {

        graphics.setForegroundColor(getModel().getConfiguration().getSideMarkerTextColor());

        graphics.drawString(useShortendLabels ? getModel().getShortendLabels()[i] : getModel().getLabels()[i],
            new Point(10, (((getModel().getItemCount() - (i + 1)) * getModel().getConfiguration()
                .getHorizontalBoxSize())) + offset));
      }
    }

    // don't rotate
    else {
      graphics.setForegroundColor(getModel().getConfiguration().getSideMarkerTextColor());

      int centerOffset = (getModel().getConfiguration().getHorizontalBoxSize() / 2);
      int fontHeight = getFont().getFontData()[0].getHeight() + 2;
      int horizontalBoxSize = getModel().getConfiguration().getHorizontalBoxSize();
      for (int i = 0; i < getModel().getItemCount(); i++) {
        String label = useShortendLabels ? getModel().getShortendLabels()[i] : getModel().getLabels()[i];
        int offset = i * horizontalBoxSize + centerOffset;
        for (int j = 0; j < label.length(); j++) {
          String currentChar = label.substring(j, j + 1);
          graphics.drawString(currentChar, offset - (FigureUtilities.getTextWidth(currentChar, getFont()) / 2), j
              * fontHeight);
        }
      }
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