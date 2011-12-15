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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.BundleMakerProjectChangedEvent;
import org.bundlemaker.core.BundleMakerProjectChangedEvent.Type;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.projectdescription.AbstractContent;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectContent;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectContentProvider;
import org.bundlemaker.core.projectdescription.IModifiableBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IResourceStandin;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProjectDescription implements IModifiableBundleMakerProjectDescription {

  /** - */
  private static NumberFormat                      FORMATTER       = new DecimalFormat("000000");

  /** the current identifier */
  private int                                      _currentId      = 0;

  /** - */
  private Object                                   _identifierLock = new Object();

  /** - */
  private List<IBundleMakerProjectContent>         _projectContent;

  /** - */
  private List<IBundleMakerProjectContentProvider> _projectContentProviders;

  /** the resource list */
  private List<IResourceStandin>                   _sourceResources;

  /** the resource list */
  private List<IResourceStandin>                   _binaryResources;

  /** - */
  private String                                   _jre;

  /** - */
  private boolean                                  _initialized;

  /** - */
  private BundleMakerProject                       _bundleMakerProject;

  /**
   * <p>
   * Creates a new instance of type {@link BundleMakerProjectDescription}.
   * </p>
   * 
   * @param bundleMakerProject
   */
  public BundleMakerProjectDescription(BundleMakerProject bundleMakerProject) {

    //
    _projectContent = new ArrayList<IBundleMakerProjectContent>();
    _projectContentProviders = new ArrayList<IBundleMakerProjectContentProvider>();
    _sourceResources = new ArrayList<IResourceStandin>();
    _binaryResources = new ArrayList<IResourceStandin>();
    _bundleMakerProject = bundleMakerProject;
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
  public List<? extends IBundleMakerProjectContentProvider> getContentProviders() {
    return _projectContentProviders;
  }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // // TODO
  // public IBundleMakerProjectContent getFileBasedContent(String id) {
  // //
  // return getModifiableFileBasedContent(id);
  // }
  //
  // @Override
  // public IModifiableFileBasedContent getModifiableFileBasedContent(String id) {
  //
  // // file based content
  // for (FileBasedContent fileBasedContent : _fileBasedContent) {
  //
  // //
  // if (fileBasedContent.getId().equals(id)) {
  // return fileBasedContent;
  // }
  // }
  //
  // //
  // return null;
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IBundleMakerProjectContent> getContent() {
    return Collections.unmodifiableList(_projectContent);
  }

  @Override
  @Deprecated
  public IBundleMakerProjectContent getFileBasedContent(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addContentProvider(IBundleMakerProjectContentProvider contentProvider) {
    Assert.isNotNull(contentProvider);

    addContentProvider(contentProvider, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeContentProvider(IBundleMakerProjectContentProvider contentProvider) {
    Assert.isNotNull(contentProvider);

    //
    _projectContentProviders.remove(contentProvider);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moveUpContentProviders(List<? extends IBundleMakerProjectContentProvider> selectedItems) {

    // asserts
    Assert.isNotNull(selectedItems);

    //
    if (selectedItems.isEmpty()) {
      return;
    }

    //
    List<IBundleMakerProjectContentProvider> newOrder = new LinkedList<IBundleMakerProjectContentProvider>(
        _projectContentProviders);
    newOrder = moveUp(newOrder, selectedItems);

    //
    _projectContentProviders.clear();
    _projectContentProviders.addAll(newOrder);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moveDownContentProviders(List<? extends IBundleMakerProjectContentProvider> selectedItems) {

    // asserts
    Assert.isNotNull(selectedItems);

    //
    if (selectedItems.isEmpty()) {
      return;
    }

    List<IBundleMakerProjectContentProvider> newOrder = new LinkedList<IBundleMakerProjectContentProvider>(
        _projectContentProviders);
    Collections.reverse(newOrder);
    newOrder = moveUp(newOrder, selectedItems);
    Collections.reverse(newOrder);

    _projectContentProviders.clear();
    _projectContentProviders.addAll(newOrder);

  }

  @Override
  public void removeContentProvider(String id) {
    for (Iterator<IBundleMakerProjectContent> iterator = _projectContent.iterator(); iterator.hasNext();) {

      AbstractContent content = (AbstractContent) iterator.next();

      if (content.getId().equals(id)) {
        iterator.remove();
        return;
      }
    }
  }

  @Override
  public void clear() {
    synchronized (_identifierLock) {
      _projectContent.clear();
      _projectContentProviders.clear();
      _currentId = 0;
      _initialized = false;
      _jre = null;
    }
  }

  public void addContentProvider(IBundleMakerProjectContentProvider contentProvider, boolean resetIdentifier) {
    Assert.isNotNull(contentProvider);

    //
    _projectContentProviders.add(contentProvider);

    //
    if (resetIdentifier) {
      contentProvider.setId(getNextContentProviderId());
    }
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
  public void initialize(IBundleMakerProject bundlemakerProject) throws CoreException {

    //
    if (_initialized) {
      return;
    }

    // clear the project content list
    _projectContent.clear();

    // // TODO
    // if (isValid()) {
    // throw new RuntimeException("Invalid description");
    // }

    //
    for (IBundleMakerProjectContentProvider contentProvider : _projectContentProviders) {

      //
      List<IBundleMakerProjectContent> projectContents = contentProvider
          .getBundleMakerProjectContent(getBundleMakerProject());

      //
      _projectContent.addAll(projectContents);
    }

    // for (IBundleMakerProjectContent content : _projectContent) {
    // System.out.println(content.getSourceRootPaths());
    // System.out.println(content.getBinaryRootPaths());
    // }

    // //
    // int sourceResourcesCount = 0;
    // int binaryResourcesCount = 0;
    //
    // // TODO
    // for (IBundleMakerProjectContent fileBasedContent : _projectContent) {
    // fileBasedContent.initialize(this);
    //
    // //
    // if (fileBasedContent.isAnalyze()) {
    // binaryResourcesCount += fileBasedContent.getModifiableResourceContent().getModifiableBinaryResources().size();
    // sourceResourcesCount += fileBasedContent.getModifiableResourceContent().getModifiableSourceResources().size();
    // }
    // }
    //
    // // TODO:
    // System.out.println("Source resources to process: " + sourceResourcesCount);
    // System.out.println("Binary resources to process: " + binaryResourcesCount);

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
  public final List<IResource> getSourceResources() {
    List<? extends IResource> result = Collections.unmodifiableList(_sourceResources);
    return (List<IResource>) result;
  }

  @SuppressWarnings("unchecked")
  public final List<IResource> getBinaryResources() {
    List<? extends IResource> result = Collections.unmodifiableList(_binaryResources);
    return (List<IResource>) result;
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
    _jre = jre;
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
    _bundleMakerProject.notifyListeners(new BundleMakerProjectChangedEvent(Type.PROJECT_DESCRIPTION_CHANGED));
  }

  /**
   * <p>
   * </p>
   * 
   * @param selectedItems
   * @return
   */
  private List<IBundleMakerProjectContentProvider> moveUp(List<? extends IBundleMakerProjectContentProvider> original,
      List<? extends IBundleMakerProjectContentProvider> selectedItems) {

    //
    int nElements = selectedItems.size();
    List<IBundleMakerProjectContentProvider> res = new ArrayList<IBundleMakerProjectContentProvider>(nElements);
    IBundleMakerProjectContentProvider floating = null;
    for (int i = 0; i < nElements; i++) {
      IBundleMakerProjectContentProvider curr = selectedItems.get(i);
      if (original.contains(curr)) {
        res.add(curr);
      } else {
        if (floating != null) {
          res.add(floating);
        }
        floating = curr;
      }
    }
    if (floating != null) {
      res.add(floating);
    }
    return res;
  }
}
