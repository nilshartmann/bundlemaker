package org.bundlemaker.analysis.model;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Defines the type of an artifact.
 * </p>
 * 
 * @author Kai Lehmann
 * @author Frank Schl&uuml;ter
 */
public enum ArtifactType {

  /** - */
  Root("root", true),

  /** - */
  Group("group", true),

  /** - */
  Module("osgibundle", true),

  /** - */
  Package("package", true),

  /** - */
  Type("class"), Resource("resource");

  /** - */
  private String  _abbreviation;

  /** - */
  private boolean _isContainer = false;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactType}.
   * </p>
   * 
   * @param kuerzel
   */
  private ArtifactType(String abbreviation) {
    Assert.isNotNull(abbreviation);

    this._abbreviation = abbreviation;
  }

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactType}.
   * </p>
   * 
   * @param abbreviation
   * @param isContainer
   */
  private ArtifactType(String abbreviation, boolean isContainer) {
    Assert.isNotNull(abbreviation);

    this._abbreviation = abbreviation;
    this._isContainer = isContainer;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  // TODO: RENAME!
  public String getKuerzel() {
    return _abbreviation;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isContainer() {
    return _isContainer;
  }
}
