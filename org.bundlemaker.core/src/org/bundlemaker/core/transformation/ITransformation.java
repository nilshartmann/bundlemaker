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
package org.bundlemaker.core.transformation;

import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ITransformation {

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @param monitor
   *          the progress monitor to use for reporting progress to the user. It is the caller's responsibility to call
   *          done() on the given monitor. Accepts <code>null</code>, indicating that no progress should be reported and
   *          that the operation cannot be canceled.
   */
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor);
}
