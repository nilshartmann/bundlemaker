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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.internal.api.resource.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.api.resource.IModifiableModule;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.internal.modules.event.ModularizedSystemChangedListenerList;
import org.bundlemaker.core.internal.resource.ModuleIdentifier;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleAwareBundleMakerProject;
import org.bundlemaker.core.resource.IModuleIdentifier;
import org.bundlemaker.core.resource.ITransformation;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Abstract base class for {@link IModularizedSystem IModularizedSystems}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractModularizedSystem implements IModifiableModularizedSystem {

  /** the name of working copy */
  private String                               _name;

  /** the user attributes */
  private Map<String, Object>                  _userAttributes;

  /** the project description */
  private IModuleAwareBundleMakerProject       _project;

  /** the defined resource modules */
  private List<IModifiableModule>              _modules;

  /** the execution environment type module */
  private IModule                              _executionEnvironment;

  /** the list of defined transformations */
  private List<ITransformation>                _transformations;

  /** - */
  private Set<Group>                           _groups;

  /** the listener list used for transformations */
  private ModularizedSystemChangedListenerList _listenerList;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractModularizedSystem}.
   * </p>
   * 
   * @param name
   * @param projectDescription
   */
  public AbstractModularizedSystem(String name, IModuleAwareBundleMakerProject project) {

    Assert.isNotNull(name);
    Assert.isNotNull(project);

    // set the name
    _name = name;

    // set the project description
    _project = project;

    // initialize fields
    _userAttributes = new HashMap<String, Object>();
    _transformations = new ArrayList<ITransformation>();
    _modules = new LinkedList<IModifiableModule>();
    _groups = new HashSet<Group>();

    //
    _listenerList = new ModularizedSystemChangedListenerList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getName() {
    return _name;
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
  public IModuleAwareBundleMakerProject getBundleMakerProject() {
    return _project;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final List<ITransformation> getTransformations() {
    return Collections.unmodifiableList(_transformations);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final List<ITransformation> getModifiableTransformationList() {
    return _transformations;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final IModule getExecutionEnvironment() {
    return _executionEnvironment;
  }

  /**
   * {@inheritDoc}
   */
  public final Set<IModule> getModules() {

    // return an unmodifiable copy
    return Collections.unmodifiableSet(new HashSet<IModule>(_modules));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final IModule getModule(IModuleIdentifier identifier) {

    // assert
    Assert.isNotNull(identifier);

    // search in resource modules
    for (IModule iModule : _modules) {
      if (identifier.equals(iModule.getModuleIdentifier())) {
        return iModule;
      }
    }

    // return null
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IModule> getModules(String name) {

    // assert
    Assert.isNotNull(name);

    // create result list
    Collection<IModule> result = new LinkedList<IModule>();

    // search in resource modules
    for (IModule iModule : _modules) {
      if (name.equals(iModule.getModuleIdentifier().getName())) {
        result.add(iModule);
      }
    }

    // return result
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModule getModule(String name, String version) {
    return getModule(new ModuleIdentifier(name, version));
  }

  @Override
  public final List<IModifiableModule> getModifiableResourceModules() {
    return _modules;
  }

  /**
   * {@inheritDoc}
   */
  public final IModifiableModule getModifiableResourceModule(IModuleIdentifier identifier) {

    Assert.isNotNull(identifier);

    IModule module = getModule(identifier);

    //
    if (module instanceof IModifiableModule) {
      return (IModifiableModule) module;
    }

    //
    return null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return the listenerList
   */
  public ModularizedSystemChangedListenerList getListenerList() {
    return _listenerList;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IPath> getGroups() {

    //
    List<IPath> result = new LinkedList<IPath>();

    //
    for (Group group : _groups) {
      result.add(group.getPath());
    }

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<Group> internalGroups() {
    return _groups;
  }

  /**
   * <p>
   * </p>
   * 
   * @param executionEnvironment
   */
  public void setExecutionEnvironment(IModule executionEnvironment) {

    Assert.isNotNull(executionEnvironment);

    _executionEnvironment = executionEnvironment;
  }

  /**
   * <p>
   * </p>
   * 
   * @param identifier
   * @return
   */
  protected boolean hasResourceModule(IModuleIdentifier identifier) {
    Assert.isNotNull(identifier);

    //
    for (IModule iModule : _modules) {
      if (identifier.equals(iModule.getModuleIdentifier())) {
        return true;
      }
    }

    //
    return false;
  }
}
