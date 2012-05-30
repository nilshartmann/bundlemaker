package org.bundlemaker.core.reports.exporter.html;

import java.io.File;

import org.bundlemaker.core.exporter.IModularizedSystemExporter2;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.eclipse.core.runtime.IProgressMonitor;

public class HTMLReportExporter implements IModularizedSystemExporter2 {

  @Override
  public void export(IModularizedSystem modularizedSystem, File outputDirectory, IProgressMonitor progressMonitor)
      throws Exception {

    //
    new ModularizedSystemExporterAdapter(
        new DependenciesReportExporter()).export(modularizedSystem, outputDirectory, null);

    //
    new ModularizedSystemExporterAdapter(
        new ModuleDependenciesReportExporter()).export(modularizedSystem, outputDirectory, null);

    //
    new ModularizedSystemExporterAdapter(
        new ModuleContentReportExporter()).export(modularizedSystem, outputDirectory, null);
  }
}
