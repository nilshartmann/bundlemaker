package org.bundlemaker.core.reports.exporter.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.exporter.IModuleExporter;
import org.bundlemaker.core.modules.IModule;
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

  /** - */
  private IRootArtifact _rootArtifact;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected IRootArtifact getCurrentRootArtifact() {
    return _rootArtifact;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doExport(IProgressMonitor progressMonitor) throws CoreException {

    //
    SubMonitor subMonitor = SubMonitor.convert(progressMonitor, 3);
    subMonitor.beginTask(null, 3);

    //
    _rootArtifact = getCurrentModularizedSystem().getArtifactModel(getModelConfiguration());

    try {

      //
      File file = new File(getCurrentContext().getDestinationDirectory(), getReportName() + ".html");

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
   * @return
   */
  protected abstract String getReportName();

  /**
   * <p>
   * </p>
   * 
   * @param bufferedWriter
   * @throws IOException
   */
  protected abstract void writeHtmlBody(BufferedWriter bw) throws IOException;

  private BufferedWriter getBufferedWriter(File file) throws IOException {
    FileWriter fw = new FileWriter(file);
    return new BufferedWriter(fw, 8192);
  }

  private void writeHtmlHead(BufferedWriter bw) throws IOException {
    bw.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\""
        + "\"http://www.w3.org/TR/html4/loose.dtd\">\n");
    bw.write("<html>\n");
    bw.write("<head>\n");
    bw.write("  <title>\n");
    bw.write(getReportName());
    bw.write("</title>\n");
    bw.write("  <meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">\n");
    bw.write("  <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">\n");
    bw.write("</head>\n");
  }

  protected IModuleArtifact getModuleArtifact(final IModule module) {

    final IModuleArtifact[] result = new IModuleArtifact[1];

    getCurrentRootArtifact().accept(new IAnalysisModelVisitor.Adapter() {

      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {
        if (moduleArtifact.getAssociatedModule().equals(module)) {
          result[0] = moduleArtifact;
        }
        return false;
      }
    });

    return result[0];
  }

  /**
   * <p>
   * </p>
   *
   * @return
   */
  protected IAnalysisModelConfiguration getModelConfiguration() {
    return IAnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION;
  }
}
