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
package org.bundlemaker.core.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.internal.parser.ModelSetup;
import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.internal.store.IDependencyStore;
import org.bundlemaker.core.internal.store.IPersistentDependencyStore;
import org.bundlemaker.core.internal.transformation.BasicProjectContentTransformation;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.parser.IParserFactory;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * <p>
 * Implementation of the interface {@link IBundleMakerProject}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProject implements IBundleMakerProject {

  /** the associated eclipse project (the bundle make project) */
  private IProject                       _project;

  /** the bundle maker project description */
  private BundleMakerProjectDescription  _projectDescription;

  /** the associated info store */
  private IDependencyStore               _additionalInfoStore;

  /** the state the project is in */
  private BundleMakerProjectState        _projectState;

  /** the project description working copies */
  private Map<String, ModularizedSystem> _modifiableModualizedSystemWorkingCopies;

  /**
   * <p>
   * Creates a new instance of type {@link BundleMakerProject}.
   * </p>
   * 
   * @param project
   * @throws Exception
   */
  public BundleMakerProject(IProject project) throws CoreException {

    // TODO: CoreException
    Assert.isTrue(project.hasNature(BundleMakerCore.NATURE_ID));

    // set the project
    _project = project;

    // read the projectDescription
    _projectDescription = loadProjectDescription();

    // create the working copies map
    _modifiableModualizedSystemWorkingCopies = new HashMap<String, ModularizedSystem>();

    //
    _projectState = BundleMakerProjectState.CREATED;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BundleMakerProjectState getState() {
    return _projectState;
  }

  /**
   * {@inheritDoc}
   */
  public void initialize(IProgressMonitor progressMonitor) throws CoreException {

    // reload the project description
    _projectDescription = loadProjectDescription();
    _projectDescription.initialize(this);

    // set the initialized flag
    _projectState = BundleMakerProjectState.INITIALIZED;

    // step 4: call initialize on the parser factories
    for (IParserFactory parserFactory : Activator.getDefault().getParserFactoryRegistry().getParserFactories()) {
      parserFactory.initialize(this);
    }
  }

  @Override
  public void parseAndOpen(IProgressMonitor progressMonitor) throws CoreException {

    // assert
    assertState(BundleMakerProjectState.INITIALIZED);

    // get the dependency store
    ModelSetup modelSetup = new ModelSetup(this);
    modelSetup.setup(_projectDescription.getModifiableFileBasedContent(),
        ((IPersistentDependencyStore) getDependencyStore(null)), progressMonitor);

    // set 'READY' state
    _projectState = BundleMakerProjectState.READY;

    // create default working copy
    IModularizedSystem modularizedSystem = createModularizedSystemWorkingCopy(getProject().getName());
    modularizedSystem.applyTransformations();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    // delete the dependency store
    try {

      //
      IDependencyStore dependencyStore = getDependencyStore(null);

      //
      if (dependencyStore instanceof IPersistentDependencyStore) {
        ((IPersistentDependencyStore) dependencyStore).dispose();
      }

    } catch (CoreException e) {
      // TODO
      e.printStackTrace();
    }

    Activator.getDefault().removeCachedBundleMakerProject(_project);

    // set the project state
    _projectState = BundleMakerProjectState.DISPOSED;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IProblem> getProblems() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public final List<IResource> getSourceResources() {
    return _projectDescription.getSourceResources();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public final List<IResource> getBinaryResources() {
    return _projectDescription.getBinaryResources();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IModularizedSystem> getModularizedSystemWorkingCopies() throws CoreException {
    Collection<? extends IModularizedSystem> result = _modifiableModualizedSystemWorkingCopies.values();
    return Collections.unmodifiableCollection(result);
  }

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  public IModularizedSystem createModularizedSystemWorkingCopy(String name) throws CoreException {

    // assert
    Assert.isNotNull(name);
    Assert.isTrue(name.trim().length() > 0);
    assertState(BundleMakerProjectState.READY);

    //
    if (_modifiableModualizedSystemWorkingCopies.containsKey(name)) {
      // TODO
      throw new RuntimeException("");
    }

    // create the modularized system
    ModularizedSystem modularizedSystem = new ModularizedSystem(name, _projectDescription);

    // create the default transformation
    ITransformation basicContentTransformation = new BasicProjectContentTransformation();
    modularizedSystem.getTransformations().add(basicContentTransformation);

    // add the result to the hash map
    _modifiableModualizedSystemWorkingCopies.put(name, modularizedSystem);

    //
    return modularizedSystem;
  }

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  public boolean hasModularizedSystemWorkingCopy(String name) throws CoreException {

    // assert
    assertState(BundleMakerProjectState.READY);

    return _modifiableModualizedSystemWorkingCopies.containsKey(name);
  }

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  public IModularizedSystem getModularizedSystemWorkingCopy(String name) throws CoreException {

    // assert
    assertState(BundleMakerProjectState.READY);

    if (!hasModularizedSystemWorkingCopy(name)) {
      // TODO
      throw new RuntimeException(name);
    }

    return _modifiableModualizedSystemWorkingCopies.get(name);
  }

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  public void deleteModularizedSystemWorkingCopy(String name) throws CoreException {

    // assert
    assertState(BundleMakerProjectState.READY);

    if (_modifiableModualizedSystemWorkingCopies.containsKey(name)) {
      _modifiableModualizedSystemWorkingCopies.remove(name);
    }
  }

  /**
   * {@inheritDoc}
   */
  public IProject getProject() {
    return _project.getProject();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IBundleMakerProjectDescription getProjectDescription() {
    return _projectDescription;
  }

  /**
   * <p>
   * </p>
   * 
   * @param progressMonitor
   * @return
   * @throws CoreException
   */
  public IDependencyStore getDependencyStore(IProgressMonitor progressMonitor) throws CoreException {

    // assert
    assertState(BundleMakerProjectState.INITIALIZED, BundleMakerProjectState.READY);

    if (_additionalInfoStore == null) {
      return Activator.getDefault().getPersistentDependencyStore(this);
    }

    return _additionalInfoStore;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_project == null) ? 0 : _project.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BundleMakerProject other = (BundleMakerProject) obj;
    if (_project == null) {
      if (other._project != null)
        return false;
    } else if (!_project.equals(other._project))
      return false;
    return true;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws CoreException
   */
  private BundleMakerProjectDescription loadProjectDescription() throws CoreException {

    //
    return ProjectDescriptionStore.loadProjectDescription(this);
  }

  /**
   * <p>
   * </p>
   * 
   * @param state
   * @throws CoreException
   */
  private void assertState(BundleMakerProjectState... state) throws CoreException {

    // check the states
    for (BundleMakerProjectState aState : state) {
      if (aState != null && aState.equals(getState())) {
        return;
      }
    }

    // throw new exception
    throw new CoreException(new Status(IStatus.ERROR, BundleMakerCore.BUNDLE_ID, String.format(
        "BundleMakerProject must be in one of the following states: '%s', but current state is '%s'.",
        Arrays.asList(state), getState())));
  }
}
