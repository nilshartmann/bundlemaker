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
package org.bundlemaker.core.projectdescription.modifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.internal.projectdescription.ResourceContent;
import org.bundlemaker.core.internal.projectdescription.RootPath;
import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IRootPath;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

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

  private AnalyzeMode                           _analyze;

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

    _analyze = AnalyzeMode.BINARIES_ONLY;

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

  public boolean isAnalyze() {
    return _analyze.isAnalyze();
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
      binaryPaths.add(new RootPath(path, true));
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
      modifiableSourcePaths.add(new RootPath(path, false));
    }

  }

  @Override
  public Set<IRootPath> getSourceRootPaths() {
    return _resourceContent.getSourcePaths();
  }

  @Override
  public Set<? extends IResource> getResources(ContentType type) {
    return isAnalyze() ? _resourceContent.getResources(type) : EMPTY_RESOURCE_SET;
  }

  @Override
  public Set<? extends IResource> getBinaryResources() {
    return isAnalyze() ? _resourceContent.getBinaryResources() : EMPTY_RESOURCE_SET;
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

  public void setAnalyzeMode(AnalyzeMode analyzeMode) {
    Assert.isNotNull(analyzeMode, "Paramter 'analyzeMode' must not be null");
    _analyze = analyzeMode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.projectdescription.IFileBasedContent#getAnalyzeMode()
   */
  @Override
  public AnalyzeMode getAnalyzeMode() {
    return _analyze;
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
