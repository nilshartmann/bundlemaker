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
  Set<IRootPath> getBinaryRootPaths();

  /**
   * <p>
   * Return <code>true</code> if this content entry is a resource entry that should be parsed and analyzed,
   * <code>false</code> otherwise.
   * </p>
   * 
   * @return <code>true</code> if this content entry is a resource entry that should be parsed and analyzed,
   *         <code>false</code> otherwise.
   */
  boolean isAnalyze();

  /**
   * <p>
   * </p>
   * 
   * @return the {@link AnalyzeMode} for this content
   */
  AnalyzeMode getAnalyzeMode();

  /**
   * <p>
   * Returns the set of all defined source paths for this content entry. The result set is never <code>null</code>, but
   * maybe empty.
   * </p>
   * 
   * @return the set of all defined source paths for this content entry.
   */
  Set<IRootPath> getSourceRootPaths();

  /**
   * <p>
   * Returns a {@link Set} of all resources of the specified type
   * </p>
   * <p>
   * If this content entry is not a resource content ( <code>isAnalyze()</code> returns <code>false</code>), an empty
   * set will be returned.
   * </p>
   * 
   * @param type
   * @return a Set of resources, never null.
   */
  Set<? extends IResource> getResources(ContentType type);

  /**
   * <p>
   * Returns a {@link Set} of all binary resources
   * </p>
   * <p>
   * This is a convenience method for {@link #getResources(ContentType) getResources(ContentType.BINARY)}
   * </p>
   * <p>
   * If this content entry is not a resource content ( <code>isAnalyze()</code> returns <code>false</code>), an empty
   * set will be returned.
   * </p>
   * 
   * @return a Set of resources, never null.
   */
  Set<? extends IResource> getBinaryResources();

  /**
   * Returns all source resources
   * <p>
   * This is a convenience method for {@link #getResources(ContentType) getResources(ContentType.SOURCE)}
   * </p>
   * <p>
   * If this content entry is not a resource content ( <code>isAnalyze()</code> returns <code>false</code>), an empty
   * set will be returned.
   * </p>
   * 
   * @return a Set of resources, never null.
   */
  Set<? extends IResource> getSourceResources();

}
