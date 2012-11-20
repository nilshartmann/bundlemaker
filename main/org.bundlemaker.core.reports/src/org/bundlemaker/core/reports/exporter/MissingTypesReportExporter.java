package org.bundlemaker.core.reports.exporter;

import java.io.File;
import java.io.FileWriter;

import org.bundlemaker.core.analysis.ArtifactHelper;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.exporter.IModularizedSystemExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MissingTypesReportExporter implements IModularizedSystemExporter {

  /** - */
  private File       _resultFile;

  /** - */
  private FileWriter _fileWriter;

  /**
   * <p>
   * </p>
   * 
   * @param resultFile
   */
  public void setResultFile(File resultFile) {
    _resultFile = resultFile;
  }

  /**
   * {@inheritDoc}
   */
  /**
   * {@inheritDoc}
   */
  @Override
  public void export(IModularizedSystem modularizedSystem, IModuleExporterContext context,
      IProgressMonitor progressMonitor) throws Exception {

    //
    context.getDestinationDirectory().mkdirs();

    //
    if (_resultFile == null) {
      _resultFile = createResultFile(context);
    }

    //
    _fileWriter = new FileWriter(_resultFile);

    //
    _fileWriter.append("Report\n");
    _fileWriter.append("------------------------------------------------------\n");
    _fileWriter.append("\n");
    _fileWriter.append("Missing types:\n");

    //
    IRootArtifact rootArtifact = modularizedSystem
        .getAnalysisModel(IAnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);
    IModuleArtifact moduleArtifact = ArtifactHelper.findChild(rootArtifact, "<< Missing Types >>", IModuleArtifact.class);

    //
    String dump = ArtifactUtils.artifactToString(moduleArtifact);

    //
    _fileWriter.append(dump);

    //
    _fileWriter.append("\n");

    //
    _fileWriter.flush();
    _fileWriter.close();
  }

  /**
   * <p>
   * </p>
   * 
   * @param context
   * @return
   */
  protected File createResultFile(IModuleExporterContext context) {
    return new File(context.getDestinationDirectory(), "missingTypes.txt");
  }
}
