package org.bundlemaker.analysis.ui.dsmview.utils;

import org.bundlemaker.analysis.ui.dsmview.AbstractDsmViewModel;
import org.bundlemaker.analysis.ui.dsmview.DefaultDsmViewConfiguration;
import org.bundlemaker.analysis.ui.dsmview.DsmViewWidget;
import org.bundlemaker.analysis.ui.dsmview.IDsmViewConfiguration;
import org.bundlemaker.analysis.ui.dsmview.figures.IMatrixListener;
import org.bundlemaker.analysis.ui.dsmview.figures.MatrixEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Main {

  public static void main(String args[]) {

    //
    final String[][] values = new String[][] { { "1", "12", "1", "12" }, { "3", "56", "1", "12" },
        { "3", "56", "1", "12" }, { "3", "56", "1", "12" } };

    final String[] labels = new String[] { "1234567890123456789012345678901234567890Test_1", "Test_2", "Test_3",
        "Test_4" };

    Display d = new Display();
    final Shell shell = new Shell(d);
    shell.setSize(800, 800);
    shell.setLayout(new BorderLayout());

    final AbstractDsmViewModel model = new AbstractDsmViewModel() {

      @Override
      public String getToolTip(int x, int y) {
        return "same";
      }

      @Override
      protected String[][] createValues() {
        return values;
      }

      @Override
      protected String[] createLabels() {
        return labels;
      }

      @Override
      protected IDsmViewConfiguration createConfiguration() {
        return new DefaultDsmViewConfiguration();
      }
    };

    final DsmViewWidget viewWidget = new DsmViewWidget(model, shell);
    viewWidget.setLayoutData(BorderData.CENTER);
    viewWidget.setZoom((50 + 10) * 0.02f);

    Composite composite = new Composite(shell, 0);
    composite.setLayoutData(BorderData.SOUTH);
    FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
    fillLayout.marginHeight = 5;
    fillLayout.marginWidth = 5;
    fillLayout.spacing = 1;

    composite.setLayout(fillLayout);

    final Label label1 = new Label(composite, SWT.SHADOW_NONE);
    label1.setText("Label 123");

    final Label label2 = new Label(composite, SWT.SHADOW_NONE);
    label2.setText("Label 123");

    viewWidget.addMatrixListener(new IMatrixListener.Adapter() {
      @Override
      public void marked(MatrixEvent event) {
        label1.setText(event.getX() <= labels.length && event.getX() >= 0 ? labels[event.getX()] : "");
        label2.setText(event.getY() <= labels.length && event.getY() >= 0 ? labels[event.getY()] : "");
      }
    });

    shell.open();

    while (!shell.isDisposed())
      while (!d.readAndDispatch())
        d.sleep();
  }
}
