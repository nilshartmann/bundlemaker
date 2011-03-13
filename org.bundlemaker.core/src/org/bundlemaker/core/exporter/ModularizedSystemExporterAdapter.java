package org.bundlemaker.core.exporter;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystemExporterAdapter implements IModularizedSystemExporter {

  /** - */
  private IModuleExporter               _moduleExporter;

  /** - */
  private IModularizedSystem            _currentModularizedSystem;

  /** - */
  private IResourceModule               _currentModule;

  /** - */
  private IModuleExporterContext        _currentContext;

  /** - */
  private IQueryFilter<IResourceModule> _moduleFilter;

  /**
   * <p>
   * Creates a new instance of type {@link ModularizedSystemExporterAdapter}.
   * </p>
   * 
   * @param moduleExporter
   */
  public ModularizedSystemExporterAdapter(IModuleExporter moduleExporter) {
    Assert.isNotNull(moduleExporter);

    _moduleExporter = moduleExporter;
  }

  /**
   * <p>
   * </p>
   * 
   * @param moduleFilter
   */
  public void setModuleFilter(IQueryFilter<IResourceModule> moduleFilter) {
    _moduleFilter = moduleFilter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void export(IModularizedSystem modularizedSystem, IModuleExporterContext context) throws Exception {

    _currentModularizedSystem = modularizedSystem;
    _currentContext = context;
    _currentContext = preExportModules();

    // simply call export() for each contained
    for (IResourceModule resourceModule : _currentModularizedSystem.getResourceModules()) {

      if (_moduleFilter == null || _moduleFilter.matches(resourceModule)) {

        //
        _currentModule = resourceModule;

        //
        preExportModule();

        try {

          // export if possible
          if (_moduleExporter.canExport(_currentModularizedSystem, _currentModule, _currentContext)) {
            _moduleExporter.export(_currentModularizedSystem, _currentModule, _currentContext);
          } else {
            handleNonExportableModule();
          }

        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          new SimpleReportExporter().export(modularizedSystem, _currentModule, context);
        }

        //
        postExportModule();
      }
    }

    postExportModules();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IModularizedSystem getCurrentModularizedSystem() {
    return _currentModularizedSystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IResourceModule getCurrentModule() {
    return _currentModule;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IModuleExporterContext getCurrentContext() {
    return _currentContext;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IModuleExporter getModuleExporter() {
    return _moduleExporter;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws Exception
   */
  protected IModuleExporterContext preExportModules() throws Exception {
    return _currentContext;
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  protected void postExportModules() throws Exception {
  }

  /**
   * <p>
   * </p>
   */
  protected void handleNonExportableModule() {
  }

  /**
   * <p>
   * </p>
   */
  protected void preExportModule() {
  }

  /**
   * <p>
   * </p>
   */
  protected void postExportModule() {
  }
}
