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

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.bundlemaker.core.resource.IResource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
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
   * </p>
   * 
   * @return
   */
  Set<VariablePath> getBinaryRootPaths();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Set<VariablePath> getSourceRootPaths();

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
   * </p>
   * 
   * @return the {@link AnalyzeMode} for this content
   */
  AnalyzeMode getAnalyzeMode();

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
  Set<? extends IResource> getResources(ProjectContentType type);

  /**
   * <p>
   * Returns a {@link Set} of all binary resources
   * </p>
   * <p>
   * This is a convenience method for {@link #getResources(ProjectContentType) getResources(ContentType.BINARY)}
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
   * This is a convenience method for {@link #getResources(ProjectContentType) getResources(ContentType.SOURCE)}
   * </p>
   * <p>
   * If this content entry is not a resource content ( <code>isAnalyze()</code> returns <code>false</code>), an empty
   * set will be returned.
   * </p>
   * 
   * @return a Set of resources, never null.
   */
  Set<? extends IResource> getSourceResources();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Map<String, Object> getUserAttributes();
}
