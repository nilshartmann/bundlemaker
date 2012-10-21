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

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleManager;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class TransformationScriptConsoleFactory implements IConsoleFactory {

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.console.IConsoleFactory#openConsole()
   */
  @Override
  public void openConsole() {
    showConsole();

  }

  public static void showConsole() {
    TransformationScriptConsole console = TransformationScriptConsole.instance();
    if (console != null) {
      IConsoleManager manager = ConsolePlugin.getDefault().getConsoleManager();
      IConsole[] existing = manager.getConsoles();
      boolean exists = false;
      for (int i = 0; i < existing.length; i++) {
        if (console == existing[i])
          exists = true;
      }
      if (!exists)
        manager.addConsoles(new IConsole[] { console });
      manager.showConsoleView(console);
    }
  }

}
