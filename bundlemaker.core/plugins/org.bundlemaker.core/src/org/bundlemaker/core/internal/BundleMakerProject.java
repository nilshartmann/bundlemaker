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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.BundleMakerProjectChangedEvent;
import org.bundlemaker.core.BundleMakerProjectChangedEvent.Type;
import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IBundleMakerProjectChangedListener;
import org.bundlemaker.core.IBundleMakerProjectHook;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.internal.parser.ModelSetup;
import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.internal.projectdescription.IResourceStandin;
import org.bundlemaker.core.internal.projectdescription.ProjectDescriptionStore;
import org.bundlemaker.core.internal.store.IPersistentDependencyStore;
import org.bundlemaker.core.internal.store.IPersistentDependencyStoreFactory;
import org.bundlemaker.core.internal.transformation.BasicProjectContentTransformation;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.transformation.ITransformation;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.resource.IModuleResource;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;

/**
 * <p>
 * Implementation of the interface {@link IBundleMakerProject}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProject implements IInternalBundleMakerProject {

  /** the associated eclipse project (the bundle make project) */
  private IProject                                 _project;

  /** the bundle maker project description */
  private BundleMakerProjectDescription            _projectDescription;

  /** - */
  private List<IBundleMakerProjectChangedListener> _projectChangedListeners;

  /** the state the project is in */
  private BundleMakerProjectState                  _projectState;

  /** the project description working copies */
  private Map<String, ModularizedSystem>           _modifiableModualizedSystemWorkingCopies;

  /** - */
  private List<IProblem>                           _problems;

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
    _projectChangedListeners = new LinkedList<IBundleMakerProjectChangedListener>();
    addBundleMakerProjectChangedListener(new IBundleMakerProjectChangedListener() {
      @Override
      public void bundleMakerProjectChanged(BundleMakerProjectChangedEvent event) {
        if (event.getType().equals(Type.PROJECT_DESCRIPTION_RECOMPUTED)) {
          BundleMakerProject.this._projectState = BundleMakerProjectState.DIRTY;
        }
      }
    });

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
  @Override
  public void addBundleMakerProjectChangedListener(IBundleMakerProjectChangedListener listener) {

    //
    if (!_projectChangedListeners.contains(listener)) {
      _projectChangedListeners.add(listener);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeBundleMakerProjectChangedListener(IBundleMakerProjectChangedListener listener) {

    //
    if (_projectChangedListeners.contains(listener)) {
      _projectChangedListeners.remove(listener);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void initialize(IProgressMonitor progressMonitor) throws CoreException {

    // reload the project description
    _projectDescription = loadProjectDescription();
    _projectDescription.initialize(progressMonitor, this);

    // set the initialized flag
    _projectState = BundleMakerProjectState.INITIALIZED;

    // notify listeners
    notifyListeners(new BundleMakerProjectChangedEvent(Type.PROJECT_STATE_CHANGED));
  }

  @Override
  public void parseAndOpen(IProgressMonitor progressMonitor) throws CoreException {

    // assert
    assertState(BundleMakerProjectState.INITIALIZED, BundleMakerProjectState.READY);

    // clear the modularized system working copies
    // TODO
    _modifiableModualizedSystemWorkingCopies.clear();

    // get the store
    IPersistentDependencyStoreFactory factory = Activator.getDefault().getPersistentDependencyStoreFactory();
    IPersistentDependencyStore store = factory.getPersistentDependencyStore(this);

    try {

      // get the dependency store
      ModelSetup modelSetup = new ModelSetup(this);
      _problems = modelSetup.setup(_projectDescription.getContent(), store, progressMonitor);

      // set 'READY' state
      _projectState = BundleMakerProjectState.READY;

      // create default working copy
      IModularizedSystem modularizedSystem = hasModularizedSystemWorkingCopy(getProject().getName()) ? getModularizedSystemWorkingCopy(getProject()
          .getName())
          : createModularizedSystemWorkingCopy(progressMonitor, getProject().getName());

    } finally {

      // release the store
      factory.releasePersistentDependencyStore(this);
    }

    // notify listeners
    notifyListeners(new BundleMakerProjectChangedEvent(Type.PROJECT_STATE_CHANGED));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    // set the project state
    _projectState = BundleMakerProjectState.DISPOSED;

    // notify listeners
    notifyListeners(new BundleMakerProjectChangedEvent(Type.PROJECT_STATE_CHANGED));

    //
    Activator.getDefault().removeCachedBundleMakerProject(_project);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IProblem> getProblems() {

    //
    List<IProblem> emptyList = Collections.emptyList();

    //
    return _problems == null ? emptyList : _problems;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public final List<IModuleResource> getSourceResources() {
    return _projectDescription.getSourceResources();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public final List<IModuleResource> getBinaryResources() {
    return _projectDescription.getBinaryResources();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final List<IResourceStandin> getSourceResourceStandins() {
    return _projectDescription.getSourceResourceStandins();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final List<IResourceStandin> getBinaryResourceStandins() {
    return _projectDescription.getBinaryResourceStandins();
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
  public IModularizedSystem createModularizedSystemWorkingCopy(IProgressMonitor progressMonitor, String name)
      throws CoreException {

    // assert
    Assert.isNotNull(name);
    Assert.isTrue(name.trim().length() > 0);
    assertState(BundleMakerProjectState.READY);

    //
    if (_modifiableModualizedSystemWorkingCopies.containsKey(name)) {
      // TODO
      throw new IllegalStateException("Modularized system '" + name + "' already registered");
    }

    // create the modularized system
    ModularizedSystem modularizedSystem = new ModularizedSystem(name, _projectDescription);

    // add the result to the hash map
    _modifiableModualizedSystemWorkingCopies.put(name, modularizedSystem);

    // create the default transformation
    ITransformation basicContentTransformation = new BasicProjectContentTransformation();
    modularizedSystem.initialize(null);
    modularizedSystem.applyTransformations(null, basicContentTransformation);

    // invoke hook if available
    IBundleMakerProjectHook projectHook = Activator.getDefault().getBundleMakerProjectHook();
    if (projectHook != null) {
      try {
        projectHook.modularizedSystemCreated(
            new NullProgressMonitor(), // TODO
            modularizedSystem);
      } catch (CoreException coreException) {
        throw coreException;
      } catch (Exception ex) {
        // TODO: log exception instead of re-throw?
        throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
            "BundleMaker project hook failed: " + ex, ex));
      }
    }

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
   */
  @Override
  public IModularizedSystem getModularizedSystemWorkingCopy() throws CoreException {
    return getModularizedSystemWorkingCopy(getProject().getName());
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
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return getProject().getName();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public IProjectDescription getProjectDescription() {
    return _projectDescription;
  }

  @Override
  public IModifiableProjectDescription getModifiableProjectDescription() {
    return _projectDescription;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.IBundleMakerProject#reloadProjectDescription()
   */
  @Override
  public void reloadProjectDescription() throws CoreException {
    _projectDescription = loadProjectDescription();

    notifyListeners(new BundleMakerProjectChangedEvent(Type.PROJECT_DESCRIPTION_CHANGED));

  }

  @Override
  public void modifyBundleMakerProjectDescription(IProjectDescriptionModifier modifier) throws CoreException {

    //
    Assert.isNotNull(modifier);

    // Creating the project description
    IModifiableProjectDescription projectDescription = this.getModifiableProjectDescription();

    modifier.modifyProjectDescription(projectDescription);

    projectDescription.save();
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
   * @param event
   */
  public void notifyListeners(BundleMakerProjectChangedEvent event) {
    Assert.isNotNull(event);

    // clone
    IBundleMakerProjectChangedListener[] listeners = _projectChangedListeners
        .toArray(new IBundleMakerProjectChangedListener[0]);

    //
    for (IBundleMakerProjectChangedListener listener : listeners) {
      listener.bundleMakerProjectChanged(event);
    }
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
