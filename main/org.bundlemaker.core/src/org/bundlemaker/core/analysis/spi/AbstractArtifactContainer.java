package org.bundlemaker.core.analysis.spi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.analysis.AnalysisModelException;
import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.selectors.DefaultArtifactSelector;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.transformation.AddArtifactsTransformation;
import org.bundlemaker.core.transformation.RemoveArtifactsTransformation;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.google.gson.JsonElement;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractArtifactContainer extends AbstractArtifact {

  /** - */
  private Collection<IBundleMakerArtifact>       _children;

  /** - */
  private Map<IBundleMakerArtifact, IDependency> _aggregatedDependenciesTo;

  /** - */
  private Map<IBundleMakerArtifact, IDependency> _aggregatedDependenciesFrom;

  /** - */
  private List<IDependency>                      _allCoreDependenciesTo;

  /** - */
  private List<IDependency>                      _allCoreDependenciesFrom;

  /** - */
  private List<IReferencedArtifact>              _allReferencedArtifacts;

  /** - */
  private List<IReferencingArtifact>             _allReferencingArtifacts;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifactContainer}.
   * </p>
   * 
   * @param type
   * @param name
   */
  public AbstractArtifactContainer(String name) {
    super(name);

    // initialize children
    _children = new ArrayList<IBundleMakerArtifact>();
  }

  /**
   * {@inheritDoc}
   */
  public IBundleMakerArtifact getChild(String path) {

    // assert not null
    Assert.isNotNull(path);

    //
    String[] splittedString = path.split("\\|");

    //
    return getChild(splittedString);
  }

  /**
   * {@inheritDoc}
   */
  public IBundleMakerArtifact getChild(IPath path) {
    return getChild(path.segments());
  }

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @return
   */
  public boolean hasChild(String path) {
    return getChild(path) != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUniquePathIdentifier() {
    return getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getFullPath() {

    //
    if (hasParent() && !(getParent() instanceof IRootArtifact)) {

      //
      IPath path = getParent().getFullPath();
      return path.append(getUniquePathIdentifier());

    } else {

      //
      return new Path(getUniquePathIdentifier());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param splittedString
   * @return
   */
  private IBundleMakerArtifact getChild(String[] splittedString) {

    // assert not null
    Assert.isNotNull(splittedString);

    // if segment count == 0 -> return null
    if (splittedString.length == 0) {
      return null;
    }

    // if segment count = 1 -> return matching direct child
    else if (splittedString.length == 1) {
      return getDirectChild(splittedString[0]);
    }

    // else call recursive
    else {

      // get the direct child
      IBundleMakerArtifact directChild = getDirectChild(splittedString[0]);

      // recurse
      if (directChild != null) {

        //
        if (directChild instanceof AbstractArtifactContainer) {
          String[] newArray = new String[splittedString.length - 1];
          System.arraycopy(splittedString, 1, newArray, 0, newArray.length);
          return ((AbstractArtifactContainer) directChild).getChild(newArray);
        }

        // support for "non-AbstractArtifactContainer" IArtifacts
        else {
          StringBuilder builder = new StringBuilder();
          for (int i = 1; i < splittedString.length; i++) {
            builder.append(splittedString[i]);
            if (i + 1 < splittedString.length) {
              builder.append("|");
            }
          }
          return directChild.getChild(builder.toString());
        }
      }
    }

    // return null
    return null;
  }

  /**************************************************************************************************/

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public List<IReferencingArtifact> getContainedReferencingArtifacts() {

    //
    initializeCaches();

    //
    return _allReferencingArtifacts;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public List<IReferencedArtifact> getContainedReferencedArtifacts() {

    //
    initializeCaches();

    //
    return _allReferencedArtifacts;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IDependency> getDependenciesFrom() {

    //
    initializeCaches();

    // return the core dependencies
    return _allCoreDependenciesFrom;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IDependency getDependencyFrom(IBundleMakerArtifact artifact) {

    // 'aggregated' dependency
    if (!aggregatedDependenciesFrom().containsKey(artifact)) {

      // create new dependency
      Dependency dependency = new Dependency(artifact, this, false);
      for (IDependency reference : getDependenciesFrom()) {
        if (artifact.contains(reference.getFrom())) {
          ((Dependency) dependency).addDependency(reference);
        }
      }

      // store dependency
      aggregatedDependenciesFrom().put(artifact, dependency);
    }

    //
    IDependency dependency = aggregatedDependenciesFrom().get(artifact);
    if (dependency != null && dependency.getWeight() > 0) {
      return dependency;
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IDependency> getDependenciesFrom(Collection<? extends IBundleMakerArtifact> artifacts) {

    //
    List<IDependency> result = new LinkedList<IDependency>();

    //
    for (IBundleMakerArtifact artifact : artifacts) {
      IDependency dependency = getDependencyTo(artifact);
      if (dependency != null) {
        result.add(dependency);
      }
    }

    //
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IDependency> getDependenciesFrom(IBundleMakerArtifact... artifacts) {
    return getDependenciesFrom(Arrays.asList(artifacts));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IDependency> getDependenciesTo() {

    //
    initializeCaches();

    // return the core dependencies
    return _allCoreDependenciesTo;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IDependency getDependencyTo(IBundleMakerArtifact artifact) {

    // 'aggregated' dependency
    if (!aggregatedDependenciesTo().containsKey(artifact)) {

      // create new dependency
      Dependency dependency = new Dependency(this, artifact, false);
      for (IDependency reference : getDependenciesTo()) {
        if (artifact.contains(reference.getTo())) {
          ((Dependency) dependency).addDependency(reference);
        }
      }

      // store dependency
      aggregatedDependenciesTo().put(artifact, dependency);
    }

    //
    IDependency dependency = aggregatedDependenciesTo().get(artifact);
    if (dependency != null && dependency.getWeight() > 0) {
      return dependency;
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IDependency> getDependenciesTo(Collection<? extends IBundleMakerArtifact> artifacts) {

    //
    List<IDependency> result = new LinkedList<IDependency>();

    //
    for (IBundleMakerArtifact artifact : artifacts) {
      IDependency dependency = getDependencyTo(artifact);
      if (dependency != null) {
        result.add(dependency);
      }
    }

    //
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IDependency> getDependenciesTo(IBundleMakerArtifact... artifacts) {
    return getDependenciesTo(Arrays.asList(artifacts));
  }

  /**
   * {@inheritDoc}
   */
  public final void invalidateCaches() {
    super.invalidateCaches();

    //
    if (_allCoreDependenciesFrom != null) {
      _allCoreDependenciesFrom.clear();
      _allCoreDependenciesFrom = null;
    }

    //
    if (_allCoreDependenciesTo != null) {
      _allCoreDependenciesTo.clear();
      _allCoreDependenciesTo = null;
    }

    //
    if (_aggregatedDependenciesTo != null) {
      _aggregatedDependenciesTo.clear();
      _aggregatedDependenciesTo = null;
    }

    //
    if (_aggregatedDependenciesFrom != null) {
      _aggregatedDependenciesFrom.clear();
      _aggregatedDependenciesFrom = null;
    }
  }

  /********************************************************************************************************/

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  protected abstract void onRemoveArtifact(IBundleMakerArtifact artifact);

  /**
   * {@inheritDoc}
   */
  public void removeFromParent() {
    if (this.getParent() != null) {
      this.getParent().removeArtifact(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasParent() {
    return getParent() != null;
  }

  @Override
  public void setParent(IBundleMakerArtifact parent) {
    super.setParent(parent);
    getRoot();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean containsTypesOrResources() {

    // return result
    return containsTypes() || containsResources();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsTypes() {

    // return 'true' if any child contains (or is) a type
    for (IBundleMakerArtifact artifact : getChildren()) {
      if (((IBundleMakerArtifact) artifact).containsTypes()) {
        return true;
      }
    }

    // otherwise return false
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsResources() {

    // return 'true' if any child contains (or is) a resource
    for (IBundleMakerArtifact artifact : getChildren()) {
      if (((IBundleMakerArtifact) artifact).containsResources()) {
        return true;
      }
    }

    // otherwise return false
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean canAdd(IBundleMakerArtifact artifact) {

    if (artifact == null) {
      return false;
    }

    if (this.equals(artifact)) {
      return false;
    }

    if (!((IBundleMakerArtifact) artifact).getRoot().equals(this.getRoot())) {
      return false;
    }

    return handleCanAdd(artifact) == null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addArtifact(IBundleMakerArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);

    //
    assertCanAdd(artifact);

    //
    addArtifacts(new DefaultArtifactSelector(artifact));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifacts(List<? extends IBundleMakerArtifact> artifacts) {

    // assert not null
    Assert.isNotNull(artifacts);

    //
    for (IBundleMakerArtifact artifact : artifacts) {
      assertCanAdd(artifact);
    }

    //
    addArtifacts(new DefaultArtifactSelector(artifacts));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifacts(IArtifactSelector artifactSelector) {

    //
    AddArtifactsTransformation transformation = new AddArtifactsTransformation(this, artifactSelector);

    //
    ((IModifiableModularizedSystem) getModularizedSystem()).applyTransformations(null, transformation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeArtifact(IBundleMakerArtifact artifact) {
    Assert.isNotNull(artifact);

    // remove artifacts
    removeArtifacts(new DefaultArtifactSelector(artifact));

    //
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeArtifacts(List<? extends IBundleMakerArtifact> artifacts) {

    // assert not null
    Assert.isNotNull(artifacts);

    // remove artifacts
    removeArtifacts(new DefaultArtifactSelector(artifacts));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeArtifacts(IArtifactSelector artifactSelector) {

    // assert not null
    Assert.isNotNull(artifactSelector);

    // get the json configuration
    JsonElement jsonConfiguration = new RemoveArtifactsTransformation.Configuration(this,
        artifactSelector).toJsonTree();

    // get the list of artifacts
    List<? extends IBundleMakerArtifact> bundleMakerArtifacts = artifactSelector.getBundleMakerArtifacts();

    // add the artifacts
    for (IBundleMakerArtifact artifact : artifactSelector.getBundleMakerArtifacts()) {
      Assert.isTrue(canRemove(artifact));
    }

    //
    for (IBundleMakerArtifact artifact : bundleMakerArtifacts) {
      onRemoveArtifact(artifact);
    }

    // add the transformation
    getModularizedSystem().getTransformations().add(
        new RemoveArtifactsTransformation(jsonConfiguration));
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  public final void internalAddArtifact(IBundleMakerArtifact artifact) {

    // assert not null
    Assert.isNotNull(artifact);
    Assert.isTrue(!artifact.isAncestorOf(this));
    Assert.isTrue(!artifact.equals(this));

    // if the artifact has a parent, it has to be removed
    if (artifact.getParent() != null) {
      ((AbstractArtifactContainer) artifact.getParent()).internalRemoveArtifact(artifact);
    }

    // call super
    if (!_children.contains(artifact)) {
      _children.add(artifact);
    }
    AbstractArtifact modifiableArtifact = (AbstractArtifact) artifact;
    modifiableArtifact.setParent(this);
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  public final void internalRemoveArtifact(IBundleMakerArtifact artifact) {

    // assert not null
    Assert.isNotNull(artifact);

    // set parent to null
    if (artifact.getParent() != null) {
      ((AbstractArtifact) artifact).setParent(null);
    }

    // call super
    _children.remove(artifact);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final IBundleMakerArtifact artifact) {
    return this.isAncestorOf(artifact);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IBundleMakerArtifact> getChildren() {
    return Collections.unmodifiableCollection(new LinkedList<IBundleMakerArtifact>(_children));
  }

  /**
   * {@inheritDoc}
   */

  @Override
  @SuppressWarnings("unchecked")
  public <T extends IBundleMakerArtifact> Collection<T> getChildren(Class<T> clazz) {

    //
    Assert.isNotNull(clazz);

    //
    List<T> result = new ArrayList<T>();

    //
    for (IBundleMakerArtifact child : _children) {
      if (clazz.isAssignableFrom(child.getClass())) {
        result.add((T) child);
      }
    }

    //
    return Collections.unmodifiableCollection(result);
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  protected String handleCanAdd(IBundleMakerArtifact artifact) {
    return null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @deprecated use canAdd and Assert.isTrue() instead
   */
  @Deprecated
  public void assertCanAdd(IBundleMakerArtifact artifact) {

    //
    if (artifact == null) {
      throw new AnalysisModelException("Can not add 'null' to " + this);
    }

    if (artifact == this) {
      throw new AnalysisModelException("Can not artifact to itself. " + this);
    }

    //
    String canAddMessage = handleCanAdd(artifact);

    //
    if (canAddMessage != null) {
      throw new AnalysisModelException("Can not add " + artifact + " to " + this + ":\n" + canAddMessage);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  protected String handleCanRemove(IBundleMakerArtifact artifact) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean canRemove(IBundleMakerArtifact artifact) {

    if (artifact == null) {
      return false;
    }

    if (this.equals(artifact)) {
      return false;
    }

    if (!((IBundleMakerArtifact) artifact).getRoot().equals(this.getRoot())) {
      return false;
    }

    if (!isAncestorOf(artifact)) {
      return false;
    }

    return handleCanRemove(artifact) == null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param identifier
   * @return
   */
  private IBundleMakerArtifact getDirectChild(String identifier) {

    // assert not null
    Assert.isNotNull(identifier);

    //
    IBundleMakerArtifact result = null;

    // step 1a: search for the qualified name
    for (IBundleMakerArtifact artifact : getChildren()) {

      // check the qualified name
      if (identifier.equals(artifact.getQualifiedName())) {

        // check if null...
        if (result == null) {
          result = artifact;
        }

        // ... else throw exception
        else {
          throw new RuntimeException(String.format("Ambigous identifier '%s' [%s, %s]", result, getQualifiedName(),
              getChildren()));
        }
      }
    }

    // step 1b:
    if (result != null) {
      return result;
    }

    // step 2: search for the simple name
    for (IBundleMakerArtifact artifact : getChildren()) {

      // check the qualified name
      if (identifier.equals(artifact.getName())) {

        // check if null...
        if (result == null) {
          result = artifact;
        }

        // ... else throw exception
        else {
          // TODO
          throw new RuntimeException(String.format("Ambigous identifier '%s' [%s, %s]", result, result.toString(),
              artifact.toString()));
        }
      }
    }

    // return result
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Collection<IBundleMakerArtifact> getModifiableChildrenCollection() {
    return _children;
  }

  /**
   * <p>
   * </p>
   * 
   */
  public void initializeCaches() {

    //
    super.initializeCaches();

    //
    if (_allCoreDependenciesTo == null || _allReferencingArtifacts == null ||
        _allCoreDependenciesFrom == null || _allReferencedArtifacts == null) {

      //
      _allReferencingArtifacts = new ArrayList<IReferencingArtifact>();
      _allReferencedArtifacts = new ArrayList<IReferencedArtifact>();
      _allCoreDependenciesTo = new ArrayList<IDependency>();
      _allCoreDependenciesFrom = new ArrayList<IDependency>();

      // add core dependencies
      if (this instanceof IReferencingArtifact) {
        _allReferencingArtifacts.add(((IReferencingArtifact) this));
        _allCoreDependenciesTo.addAll(((IReferencingArtifact) this).getDependenciesTo());
      }
      // add core dependencies
      if (this instanceof IReferencedArtifact) {
        _allReferencedArtifacts.add((IReferencedArtifact) this);
        _allCoreDependenciesFrom.addAll(((IReferencedArtifact) this).getDependenciesFrom());
      }

      //
      for (IBundleMakerArtifact child : _children) {

        // call recursive...
        _allCoreDependenciesTo.addAll(child.getDependenciesTo());
        _allCoreDependenciesFrom.addAll(child.getDependenciesFrom());
        _allReferencingArtifacts.addAll(child.getContainedReferencingArtifacts());
        _allReferencedArtifacts.addAll(child.getContainedReferencedArtifacts());
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private Map<IBundleMakerArtifact, IDependency> aggregatedDependenciesTo() {

    //
    if (_aggregatedDependenciesTo == null) {
      _aggregatedDependenciesTo = new HashMap<IBundleMakerArtifact, IDependency>();
    }

    //
    return _aggregatedDependenciesTo;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private Map<IBundleMakerArtifact, IDependency> aggregatedDependenciesFrom() {

    //
    if (_aggregatedDependenciesFrom == null) {
      _aggregatedDependenciesFrom = new HashMap<IBundleMakerArtifact, IDependency>();
    }

    //
    return _aggregatedDependenciesFrom;
  }
}
