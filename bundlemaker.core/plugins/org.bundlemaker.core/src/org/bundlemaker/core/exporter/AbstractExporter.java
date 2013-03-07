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

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;

/**
 * <p>
 * Abstract base class for all exporters.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractExporter implements IModuleExporter {

  /** the modularized system */
  private IModularizedSystem     _currentModularizedSystem;

  /** the current module */
  private IResourceModule        _currentModule;

  /** the current context */
  private IModuleExporterContext _currentContext;

  /**
   * <p>
   * Returns the current {@link IModularizedSystem}.
   * </p>
   * 
   * @return the current {@link IModularizedSystem}
   */
  protected IModularizedSystem getCurrentModularizedSystem() {
    return _currentModularizedSystem;
  }

  /**
   * <p>
   * Returns the current {@link IResourceModule}.
   * </p>
   * 
   * @return the current {@link IResourceModule}
   */
  protected IResourceModule getCurrentModule() {
    return _currentModule;
  }

  /**
   * <p>
   * Returns the current {@link IModuleExporterContext}.
   * </p>
   * 
   * @return the current {@link IModuleExporterContext}
   */
  protected IModuleExporterContext getCurrentContext() {
    return _currentContext;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canExport(IModularizedSystem modularizedSystem, IResourceModule module, IModuleExporterContext context) {

    //
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void export(IModularizedSystem modularizedSystem, IResourceModule module,
      IModuleExporterContext context, IProgressMonitor progressMonitor) throws CoreException {

    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(module);
    Assert.isNotNull(context);

    // set attributes
    _currentModularizedSystem = modularizedSystem;
    _currentContext = context;
    _currentModule = module;
    SubMonitor subMonitor = SubMonitor.convert(progressMonitor, "Exporting " + module.getModuleIdentifier().toString(),
        100);
    subMonitor.subTask("Exporting " + module.getModuleIdentifier());

    // pre export
    preExportModule();
    subMonitor.worked(10);

    // export
    checkIfCanceled(subMonitor);

    doExport(subMonitor.newChild(80));

    // post export
    checkIfCanceled(subMonitor);
    postExportModule();
    subMonitor.worked(10);
  }

  /**
   * <p>
   * This method is called <i>before</i> the module is exported.
   * </p>
   * 
   * @throws CoreException
   */
  protected void preExportModule() throws CoreException {
    // empty implementation
  }

  /**
   * <p>
   * </p>
   * 
   * @param progressMonitor
   *          A progress monitor or null. The caller is responsible for calling done on the progress monitor instance
   * @throws CoreException
   */
  protected void doExport(IProgressMonitor progressMonitor) throws CoreException {
    // empty implementation
  }

  /**
   * <p>
   * This method is called <i>after</i> the module is exported.
   * </p>
   * 
   * @throws CoreException
   */
  protected void postExportModule() throws CoreException {
    // empty implementation
  }

  /**
   * <p>
   * Throws an {@link OperationCanceledException} if the underlying {@link IProgressMonitor} has been canceled.
   * </p>
   * 
   * @param monitor
   *          the monitor
   * @throws OperationCanceledException
   */
  static void checkIfCanceled(IProgressMonitor monitor) throws OperationCanceledException {
    if (monitor != null && monitor.isCanceled()) {
      throw new OperationCanceledException();
    }
  }
}
