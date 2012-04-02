package org.bundlemaker.core.ui.editor.dsm;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class DsmDetailComposite extends Composite {

  /** - */
  private Label  _fromLabel;

  /** - */
  private Label  _toLabel;

  /** - */
  private Label  _selectionCountLabel;

  /** - */
  private Button _visualizeChildren;

  /** - */
  private int    _x_selected = -1;

  /** - */
  private int    _y_selected = -1;

  /**
   * <p>
   * Creates a new instance of type {@link DsmDetailComposite}.
   * </p>
   * 
   * @param parent
   * @param viewModel
   */
  public DsmDetailComposite(Composite parent, DsmViewWidget viewWidget) {
    super(parent, 0);

    //
    Assert.isNotNull(viewWidget);

    // this.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
    this.setLayout(new FillLayout());

    Composite composite = new Composite(this, SWT.NONE);
    GridLayout gridLayout = new GridLayout(2, false);
    gridLayout.marginHeight = 0;
    gridLayout.marginWidth = 0;
    composite.setLayout(gridLayout);

    //
    _selectionCountLabel = new Label(composite, SWT.TRAIL);
    GridDataFactory.swtDefaults().grab(false, false).align(SWT.FILL, SWT.FILL).applyTo(_selectionCountLabel);
    Label theLabel = new Label(composite, SWT.LEAD);
    theLabel.setText("dependencies");
    GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(theLabel);

    _fromLabel = createFieldWithLabel(composite, "from");
    _toLabel = createFieldWithLabel(composite, "to");

    composite = new Composite(this, SWT.NONE);
    gridLayout = new GridLayout(1, false);
    gridLayout.marginHeight = 0;
    gridLayout.marginWidth = 0;
    composite.setLayout(gridLayout);
    _visualizeChildren = new Button(composite, SWT.CHECK);
    _visualizeChildren.setText("Visualize children of selected artifacts");

  }

  /**
   * <p>
   * </p>
   * 
   * @return the fromLabel
   */
  protected final Label getFromLabel() {
    return _fromLabel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return the toLabel
   */
  protected final Label getToLabel() {
    return _toLabel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return the selectionCountLabel
   */
  protected final Label getSelectionCountLabel() {
    return _selectionCountLabel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return the visualizeChildren
   */
  public final Button getVisualizeChildrenButton() {
    return _visualizeChildren;
  }

  /**
   * <p>
   * </p>
   * 
   * @param label
   * @return
   */
  private Label createFieldWithLabel(Composite parent, String label) {

    //
    Label theLabel = new Label(parent, SWT.TRAIL);
    theLabel.setText(label);
    GridDataFactory.swtDefaults().grab(false, false).align(SWT.FILL, SWT.FILL).applyTo(theLabel);

    //
    Label result = new Label(parent, SWT.LEAD);
    GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(result);

    //
    return result;
  }
}
