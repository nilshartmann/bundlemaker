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
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceContent {

  /** - */
  private Set<IPath>           _sourcePaths;

  /** - */
  private Set<ResourceStandin> _binaryResources;

  /** - */
  private Set<ResourceStandin> _sourceResources;

  /** - */
  private boolean              _analyzeSourceResources;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceContent}.
   * </p>
   */
  public ResourceContent() {

    //
    _sourcePaths = new HashSet<IPath>();
    _binaryResources = new HashSet<ResourceStandin>();
    _sourceResources = new HashSet<ResourceStandin>();
  }

  /**
   * {@inheritDoc}
   */
  public Set<IPath> getSourcePaths() {
    return Collections.unmodifiableSet(_sourcePaths);
  }

  /**
   * {@inheritDoc}
   */
  /**
   * {@inheritDoc}
   */
  public boolean isAnalyzeSourceResources() {
    return _analyzeSourceResources;
  }

  /**
   * {@inheritDoc}
   */
  public IResource getResource(IPath path, ContentType type) {

    switch (type) {
    case BINARY: {
      return getBinaryResource(path);
    }
    case SOURCE: {
      return getSourceResource(path);
    }
    default: {
      return null;
    }
    }
  }

  /**
   * {@inheritDoc}
   */
  public Set<? extends IResource> getResources(ContentType type) {

    switch (type) {
    case BINARY: {
      return getBinaryResources();
    }
    case SOURCE: {
      return getSourceResources();
    }
    default: {
      return null;
    }
    }
  }

  /**
   * {@inheritDoc}
   */
  public IResource getBinaryResource(IPath path) {

    //
    for (ResourceStandin resourceStandin : _binaryResources) {

      if (new Path(resourceStandin.getPath()).equals(path)) {
        return resourceStandin;
      }
    }

    return null;
  }

  public Set<? extends IResource> getBinaryResources() {

    //
    return Collections.unmodifiableSet(_binaryResources);
  }

  public IResource getSourceResource(IPath path) {

    //
    for (ResourceStandin resourceStandin : _sourceResources) {

      if (new Path(resourceStandin.getPath()).equals(path)) {
        return resourceStandin;
      }
    }

    //
    return null;
  }

  public Set<? extends IResource> getSourceResources() {

    //
    return Collections.unmodifiableSet(_sourceResources);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final Set<ResourceStandin> getModifiableSourceResources() {

    //
    return _sourceResources;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final Collection<ResourceStandin> getModifiableBinaryResources() {

    //
    return _binaryResources;
  }

  public Set<IPath> getModifiableSourcePaths() {
    return _sourcePaths;
  }

  public void setAnalyzeSourceResources(boolean analyzeSourceResources) {
    _analyzeSourceResources = analyzeSourceResources;
  }

}
