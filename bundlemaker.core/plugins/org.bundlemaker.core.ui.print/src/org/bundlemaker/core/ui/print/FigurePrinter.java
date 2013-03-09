package org.bundlemaker.core.ui.print;

import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

public class FigurePrinter {

  public static void save(IFigure figure) {

    try {

      Display display = PlatformUI.getWorkbench().getDisplay();
      FileDialog fd = new FileDialog(display.getActiveShell(), SWT.SAVE);
      fd.setText("Save");
      fd.setFilterPath("D:/");
      String[] filterExt = { "*.png" };
      fd.setFilterExtensions(filterExt);
      String selected = fd.open();

      if (selected == null) {
        return;
      }

      if (!selected.endsWith(".png")) {
        selected = selected.concat(".png");
      }

      Dimension size = figure.getPreferredSize(); 
      Image image = new Image(Display.getDefault(), size.width, size.height); 
      GC gc = new GC(image); 
      SWTGraphics graphics = new SWTGraphics(gc); 
      figure.paint(graphics); 

      ImageLoader loader = new ImageLoader(); 
      loader.data = new ImageData[] {image.getImageData()}; 
      loader.save(new FileOutputStream(selected), SWT.IMAGE_PNG); 
      

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
   
  }
}
