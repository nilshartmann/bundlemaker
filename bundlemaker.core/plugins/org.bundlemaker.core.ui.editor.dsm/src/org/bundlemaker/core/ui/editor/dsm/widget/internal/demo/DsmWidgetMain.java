package org.bundlemaker.core.ui.editor.dsm.widget.internal.demo;

import org.bundlemaker.core.ui.editor.dsm.widget.DsmViewWidget;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DsmWidgetMain {

  /**
   * <p>
   * </p>
   * 
   * @param args
   */
  public static void main(String[] args) {

    Display display = new Display();

    final Shell shell = new Shell(display);
    shell.setLayout(new FillLayout());
    shell.setSize(500, 500);

    DsmViewWidget dsmViewWidget = new DsmViewWidget(new DemoDsmViewModel(10), null, null, shell);
    dsmViewWidget.setSize(500, 500);

    shell.open();

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }

    display.dispose();
  }
}
