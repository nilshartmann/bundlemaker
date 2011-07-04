package org.bundlemaker.analysis.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;

/**
 * Abstrakte Oberklasse fuer die beiden unterschiedlichen Artefakte.
 * Unterschieden wird zwischen gruppierenden Artefakten und Primaerartefakten.
 * 
 * @author Kai Lehmann
 * @author Frank Schl&uuml;ter
 */
public abstract class AbstractArtifact implements IModifiableArtifact {

  // Ordnungs Eigenschaften
  private final ArtifactType  type;

  private final String        name;

  private Integer             ordinal;

  private IArtifact           parent;

  // Meta-Daten
  private Map<String, Object> properties;

  public AbstractArtifact(ArtifactType type, String name) {
    this.type = type;
    this.name = name;
  }

  private Map<String, Object> getProperties() {
    if (properties == null) {
      properties = new HashMap<String, Object>();
    }
    return properties;
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
		addArtifact(artifact, false);
	}

  public IArtifact getParent(ArtifactType type) {
    IArtifact parent = this.getParent();

    if (parent == null) {
      return null;
    } else if (type.equals(parent.getType())) {
      return this.getParent();
    } else {
      return parent.getParent(type);
    }
  }

  public ArtifactType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public Integer getOrdinal() {
    if (ordinal != null) {
      return ordinal;
    } else if (this.getParent() != null) {
      return this.getParent().getOrdinal();
    }
    return null;
  }

  public void setOrdinal(Integer ordinal) {
    this.ordinal = ordinal;
  }

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

  public void setProperty(String key, Object value) {
    getProperties().put(key, value);
  }

  public <T> T getProperty(String key, Class<T> t) {
    if (properties == null) {
      return null;
    } else {
      T property = (T) getProperties().get(key);
      if (property != null) {
        return property;
      } else if (this.getParent() != null) {
        return this.getParent().getProperty(key, t);
      } else {
        return null;
      }
    }
  }

  public String getProperty(String key) {
    return getProperty(key, String.class);
  }
}
