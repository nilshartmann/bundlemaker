/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.projectdescription;

/**
 * Specifies how an {@link IBundleMakerProjectContent} entry in a {@link IBundleMakerProjectDescription} should be analyzed
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public enum AnalyzeMode {

  /**
   * Analyze this content (binaries only)
   */
  BINARIES_ONLY,

  /**
   * Analyze this content (sources and binaries)
   */
  BINARIES_AND_SOURCES,

  /**
   * Do not analyze this content add all
   */
  DO_NOT_ANALYZE;

  /**
   * @return if this instance either is {@link #BINARIES_ONLY} or {@link #BINARIES_AND_SOURCES},
   */
  public boolean isAnalyze() {
    return this == BINARIES_ONLY || this == BINARIES_AND_SOURCES;
  }

}
