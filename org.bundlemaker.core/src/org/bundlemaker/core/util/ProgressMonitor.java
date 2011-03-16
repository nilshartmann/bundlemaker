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

import org.bundlemaker.core.internal.Activator;
import org.bundlemaker.core.internal.parser.ResourceCache;
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

  private static final String CACHE_MSG      = " - [|Resources|: %s, |FlyWeightStrings|: %s, |References|: %s, |ReferencesAttributes|: %s]";

  /** - */
  private int                 _totalWork;

  /** - */
  private int                 _done          = 0;

  /** - */
  private int                 _doneInProzent = 0;

  /** - */
  private StopWatch           _stopWatch;

  /** - */
  private ResourceCache       _resourceCache;

  /**
   * @see org.eclipse.core.runtime.NullProgressMonitor#beginTask(java.lang.String, int)
   */
  public void beginTask(String name, int totalWork) {
    Assert.isNotNull(name);
    Assert.isTrue(totalWork > 0);

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
          estimatedOverallTime / 1000, estimatedTimeLeft / 1000, MemoryUtils.getMemoryUsage()));

      if (_resourceCache != null) {
        System.err.println(String.format(CACHE_MSG, _resourceCache.getResourceMap().size(), _resourceCache
            .getFlyWeightCache().getFlyWeightStrings().size(), _resourceCache.getFlyWeightCache().getReferenceCache()
            .size(), _resourceCache.getFlyWeightCache().getReferenceAttributesCache().size()));
      }
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
   * @param cache
   */
  public void setResourceCache(ResourceCache cache) {
    _resourceCache = cache;
  }

}
