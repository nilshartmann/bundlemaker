package org.bundlemaker.core.reports.exporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.exporter.IModularizedSystemExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.reports.visitors.DuplicatePackagesVisitor;
import org.bundlemaker.core.reports.visitors.DuplicateTypesVisitor;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DuplicateTypesReportExporter implements IModularizedSystemExporter {

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
  @Override
  public void export(IModularizedSystem modularizedSystem, IModuleExporterContext context,
      IProgressMonitor progressMonitor) throws Exception {

    //
    IRootArtifact rootArtifact = modularizedSystem.getArtifactModel(
        ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION).getRoot();

    //
    DuplicateTypesVisitor duplicateTypesVisitor = createDuplicateTypesVisitor();
    DuplicatePackagesVisitor duplicatePackagesVisitor = createDuplicatePackagesVisitor();
    rootArtifact.accept(duplicateTypesVisitor, duplicatePackagesVisitor);

    context.getDestinationDirectory().mkdirs();
    if (_resultFile == null) {
      _resultFile = createResultFile(context);
    }
    _fileWriter = new FileWriter(_resultFile);

    //
    _fileWriter.append("Report\n");
    _fileWriter.append("------------------------------------------------------\n");
    _fileWriter.append("\n");

    //
    _fileWriter.append("Duplicate packages:\n");

    //
    List<String> keys = new LinkedList<String>(duplicatePackagesVisitor.getDuplicatePackages().keySet());
    Collections.sort(keys);

    //
    for (String key : keys) {
      dumpDuplicatePackages(_fileWriter, key, duplicatePackagesVisitor.getDuplicatePackages().get(key));
    }

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
    return new File(context.getDestinationDirectory(), "report.txt");
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected DuplicatePackagesVisitor createDuplicatePackagesVisitor() {
    return new DuplicatePackagesVisitor();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected DuplicateTypesVisitor createDuplicateTypesVisitor() {
    return new DuplicateTypesVisitor();
  }

  /**
   * <p>
   * </p>
   * 
   * @param fileWriter
   * @param entry
   * @throws IOException
   */
  protected void dumpDuplicatePackages(FileWriter fileWriter, String packageName,
      Collection<IPackageArtifact> packageArtifacts) throws IOException {

    _fileWriter.append("\n");

    // step 1: dump the package and the module names (e.g. com.example.mypackage [test_1.2.3, test2_1.0.0])
    _fileWriter.append(String.format(" -**- %s [", packageName));
    for (Iterator<IPackageArtifact> iterator = packageArtifacts.iterator(); iterator.hasNext();) {
      IPackageArtifact packageArtifact = iterator.next();
      IModuleArtifact moduleArtifact = (IModuleArtifact) packageArtifact.getParent(ArtifactType.Module);
      _fileWriter.append(moduleArtifact.getName());
      if (iterator.hasNext()) {
        _fileWriter.append(", ");
      }
    }
    _fileWriter.append("]\n");

    boolean identical = true;

    // step 2: dump "exclusive"
    for (IPackageArtifact packageArtifact : packageArtifacts) {

      IModuleArtifact moduleArtifact = (IModuleArtifact) packageArtifact.getParent(ArtifactType.Module);
      List<IResourceArtifact> exclusiveResources = new LinkedList<IResourceArtifact>();

      //
      for (IBundleMakerArtifact child : packageArtifact.getChildren()) {
        if (child.getType().equals(ArtifactType.Resource)) {

          //
          IResourceArtifact resourceArtifact = (IResourceArtifact) child;

          //
          if (!containedInAllPackages(resourceArtifact, packageArtifacts)) {
            exclusiveResources.add(resourceArtifact);
          }
        }
      }

      //
      if (!exclusiveResources.isEmpty()) {

        identical = false;

        Collections.sort(exclusiveResources);

        _fileWriter.append("      - Only in " + moduleArtifact.getQualifiedName() + " : ");
        for (IResourceArtifact exclusiveResource : exclusiveResources) {
          _fileWriter.append(exclusiveResource + " ");
        }
        _fileWriter.append("\n");
      }
    }

    //
    if (identical) {
      _fileWriter.append("      Identical\n");
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceArtifact
   * @param packageArtifacts
   * @return
   */
  private boolean containedInAllPackages(IResourceArtifact resourceArtifact,
      Collection<IPackageArtifact> packageArtifacts) {

    //
    for (IPackageArtifact packageArtifact : packageArtifacts) {
      if (!packageArtifact.hasChild(resourceArtifact.getName())) {
        System.out.println(resourceArtifact.getName());
        return false;
      }
    }

    //
    return true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param level
   * @param stringBuilder
   * @throws IOException
   */
  private void dumpPackage(String prefix, IArtifact artifact) throws IOException {

    //
    _fileWriter.append(prefix);
    // _fileWriter.append(artifact.getType().toString());
    // _fileWriter.append(" : ");
    _fileWriter.append(artifact.getQualifiedName());
    _fileWriter.append("\n");

    //
    List<IArtifact> sorted = new ArrayList<IArtifact>(artifact.getChildren());
    Collections.sort(sorted, new Comparator<IArtifact>() {
      @Override
      public int compare(IArtifact o1, IArtifact o2) {
        return o1.getQualifiedName().compareTo(o2.getQualifiedName());
      }
    });

    //
    for (IArtifact child : sorted) {
      if (child.getType().equals(ArtifactType.Resource)
      // || child.getType().equals(ArtifactType.Group)
      // || child.getType().equals(ArtifactType.Module)
      ) {
        dumpPackage("  " + prefix, child);
      }
    }
  }
}
