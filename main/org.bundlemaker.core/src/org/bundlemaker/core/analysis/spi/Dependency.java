package org.bundlemaker.core.analysis.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.analysis.DependencyKind;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.eclipse.core.runtime.Assert;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.ExceptionFactory;

/**
 * <p>
 * </p>
 */
public class Dependency implements IDependency {

  private final String            _id;

  /** - */
  private IBundleMakerArtifact    _to;

  /** - */
  private IBundleMakerArtifact    _from;

  /** - */
  private Collection<IDependency> _dependencies;

  /** - */
  private boolean                 _isInitialized;

  /** - */
  private boolean                 _isCoreDependency;

  /** - */
  private Map<String, Object>     _properties;

  /** - */
  private DependencyKind          dependencyKind = DependencyKind.USES;

  /**
   * <p>
   * Creates a new instance of type {@link Dependency}.
   * </p>
   * 
   * @param from
   * @param to
   * @param isCoreDependency
   */
  public Dependency(IBundleMakerArtifact from, IBundleMakerArtifact to, boolean isCoreDependency) {

    Assert.isNotNull(from);
    Assert.isNotNull(to);

    // TODO
    _id = (isCoreDependency ? "core_" : "") + from.getId() + ">" + to.getId();

    //
    _from = from;
    _to = to;

    //
    _isCoreDependency = isCoreDependency;
  }

  /**
   * <p>
   * </p>
   * 
   * @param dependencyKind
   */
  public void setDependencyKind(DependencyKind dependencyKind) {
    this.dependencyKind = dependencyKind;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DependencyKind getDependencyKind() {
    return dependencyKind;
  }

  /**
   * Fuegt der Abhaengigkeit eine andere Abhaengigkeit hinzu. Diese Methode wird verwendet, um aggregierte
   * Abhaengigkeiten zu erstellen
   * 
   * @param dependency
   *          Abhaengigkeit, die hinzugefuegt werden soll
   */
  public void addDependency(IDependency dependency) {
    if (_dependencies == null) {
      _dependencies = new ArrayList<IDependency>();
    }
    _dependencies.add(dependency);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isInitialized() {
    return _isCoreDependency || _isInitialized;
  }

  /**
   * <p>
   * </p>
   * 
   * @param isInitialized
   */
  public void setInitialized() {
    _isInitialized = true;
  }

  /**
   * {@inheritDoc}
   */
  public void clearDependencies() {

    // clear the dependencies
    if (_dependencies != null) {
      _dependencies.clear();
    }

    // set initialized
    _isInitialized = false;
  }

  /**
   * @return the weight
   */
  @Override
  public int getWeight() {

    //
    if (_dependencies != null && !_dependencies.isEmpty()) {
      return _isCoreDependency ? _dependencies.size() + 1 : _dependencies.size();
    } else {
      return _isCoreDependency ? 1 : 0;
    }
  }

  /**
   * @return the from
   */
  @Override
  public IBundleMakerArtifact getFrom() {
    return _from;
  }

  @Override
  public IBundleMakerArtifact getTo() {
    return _to;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();

    sb.append("Dependency( ");
    sb.append(this.getFrom().getQualifiedName());
    sb.append(" ");
    sb.append(dependencyKind);
    sb.append(" ");
    sb.append(this.getTo().getQualifiedName());
    sb.append(": ");
    sb.append(this.getWeight());
    sb.append(" )");

    return sb.toString();
  }

  @Override
  /**
   * Gibt eine <code>Collection</code> von Abhaengigkeiten zurueck, falls die
   * Abhaengigkeit aus aggegrierten Abhaengigkeiten besteht.
   * 
   * @return <code>Collection</code> von Abhaengigkeiten
   * 
   */
  public Collection<IDependency> getDependencies() {
    return _dependencies;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IDependency> getCoreDependencies() {
    Collection<IDependency> result = new LinkedList<IDependency>();
    getLeafDependencies(result);
    return result;
  }

  public void getLeafDependencies(Collection<IDependency> leafDependencies) {

    //
    if (_isCoreDependency) {
      leafDependencies.add(this);
    }

    //
    else {
      if (_dependencies != null) {
        for (IDependency dependency : _dependencies) {
          ((Dependency) dependency).getLeafDependencies(leafDependencies);
        }
      }
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_from == null) ? 0 : _from.hashCode());
    result = prime * result + (_isCoreDependency ? 1231 : 1237);
    result = prime * result + ((_to == null) ? 0 : _to.hashCode());
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
    Dependency other = (Dependency) obj;
    if (_from == null) {
      if (other._from != null)
        return false;
    } else if (!_from.equals(other._from))
      return false;
    if (_isCoreDependency != other._isCoreDependency)
      return false;
    if (_to == null) {
      if (other._to != null)
        return false;
    } else if (!_to.equals(other._to))
      return false;
    return true;
  }

  @Override
  public Object getProperty(String key) {
    return properties().get(key);
  }

  @Override
  public Set<String> getPropertyKeys() {
    return properties().keySet();
  }

  @Override
  public void setProperty(String key, Object value) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Element#removeProperty(java.lang.String)
   */
  @Override
  public Object removeProperty(String key) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Element#getId()
   */
  @Override
  public Object getId() {
    return this._id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Edge#getVertex(com.tinkerpop.blueprints.Direction)
   */
  @Override
  public Vertex getVertex(Direction direction) throws IllegalArgumentException {
    if (direction == Direction.BOTH) {
      throw ExceptionFactory.bothIsNotSupported();
    }

    IBundleMakerArtifact artifact = null;
    // same "wrong" mapping as in com.tinkerpop.blueprints.impls.neo4j.Neo4jEdge.getVertex(Direction)
    if (direction == Direction.IN) {
      artifact = getTo();
    } else if (direction == Direction.OUT) {
      artifact = getFrom();
    }

    return artifact;

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Edge#getLabel()
   */
  @Override
  public String getLabel() {
    return getDependencyKind().name().toLowerCase();
  }

  /**
   * (Lazy) initializes the properties map and add the default properties
   * 
   * @return
   */
  protected Map<String, Object> properties() {
    if (_properties == null) {
      Map<String, Object> properties = new Hashtable<String, Object>();
      addDefaultProperties(properties);
      _properties = properties;
    }

    return this._properties;
  }

  /**
   * Sets the default properties for a Dependency.
   * 
   * <table>
   * <tr>
   * <th>Name</th>
   * <th>Value</th>
   * </tr>
   * <tr>
   * <td>type</td>
   * <td>Uses, Implements, Extends, see {@link DependencyKind}</td>
   * </tr>
   * <tr>
   * <td>name</td>
   * <td>Name of this dependency following this convention: from.name-to.name</td>
   * </table>
   * 
   * @param properties
   */
  protected void addDefaultProperties(Map<String, Object> properties) {
    properties.put("type", getDependencyKind().name().toLowerCase());
    properties.put("name", getFrom().getName() + "-" + getTo().getName());
  }

}
