package org.bundlemaker.core.ui.dsmview;

import org.bundlemaker.core.ui.dsmview.figures.IMatrixListener;
import org.bundlemaker.core.ui.dsmview.figures.MatrixEvent;
import org.bundlemaker.core.ui.dsmview.utils.BorderData;
import org.bundlemaker.core.ui.dsmview.utils.BorderLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DsmViewComposite extends Composite {

  /** - */
  private DsmViewWidget _viewWidget;

  /** - */
  private Label         _fromLabel;

  /** - */
  private Label         _toLabel;

  /**
   * <p>
   * Creates a new instance of type {@link DsmViewComposite}.
   * </p>
   * 
   * @param parent
   * @param style
   */
  public DsmViewComposite(Composite parent, IDsmViewModel viewModel) {
    super(parent, 0);

    setLayout(new BorderLayout());

    _viewWidget = new DsmViewWidget(viewModel, this);
    _viewWidget.setLayoutData(BorderData.CENTER);
    _viewWidget.setZoom((50 + 10) * 0.02f);

    Composite composite = new Composite(this, 0);
    composite.setLayoutData(BorderData.SOUTH);
    FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
    fillLayout.marginHeight = 5;
    fillLayout.marginWidth = 5;
    fillLayout.spacing = 1;

    composite.setLayout(fillLayout);

    _fromLabel = new Label(composite, SWT.NONE);
    _toLabel = new Label(composite, SWT.NONE);

    _viewWidget.addMatrixListener(new IMatrixListener.Adapter() {
      @Override
      public void marked(MatrixEvent event) {
        _fromLabel.setText(event.getY() <= _viewWidget.getModel().getLabels().length && event.getY() >= 0 ? _viewWidget
            .getModel().getLabels()[event.getY()] : "");
        _toLabel.setText(event.getX() <= _viewWidget.getModel().getLabels().length && event.getX() >= 0 ? _viewWidget
            .getModel().getLabels()[event.getX()] : "");
      }
    });
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

  public void setModel(DsmViewModel tableModel) {
    _viewWidget.setModel(tableModel);
  }
}
