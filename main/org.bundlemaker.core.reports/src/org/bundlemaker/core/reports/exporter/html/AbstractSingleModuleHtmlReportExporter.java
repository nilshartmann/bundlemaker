package org.bundlemaker.core.reports.exporter.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.exporter.IModuleExporter;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractSingleModuleHtmlReportExporter extends AbstractExporter implements IModuleExporter {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doExport(IProgressMonitor progressMonitor) throws CoreException {

    //
    SubMonitor subMonitor = SubMonitor.convert(progressMonitor, 3);
    subMonitor.beginTask(null, 3);

    try {

      //
      File file = new File(getCurrentContext().getDestinationDirectory(), getCurrentModule().getModuleIdentifier()
          .toString() + ".html");

      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }

      //
      BufferedWriter bufferedWriter = getBufferedWriter(file);

      writeHtmlHead(bufferedWriter);
      writeHtmlBody(bufferedWriter);

      bufferedWriter.flush();
      bufferedWriter.close();
      subMonitor.worked(2);

    } catch (Exception e) {
      // TODO
      e.printStackTrace();
      throw new CoreException(new Status(IStatus.ERROR, "", ""));
    }

  }

  /**
   * <p>
   * </p>
   * 
   * @param bufferedWriter
   * @throws IOException
   */
  private void writeHtmlBody(BufferedWriter bw) throws IOException {

    bw.write("<ul>\n");

    //
    for (IResource resource : getCurrentModule().getResources(ContentType.BINARY)) {
      bw.write("<li>" + resource.getPath() + "</li>\n");
    }

    bw.write("</ul>\n");
  }

  private BufferedWriter getBufferedWriter(File file) throws IOException {
    FileWriter fw = new FileWriter(file);
    return new BufferedWriter(fw, 8192);
  }

  private void writeHtmlHead(BufferedWriter bw) throws IOException {
    bw.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\""
        + "\"http://www.w3.org/TR/html4/loose.dtd\">\n");
    bw.write("<html>\n");
    bw.write("<head>\n");
    bw.write("  <title>HALLO</title>\n");
    bw.write("  <meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">\n");
    bw.write("  <link rel=\"stylesheet\" type=\"text/css\" href=\"../style.css\">\n");
    bw.write("</head>\n");
  }
}
