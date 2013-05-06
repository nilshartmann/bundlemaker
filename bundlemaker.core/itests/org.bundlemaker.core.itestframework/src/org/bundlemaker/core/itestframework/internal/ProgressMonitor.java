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
package org.bundlemaker.core.itestframework.internal;

import org.bundlemaker.core.util.StopWatch;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProgressMonitor extends NullProgressMonitor {

  private static final String MSG            = "Processed %s/%s steps (%s) [ %s sec, %s sec, %s sec] %s";

  /** - */
  private int                 _totalWork;

  /** - */
  private int                 _done          = 0;

  /** - */
  private int                 _doneInProzent = 0;

  /** - */
  private StopWatch           _stopWatch;

  /**
   * @see org.eclipse.core.runtime.NullProgressMonitor#beginTask(java.lang.String, int)
   */
  public void beginTask(String name, int totalWork) {
    Assert.isNotNull(name);

    _totalWork = totalWork;

    _stopWatch = new StopWatch();
    _stopWatch.start();
  }

  public void setTaskName(String name) {
    // System.err.println(name);
  }

  public void worked(int work) {
    _done = _done + work;

    if ((_done * 100) / _totalWork > _doneInProzent) {

      _doneInProzent = (_done * 100) / _totalWork;
      long elapsedTime = _stopWatch.getElapsedTime();
      long estimatedOverallTime = (elapsedTime / _doneInProzent * 100);
      long estimatedTimeLeft = estimatedOverallTime - elapsedTime;
      estimatedTimeLeft = estimatedTimeLeft > 0 ? estimatedTimeLeft : 0;

      System.err.println(String.format(MSG, _done, _totalWork, _doneInProzent, elapsedTime / 1000,
          estimatedOverallTime / 1000, estimatedTimeLeft / 1000, getMemoryUsage()));
    }
  }

  @Override
  public void done() {
    _stopWatch.stop();
  }
  
  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static String getMemoryUsage() {
    long totalMem = Runtime.getRuntime().totalMemory();
    long freeMem = Runtime.getRuntime().freeMemory();
    return "Memory used: " + (totalMem - freeMem) / (1024 * 1024) + " MB (total: " + totalMem / (1024 * 1024) + " MB )";
  }
}
