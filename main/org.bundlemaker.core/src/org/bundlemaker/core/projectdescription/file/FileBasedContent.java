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
package org.bundlemaker.core.projectdescription.file;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.projectdescription.AbstractContent;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Implementation of an {@link IProjectDescription} that contains file based definition (source and binary folders
 * and/or archives).
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FileBasedContent extends AbstractContent implements IProjectContentEntry {

  /** - */
  private static final Set<VariablePath> EMPTY_ROOTPATH_SET = Collections.unmodifiableSet(new HashSet<VariablePath>());

  /** the binary pathes */
  private Set<VariablePath>              _binaryPaths;

  /** the source pathes */
  private Set<VariablePath>              _sourcePaths;

  /**
   * <p>
   * Creates a new instance of type {@link FileBasedContent}.
   * </p>
   */
  public FileBasedContent(IProjectContentProvider provider) {
    this(provider, false);
  }

  public FileBasedContent(IProjectContentProvider provider, boolean notifyChanges) {
    super(provider, notifyChanges);

    //
    setAnalyzeMode(AnalyzeMode.BINARIES_ONLY);

    //
    _binaryPaths = new HashSet<VariablePath>();
  }

  /**
   * {@inheritDoc}
   */
  public Set<VariablePath> getBinaryRootPaths() {
    return Collections.unmodifiableSet(_binaryPaths);
  }

  /**
   * {@inheritDoc}
   */
  public Set<VariablePath> getSourceRootPaths() {
    return _sourcePaths != null ? _sourcePaths : EMPTY_ROOTPATH_SET;
  }

  /**
   * {@inheritDoc}
   */
  protected void onInitialize(IProjectDescription projectDescription) throws CoreException {

    if (isAnalyze()) {

      // add the binary resources
      for (VariablePath root : _binaryPaths) {
        for (String filePath : FileUtils.getAllChildren(root.getAsFile())) {
          // create the resource standin
          createNewResourceStandin(getId(), root.getResolvedPath().toString(), filePath, ContentType.BINARY);
        }
      }

      // add the source resources
      if (_sourcePaths != null) {
        for (VariablePath root : _sourcePaths) {
          for (String filePath : FileUtils.getAllChildren(root.getAsFile())) {
            // create the resource standin
            createNewResourceStandin(getId(), root.getResolvedPath().toString(), filePath, ContentType.SOURCE);
          }
        }
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootPath
   * @param type
   */
  public void addRootPath(VariablePath rootPath, ContentType type) {
    Assert.isNotNull(rootPath);
    Assert.isNotNull(type);

    //
    if (type.equals(ContentType.BINARY)) {
      _binaryPaths.add(rootPath);
    } else if (type.equals(ContentType.SOURCE)) {
      sourcePaths().add(rootPath);
    }

    fireProjectDescriptionChangeEvent();
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootPath
   * @param type
   */
  public void removeRootPath(VariablePath rootPath, ContentType type) {
    Assert.isNotNull(rootPath);
    Assert.isNotNull(type);

    //
    if (type.equals(ContentType.BINARY)) {
      _binaryPaths.remove(rootPath);
    } else if (type.equals(ContentType.SOURCE)) {
      _sourcePaths.remove(rootPath);
    }

    fireProjectDescriptionChangeEvent();
  }

  /**
   * <p>
   * </p>
   * 
   * @param binaryRootPaths
   */
  public void setBinaryPaths(String[] binaryRootPaths) {
    Assert.isNotNull(binaryRootPaths);

    _binaryPaths.clear();

    for (String path : binaryRootPaths) {
      _binaryPaths.add(new VariablePath(path));
    }

    fireProjectDescriptionChangeEvent();

  }

  /**
   * <p>
   * </p>
   * 
   * @param sourceRootPaths
   */
  public void setSourcePaths(String[] sourceRootPaths) {
    Assert.isNotNull(sourceRootPaths);

    sourcePaths().clear();

    for (String path : sourceRootPaths) {
      sourcePaths().add(new VariablePath(path));
    }

    fireProjectDescriptionChangeEvent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("FileBasedContent [_binaryPaths=");
    builder.append(_binaryPaths);
    builder.append(", _sourcePaths=");
    builder.append(_sourcePaths);
    builder.append(", getId()=");
    builder.append(getId());
    builder.append(", getName()=");
    builder.append(getName());
    builder.append(", getVersion()=");
    builder.append(getVersion());
    builder.append(", isAnalyze()=");
    builder.append(isAnalyze());
    builder.append("]");
    return builder.toString();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private Set<VariablePath> sourcePaths() {

    // lazy initialization
    if (_sourcePaths == null) {
      _sourcePaths = new HashSet<VariablePath>();
    }

    // return the source paths
    return _sourcePaths;
  }

}
