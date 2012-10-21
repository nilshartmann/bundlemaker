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
package org.bundlemaker.core.ui.transformation.console;

import java.io.IOException;

import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class TransformationScriptConsole extends MessageConsole {

  private static TransformationScriptConsole _instance;

  private MessageConsoleStream               _messageStream;

  public static synchronized TransformationScriptConsole instance() {
    if (_instance == null) {
      _instance = new TransformationScriptConsole();
    }
    return _instance;
  }

  /**
   * @param name
   * @param imageDescriptor
   * @param autoLifecycle
   */
  public TransformationScriptConsole() {
    super("BundleMaker Transformation Script Console", null, false);

    _messageStream = newMessageStream();
  }

  public void append(String s) {
    _messageStream.println(s);
    try {
      _messageStream.flush();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
