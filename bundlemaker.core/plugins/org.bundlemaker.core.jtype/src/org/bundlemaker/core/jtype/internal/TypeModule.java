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
package org.bundlemaker.core.jtype.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.jtype.IReference;
import org.bundlemaker.core.jtype.IType;
import org.bundlemaker.core.jtype.ITypeModule;
import org.bundlemaker.core.jtype.ITypeResource;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeModule implements ITypeModule {

  /** the contained type names */
  private Map<String, IType> _containedTypes;

  /** back-reference: the containing module */
  private IModule            _module;

  /**
   * <p>
   * Creates a new instance of type {@link TypeModule}.
   * </p>
   */
  public TypeModule() {

    // create the contained types sets
    _containedTypes = new HashMap<String, IType>();
  }

  public TypeModule(IModule module) {

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
  public IType getType(String fullyQualifiedName) {
    //
    return _containedTypes.get(fullyQualifiedName);
  }

  /**
   * {@inheritDoc}
   */
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
  public Collection<IType> getContainedTypes() {

    // return an unmodifiable copy
    return Collections.unmodifiableCollection(_containedTypes.values());
  }

  /**
   * {@inheritDoc}
   */
  public Set<String> getContainedTypeNames() {

    // return an unmodifiable copy
    return Collections.unmodifiableSet(_containedTypes.keySet());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(IType type) {

    if (!type.isLocalOrAnonymousType()) {

      //
      _containedTypes.put(type.getFullyQualifiedName(), type);

      // // notify
      // if (getModule().hasModularizedSystem()) {
      // getModule().getModularizedSystem().adaptAs(ITypeModularizedSystem.class).typeChanged(type, getModule(),
      // ChangeAction.ADDED);
      // }
    }
  }

  @Override
  public void remove(IType type) {

    //
    if (_containedTypes.containsKey(type.getFullyQualifiedName())) {

      //
      _containedTypes.remove(type.getFullyQualifiedName());

      // // notify
      // if (getModule().hasModularizedSystem()) {
      // getModule().getModularizedSystem().adaptAs(ITypeModularizedSystem.class).typeChanged(type, getModule(),
      // ChangeAction.REMOVED);
      // }
    }
  }

  public void setModule(IModule module) {
    _module = module;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IReference> getReferences() {

    //
    Set<IReference> result = new HashSet<IReference>();

    // iterate over all resources
    for (IModuleResource resource : _module.getResources(ResourceType.BINARY)) {
      for (IReference reference : resource.adaptAs(ITypeResource.class).getReferences()) {
        result.add(reference);
      }
    }

    for (IModuleResource resource : _module.getResources(ResourceType.SOURCE)) {
      for (IReference reference : resource.adaptAs(ITypeResource.class).getReferences()) {
        result.add(reference);
      }
    }

    //
    for (IType type : getContainedTypes()) {
      for (IReference reference : type.getReferences()) {
        result.add(reference);
      }
    }

    // return result
    return result;
  }
}
