package org.bundlemaker.analysis.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Abstract base class for all artifacts.
 * </p>
 * 
 * @author Kai Lehmann
 * @author Frank Schl&uuml;ter
 */
public abstract class AbstractArtifact implements IModifiableArtifact {

  // Ordnungs Eigenschaften
  private final ArtifactType  type;

  private String              name;

  private Integer             ordinal;

  private IArtifact           parent;

  // Meta-Daten
  private Map<Object, Object> properties;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifact}.
   * </p>
   * 
   * @param type
   * @param name
   */
  public AbstractArtifact(ArtifactType type, String name) {
    Assert.isNotNull(type);
    Assert.isNotNull(name);

    this.type = type;
    this.name = name;
  }

  @Override
  public IArtifact getChild(String path) {
    return null;
  }

  @Override
  public IArtifact getParent() {
    return parent;
  }

  @Override
  public void setParent(IArtifact parent) {
    this.parent = parent;
  }

  @Override
  public void addArtifact(IArtifact artifact, boolean relinkParent) {
    addArtifact(artifact);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifact getParent(ArtifactType type) {
    IArtifact parent = this.getParent();

    if (parent == null) {
      return null;
    } else if (parent == this) {
      return null;
    } else if (type.equals(parent.getType())) {

      return this.getParent();
    } else {
      return parent.getParent(type);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ArtifactType getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getOrdinal() {
    if (ordinal != null) {
      return ordinal;
    } else if (this.getParent() != null) {
      return this.getParent().getOrdinal();
    }
    return null;
  }

  @Override
  public void setOrdinal(Integer ordinal) {
    this.ordinal = ordinal;
  }

  @Override
  public String toString() {
    return this.getName();
  }

  @Override
  public List<IDependency> getDependencies(Collection<IArtifact> artifacts) {
    List<IDependency> dependencies = new ArrayList<IDependency>();

    for (IArtifact artifact : artifacts) {
      IDependency dependency = this.getDependency(artifact);
      if (dependency != null && dependency.getWeight() != 0) {
        dependencies.add(this.getDependency(artifact));
      }
    }
    return dependencies;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProperty(Object key, Object value) {
    Assert.isNotNull(key);

    properties().put(key, value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T getProperty(Object key, Class<T> t) {

    // return null if the properties havn't been initialized yet
    if (properties == null) {
      return null;
    }

    // return the property (if exists)
    else {
      T property = (T) properties().get(key);
      if (property != null) {
        return property;
      } else if (this.getParent() != null) {
        return this.getParent().getProperty(key, t);
      } else {
        return null;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getProperty(Object key) {
    return getProperty(key, String.class);
  }

  protected void setName(String name) {
    this.name = name;
  }

  /**
   * <p>
   * Internal method that lazily initializes the properties
   * </p>
   * 
   * @return
   */
  private Map<Object, Object> properties() {

    // lazy initialize the map
    if (properties == null) {
      properties = new HashMap<Object, Object>();
    }

    // return the result
    return properties;
  }
}
