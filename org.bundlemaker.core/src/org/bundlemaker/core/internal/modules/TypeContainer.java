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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractCachingModularizedSystem;
import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractCachingModularizedSystem.ChangeAction;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.modifiable.IModifiableTypeContainer;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.modules.query.StringQueryFilters;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeContainer implements IModifiableTypeContainer {

  /** the contained type names */
  private Map<String, IType> _containedTypes;

  /** back-reference: the containing module */
  private IModule            _module;

  /**
   * <p>
   * Creates a new instance of type {@link TypeContainer}.
   * </p>
   */
  public TypeContainer() {

    // create the contained types sets
    _containedTypes = new HashMap<String, IType>();
  }

  public TypeContainer(IModule module) {

    Assert.isNotNull(module);

    _module = module;

    // create the contained types sets
    _containedTypes = new HashMap<String, IType>();
  }

  @Override
  public IModule getModule() {
    return _module;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType getType(String fullyQualifiedName) {

    //
    return _containedTypes.get(fullyQualifiedName);
  }

  /**
   * <p>
   * </p>
   * 
   * @param typeNames
   * @return
   */
  public boolean containsAll(Set<String> typeNames) {

    try {
      for (String typeName : typeNames) {
        if (getType(typeName) == null) {
          return false;
        }
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    //
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IType> getContainedTypes() {

    // return an unmodifiable copy
    return Collections.unmodifiableCollection(_containedTypes.values());
  }

  @Override
  public Collection<IType> getContainedTypes(IQueryFilter<IType> filter) {

    // assert
    Assert.isNotNull(filter);

    // create the result
    Set<IType> result = new HashSet<IType>();

    //
    for (IType containedType : _containedTypes.values()) {

      if (!result.contains(containedType) && filter.matches(containedType)) {

        // add the result
        result.add(containedType);
      }
    }

    // return result
    return Collections.unmodifiableSet(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> getContainedTypeNames() {

    // return an unmodifiable copy
    return Collections.unmodifiableSet(_containedTypes.keySet());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> getContainedTypeNames(IQueryFilter filter) {

    // assert
    Assert.isNotNull(filter);

    // create the result
    Set<String> result = new HashSet<String>();

    //
    for (String containedType : _containedTypes.keySet()) {

      if (!result.contains(containedType) && filter.matches(containedType)) {

        // add the result
        result.add(containedType);
      }
    }

    // return result
    return Collections.unmodifiableSet(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> getContainedPackageNames() {

    return getContainedPackageNames(StringQueryFilters.TRUE_QUERY_FILTER);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> getContainedPackageNames(IQueryFilter filter) {
    Assert.isNotNull(filter);

    // create the result
    Set<String> result = new HashSet<String>();

    //
    for (String containedType : _containedTypes.keySet()) {

      //
      String packageName = "";

      //
      if (containedType.indexOf('.') != -1) {

        // get the packageName
        packageName = containedType.substring(0, containedType.lastIndexOf('.'));

        //
        if (!result.contains(packageName) && filter.matches(packageName)) {
          result.add(packageName);
        }
      }
    }

    // return result
    return Collections.unmodifiableSet(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(IType type) {

    if (!type.isLocalOrAnonymousType()) {

      //
      _containedTypes.put(type.getFullyQualifiedName(), type);
      // notify
      if (getModule().hasModularizedSystem()) {
        ((AbstractCachingModularizedSystem) getModule().getModularizedSystem()).typeChanged(type, getModule(),
            ChangeAction.ADDED);
      }
    }
  }

  @Override
  public void remove(IType type) {
    _containedTypes.remove(type.getFullyQualifiedName());

    // notify
    if (getModule().hasModularizedSystem()) {
      ((AbstractCachingModularizedSystem) getModule().getModularizedSystem()).typeChanged(type, getModule(),
          ChangeAction.REMOVED);
    }
  }

  public void setModule(IModule module) {
    _module = module;
  }
}
