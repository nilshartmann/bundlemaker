package org.bundlemaker.core.analysis.spi;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Abstract base class for all artifacts.
 * </p>
 * 
 * @author Kai Lehmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractArtifact implements IBundleMakerArtifact {

  /** the name of this artifact */
  private String               _name;

  /** the direct parent */
  private IBundleMakerArtifact _parent;

  /** the root artifact */
  private IRootArtifact        _root;

  /** the properties */
  private Map<Object, Object>  _properties;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifact}.
   * </p>
   * 
   * @param name
   *          the name of this artifact, must not be {@code null}.
   */
  public AbstractArtifact(String name) {
    Assert.isNotNull(name);

    // set the name
    this._name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return _name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInstanceOf(Class<? extends IBundleMakerArtifact> clazz) {

    // return false if clazz is null
    if (clazz == null) {
      return false;
    }

    // return result
    return clazz.isAssignableFrom(this.getClass());
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T extends IBundleMakerArtifact> T castTo(Class<T> clazz) {

    //
    Assert.isNotNull(clazz);
    Assert.isTrue(isInstanceOf(clazz), String.format("Can not cast '%s' to '%s'.", this.getClass(), clazz));

    // cast
    return (T) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModularizedSystem getModularizedSystem() {
    return getRoot().getModularizedSystem();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IAnalysisModelConfiguration getConfiguration() {
    return getRoot().getConfiguration();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IBundleMakerArtifact getParent() {
    return _parent;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T extends IBundleMakerArtifact> T getParent(Class<T> type) {

    // return null if no parent exist
    if (_parent == null || _parent == this) {
      return null;
    }

    //
    if (type.isAssignableFrom(_parent.getClass())) {
      return (T) _parent;
    }

    //
    return _parent.getParent(type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAncestorOf(IBundleMakerArtifact artifact) {

    //
    if (artifact == null) {
      return false;
    }

    //
    IBundleMakerArtifact parent = artifact.getParent();
    while (parent != null) {
      if (this.equals(parent)) {
        return true;
      }
      parent = parent.getParent();
    }

    //
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getRoot() {

    //
    if (_root == null) {
      _root = (IRootArtifact) getParent(IRootArtifact.class);
    }

    //
    return _root;
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
  @SuppressWarnings("unchecked")
  @Override
  public <T> T getProperty(Object key, Class<T> t) {

    // return null if the properties havn't been initialized yet
    if (_properties == null) {
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifact(IBundleMakerArtifact artifact) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifacts(List<? extends IBundleMakerArtifact> artifact) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifacts(IArtifactSelector artifactSelector) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeArtifact(IBundleMakerArtifact artifact) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeArtifacts(List<? extends IBundleMakerArtifact> artifact) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeArtifacts(IArtifactSelector artifactSelector) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<IDependency> getDependenciesFrom() {
    return Collections.emptyList();
  }

  @Override
  public IDependency getDependencyFrom(IBundleMakerArtifact artifact) {
    return null;
  }

  @Override
  public Collection<? extends IDependency> getDependenciesFrom(Collection<? extends IBundleMakerArtifact> artifacts) {
    return Collections.emptyList();
  }

  @Override
  public Collection<? extends IDependency> getDependenciesFrom(IBundleMakerArtifact... artifacts) {
    return Collections.emptyList();
  }

  @Override
  public Collection<IDependency> getDependenciesTo() {
    return Collections.emptyList();
  }

  @Override
  public IDependency getDependencyTo(IBundleMakerArtifact artifact) {
    return null;
  }

  @Override
  public Collection<? extends IDependency> getDependenciesTo(Collection<? extends IBundleMakerArtifact> artifacts) {
    return Collections.emptyList();
  }

  @Override
  public Collection<? extends IDependency> getDependenciesTo(IBundleMakerArtifact... artifacts) {
    return Collections.emptyList();
  }

  @Override
  public Collection<IBundleMakerArtifact> getChildren() {
    return Collections.emptySet();
  }

  @Override
  public <T extends IBundleMakerArtifact> Collection<T> getChildren(Class<T> clazz) {
    return Collections.emptySet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return _name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(IBundleMakerArtifact o) {

    //
    if (o == null) {
      return Integer.MIN_VALUE;
    }

    // compare the qualified name
    return this.getQualifiedName().compareTo(o.getQualifiedName());
  }

  /**
   * <p>
   * Sets the given parent
   * </p>
   * 
   * @param parent
   *          may be {@code null}.
   */
  protected void setParent(IBundleMakerArtifact parent) {
    this._parent = parent;
  }

  /**
   * <p>
   * Sets the name of this {@link IBundleMakerArtifact}.
   * </p>
   * 
   * @param name
   *          must not be {@code null}.
   */
  protected void setName(String name) {
    Assert.isNotNull(name);

    this._name = name;
  }

  /**
   * <p>
   * Internal method that lazily initializes the properties map.
   * </p>
   * 
   * @return
   */
  private Map<Object, Object> properties() {

    // lazy initialize the map
    if (_properties == null) {
      _properties = new HashMap<Object, Object>();
    }

    // return the result
    return _properties;
  }
}
