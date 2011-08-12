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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IRootPath;
import org.bundlemaker.core.projectdescription.modifiable.IModifiableFileBasedContent;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FileBasedContent implements IModifiableFileBasedContent {

  /** - */
  private static final Set<? extends IResource> EMPTY_RESOURCE_SET = Collections
                                                                       .unmodifiableSet(new HashSet<IResource>());

  /** - */
  private boolean                               _isInitialized;

  /** - */
  private String                                _id;

  /** - */
  private String                                _name;

  /** - */
  private String                                _version;

  /** - */
  private Set<IRootPath>                        _binaryPaths;

  private boolean                               _analyze;

  /** - */
  private ResourceContent                       _resourceContent;

  /**
   * <p>
   * Creates a new instance of type {@link FileBasedContent}.
   * </p>
   */
  public FileBasedContent() {

    //
    _isInitialized = false;

    //
    _binaryPaths = new HashSet<IRootPath>();

    _resourceContent = new ResourceContent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {
    return _id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return _name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getVersion() {
    return _version;
  }

  @Override
  public Set<IRootPath> getBinaryRootPaths() {
    return Collections.unmodifiableSet(_binaryPaths);
  }

  /**
   * TODO rename in: isAnalyze() or something similiar
   */
  @Override
  public boolean isResourceContent() {
    return _analyze;
  }

  public boolean isAnalyze() {
    return _analyze;
  }

  public ResourceContent getModifiableResourceContent() {
    return _resourceContent;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<IRootPath> getModifiableBinaryPaths() {
    return _binaryPaths;
  }

  public Set<IRootPath> getModifiableSourcePaths() {
    return _resourceContent.getModifiableSourcePaths();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.projectdescription.modifiable.IModifiableFileBasedContent#setBinaryPaths(java.lang.String[])
   */
  @Override
  public void setBinaryPaths(String[] binaryRootPaths) {
    Assert.isNotNull(binaryRootPaths);

    Set<IRootPath> binaryPaths = getModifiableBinaryPaths();
    binaryPaths.clear();

    for (String path : binaryRootPaths) {
      binaryPaths.add(new RootPath(path));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.projectdescription.modifiable.IModifiableFileBasedContent#setSourcePaths(java.lang.String[])
   */
  @Override
  public void setSourcePaths(String[] sourceRootPaths) {
    Assert.isNotNull(sourceRootPaths);
    if (_resourceContent == null) {
      System.err.println("Warning! Attemt to set source paths on binary resource! Ignore.");
      return;
    }

    Set<IRootPath> modifiableSourcePaths = _resourceContent.getModifiableSourcePaths();
    modifiableSourcePaths.clear();

    for (String path : sourceRootPaths) {
      modifiableSourcePaths.add(new RootPath(path));
    }

  }

  @Override
  public Set<IRootPath> getSourceRootPaths() {
    return _resourceContent.getSourcePaths();
  }

  @Override
  public boolean isAnalyzeSourceResources() {
    return _analyze && _resourceContent.isAnalyzeSourceResources();
  }

  @Override
  public IResource getResource(IPath path, ContentType type) {
    return isAnalyze() ? _resourceContent.getResource(path, type) : null;
  }

  @Override
  public Set<? extends IResource> getResources(ContentType type) {
    return isAnalyze() ? _resourceContent.getResources(type) : EMPTY_RESOURCE_SET;
  }

  @Override
  public IResource getBinaryResource(IPath path) {
    return isAnalyze() ? _resourceContent.getBinaryResource(path) : null;
  }

  @Override
  public Set<? extends IResource> getBinaryResources() {
    return isAnalyze() ? _resourceContent.getBinaryResources() : EMPTY_RESOURCE_SET;
  }

  @Override
  public IResource getSourceResource(IPath path) {
    return isAnalyze() ? _resourceContent.getSourceResource(path) : null;
  }

  @Override
  public Set<? extends IResource> getSourceResources() {
    return isAnalyze() ? _resourceContent.getSourceResources() : EMPTY_RESOURCE_SET;
  }

  /**
   * <p>
   * </p>
   * 
   * @param id
   */
  public void setId(String id) {
    _id = id;
  }

  public void setName(String name) {
    _name = name;
  }

  public void setVersion(String version) {
    _version = version;
  }

  public void setAnalyze(boolean analyze) {
    _analyze = analyze;
    }

  @Deprecated
  @Override
  public void setResourceContent(boolean resourceContent) {
    setAnalyze(resourceContent);
  }

  public void setAnalyzeSourceResources(boolean flag) {
    if (_resourceContent != null) {
      _resourceContent.setAnalyzeSourceResources(flag);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param fileBasedContent
   * @param bundleMakerProject
   * @throws CoreException
   */
  public void initialize(BundleMakerProjectDescription projectDescription) throws CoreException {

    //
    Assert.isNotNull(projectDescription);

    // return if content already is initialized
    if (_isInitialized) {
      return;
    }

    if (isAnalyze()) {

      // add the binary resources
      for (IRootPath root : _binaryPaths) {

        for (String child : FileUtils.getAllChildren(root.getAsFile())) {

          // create the resource standin
          ResourceStandin resourceStandin = new ResourceStandin(_id, root.getResolvedPath().toString(), child);
          projectDescription.addBinaryResource(resourceStandin);

          // add the resource
          _resourceContent.getModifiableBinaryResources().add(resourceStandin);
        }
      }

      // add the source resources
      for (IRootPath root : _resourceContent.getSourcePaths()) {

        for (String child : FileUtils.getAllChildren(root.getAsFile())) {

          // create the resource standin
          ResourceStandin resourceStandin = new ResourceStandin(_id, root.getResolvedPath().toString(), child);
          projectDescription.addSourceResource(resourceStandin);

          // add the resource
          _resourceContent.getModifiableSourceResources().add(resourceStandin);
        }
      }
    }

    // set initialized
    _isInitialized = true;
  }

  public Collection<ResourceStandin> getModifiableBinaryResources() {
    return _resourceContent.getModifiableBinaryResources();
  }

  public Collection<ResourceStandin> getModifiableSourceResources() {
    return _resourceContent.getModifiableSourceResources();
  }
}
