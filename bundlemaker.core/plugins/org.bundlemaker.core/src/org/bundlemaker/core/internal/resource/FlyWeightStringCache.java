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
package org.bundlemaker.core.internal.resource;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FlyWeightStringCache {

  /** - */
  private static final int                           FLY_WEIGHT_STRINGS_INITIAL_CAPACITY = 10000;

  /** - */
  private ConcurrentHashMap<String, FlyWeightString> _flyWeightStrings;

  /**
   * <p>
   * Creates a new instance of type {@link FlyWeightStringCache}.
   * </p>
   */
  public FlyWeightStringCache() {
    _flyWeightStrings = new ConcurrentHashMap<String, FlyWeightString>(FLY_WEIGHT_STRINGS_INITIAL_CAPACITY);
  }

  /**
   * <p>
   * </p>
   * 
   * @param string
   * @return
   */
  public FlyWeightString getFlyWeightString(String string) {

    // return if already there
    if (_flyWeightStrings.containsKey(string)) {
      return _flyWeightStrings.get(string);
    }

    // map doesn't contain key, create one -- note that first writer wins,
    // all others just throw away their value
    FlyWeightString flyWeightString = new FlyWeightString(string);
    _flyWeightStrings.putIfAbsent(string, flyWeightString);

    // return the value that won
    return _flyWeightStrings.get(string);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ConcurrentHashMap<String, FlyWeightString> getFlyWeightStrings() {
    return _flyWeightStrings;
  }
}
