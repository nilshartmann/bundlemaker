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
package org.bundlemaker.core._type.modules;

import java.util.Set;

import org.bundlemaker.core._type.IType;
import org.bundlemaker.core.modules.IModule;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ITypeSelector {

  /**
   * <p>
   * Selects a {@link IType} for the given {@link IResourceModule} from the set of possible types. The set of possible
   * types contains all types in the system with the given fully qualified name.
   * </p>
   * 
   * @param module
   *          the module to select the type for
   * @param fullyQualifiedName
   *          the fully qualified name of the type to select
   * @param types
   *          the set of all implementations of the specified type
   * @return the selected type
   */
  IType selectType(IModule module, String fullyQualifiedName, Set<IType> types);
}
