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
package org.bundlemaker.core.util;

/**
 * <p>
 * Implements a stop watch.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class StopWatch {

  /** the start time */
  private long    startTime = 0;

  /** the stop time */
  private long    stopTime  = 0;

  /** is the stop watch running? */
  private boolean _running  = false;

  /**
   * <p>
   * Starts the stop watch.
   * </p>
   */
  public void start() {
    this.startTime = System.currentTimeMillis();
    this._running = true;
  }

  /**
   * <p>
   * Stops the stop watch.
   * </p>
   */
  public void stop() {
    this.stopTime = System.currentTimeMillis();
    this._running = false;
  }

  /**
   * <p>
   * Returns the elapsed time.
   * </p>
   * 
   * @return the elapsed time.
   */
  public long getElapsedTime() {
    long elapsed;
    if (this._running) {
      elapsed = (System.currentTimeMillis() - this.startTime);
    } else {
      elapsed = (this.stopTime - this.startTime);
    }
    return elapsed;
  }

  /**
   * <p>
   * </p>
   * 
   * @param msg
   */
  public void dumpElapsedTime(String msg) {
    System.out.println(msg + " : " + getElapsedTime());
  }
}
