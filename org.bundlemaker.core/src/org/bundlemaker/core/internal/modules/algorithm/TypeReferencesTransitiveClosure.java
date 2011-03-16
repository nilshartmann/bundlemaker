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
package org.bundlemaker.core.internal.modules.algorithm;

import java.util.Set;

import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * </p>
 */
public class TypeReferencesTransitiveClosure extends AbstractTypeClosureQuery {

  /**
   * <p>
   * Creates a new instance of type {@link TypeReferencesTransitiveClosure}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public TypeReferencesTransitiveClosure(ModularizedSystem modularizedSystem) {
    super(modularizedSystem);
  }

  /**
   * <p>
   * </p>
   * 
   * @param typeName
   */
  public void resolveType(String typeName, IQueryFilter<IType> queryFilter) {

    System.out.println(String.format("Resolving type '%s'.", typeName));

    Set<IType> types = getModularizedSystem().getTypeNameToTypeCache().get(typeName);

    if (types == null || types.isEmpty()) {
      System.out.println("NO TYPE FOR '" + typeName + "'.");
      return;
    }

    if (types.size() > 1) {
      System.out.println("MULTIPLE TYPE FOR '" + typeName + "'.");
    }

    IType type = ((IType[]) types.toArray(new IType[0]))[0];
    getTypesMap().put(type.getFullyQualifiedName(), type);

    for (IReference reference : type.getReferences()) {
      if (!getTypesMap().containsKey(reference.getFullyQualifiedName())) {
        resolveType(reference.getFullyQualifiedName(), queryFilter);
      }
    }
  }
}
