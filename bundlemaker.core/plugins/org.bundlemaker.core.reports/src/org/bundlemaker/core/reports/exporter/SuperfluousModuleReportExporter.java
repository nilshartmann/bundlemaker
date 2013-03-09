package org.bundlemaker.core.reports.exporter;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bundlemaker.core.exporter.IModularizedSystemExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.collections.GenericCache;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SuperfluousModuleReportExporter implements IModularizedSystemExporter {

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
    _fileWriter.append("Superfluous modules:\n");

    //
    GenericCache<IModule, List<IType>> cache = new GenericCache<IModule, List<IType>>() {
      @Override
      protected List<IType> create(IModule key) {
        return new LinkedList<IType>();
      }
    };

    //
    for (IResourceModule module : modularizedSystem.getResourceModules()) {

      //
      boolean superfluous = true;

      //
      for (IType type : module.getContainedTypes()) {

        //
        Set<IType> types = modularizedSystem.getTypes(type.getFullyQualifiedName());

        //
        if (types.size() <= 1) {
          superfluous = false;
          break;
        } else {
          for (IType iType : types) {

            //
            IModule containingModule = iType.getModule(modularizedSystem);

            if (!module.equals(containingModule)) {

              //
              cache.getOrCreate(containingModule).add(type);
            }
          }
        }
      }

      //
      if (superfluous) {

        //
        _fileWriter.append(" - " + module.getModuleIdentifier().toString() + "\n");

        //
        for (Entry<IModule, List<IType>> entry : cache.entrySet()) {

          //
          if (module.getContainedTypes().size() == entry.getValue().size()) {
            _fileWriter.append("   - completely covered by '" + entry.getKey().getModuleIdentifier().toString() + "\n");
          }
        }
      }
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
    return new File(context.getDestinationDirectory(), "superfluousModules.txt");
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
}
