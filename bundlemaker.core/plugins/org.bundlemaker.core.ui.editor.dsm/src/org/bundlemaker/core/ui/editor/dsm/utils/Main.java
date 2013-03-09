package org.bundlemaker.core.ui.editor.dsm.utils;

import org.bundlemaker.core.ui.editor.dsm.AbstractDsmViewModel;
import org.bundlemaker.core.ui.editor.dsm.DsmDetailComposite;
import org.bundlemaker.core.ui.editor.dsm.DsmViewWidget;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {

  public static void main(String args[]) {

    Display d = new Display();
    final Shell shell = new Shell(d);
    shell.setSize(800, 800);
    shell.setLayout(new BorderLayout());
    Canvas canvas = new Canvas(shell, SWT.NONE);
    draw(canvas, new DemoDsmViewModel(500));
    
    // LightweightSystem lws = new LightweightSystem(shell);
   // lws.setControl(canvas);
    
    
    // final DsmViewComposite viewComposite = new DsmViewComposite(shell, model);

//     final DsmViewWidget viewWidget = new DsmViewWidget(model, shell);
//     viewWidget.setLayoutData(BorderData.CENTER);
//     viewWidget.setZoom((50 + 10) * 0.02f);
    //
    // Composite composite = new Composite(shell, 0);
    // composite.setLayoutData(BorderData.SOUTH);
    // FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
    // fillLayout.marginHeight = 5;
    // fillLayout.marginWidth = 5;
    // fillLayout.spacing = 1;
    //
    // composite.setLayout(fillLayout);
    //
    // final Label label1 = new Label(composite, SWT.SHADOW_NONE);
    // label1.setText("Label 123");
    //
    // final Label label2 = new Label(composite, SWT.SHADOW_NONE);
    // label2.setText("Label 123");
    //
    // viewWidget.addMatrixListener(new IMatrixListener.Adapter() {
    // @Override
    // public void marked(MatrixEvent event) {
    // label1.setText(event.getX() <= labels.length && event.getX() >= 0 ? labels[event.getX()] : "");
    // label2.setText(event.getY() <= labels.length && event.getY() >= 0 ? labels[event.getY()] : "");
    // }
    // });

    shell.open();

    while (!shell.isDisposed())
      while (!d.readAndDispatch())
        d.sleep();
  }
  
  static private void draw(Canvas parent, AbstractDsmViewModel model) {
    //
    GridLayout gridLayout = new GridLayout(1, true);
    parent.setLayout(gridLayout);

    //
    DsmViewWidget _viewWidget = new DsmViewWidget(model, parent);
    GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(_viewWidget);
    _viewWidget.setZoom((50 + 10) * 0.02f);

    //
    DsmDetailComposite _detailComposite = new DsmDetailComposite(parent, _viewWidget);
    GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(_detailComposite);
    // setDefaultDependencyDescription();

//    //
//    _viewWidget.addMatrixListener(new IMatrixListener.Adapter() {
//
//      @Override
//      public void marked(MatrixEvent event) {
//
//        //
//        if (isCellSelected(event)) {
//          _detailComposite.getSelectionCountLabel().setText(
//              getNullSafeString(_viewWidget.getModel().getValues()[event.getX()][event.getY()], "0"));
//          _detailComposite.getFromLabel().setText(_viewWidget.getModel().getLabels()[event.getY()]);
//          _detailComposite.getToLabel().setText(_viewWidget.getModel().getLabels()[event.getX()]);
//        }
//
//        //
//        else if (_selectedCell != null) {
//          _detailComposite.getSelectionCountLabel().setText(
//              getNullSafeString(_viewWidget.getModel().getValues()[_selectedCell.getX()][_selectedCell.getY()], "0"));
//          _detailComposite.getFromLabel().setText(_viewWidget.getModel().getLabels()[_selectedCell.getY()]);
//          _detailComposite.getToLabel().setText(_viewWidget.getModel().getLabels()[_selectedCell.getX()]);
//        }
//
//        //
//        else {
//          setDefaultDependencyDescription();
//        }
      }

//      /**
//       * {@inheritDoc}
//       */
//      @Override
//      public void singleClick(MatrixEvent event) {
//        if (isCellSelected(event)) {
//          _selectedCell = event;
//        }
//      }
//    });
//
//    //
//    _detailComposite.getVisualizeChildrenButton().addSelectionListener(new SelectionAdapter() {
//      @Override
//      public void widgetSelected(SelectionEvent e) {
//        Selection.instance().getArtifactSelectionService()
//            .setUseChildrenOfSelectedArtifacts(_detailComposite.getVisualizeChildrenButton().getSelection());
//      }
//    });
//  }
}
