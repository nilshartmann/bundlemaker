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
package org.bundlemaker.core.analysis.model;

/**
 * <p>
 * </p>
 * 
 * @author Frank Schlueter
 * @author Kai Lehmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public enum DependencyKind {

  /** simple 'uses' dependency */
  USES,

  /** 'implements' dependency (includes the 'uses' dependency) */
  IMPLEMENTS,

  /** 'extends' dependency (includes the 'uses' dependency) */
  EXTENDS,

  /** 'annotates' dependency (includes the 'uses' dependency) */
  ANNOTATES
}
