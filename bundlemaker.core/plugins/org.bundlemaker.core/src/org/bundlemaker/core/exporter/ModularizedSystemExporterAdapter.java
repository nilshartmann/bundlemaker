/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.exporter;

import java.io.File;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystemExporterAdapter implements IModularizedSystemExporter, IModularizedSystemExporter2 {

  /** - */
  private IModuleExporter        _moduleExporter;

  /** - */
  private IModularizedSystem     _currentModularizedSystem;

  /** - */
  private IResourceModule        _currentModule;

  /** - */
  private IModuleExporterContext _currentContext;

  /** - */
  private IQueryFilter<IModule>  _moduleFilter;

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
  public void setModuleFilter(IQueryFilter<IModule> moduleFilter) {
    _moduleFilter = moduleFilter;
  }

  @Override
  public void export(IModularizedSystem modularizedSystem, File outputDirectory, IProgressMonitor progressMonitor)
      throws Exception {

    IModuleExporterContext ctx = new DefaultModuleExporterContext(modularizedSystem.getBundleMakerProject(),
        outputDirectory, modularizedSystem);

    export(modularizedSystem, ctx, progressMonitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void export(IModularizedSystem modularizedSystem, IModuleExporterContext context,
      IProgressMonitor mainMonitor) throws Exception {

    _currentModularizedSystem = modularizedSystem;
    _currentContext = context;
    _currentContext = preExportModules();

    // Create SubMonitor
    int modulesToExportCount = countModulesToExport();
    SubMonitor subMonitor = SubMonitor.convert(mainMonitor, "Exporting " + modularizedSystem.getName(),
        modulesToExportCount);

    try {
      // simply call export() for each contained
      int counter = 0;
      for (IResourceModule resourceModule : _currentModularizedSystem.getResourceModules()) {

        if (_moduleFilter == null || _moduleFilter.matches(resourceModule)) {

          //
          _currentModule = resourceModule;
          counter++;

          //
          preExportModule();

          try {
            // export if possible
            if (_moduleExporter.canExport(_currentModularizedSystem, _currentModule, _currentContext)) {
              _moduleExporter
                  .export(_currentModularizedSystem, _currentModule, _currentContext, subMonitor.newChild(1));
            } else {
              handleNonExportableModule();
            }
          } catch (OperationCanceledException ex) {
            // Operation has been canceled (by user)
            return;
          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            new SimpleReportExporter().export(modularizedSystem, _currentModule, context, subMonitor.newChild(1));
          }
          postExportModule();

        }
      }

      if (subMonitor.isCanceled()) {
        return;
      }
      postExportModules();
    } finally {

      // close the ProgressMonitor
      if (mainMonitor != null) {
        mainMonitor.done();
      }
    }
  }

  /**
   * @return
   */
  private int countModulesToExport() {
    int modulesToExport = 0;
    for (IResourceModule resourceModule : _currentModularizedSystem.getResourceModules()) {

      if (_moduleFilter == null || _moduleFilter.matches(resourceModule)) {
        modulesToExport++;
      }
    }
    return modulesToExport;
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
