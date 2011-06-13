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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * Defines the interface for module exporter. A module exporter can export a single {@link IResourceModule} to an
 * external format.
 * </p>
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModuleExporter {

  /**
   * <p>
   * Returns <code>true</code> if this {@link IModuleExporter} can export the specified {@link IResourceModule}.
   * </p>
   * 
   * @param modularizedSystem
   *          the {@link IModularizedSystem}
   * @param module
   *          the {@link IResourceModule}
   * @param context
   *          the {@link IModuleExporterContext}
   * 
   * @return <code>true</code> if this {@link IModuleExporter} can export the specified {@link IResourceModule}.
   */
  boolean canExport(IModularizedSystem modularizedSystem, IResourceModule module, IModuleExporterContext context);

  /**
   * <p>
   * Exports the specified {@link IResourceModule} to an external format.
   * </p>
   * 
   * @param modularizedSystem
   *          the {@link IModularizedSystem}
   * @param module
   *          the {@link IResourceModule}
   * @param context
   *          the {@link IModuleExporterContext}
   * @param progressMonitor
   *          the {@link IProgressMonitor} or null
   * 
   * @throws CoreException
   */
  void export(IModularizedSystem modularizedSystem, IResourceModule module, IModuleExporterContext context,
      IProgressMonitor progressMonitor) throws CoreException;
}
