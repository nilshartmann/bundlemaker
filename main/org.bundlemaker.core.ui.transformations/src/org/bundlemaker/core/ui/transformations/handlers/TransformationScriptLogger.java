/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.transformations.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.bundlemaker.core.transformations.script.ITransformationScriptLogger;
import org.bundlemaker.core.ui.transformations.console.TransformationScriptConsole;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class TransformationScriptLogger implements ITransformationScriptLogger {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.transformations.script.ITransformationScriptLogger#log(java.lang.String)
   */
  @Override
  public void log(String msg) {
    TransformationScriptConsole.instance().append(msg);
  }

  public void log(String msg, Throwable t) {

    StringWriter writer = new StringWriter();
    PrintWriter pw = new PrintWriter(writer);

    t.printStackTrace(pw);

    TransformationScriptConsole.instance().append(msg);
    TransformationScriptConsole.instance().append(writer.toString());
    try {
      writer.close();
    } catch (IOException e) {
      // ignore
    }
  }

}
