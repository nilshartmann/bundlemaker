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

import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ITypeSelector;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IType;
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
  private ITypeSelector       _defaultTypeSelector;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractQueryableModularizedSystem}.
   * </p>
   * 
   * @param name
   * @param projectDescription
   */
  public AbstractQueryableModularizedSystem(String name, IBundleMakerProjectDescription projectDescription) {
    super(name, projectDescription);

    //
    _moduleSelectors = new LinkedList<ITypeSelector>();

    //
    _defaultTypeSelector = new DefaultTypeSelector(projectDescription);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ITypeSelector> getTypeSelectors() {
    return _moduleSelectors;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType getType(String fullyQualifiedName) throws AmbiguousElementException {
    return getType(fullyQualifiedName, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType getType(String fullyQualifiedName, IResourceModule referencingModule) throws AmbiguousElementException {

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
      if (referencingModule != null) {
        for (ITypeSelector moduleSelector : _moduleSelectors) {
          IType type = moduleSelector.selectType(referencingModule, fullyQualifiedName, types);
          if (type != null) {
            return type;
          }
        }
      }

      return _defaultTypeSelector.selectType(referencingModule, fullyQualifiedName, types);
      // throw new AmbiguousElementException(fullyQualifiedName);
    }

    // return the type
    return types.toArray(new IType[0])[0];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IType> getTypes(String fullyQualifiedName) {
    return getTypes(fullyQualifiedName, null);
  }

  @Override
  public Set<IType> getTypes(String fullyQualifiedName, IResourceModule referencingModule) {
    //
    Assert.isNotNull(fullyQualifiedName);
    Assert.isTrue(fullyQualifiedName.trim().length() > 0);

    // get type modules
    Set<IType> types = getTypeNameToTypeCache().get(fullyQualifiedName);
    types = types != null ? types : new HashSet<IType>();

    // return the result
    return Collections.unmodifiableSet(types);
  }

  @Override
  public IModule getTypeContainingModule(String fullyQualifiedName) throws AmbiguousElementException {
    // TODO: null is NOT ALLOWED HERE!!!
    return getTypeContainingModule(fullyQualifiedName, null);
  }

  @Override
  public IModule getTypeContainingModule(String fullyQualifiedName, IResourceModule referencingModule)
      throws AmbiguousElementException {

    //
    IType type = getType(fullyQualifiedName, referencingModule);

    //
    if (type == null) {
      return null;
    }

    return type.getModule(this);
  }

  @Override
  public Set<IModule> getTypeContainingModules(String fullyQualifiedName) {
    return getTypeContainingModules(fullyQualifiedName, null);
  }

  @Override
  public Set<IModule> getTypeContainingModules(String fullyQualifiedName, IResourceModule referencingModule) {
    //
    if (getTypeNameToTypeCache().containsKey(fullyQualifiedName)) {

      Set<IType> types = getTypeNameToTypeCache().get(fullyQualifiedName);

      Set<IModule> result = new HashSet<IModule>(types.size());

      for (IType type : types) {
        // TODO: direct call
        result.add(type.getModule(this));
      }

      //
      return Collections.unmodifiableSet(result);

    } else {
      return Collections.emptySet();
    }
  }

  /******************************************************/
  @Override
  public IModule getPackageContainingModule(String fullyQualifiedPackageName) throws AmbiguousElementException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<IModule> getPackageContainingModules(String fullyQualifiedPackageName) {
    // TODO Auto-generated method stub
    return null;
  }
}
