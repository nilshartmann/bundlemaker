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
package org.bundlemaker.core.project;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.common.ResourceType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IProjectContentEntry {

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
   * Returns the {@link IProjectContentProvider} that created this {@link IProjectContentEntry}.
   * </p>
   * 
   * @return the {@link IProjectContentProvider} that created this {@link IProjectContentEntry}.
   */
  IProjectContentProvider getProvider();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Map<String, Object> getUserAttributes();

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
   * Returns the {@link AnalyzeMode} for this {@link IProjectContentEntry}.
   * </p>
   * 
   * @return the {@link AnalyzeMode} for this {@link IProjectContentEntry}
   */
  AnalyzeMode getAnalyzeMode();

  /**
   * <p>
   * Returns all binary root paths of this {@link IProjectContentEntry}.
   * </p>
   * 
   * @return all binary root paths of this {@link IProjectContentEntry}.
   */
  Set<VariablePath> getBinaryRootPaths();

  /**
   * <p>
   * Returns all source root paths of this {@link IProjectContentEntry}.
   * </p>
   * 
   * @return all source root paths of this {@link IProjectContentEntry}.
   */
  Set<VariablePath> getSourceRootPaths();

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
  Collection<? extends IProjectContentResource> getResources(ResourceType type);

  /**
   * <p>
   * Returns a {@link Set} of all binary resources
   * </p>
   * <p>
   * This is a convenience method for {@link #getResources(ResourceType) getResources(ContentType.BINARY)}
   * </p>
   * <p>
   * If this content entry is not a resource content ( <code>isAnalyze()</code> returns <code>false</code>), an empty
   * set will be returned.
   * </p>
   * 
   * @return a Set of resources, never null.
   */
  Collection<? extends IProjectContentResource> getBinaryResources();

  /**
   * Returns all source resources
   * <p>
   * This is a convenience method for {@link #getResources(ResourceType) getResources(ContentType.SOURCE)}
   * </p>
   * <p>
   * If this content entry is not a resource content ( <code>isAnalyze()</code> returns <code>false</code>), an empty
   * set will be returned.
   * </p>
   * 
   * @return a Set of resources, never null.
   */
  Collection<? extends IProjectContentResource> getSourceResources();

  /**
   * @param path
   * @param type
   * @return
   */
  IProjectContentResource getResource(String path, ResourceType type);
}
