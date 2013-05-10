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
package org.bundlemaker.core.internal.projectdescription;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.BundleMakerProjectChangedEvent;
import org.bundlemaker.core.BundleMakerProjectChangedEvent.Type;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.internal.projectdescription.api.IInternalProjectDescription;
import org.bundlemaker.core.projectdescription.AbstractProjectContentProvider;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.resource.IModuleResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProjectDescription implements IModifiableProjectDescription, IInternalProjectDescription {

  /** - */
  private static NumberFormat               FORMATTER  = new DecimalFormat("000000");

  /** the current identifier */
  @Expose
  @SerializedName("current-id")
  private int                               _currentId = 0;

  /** - */
  @Expose
  @SerializedName("project-content-providers")
  private List<IProjectContentProvider>     _projectContentProviders;

  /** - */
  @Expose
  @SerializedName("jre")
  private String                            _jre;

  /** - */
  private Object                            _identifierLock;

  /** - */
  private List<IProjectContentEntry>        _projectContentEntries;

  /** - */
  private Map<String, IProjectContentEntry> _projectContentEntriesMap;

  /** the resource list */
  private List<IResourceStandin>            _sourceResources;

  /** the resource list */
  private List<IResourceStandin>            _binaryResources;

  /** - */
  private boolean                           _initialized;

  /** - */
  private BundleMakerProject                _bundleMakerProject;

  /**
   * <p>
   * Creates a new instance of type {@link BundleMakerProjectDescription}.
   * </p>
   * 
   * @param bundleMakerProject
   */
  public BundleMakerProjectDescription(BundleMakerProject bundleMakerProject) {

    //
    _bundleMakerProject = bundleMakerProject;

    //
    _projectContentEntries = new ArrayList<IProjectContentEntry>();
    _projectContentProviders = new ArrayList<IProjectContentProvider>();
    _projectContentEntriesMap = new HashMap<String, IProjectContentEntry>();
    _sourceResources = new ArrayList<IResourceStandin>();
    _binaryResources = new ArrayList<IResourceStandin>();
    _identifierLock = new Object();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IBundleMakerProject getBundleMakerProject() {
    return _bundleMakerProject;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<? extends IProjectContentProvider> getContentProviders() {
    return Collections.unmodifiableList(_projectContentProviders);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IProjectContentEntry> getContent() {
    return Collections.unmodifiableList(_projectContentEntries);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IProjectContentEntry getProjectContentEntry(String identifier) {
    Assert.isNotNull(identifier);

    return _projectContentEntriesMap.get(identifier);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addContentProvider(IProjectContentProvider contentProvider) {
    Assert.isNotNull(contentProvider);

    addContentProvider(contentProvider, true);

    fireProjectDescriptionChangedEvent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeContentProvider(IProjectContentProvider contentProvider) {
    Assert.isNotNull(contentProvider);

    //
    _projectContentProviders.remove(contentProvider);

    // notify listeners
    fireProjectDescriptionChangedEvent();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moveUpContentProviders(List<? extends IProjectContentProvider> selectedItems) {

    // assert selected items are not null
    Assert.isNotNull(selectedItems);

    // return if empty
    if (selectedItems.isEmpty()) {
      return;
    }

    // order the selected items
    List<IProjectContentProvider> orderSelectedItems = new LinkedList<IProjectContentProvider>(selectedItems);
    Collections.sort(orderSelectedItems, new Comparator<IProjectContentProvider>() {
      @Override
      public int compare(IProjectContentProvider o1, IProjectContentProvider o2) {
        return _projectContentProviders.indexOf(o1) - _projectContentProviders.indexOf(o2);
      }
    });

    try {
      // move the items up
      for (IProjectContentProvider provider : orderSelectedItems) {

        //
        int index = _projectContentProviders.indexOf(provider);
        if (index == 0) {
          return;
        }

        //
        _projectContentProviders.remove(provider);
        _projectContentProviders.add(index - 1, provider);
      }
    } finally {
      fireProjectDescriptionChangedEvent();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moveDownContentProviders(List<? extends IProjectContentProvider> selectedItems) {

    // assert selected items are not null
    Assert.isNotNull(selectedItems);

    // return if empty
    if (selectedItems.isEmpty()) {
      return;
    }

    // order the selected items
    List<IProjectContentProvider> orderSelectedItems = new LinkedList<IProjectContentProvider>(selectedItems);
    Collections.sort(orderSelectedItems, new Comparator<IProjectContentProvider>() {
      @Override
      public int compare(IProjectContentProvider o1, IProjectContentProvider o2) {
        return _projectContentProviders.indexOf(o2) - _projectContentProviders.indexOf(o1);
      }
    });

    // move the items up
    try {
      for (IProjectContentProvider provider : orderSelectedItems) {

        //
        int index = _projectContentProviders.indexOf(provider);
        if (index == _projectContentProviders.size() - 1) {
          return;
        }

        //
        _projectContentProviders.remove(provider);
        _projectContentProviders.add(index + 1, provider);
      }
    } finally {
      fireProjectDescriptionChangedEvent();
    }
  }

  @Override
  public void removeContentProvider(String id) {
    for (Iterator<IProjectContentEntry> iterator = _projectContentEntries.iterator(); iterator.hasNext();) {

      ProjectContentEntry content = (ProjectContentEntry) iterator.next();

      if (content.getId().equals(id)) {
        iterator.remove();

        fireProjectDescriptionChangedEvent();

        return;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    synchronized (_identifierLock) {
      _projectContentEntries.clear();
      _projectContentProviders.clear();
      _projectContentEntriesMap.clear();
      _currentId = 0;
      _initialized = false;
      _jre = null;
    }

    fireProjectDescriptionChangedEvent();
  }

  void addContentProvider(IProjectContentProvider contentProvider, boolean resetIdentifier) {
    Assert.isNotNull(contentProvider);

    //
    _projectContentProviders.add(contentProvider);

    //
    if (resetIdentifier) {
      contentProvider.setId(getNextContentProviderId());
    }

    // contentProvider should always be AbstractContentProvider...
    if (contentProvider instanceof AbstractProjectContentProvider) {
      AbstractProjectContentProvider abstractContentProvider = (AbstractProjectContentProvider) contentProvider;
      abstractContentProvider.setProjectDescription(this);
    }

    // this is an internal method only. do NOT fire BundleMakerProjectChangedEvent

  }

  public String getNextContentProviderId() {
    synchronized (_identifierLock) {
      return FORMATTER.format(_currentId++);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundlemakerProject
   * @throws CoreException
   */
  public void initialize(IProgressMonitor progressMonitor, IBundleMakerProject bundlemakerProject) throws CoreException {

    //
    if (_initialized) {
      return;
    }

    // clear the project content list
    _projectContentEntries.clear();

    // // TODO
    // if (isValid()) {
    // throw new RuntimeException("Invalid description");
    // }

    //
    for (IProjectContentProvider contentProvider : _projectContentProviders) {

      //
      List<IProjectContentEntry> projectContents = contentProvider
          .getBundleMakerProjectContent(progressMonitor, getBundleMakerProject());

      //
      _projectContentEntries.addAll(projectContents);
    }

    //
    for (IProjectContentEntry entry : _projectContentEntries) {
      _projectContentEntriesMap.put(entry.getId(), entry);
    }

    //
    _initialized = true;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isValid() {
    return _jre == null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getJRE() {
    return _jre;
  }

  public boolean isInitialized() {
    return _initialized;
  }

  @SuppressWarnings("unchecked")
  public final List<IModuleResource> getSourceResources() {
    List<? extends IModuleResource> result = Collections.unmodifiableList(_sourceResources);
    return (List<IModuleResource>) result;
  }

  @SuppressWarnings("unchecked")
  public final List<IModuleResource> getBinaryResources() {
    List<? extends IModuleResource> result = Collections.unmodifiableList(_binaryResources);
    return (List<IModuleResource>) result;
  }

  public final List<IResourceStandin> getSourceResourceStandins() {
    return Collections.unmodifiableList(_sourceResources);
  }

  public final List<IResourceStandin> getBinaryResourceStandins() {
    return Collections.unmodifiableList(_binaryResources);
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   */
  public void addSourceResource(IResourceStandin resourceStandin) {
    _sourceResources.add(resourceStandin);
  }

  public void addBinaryResource(IResourceStandin resourceStandin) {
    _binaryResources.add(resourceStandin);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public int getCurrentId() {
    synchronized (_identifierLock) {
      return _currentId;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setJre(String jre) {
    setJreInternal(jre);

    fireProjectDescriptionChangedEvent();
  }

  void setJreInternal(String jre) {
    _jre = jre;

    // internal method. Do NOT fire BundleMakerProjectChangedEvent
  }

  /**
   * <p>
   * </p>
   * 
   * @param currentId
   */
  public void setCurrentId(int currentId) {
    synchronized (_identifierLock) {
      _currentId = currentId;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save() throws CoreException {

    ProjectDescriptionStore.saveProjectDescription(_bundleMakerProject.getProject(), this);

    // notify listener
    _bundleMakerProject.notifyListeners(new BundleMakerProjectChangedEvent(Type.PROJECT_DESCRIPTION_SAVED));
  }

  /**
   * Notifies the BundleMakerProjectChangedListeners that the description has been changed
   */
  public void fireProjectDescriptionChangedEvent() {

    // Create the Event
    BundleMakerProjectChangedEvent event = new BundleMakerProjectChangedEvent(Type.PROJECT_DESCRIPTION_CHANGED);

    // notify listeners
    if (_bundleMakerProject != null) {
      _bundleMakerProject.notifyListeners(event);
    }
  }

  /**
   * <p>
   * </p>
   */
  public void fireProjectDescriptionRecomputedEvent() {

    // Create the Event
    BundleMakerProjectChangedEvent event = new BundleMakerProjectChangedEvent(Type.PROJECT_DESCRIPTION_RECOMPUTED);

    // notify listeners
    if (_bundleMakerProject != null) {
      _bundleMakerProject.notifyListeners(event);
    }
  }
}
