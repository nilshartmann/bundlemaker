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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractTransformationAwareModularizedSystem;
import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ITypeContainer;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.event.ModuleClassificationChangedEvent;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Abstract base class for all modules.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <I>
 * @param <T>
 */
public abstract class AbstractModule<I extends ITypeContainer, T extends I> implements IModule {

  /** the module identifier */
  private IModuleIdentifier   _moduleIdentifier;

  /** the classification */
  private Group               _classification;

  /** the user attributes */
  private Map<String, Object> _userAttributes;

  /** the self container */
  private T                   _selfContainer;

  /** the embedded container */
  private Map<String, T>      _embeddedContainers;

  /** the modularized system the module belongs to */
  private IModularizedSystem  _modularizedSystem;

  /** specified whether or not the module is attached to a modularized system */
  private boolean             _isDetached;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractModule}.
   * </p>
   * 
   * @param moduleIdentifier
   * @param modularizedSystem
   *          TODO
   * @param selfContainer
   */
  public AbstractModule(IModuleIdentifier moduleIdentifier, IModularizedSystem modularizedSystem, T selfContainer) {
    Assert.isNotNull(moduleIdentifier);
    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(selfContainer);

    // set the parameters
    _moduleIdentifier = moduleIdentifier;
    _selfContainer = selfContainer;
    _modularizedSystem = modularizedSystem;

    // create the hash map
    _userAttributes = new HashMap<String, Object>();
    _embeddedContainers = new HashMap<String, T>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModule getModule() {
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModuleIdentifier getModuleIdentifier() {
    return _moduleIdentifier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getClassification() {
    return _classification != null ? _classification.getPath() : null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasClassification() {
    return _classification != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModularizedSystem getModularizedSystem() {
    return _isDetached ? null : _modularizedSystem;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasModularizedSystem() {
    return !_isDetached;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> getUserAttributes() {
    return _userAttributes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> getContainedTypeNames() {
    return getContainedTypeNames(new IQueryFilter<String>() {
      @Override
      public boolean matches(String content) {
        return true;
      }
    });
  }

  public Group getClassificationGroup() {
    return _classification;
  }

  public final void setModuleIdentifier(String name, String version) {
    Assert.isNotNull(name);
    Assert.isNotNull(version);

    setModuleIdentifier(new ModuleIdentifier(name, version));
  }

  public final void setModuleIdentifier(IModuleIdentifier moduleIdentifier) {
    Assert.isNotNull(moduleIdentifier);

    _moduleIdentifier = moduleIdentifier;

    //
    if (hasModularizedSystem()) {
      ((ModularizedSystem) getModularizedSystem()).fireModuleIdentifierChanged(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
  public Set<String> getContainedTypeNames(final IQueryFilter<String> filter) {

    // create the result set
    final Set<String> result = new HashSet<String>();

    //
    doWithAllContainers(new ContainerClosure<T>() {
      @Override
      public boolean doWithContainer(T resourceContainer) {
        result.addAll(resourceContainer.getContainedTypeNames(filter));
        return false;
      }
    });

    // return the result
    return Collections.unmodifiableSet(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType getType(final String fullyQualifiedName) {

    // create the result set
    final IType[] result = new IType[1];

    //
    doWithAllContainers(new ContainerClosure<T>() {
      @Override
      public boolean doWithContainer(T resourceContainer) {

        result[0] = resourceContainer.getType(fullyQualifiedName);

        return result[0] == null;
      }
    });

    // return the result
    return result[0];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsType(final String fullyQualifiedName) {

    //
    final boolean[] result = new boolean[] { false };

    //
    doWithAllContainers(new ContainerClosure<T>() {
      @Override
      public boolean doWithContainer(T resourceContainer) {
        if (resourceContainer.containsType(fullyQualifiedName)) {
          result[0] = true;
          return false;
        }
        return true;
      }
    });

    //
    return result[0];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IType> getContainedTypes() {
    return getContainedTypes(new IQueryFilter<IType>() {
      @Override
      public boolean matches(IType type) {
        return true;
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IType> getContainedTypes(final IQueryFilter<IType> filter) {

    // create the result set
    final Set<IType> result = new HashSet<IType>();

    //
    doWithAllContainers(new ContainerClosure<T>() {
      @Override
      public boolean doWithContainer(T resourceContainer) {
        result.addAll(resourceContainer.getContainedTypes(filter));
        return false;
      }
    });

    // return the result
    return Collections.unmodifiableSet(result);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public T getModifiableSelfResourceContainer() {
    return _selfContainer;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Map<String, T> getEmbeddedContainers() {
    return _embeddedContainers;
  }

  /**
   * <p>
   * </p>
   * 
   * @param classificationPath
   */
  public void setClassification(IPath classificationPath) {

    //
    if (classificationPath == null || classificationPath.isEmpty()) {
      _classification = null;
    }

    //
    else {

      _classification = ((AbstractTransformationAwareModularizedSystem) getModularizedSystem())
          .getOrCreateGroup(classificationPath);
    }

    //
    if (hasModularizedSystem()) {
      ((ModularizedSystem) getModularizedSystem())
          .fireModuleClassificationChanged(new ModuleClassificationChangedEvent(this));
    }
  }

  /**
   * <p>
   * </p>
   * 
   */
  public void detach() {
    _isDetached = true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   */
  public void attach(IModularizedSystem modularizedSystem) {
    Assert.isNotNull(modularizedSystem);
    Assert.isTrue(modularizedSystem.equals(_modularizedSystem),
        "You can only add a module to the modularized system you specified when creating the module.");

    //
    _isDetached = false;
  }

  /**
   * <p>
   * </p>
   * 
   * @param closure
   */
  protected void doWithAllContainers(ContainerClosure<T> closure) {

    // do with the 'self' container
    if (closure.doWithContainer(getModifiableSelfResourceContainer())) {
      return;
    }

    // do with the embedded containers
    for (T embeddedContainer : _embeddedContainers.values()) {

      //
      if (closure.doWithContainer(embeddedContainer)) {
        return;
      }
    }
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + " [_moduleIdentifier=" + _moduleIdentifier + ", _classification="
        + _classification + "]";
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  protected interface ContainerClosure<T> {

    /**
     * <p>
     * </p>
     * 
     * @param container
     */
    boolean doWithContainer(T container);
  }
}
