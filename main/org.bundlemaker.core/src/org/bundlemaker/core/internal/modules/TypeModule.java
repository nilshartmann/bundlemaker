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
package org.bundlemaker.core.internal.modules;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ITypeContainer;
import org.bundlemaker.core.modules.modifiable.IModifiableModule;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeModule extends AbstractModule<ITypeContainer, TypeContainer> implements IModule, IModifiableModule {

  /**
   * <p>
   * Creates a new instance of type {@link TypeModule}.
   * </p>
   * 
   * @param moduleIdentifier
   */
  public TypeModule(IModuleIdentifier moduleIdentifier, IModularizedSystem modularizedSystem) {
    super(moduleIdentifier, modularizedSystem, new TypeContainer());
    getModifiableSelfResourceContainer().setModule(this);
  }
}
