/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import org.eclipse.swt.layout.GridLayout;

/**
 * <p>
 * </p>
 * Taken from org.eclipse.pde.internal.ui.editor.FormLayoutFactory
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class FormLayoutUtils {

  public static final int FORM_BODY_MARGIN_HEIGHT      = 0;

  public static final int FORM_BODY_MARGIN_WIDTH       = 0;

  // FORM BODY
  public static final int FORM_BODY_MARGIN_TOP         = 12;

  public static final int FORM_BODY_MARGIN_BOTTOM      = 12;

  public static final int FORM_BODY_MARGIN_LEFT        = 6;

  public static final int FORM_BODY_MARGIN_RIGHT       = 6;

  public static final int FORM_BODY_HORIZONTAL_SPACING = 20;

  public static final int FORM_BODY_VERTICAL_SPACING   = 17;

  public static GridLayout createFormGridLayout(boolean makeColumnsEqualWidth, int numColumns) {
    GridLayout layout = new GridLayout();

    layout.marginHeight = FORM_BODY_MARGIN_HEIGHT;
    layout.marginWidth = FORM_BODY_MARGIN_WIDTH;

    layout.marginTop = FORM_BODY_MARGIN_TOP;
    layout.marginBottom = FORM_BODY_MARGIN_BOTTOM;
    layout.marginLeft = FORM_BODY_MARGIN_LEFT;
    layout.marginRight = FORM_BODY_MARGIN_RIGHT;

    layout.horizontalSpacing = FORM_BODY_HORIZONTAL_SPACING;
    layout.verticalSpacing = FORM_BODY_VERTICAL_SPACING;

    layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
    layout.numColumns = numColumns;

    return layout;
  }

}
