package org.bundlemaker.core.ui.print;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import nl.utwente.ce.imagexport.export.svg.ExportSVG;

import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.eclipse.draw2d.IFigure;

public class FigurePrinter {

  public static void print(String fileName, IFigure figure) {
    ExportSVG exportSVG = new ExportSVG();
    try {
      exportSVG.exportImage(fileName, figure);
    } catch (SVGGraphics2DIOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    try {
      // Create a JPEG transcoder
      JPEGTranscoder t = new JPEGTranscoder();
      // Set the transcoding hints.
      t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
      t.addTranscodingHint(JPEGTranscoder.KEY_WIDTH, new Float(figure.getSize().width));
      t.addTranscodingHint(JPEGTranscoder.KEY_HEIGHT, new Float(figure.getSize().height));

      // Create the transcoder input.
      String svgURI = new File("D:/temp/svg.svg").toURI().toString();
      TranscoderInput input = new TranscoderInput(svgURI);

      // Create the transcoder output.
      OutputStream ostream = new FileOutputStream("D:/temp/out.jpg");
      TranscoderOutput output = new TranscoderOutput(ostream);

      // Save the image.
      t.transcode(input, output);

      // Flush and close the stream.
      ostream.flush();
      ostream.close();

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
