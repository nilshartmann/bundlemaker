package org.bundlemaker.core.analysis.spi;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.AdapterRoot2IArtifact;
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
  private String                    _name;

  /** the direct parent */
  private IBundleMakerArtifact      _parent;

  /** the root artifact */
  private AdapterRoot2IArtifact     _root;

  /** the properties */
  private Map<String, Object>       _properties;

  /** CACHE */
  private Set<IBundleMakerArtifact> _cachedParents;

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

  @Override
  public Collection<? extends IBundleMakerArtifact> getAncestors() {
    return Collections.unmodifiableCollection(_cachedParents);
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

  @Override
  public boolean isAncestorOf(IBundleMakerArtifact artifact) {

    //
    if (artifact == null) {
      return false;
    }

    //
    return ((AbstractArtifact) artifact).getCachedParents().contains(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getRoot() {

    //
    if (_root == null) {
      _root = (AdapterRoot2IArtifact) getParent(IRootArtifact.class);
    }

    //
    return _root;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProperty(String key, Object value) {
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

  public Set<String> getPropertyKeys() {
    if (_properties == null) {
      return Collections.emptySet();
    }

    return _properties.keySet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getProperty(String key) {
    return getProperty(key, Object.class);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IReferencingArtifact> getContainedReferencingArtifacts() {
    return Collections.emptyList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IReferencedArtifact> getContainedReferencedArtifacts() {
    return Collections.emptyList();
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
  public Collection<IDependency> getDependenciesFrom(Collection<? extends IBundleMakerArtifact> artifacts) {
    return Collections.emptyList();
  }

  @Override
  public Collection<IDependency> getDependenciesFrom(IBundleMakerArtifact... artifacts) {
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
  public Collection<IDependency> getDependenciesTo(Collection<? extends IBundleMakerArtifact> artifacts) {
    return Collections.emptyList();
  }

  @Override
  public Collection<IDependency> getDependenciesTo(IBundleMakerArtifact... artifacts) {
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
   * <p>
   * </p>
   * 
   */
  public void invalidateCaches() {
    _cachedParents = null;
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

  public void initializeCaches() {
    getCachedParents();
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
  private Map<String, Object> properties() {

    // lazy initialize the map
    if (_properties == null) {
      _properties = new HashMap<String, Object>();
    }

    // return the result
    return _properties;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private Set<IBundleMakerArtifact> getCachedParents() {

    //
    if (_cachedParents == null) {

      //
      _cachedParents = new HashSet<IBundleMakerArtifact>();

      //
      if (hasParent()) {
        _cachedParents.add(getParent());
        _cachedParents.addAll(((AbstractArtifact) getParent()).getCachedParents());
      }
    }

    //
    return _cachedParents;
  }
}
