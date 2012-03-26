package org.bundlemaker.core.ui.dsmview.utils;

import org.bundlemaker.core.ui.dsmview.AbstractDsmViewModel;
import org.bundlemaker.core.ui.dsmview.DsmViewComposite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {

  public static void main(String args[]) {

    Display d = new Display();
    final Shell shell = new Shell(d);
    shell.setSize(800, 800);
    shell.setLayout(new BorderLayout());

    final AbstractDsmViewModel model = new DemoDsmViewModel(1500);
    final DsmViewComposite viewComposite = new DsmViewComposite(shell, model);

    // final DsmViewWidget viewWidget = new DsmViewWidget(model, shell);
    // viewWidget.setLayoutData(BorderData.CENTER);
    // viewWidget.setZoom((50 + 10) * 0.02f);
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
}
