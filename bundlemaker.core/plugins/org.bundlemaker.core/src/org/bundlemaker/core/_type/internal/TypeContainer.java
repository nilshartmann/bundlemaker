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
package org.bundlemaker.core._type.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core._type.IReference;
import org.bundlemaker.core._type.IType;
import org.bundlemaker.core._type.ITypeModule;
import org.bundlemaker.core._type.ITypeResource;
import org.bundlemaker.core.internal.modules.ChangeAction;
import org.bundlemaker.core.internal.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractCachingModularizedSystem;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeContainer implements ITypeModule {

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

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IReference> getReferences() {

    //
    Set<IReference> result = new HashSet<IReference>();

    // iterate over all resources
    for (IModuleResource resource : _module.getResources(ProjectContentType.BINARY)) {
      for (IReference reference : resource.adaptAs(ITypeResource.class).getReferences()) {
        result.add(reference);
      }
    }

    for (IModuleResource resource : _module.getResources(ProjectContentType.SOURCE)) {
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
