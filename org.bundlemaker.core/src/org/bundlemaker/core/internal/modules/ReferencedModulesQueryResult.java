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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bundlemaker.core.modules.IReferencedModulesQueryResult;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.ReferenceType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReferencedModulesQueryResult implements IReferencedModulesQueryResult {

  /** - */
  private Map<IReference, IModule>      _referencesToModulesMap;

  /** - */
  private Map<IReference, Set<IModule>> _referencesToAmbiguousModulesMap;

  /** - */
  private Set<IReference>               _unsatisfiedReferences;

  /** - */
  private IResourceModule               _selfModule;

  /** - */
  private Set<String>                   _unsatisfiedReferencedTypes;

  /** - */
  private Map<String, Set<IModule>>     _referencedTypesToAmbiguousModulesMap;

  /** - */
  private Set<IModule>                  _referencedModules;

  /**
   * <p>
   * Creates a new instance of type {@link ReferencedModulesQueryResult}.
   * </p>
   */
  public ReferencedModulesQueryResult(IResourceModule self) {

    //
    _referencesToModulesMap = new HashMap<IReference, IModule>();
    _referencesToAmbiguousModulesMap = new HashMap<IReference, Set<IModule>>();
    _unsatisfiedReferences = new HashSet<IReference>();

    //
    _selfModule = self;
  }

  /**
   * <p>
   * Creates a new instance of type {@link ReferencedModulesQueryResult}.
   * </p>
   */
  public ReferencedModulesQueryResult() {
    this(null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasErrors() {

    //
    return _unsatisfiedReferences.isEmpty() && _referencesToAmbiguousModulesMap.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<IReference, IModule> getReferencedModulesMap() {

    //
    return _referencesToModulesMap;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasReferencesWithAmbiguousModules() {

    //
    return !_referencesToAmbiguousModulesMap.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<IReference, Set<IModule>> getReferencesWithAmbiguousModules() {

    //
    return _referencesToAmbiguousModulesMap;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasUnsatisfiedReferences() {
    //
    return !_unsatisfiedReferences.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IReference> getUnsatisfiedReferences() {

    //
    return _unsatisfiedReferences;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IModule> getReferencedModules() {

    if (_referencedModules == null) {

      _referencedModules = new HashSet<IModule>();

      // step 2: add the type modules
      for (IModule iTypeModule : _referencesToModulesMap.values()) {
        if (!iTypeModule.equals(_selfModule)) {
          _referencedModules.add(iTypeModule);
        }
      }
    }

    //
    return _referencedModules;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Set<IModule>> getReferencedTypesWithAmbiguousModules() {

    if (_referencedTypesToAmbiguousModulesMap == null) {

      _referencedTypesToAmbiguousModulesMap = new HashMap<String, Set<IModule>>();

      // step 2: add the type modules
      for (Entry<IReference, Set<IModule>> entry : _referencesToAmbiguousModulesMap.entrySet()) {

        // only process type references
        if (entry.getKey().getReferenceType().equals(ReferenceType.TYPE_REFERENCE)) {

          // process
          for (IModule typeModule : entry.getValue()) {

            // ignore self modules
            if (!typeModule.equals(_selfModule)) {

              // create the type module set if necessary
              if (!_referencedTypesToAmbiguousModulesMap.containsKey(entry.getKey().getFullyQualifiedName())) {

                _referencedTypesToAmbiguousModulesMap.put(entry.getKey().getFullyQualifiedName(),
                    new HashSet<IModule>());
              }

              // add the type module
              _referencedTypesToAmbiguousModulesMap.get(entry.getKey().getFullyQualifiedName()).add(typeModule);
            }
          }
        }
      }
    }

    //
    return _referencedTypesToAmbiguousModulesMap;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> getUnsatisfiedReferencedTypes() {

    //
    if (_unsatisfiedReferencedTypes == null) {

      _unsatisfiedReferencedTypes = new HashSet<String>();

      // add the type modules
      for (IReference unsatisfiedReference : _unsatisfiedReferences) {

        // only process type references
        if (unsatisfiedReference.getReferenceType().equals(ReferenceType.TYPE_REFERENCE)) {

          if (!_unsatisfiedReferencedTypes.contains(unsatisfiedReference.getFullyQualifiedName())) {

            _unsatisfiedReferencedTypes.add(unsatisfiedReference.getFullyQualifiedName());
          }
        }

      }
    }
    // return the result
    return _unsatisfiedReferencedTypes;
  }
}
