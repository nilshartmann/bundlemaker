package org.bundlemaker.core.ui.print;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.bundlemaker.core.ui.print.internal.nl.utwente.ce.imagexport.export.svg.ExportSVG;
import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.SWT;
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
      String[] filterExt = { "*.jpg" };
      fd.setFilterExtensions(filterExt);
      String selected = fd.open();

      if (selected == null) {
        return;
      }

      if (!selected.endsWith(".jpg")) {
        selected = selected.concat(".jpg");
      }

      ExportSVG exportSVG = new ExportSVG();
      File tmpFile = exportSVG.exportImage(figure);

      JPEGTranscoder t = new JPEGTranscoder();
      t.addTranscodingHint(JPEGTranscoder.KEY_WIDTH, new Float(figure.getSize().width));
      t.addTranscodingHint(JPEGTranscoder.KEY_HEIGHT, new Float(figure.getSize().height));
      t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(1.0));

      // Create the transcoder input.
      TranscoderInput input = new TranscoderInput(tmpFile.toURI().toString());

      // Create the transcoder output.
      OutputStream ostream = new FileOutputStream(selected);
      TranscoderOutput output = new TranscoderOutput(ostream);

      // Save the image.
      t.transcode(input, output);

      // Flush and close the stream.
      ostream.flush();
      ostream.close();

    } catch (SVGGraphics2DIOException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (TranscoderException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // // Create a JPEG transcoder
    // JPEGTranscoder t = new JPEGTranscoder();
    // // Set the transcoding hints.
    // t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
    // t.addTranscodingHint(JPEGTranscoder.KEY_WIDTH, new Float(figure.getSize().width));
    // t.addTranscodingHint(JPEGTranscoder.KEY_HEIGHT, new Float(figure.getSize().height));

    // IFigure rootFigure = _dsmViewWidget._matrixFigure;
    // Rectangle rootFigureBounds = rootFigure.getBounds();
    //
    // /*
    // * 2. Now we want to get the GC associated with the control on which all figures are painted by SWTGraphics. For
    // * that first get the SWT Control associated with the viewer on which the rooteditpart is set as contents
    // */
    // GC figureCanvasGC = new GC(_dsmViewWidget);
    //
    // /* 3. Create a new Graphics for an Image onto which we want to paint rootFigure */
    // Image img = new Image(null, rootFigureBounds.width, rootFigureBounds.height);
    // GC imageGC = new GC(img);
    // imageGC.setBackground(figureCanvasGC.getBackground());
    // imageGC.setForeground(figureCanvasGC.getForeground());
    // imageGC.setFont(figureCanvasGC.getFont());
    // imageGC.setLineStyle(figureCanvasGC.getLineStyle());
    // imageGC.setLineWidth(figureCanvasGC.getLineWidth());
    // imageGC.setXORMode(figureCanvasGC.getXORMode());
    // Graphics imgGraphics = new SWTGraphics(imageGC);
    //
    // /* 4. Draw rootFigure onto image. After that image will be ready for save */
    // rootFigure.paint(imgGraphics);
    //
    // /* 5. Save image */
    // ImageData[] imgData = new ImageData[1];
    // imgData[0] = img.getImageData();
    //
    // ImageLoader imgLoader = new ImageLoader();
    // imgLoader.data = imgData;
    // imgLoader.save("d:/temp/test.png", SWT.IMAGE_PNG);
    //
    // /* release OS resources */
    // figureCanvasGC.dispose();
    // imageGC.dispose();
    // img.dispose();

    // PrinterData[] printerData = Printer.getPrinterList();
    // if (printerData == null) {
    // System.out.println("Warning: No default printer.");
    // return;
    // }
    //
    // PrinterData data = null;
    // for (PrinterData printerData1 : printerData) {
    // if (printerData1.name.equals("FreePDF")) {
    // data = printerData1;
    // }
    // }
    //
    // data.orientation = PrinterData.LANDSCAPE;
    // Printer printer = new Printer(data);
    //
    // PrintFigureOperation printFigureOperation = new PrintFigureOperation(printer, _dsmViewWidget._matrixFigure);
    // printFigureOperation.setPrintMode(PrintFigureOperation.FIT_PAGE);
    // printFigureOperation.run("BundleMaker");
  }
}
