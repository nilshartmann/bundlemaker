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
package org.bundlemaker.core.projectdescription;

import java.util.Set;

import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Describes a file based content entry in an {@link IBundleMakerProjectDescription}. A file base content entry can
 * contain one or many directories or archive files (*.zip or *.jar).
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IFileBasedContent {

  /**
   * <p>
   * Returns the internal identifier of this content entry.
   * </p>
   * 
   * @return the internal identifier of this content entry.
   */
  String getId();

  /**
   * <p>
   * Returns the name of this content entry.
   * </p>
   * 
   * @return the name of this content entry.
   */
  String getName();

  /**
   * <p>
   * Returns the version of this content entry.
   * </p>
   * 
   * @return the version of this content entry.
   */
  String getVersion();

  /**
   * <p>
   * Returns the set of all binary paths that belongs to this {@link IFileBasedContent}. A {@link IFileBasedContent}
   * <i>always</i> has one or more binary paths.
   * </p>
   * 
   * @return the set of all binary paths that belongs to this {@link IFileBasedContent}.
   */
  Set<IPath> getBinaryPaths();

  /**
   * <p>
   * Return <code>true</code> if this content entry is a resource entry that should be parsed and analyzed,
   * <code>false</code> otherwise.
   * </p>
   * 
   * @return <code>true</code> if this content entry is a resource entry that should be parsed and analyzed,
   *         <code>false</code> otherwise.
   */
  boolean isResourceContent();

  /**
   * <p>
   * Returns the set of all defined source paths for this content entry. The result set is never <code>null</code>, but
   * maybe empty.
   * </p>
   * <p>
   * If this content entry is not a resource content ( <code>isResourceContent()</code> returns <code>false</code>), an
   * empty set will be returned.
   * </p>
   * 
   * @return the set of all defined source paths for this content entry.
   */
  Set<IPath> getSourcePaths();

  /**
   * <p>
   * Returns <code>true</code> if specified source files should be parsed. E.g. if you just want to create an
   * eclipse-specific source bundle for a given source archive, you set this value to <code>false</code>.
   * </p>
   * <p>
   * If this content entry is not a resource content ( <code>isResourceContent()</code> returns <code>false</code>),
   * <code>false</code> will be returned.
   * </p>
   * 
   * @return <code>true</code> if specified source files should be parsed.
   */
  boolean isAnalyzeSourceResources();

  /**
   * <p>
   * Returns the {@link IResource} for the specified path and type
   * </p>
   * <p>
   * If this content entry is not a resource content (<code>isResourceContent()</code> returns <code>false</code>),
   * <code>null</code> will be returned.
   * </p>
   * 
   * @param path
   *          The path of the resource that should be returned
   * @param type
   *          The type of the resource, either binary or source
   * @return
   */
  IResource getResource(IPath path, ContentType type);

  /**
   * <p>
   * Returns a {@link Set} of all resources of the specified type
   * </p>
   * <p>
   * If this content entry is not a resource content ( <code>isResourceContent()</code> returns <code>false</code>), an
   * empty set will be returned.
   * </p>
   * 
   * @param type
   * @return a Set of resources, never null.
   */
  Set<? extends IResource> getResources(ContentType type);

  /**
   * <p>
   * Returns the binary {@link IResource resource} for the specified path
   * </p>
   * <p>
   * If this content entry is not a resource content ( <code>isResourceContent()</code> returns <code>false</code>),
   * <code>null</code> will be returned.
   * </p>
   * 
   * @param path
   * @return the resource the path points to or null if no such resource is available
   */
  IResource getBinaryResource(IPath path);

  /**
   * <p>
   * Returns a {@link Set} of all binary resources
   * </p>
   * <p>
   * This is a convenience method for {@link #getResources(ContentType) getResources(ContentType.BINARY)}
   * </p>
   * <p>
   * If this content entry is not a resource content ( <code>isResourceContent()</code> returns <code>false</code>), an
   * empty set will be returned.
   * </p>
   * 
   * @return a Set of resources, never null.
   */
  Set<? extends IResource> getBinaryResources();

  /**
   * <p>
   * Returns the {@link IResource Resource} for the specified path
   * </p>
   * <p>
   * If this content entry is not a resource content ( <code>isResourceContent()</code> returns <code>false</code>),
   * <code>null</code> will be returned.
   * </p>
   * 
   * @param path
   * @return The resource for the given path or null if there is no resource for the given path
   */
  IResource getSourceResource(IPath path);

  /**
   * Returns all source resources
   * <p>
   * This is a convenience method for {@link #getResources(ContentType) getResources(ContentType.SOURCE)}
   * </p>
   * <p>
   * If this content entry is not a resource content ( <code>isResourceContent()</code> returns <code>false</code>), an
   * empty set will be returned.
   * </p>
   * 
   * @return a Set of resources, never null.
   */
  Set<? extends IResource> getSourceResources();
}
