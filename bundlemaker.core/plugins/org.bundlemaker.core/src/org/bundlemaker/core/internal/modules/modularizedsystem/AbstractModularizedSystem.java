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

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.internal.modules.TypeModule;
import org.bundlemaker.core.internal.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.transformation.ITransformation;
import org.bundlemaker.core.projectdescription.IProjectDescription;
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
  private String                          _name;

  /** the user attributes */
  private Map<String, Object>             _userAttributes;

  /** the project description */
  private IProjectDescription             _projectDescription;

  /** the list of defined transformations */
  private List<ITransformation>           _transformations;

  /** the defined resource modules */
  private List<IModifiableResourceModule> _resourceModules;

  /** the defined type modules */
  private List<TypeModule>                _nonResourceModules;

  /** the execution environment type module */
  private TypeModule                      _executionEnvironment;

  /** - */
  private Set<Group>                      _groups;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractModularizedSystem}.
   * </p>
   * 
   * @param name
   * @param projectDescription
   */
  public AbstractModularizedSystem(String name, IProjectDescription projectDescription) {

    Assert.isNotNull(name);
    Assert.isNotNull(projectDescription);

    // set the name
    _name = name;

    // set the project description
    _projectDescription = projectDescription;

    // initialize fields
    _userAttributes = new HashMap<String, Object>();
    _transformations = new ArrayList<ITransformation>();
    _resourceModules = new LinkedList<IModifiableResourceModule>();
    _nonResourceModules = new LinkedList<TypeModule>();
    _groups = new HashSet<Group>();
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
  public IBundleMakerProject getBundleMakerProject() {
    return _projectDescription.getBundleMakerProject();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final IProjectDescription getProjectDescription() {
    return _projectDescription;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final List<ITransformation> getTransformations() {
    return Collections.unmodifiableList(_transformations);
  }

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
  public final Set<IModule> getAllModules() {

    // create the result list
    Set<IModule> result = new HashSet<IModule>(_nonResourceModules.size() + _resourceModules.size());

    // all all modules
    result.addAll(_nonResourceModules);
    result.addAll(_resourceModules);

    // return an unmodifiable copy
    return Collections.unmodifiableSet(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final IModule getModule(IModuleIdentifier identifier) {

    // assert
    Assert.isNotNull(identifier);

    // search in resource modules
    for (IModule iModule : _resourceModules) {
      if (identifier.equals(iModule.getModuleIdentifier())) {
        return iModule;
      }
    }

    // search in non resource modules
    for (IModule iModule : _nonResourceModules) {
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
    for (IModule iModule : _resourceModules) {
      if (name.equals(iModule.getModuleIdentifier().getName())) {
        result.add(iModule);
      }
    }

    // search in non resource modules
    for (IModule iModule : _nonResourceModules) {
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
  public final Collection<IModule> getNonResourceModules() {

    // return the (unmodifiable) result
    return Collections.unmodifiableCollection((Collection<? extends IModule>) _nonResourceModules);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModule getModule(String name, String version) {
    return getModule(new ModuleIdentifier(name, version));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceModule getResourceModule(String name, String version) {
    return getResourceModule(new ModuleIdentifier(name, version));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final IResourceModule getResourceModule(IModuleIdentifier identifier) {
    return getModifiableResourceModule(identifier);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Collection<IResourceModule> getResourceModules() {

    // return the unmodifiable collection
    return Collections.unmodifiableCollection((Collection<? extends IResourceModule>) _resourceModules);
  }

  @Override
  public final List<IModifiableResourceModule> getModifiableResourceModules() {
    return _resourceModules;
  }

  protected final List<TypeModule> getModifiableNonResourceModules() {
    return _nonResourceModules;
  }

  /**
   * {@inheritDoc}
   */
  public final IModifiableResourceModule getModifiableResourceModule(IModuleIdentifier identifier) {

    Assert.isNotNull(identifier);

    IModule module = getModule(identifier);

    //
    if (module instanceof IModifiableResourceModule) {
      return (IModifiableResourceModule) module;
    }

    //
    return null;
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
  protected void setExecutionEnvironment(TypeModule executionEnvironment) {

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
    for (IModule iModule : _resourceModules) {
      if (identifier.equals(iModule.getModuleIdentifier())) {
        return true;
      }
    }

    //
    return false;
  }

  /**
   * <p>
   * </p>
   * 
   * @param identifier
   * @return
   */
  protected boolean hasTypeModule(IModuleIdentifier identifier) {
    Assert.isNotNull(identifier);

    //
    for (IModule iModule : _nonResourceModules) {
      if (identifier.equals(iModule.getModuleIdentifier())) {
        return true;
      }
    }

    //
    return false;
  }
}
