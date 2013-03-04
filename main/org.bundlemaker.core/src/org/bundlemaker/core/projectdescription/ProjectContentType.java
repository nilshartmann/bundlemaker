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

/**
 * <p>
 * The content type enumeration.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public enum ProjectContentType {

  /** source content */
  SOURCE,

  /** binary content */
  BINARY;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getShortDescription() {

    //
    switch (this) {
    case SOURCE:
      return "S";
    case BINARY:
      return "B";
    default:
      throw new RuntimeException(String.format("Unknown ContentType '%s'.", this));
    }
  }
}
