/*******************************************************************************
 * Copyright (c) 2011 Nils Hartmann
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.transformations.script.runner;


/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class SysoutTransformationScriptLogger extends AbstractTransformationScriptLogger {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.transformations.script.AbstractTransformationScriptLogger#doLog(java.lang.String)
   */
  @Override
  protected void doLog(String s) {

    System.out.println(s);

  }

}
