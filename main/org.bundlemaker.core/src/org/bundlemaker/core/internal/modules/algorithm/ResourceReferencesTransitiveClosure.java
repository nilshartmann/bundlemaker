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

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * </p>
 */
public class ResourceReferencesTransitiveClosure extends AbstractResourceClosureQuery {

  /**
   * <p>
   * Creates a new instance of type {@link ResourceReferencesTransitiveClosure}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public ResourceReferencesTransitiveClosure(ModularizedSystem modularizedSystem) {
    super(modularizedSystem);
  }

  /**
   * <p>
   * </p>
   * 
   * @param typeName
   */
  public void resolveResource(IResource resource, ContentType contentType, IQueryFilter<IType> queryFilter) {

    //
    if (getResources().contains(resource)) {
      return;
    }

    getResources().add(resource);

    //
    for (IReference reference : getReferences(resource)) {

      //
      String typeName = reference.getFullyQualifiedName();

      Set<IType> types = getModularizedSystem().getTypeNameToTypeCache().get(typeName);

      if (types == null || types.isEmpty()) {
        System.out.println("NO TYPE FOR '" + typeName + "'.");
        return;
      }

      if (types.size() > 1) {
        System.out.println("MULTIPLE TYPES FOR '" + typeName + "'.");
      }

      //
      IType type = ((IType[]) types.toArray(new IType[0]))[0];

      //
      IResource res = contentType.equals(ContentType.SOURCE) ? type.getSourceResource() : type.getBinaryResource();

      if (res != null) {
        resolveResource(res, contentType, queryFilter);
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @return
   */
  private Set<IReference> getReferences(IResource resource) {
    //
    Set<IReference> result = new HashSet<IReference>();

    //
    if (resource.getReferences() != null) {
      result.addAll(resource.getReferences());
    }

    //
    for (IType type : resource.getContainedTypes()) {
      result.addAll(type.getReferences());
    }

    //
    return result;
  }
}
