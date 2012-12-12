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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractPatternBasedSelector implements IArtifactSelector {

  /** - */
  @Expose
  @SerializedName("artifact")
  private final IBundleMakerArtifact _artifact;

  /** - */
  @Expose
  @SerializedName("includePattern")
  private final String               _includePatternsAsString;

  /** - */
  @Expose
  @SerializedName("excludePattern")
  private final String               _excludePatternsAsString;

  /** - */
  private Pattern[]                  _includePattern;

  /** - */
  private Pattern[]                  _excludePattern;

  /** - */
  private boolean                    _initialized = false;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractPatternBasedSelector}.
   * </p>
   * 
   * @param artifact
   * @param includePattern
   * @param excludePattern
   */
  public AbstractPatternBasedSelector(IBundleMakerArtifact artifact, String includePattern, String excludePattern) {
    Assert.isNotNull(artifact);
    _artifact = artifact;

    //
    _includePatternsAsString = includePattern;
    _excludePatternsAsString = excludePattern;

    //
    initialize();
  }

  /**
   * <p>
   * Creates a new instance of type {@link AbstractPatternBasedSelector}.
   * </p>
   * 
   * @param artifact
   * @param includePattern
   */
  public AbstractPatternBasedSelector(IBundleMakerArtifact artifact, String includePattern) {
    this(artifact, includePattern, null);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected IBundleMakerArtifact getBundleMakerArtifact() {
    return this._artifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @param packageName
   * @return
   */
  protected boolean isExcluded(String packageName) {

    //
    initialize();

    if (_excludePattern == null) {
      // nothing is excluded
      return false;
    }

    return matchesAny(packageName, _excludePattern);
  }

  /**
   * <p>
   * </p>
   * 
   * @param packageName
   * @return
   */
  protected boolean isIncluded(String packageName) {

    //
    initialize();

    if (_includePattern == null) {
      // every package is included
      return true;
    }

    return matchesAny(packageName, _includePattern);
  }

  /**
   * <p>
   * </p>
   * 
   * @param packageName
   * @param patterns
   * @return
   */
  protected boolean matchesAny(String packageName, Pattern[] patterns) {

    //
    initialize();

    //
    for (Pattern pattern : patterns) {
      if (pattern.matcher(packageName).matches()) {
        return true;
      }
    }

    //
    return false;
  }

  /**
   * <p>
   * </p>
   * 
   * @param includePattern
   * @param excludePattern
   */
  private void initialize() {

    //
    if (_initialized) {
      return;
    }

    //
    _includePattern = splitAndConvert(_includePatternsAsString);
    _excludePattern = splitAndConvert(_excludePatternsAsString);

    //
    _initialized = true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param s
   * @return
   */
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_artifact == null) ? 0 : _artifact.hashCode());
    result = prime * result + ((_excludePatternsAsString == null) ? 0 : _excludePatternsAsString.hashCode());
    result = prime * result + ((_includePatternsAsString == null) ? 0 : _includePatternsAsString.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbstractPatternBasedSelector other = (AbstractPatternBasedSelector) obj;
    if (_artifact == null) {
      if (other._artifact != null)
        return false;
    } else if (!_artifact.equals(other._artifact))
      return false;
    if (_excludePatternsAsString == null) {
      if (other._excludePatternsAsString != null)
        return false;
    } else if (!_excludePatternsAsString.equals(other._excludePatternsAsString))
      return false;
    if (_includePatternsAsString == null) {
      if (other._includePatternsAsString != null)
        return false;
    } else if (!_includePatternsAsString.equals(other._includePatternsAsString))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AbstractPatternBasedSelector [_artifact=" + _artifact + ", _includePatternsAsString="
        + _includePatternsAsString + ", _excludePatternsAsString=" + _excludePatternsAsString + "]";
  }
}
