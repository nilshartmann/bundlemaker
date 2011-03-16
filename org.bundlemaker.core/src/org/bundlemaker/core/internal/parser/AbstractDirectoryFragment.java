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
package org.bundlemaker.core.internal.parser;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.parser.IDirectory;
import org.bundlemaker.core.parser.IDirectoryFragment;
import org.bundlemaker.core.resource.IResourceKey;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractDirectoryFragment implements IDirectoryFragment {

  /** - */
  private IFile             _ifile;

  /** - */
  private File              _file;

  /** - */
  private IDirectory        _directory;

  private Set<IResourceKey> _resourceKeys;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractDirectoryFragment}.
   * </p>
   * 
   * @param root
   */
  public AbstractDirectoryFragment(File root) {
    Assert.isNotNull(root);

    _file = root;
  }

  /**
   * <p>
   * Creates a new instance of type {@link AbstractDirectoryFragment}.
   * </p>
   * 
   * @param ifile
   */
  public AbstractDirectoryFragment(IFile ifile) {
    Assert.isNotNull(ifile);

    _ifile = ifile;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract List<String> getContent();

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract int getResourceCount();

  public IDirectory getDirectory() {
    return _directory;
  }

  public void setDirectory(IDirectory directory) {
    _directory = directory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File getDirectoryFragmentRoot() {
    return _ifile != null ? _ifile.getRawLocation().toFile() : _file;
  }

  @Override
  public IResource getWorkspaceRelativeRoot() {
    return _ifile;
  }

  public boolean isWorkspaceRelative() {
    return _ifile != null;
  }
}
