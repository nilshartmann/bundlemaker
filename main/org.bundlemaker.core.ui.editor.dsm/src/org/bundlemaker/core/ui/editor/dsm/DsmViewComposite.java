package org.bundlemaker.core.ui.editor.dsm;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DsmViewComposite extends Composite {

  /** - */
  private DsmViewWidget      _viewWidget;

  /** - */
  private DsmDetailComposite _detailComposite;

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

    //
    GridLayout gridLayout = new GridLayout(1, true);
    setLayout(gridLayout);

    //
    _viewWidget = new DsmViewWidget(viewModel, this);
    GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(_viewWidget);
    _viewWidget.setZoom((50 + 10) * 0.02f);

    _detailComposite = new DsmDetailComposite(this, _viewWidget);
    GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(_detailComposite);
  }
  
  /**
   * <p>
   * </p>
   *
   * @return
   */
  public final Button getVisualizeChildrenButton() {
    return _detailComposite.getVisualizeChildren();
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
