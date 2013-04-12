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

import org.bundlemaker.core.internal.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.modules.modifiable.IModifiableTypeContainer;
import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractCachingModularizedSystem;
import org.bundlemaker.core.internal.modules.query.IQueryFilter;
import org.bundlemaker.core.modules.ChangeAction;
import org.bundlemaker.core.modules.IModule;
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
   * {@inheritDoc}
   */
  @Override
  public boolean containsType(String fullyQualifiedName) {
    return _containedTypes.containsKey(fullyQualifiedName);
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
  public void add(IType type) {

    if (!type.isLocalOrAnonymousType()) {

      //
      _containedTypes.put(type.getFullyQualifiedName(), type);

      // notify
      if (getModule().hasModularizedSystem()) {
        ((IModifiableModularizedSystem) getModule().getModularizedSystem()).typeChanged(type, getModule(),
            ChangeAction.ADDED);
      }
    }
  }

  public void remove(IType type) {

    //
    if (_containedTypes.containsKey(type.getFullyQualifiedName())) {

      //
      _containedTypes.remove(type.getFullyQualifiedName());

      // notify
      if (getModule().hasModularizedSystem()) {
        ((AbstractCachingModularizedSystem) getModule().getModularizedSystem()).typeChanged(type, getModule(),
            ChangeAction.REMOVED);
      }
    }
  }

  public void setModule(IModule module) {
    _module = module;
  }
}
