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
package org.bundlemaker.core.internal.modules.modularizedsystem;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core._type.IReference;
import org.bundlemaker.core._type.IType;
import org.bundlemaker.core._type.ITypeModule;
import org.bundlemaker.core._type.ITypeSelector;
import org.bundlemaker.core.project.IProjectDescription;
import org.bundlemaker.core.resource.IModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractQueryableModularizedSystem extends AbstractCachingModularizedSystem {

  /** - */
  private List<ITypeSelector> _moduleSelectors;

  /** - */
  private DefaultTypeSelector _defaultTypeSelector;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractQueryableModularizedSystem}.
   * </p>
   * 
   * @param name
   * @param projectDescription
   */
  public AbstractQueryableModularizedSystem(String name, IProjectDescription projectDescription) {
    super(name, projectDescription);

    //
    _moduleSelectors = new LinkedList<ITypeSelector>();

    //
    _defaultTypeSelector = new DefaultTypeSelector(projectDescription);
    // _defaultTypeSelector.setPreferJdkTypes(true);
  }

  /**
   * {@inheritDoc}
   */
  public List<ITypeSelector> getTypeSelectors() {
    return _moduleSelectors;
  }

  /**
   * {@inheritDoc}
   */
  public IType getType(String fullyQualifiedName) {
    return getType(fullyQualifiedName, null);
  }

  /**
   * {@inheritDoc}
   */
  public IType getType(String fullyQualifiedName, IModule referencingModule) {

    // assert
    Assert.isNotNull(fullyQualifiedName);

    // get type modules
    Set<IType> types = getTypeNameToTypeCache().get(fullyQualifiedName);

    // return null if type is unknown
    if (types == null || types.isEmpty()) {
      return null;
    }

    // if multiple type modules exist, throw an exception
    if (types.size() > 1) {

      //
      for (ITypeSelector moduleSelector : _moduleSelectors) {
        IType type = moduleSelector.selectType(referencingModule, fullyQualifiedName, types);
        if (type != null) {
          return type;
        }
      }

      return _defaultTypeSelector.selectType(referencingModule, fullyQualifiedName, types);
      // throw new AmbiguousElementException(fullyQualifiedName);
    }

    // return the type
    return types.toArray(new IType[0])[0];
  }

  public Set<IType> getTypes() {
    return Collections.unmodifiableSet(getTypeToModuleCache().keySet());
  }

  /**
   * {@inheritDoc}
   */
  public Set<IType> getTypes(String fullyQualifiedName) {
    return getTypes(fullyQualifiedName, null);
  }

  public Set<IType> getTypes(String fullyQualifiedName, IModule referencingModule) {
    //
    Assert.isNotNull(fullyQualifiedName);
    Assert.isTrue(fullyQualifiedName.trim().length() > 0);

    // get type modules
    Set<IType> types = getTypeNameToTypeCache().get(fullyQualifiedName);
    types = types != null ? types : new HashSet<IType>();

    // return the result
    return Collections.unmodifiableSet(types);
  }

  public Set<IReference> getUnsatisfiedReferences(IModule resourceModule) {

    //
    Set<IReference> result = new HashSet<IReference>();

    //
    Set<IReference> references = resourceModule.adaptAs(ITypeModule.class)
        .getReferences();

    for (IReference iReference : references) {
      if (getType(iReference.getFullyQualifiedName(), resourceModule) == null) {
        result.add(iReference);
      }
    }

    //
    return result;
  }
}
