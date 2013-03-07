/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.transformations.script.runner;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.bundlemaker.core.transformations.script.ITransformationScriptLogger;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractTransformationScriptLogger implements ITransformationScriptLogger {

  @Override
  public void log(String msg) {
    doLog(msg);
  }

  @Override
  public void log(String msg, Throwable t) {

    StringWriter writer = new StringWriter();
    PrintWriter pw = new PrintWriter(writer);

    t.printStackTrace(pw);

    doLog(msg);
    doLog(writer.toString());
    try {
      writer.close();
    } catch (IOException e) {
      // ignore
    }
  }

  protected abstract void doLog(String s);

}
