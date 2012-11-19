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
package org.bundlemaker.core.analysis.selectors;

import java.util.regex.Pattern;

import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.util.PatternUtils;
import org.eclipse.core.runtime.Assert;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractPatternBasedSelector implements IArtifactSelector {

  private final IBundleMakerArtifact _artifact;

  private final Pattern[]            _includePattern;

  private final Pattern[]            _excludePattern;

  public AbstractPatternBasedSelector(IBundleMakerArtifact artifact, String includePattern, String excludePattern) {
    Assert.isNotNull(artifact);
    _artifact = artifact;
    //
    _includePattern = splitAndConvert(includePattern);
    _excludePattern = splitAndConvert(excludePattern);

  }

  public AbstractPatternBasedSelector(IBundleMakerArtifact artifact, String includePattern) {
    this(artifact, includePattern, null);

  }

  private static Pattern[] splitAndConvert(String s) {
    if (s == null) {
      return null;
    }

    String[] items = s.split(",");
    Pattern[] patterns = new Pattern[items.length];
    for (int i = 0; i < items.length; i++) {
      String regexp = PatternUtils.convertAntStylePattern(items[i].trim());
      patterns[i] = Pattern.compile(regexp);
    }

    return patterns;

  }

  protected IBundleMakerArtifact getBundleMakerArtifact() {
    return this._artifact;
  }

  protected boolean isExcluded(String packageName) {
    if (_excludePattern == null) {
      // nothing is excluded
      return false;
    }

    return matchesAny(packageName, _excludePattern);
  }

  protected boolean isIncluded(String packageName) {
    if (_includePattern == null) {
      // every package is included
      return true;
    }

    return matchesAny(packageName, _includePattern);
  }

  /**
   * @param packageName
   * @param includedPackagesPattern
   * @return
   */
  protected boolean matchesAny(String packageName, Pattern[] patterns) {
    for (Pattern pattern : patterns) {
      if (pattern.matcher(packageName).matches()) {
        return true;
      }
    }

    return false;
  }

}
