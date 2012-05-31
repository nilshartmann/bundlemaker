package org.bundlemaker.core.reports.exporter.html;

import java.io.File;
import java.io.FileOutputStream;

import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.IProgressMonitor;

public class HTMLReportExporter {

  public void export(IModularizedSystem modularizedSystem, File outputDirectory, IProgressMonitor progressMonitor,
      IQueryFilter<IModule> filter)
      throws Exception {

    //
    ModularizedSystemExporterAdapter exporterAdapter = new ModularizedSystemExporterAdapter(
        new DependenciesReportExporter());
    exporterAdapter.setModuleFilter(filter);
    exporterAdapter.export(modularizedSystem, outputDirectory, null);

    exporterAdapter = new ModularizedSystemExporterAdapter(
        new ModuleDependenciesReportExporter());
    exporterAdapter.setModuleFilter(filter);
    exporterAdapter.export(modularizedSystem, outputDirectory, null);

    exporterAdapter = new ModularizedSystemExporterAdapter(
        new ModuleContentReportExporter());
    exporterAdapter.setModuleFilter(filter);
    exporterAdapter.export(modularizedSystem, outputDirectory, null);

    //
    FileUtils.copy(getClass().getResourceAsStream("style.css"), new FileOutputStream(new File(outputDirectory,
        "style.css")), new byte[1024]);
  }
}
