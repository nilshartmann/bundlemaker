package org.bundlemaker.core.internal.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Abstract base class for all artifacts.
 * </p>
 * 
 * @author Kai Lehmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractBundleMakerArtifact implements IBundleMakerArtifact {

  /** - */
  private String               name;

  /** - */
  private IBundleMakerArtifact parent;

  // Meta-Daten
  private Map<Object, Object>  properties;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractBundleMakerArtifact}.
   * </p>
   * 
   * @param name
   */
  public AbstractBundleMakerArtifact(String name) {
    Assert.isNotNull(name);

    this.name = name;
  }

  @Override
  public IBundleMakerArtifact getChild(String path) {
    return null;
  }

  // @Override
  // public boolean hasChild(String path) {
  // return false;
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInstanceOf(Class<? extends IBundleMakerArtifact> clazz) {

    //
    if (clazz == null) {
      return false;
    }

    //
    return clazz.isAssignableFrom(this.getClass());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T extends IBundleMakerArtifact> T castTo(Class<? extends IBundleMakerArtifact> clazz) {

    //
    Assert.isNotNull(clazz);

    //
    Assert.isTrue(isInstanceOf(clazz), String.format("Can not cast '%s' to '%s'.", this.getClass(), clazz));

    //
    return (T) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isParent(IBundleMakerArtifact artifact) {

    //
    if (artifact == null) {
      return false;
    }

    //
    if (getParent() == null) {
      return false;
    }

    //
    if (getParent().equals(artifact)) {
      return true;
    }

    //
    return getParent().isParent(artifact);
  }

  @Override
  public IBundleMakerArtifact getParent() {
    return parent;
  }

  public void setParent(IBundleMakerArtifact parent) {
    this.parent = parent;
  }

  @Override
  public <T extends IBundleMakerArtifact> T getParent(Class<T> type) {

    IBundleMakerArtifact parent = this.getParent();

    if (parent == null) {
      return null;
    } else if (parent == this) {
      return null;
    }

    if (type.isAssignableFrom(parent.getClass())) {
      return (T) parent;
    } else {
      return parent.getParent(type);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return this.getName();
  }

  @Override
  public List<? extends IDependency> getDependencies(Collection<? extends IBundleMakerArtifact> artifacts) {
    List<IDependency> dependencies = new ArrayList<IDependency>();

    for (IBundleMakerArtifact artifact : artifacts) {
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
