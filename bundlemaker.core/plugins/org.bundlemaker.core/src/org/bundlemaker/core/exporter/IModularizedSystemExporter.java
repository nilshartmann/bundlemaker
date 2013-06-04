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

import org.bundlemaker.core.resource.IModularizedSystem;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * Common interface for all exporters that export an entire {@link IModularizedSystem}.
 * </p>
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModularizedSystemExporter {

  /**
   * <p>
   * Exports the specified {@link IModularizedSystem}.
   * </p>
   * 
   * @param modularizedSystem
   *          the system to export.
   * @param context
   *          the context
   * @param progressMonitor
   *          the IProgressMonitor or null
   * @throws Exception
   */
  void export(IModularizedSystem modularizedSystem, IModuleExporterContext context, IProgressMonitor progressMonitor)
      throws Exception;
}
