package org.bundlemaker.core.ui.editor.dsm;

import org.bundlemaker.core.ui.editor.dsm.figures.IMatrixListener;
import org.bundlemaker.core.ui.editor.dsm.figures.MatrixEvent;
import org.bundlemaker.core.ui.selection.Selection;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class DsmDetailComposite extends Composite {

  /** - */
  private DsmViewWidget _viewWidget;

  /** - */
  private Label         _fromLabel;

  /** - */
  private Label         _toLabel;

  /** - */
  private Label         _selectionCountLabel;

  /** - */
  private Button        _visualizeChildren;

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

    _viewWidget = viewWidget;

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
    _visualizeChildren.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        Selection.instance().getArtifactSelectionService()
            .setUseChildrenOfSelectedArtifacts(_visualizeChildren.getSelection());
      }
    });

    _viewWidget.addMatrixListener(new IMatrixListener.Adapter() {

      @Override
      public void marked(MatrixEvent event) {

        //
        String[] labels = _viewWidget.getModel().getLabels();
        boolean xSelected = event.getX() <= labels.length && event.getX() >= 0;
        boolean ySelected = event.getY() <= labels.length && event.getY() >= 0;

        String selectionCount = ySelected && xSelected
            && _viewWidget.getModel().getValues()[event.getX()][event.getY()] != null ? _viewWidget.getModel()
            .getValues()[event.getX()][event.getY()] : "0";
        String from = ySelected ? labels[event.getY()] : "-";
        String to = xSelected ? labels[event.getX()] : "-";

        _selectionCountLabel.setText(selectionCount);
        _fromLabel.setText(from);
        _toLabel.setText(to);
      }
    });
  }

  /**
   * <p>
   * </p>
   * 
   * @return the visualizeChildren
   */
  public final Button getVisualizeChildren() {
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

  /**
   * <p>
   * </p>
   * 
   * @return the viewWidget
   */
  public DsmViewWidget getViewWidget() {
    return _viewWidget;
  }
}
