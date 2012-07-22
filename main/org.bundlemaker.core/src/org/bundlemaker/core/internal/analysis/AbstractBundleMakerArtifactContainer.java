package org.bundlemaker.core.internal.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.impl.Dependency;
import org.bundlemaker.core.analysis.ArtifactHelper;
import org.bundlemaker.core.analysis.ArtifactModelException;
import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.transformation.AddArtifactsTransformation;
import org.bundlemaker.core.transformation.RemoveTransformation;
import org.bundlemaker.core.transformation.SimpleArtifactSelector;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractBundleMakerArtifactContainer extends AbstractBundleMakerArtifact implements
    IBundleMakerArtifact {

  /** - */
  private IRootArtifact                                    _root;

  private Collection<IBundleMakerArtifact>                 children;

  private Collection<IDependency>                          dependencies;

  private Collection<IBundleMakerArtifact>                 leafs;

  private transient Map<IBundleMakerArtifact, IDependency> cachedDependencies;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractBundleMakerArtifactContainer}.
   * </p>
   * 
   * @param type
   * @param name
   */
  public AbstractBundleMakerArtifactContainer(ArtifactType type, String name) {
    super(type, name);

    children = new ArrayList<IBundleMakerArtifact>();
    cachedDependencies = new HashMap<IBundleMakerArtifact, IDependency>();
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
  @Override
  public IBundleMakerArtifact getChild(IPath path) {
    return getChild(path.segments());
  }

  public boolean hasChild(String path) {
    return getChild(path) != null;
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
        if (directChild instanceof AbstractBundleMakerArtifactContainer) {
          String[] newArray = new String[splittedString.length - 1];
          System.arraycopy(splittedString, 1, newArray, 0, newArray.length);
          return ((AbstractBundleMakerArtifactContainer) directChild).getChild(newArray);
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

  @Override
  public IDependency getDependency(IBundleMakerArtifact to) {

    if (this.equals(to)) {
      return new Dependency(this, to, 0);
    }

    IDependency dependency = cachedDependencies.get(to);
    if (dependency != null) {
      return dependency;
    } else {
      dependency = new Dependency(this, to, 0);
      for (IDependency reference : getDependencies()) {
        if (to.contains(reference.getTo())) {
          ((Dependency) dependency).addDependency(reference);
          ((Dependency) dependency).addWeight();
        }
      }
      cachedDependencies.put(to, dependency);
      return dependency;
    }

  }

  @Override
  public Collection<IDependency> getDependencies() {
    if (dependencies == null) {
      aggregateDependencies();
    }
    return dependencies;
  }

  /**
   * <p>
   * </p>
   */
  private void aggregateDependencies() {

    //
    dependencies = new ArrayList<IDependency>();

    //
    for (IBundleMakerArtifact child : children) {

      //
      Collection<IDependency> childDependencies = child.getDependencies();

      //
      dependencies.addAll(childDependencies);
    }

  }

  /**
   * <p>
   * </p>
   */
  public List<IBundleMakerArtifact> invalidateDependencyCache() {

    List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();
    result.add(this);

    //
    dependencies = null;

    //
    if (cachedDependencies != null) {
      cachedDependencies.clear();
    }

    //
    leafs = null;

    //
    return result;
  }

  public final Map<IBundleMakerArtifact, IDependency> getCachedDependencies() {
    return cachedDependencies;
  }

  /**
   * {@inheritDoc}
   */
  public Collection<? extends IDependency> getDependencies(IBundleMakerArtifact... artifacts) {
    return getDependencies(Arrays.asList(artifacts));
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  protected abstract void onRemoveArtifact(IBundleMakerArtifact artifact);

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  protected abstract void onAddArtifact(IBundleMakerArtifact artifact);

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

  @Override
  public <T extends IBundleMakerArtifact> T findChild(Class<T> clazz, String filter) {
    return ArtifactHelper.findChild(this, filter, clazz);
  }

  @Override
  public <T extends IBundleMakerArtifact> List<T> findChildren(Class<T> clazz) {
    return ArtifactHelper.findChildren(this, clazz);
  }

  @Override
  public <T extends IBundleMakerArtifact> List<T> findChildren(Class<T> clazz, String filter) {
    return ArtifactHelper.findChildren(this, filter, clazz);
  }

  @Override
  public <T extends IBundleMakerArtifact> T getChildByPath(Class<T> clazz, IPath path) {
    return ArtifactHelper.getChildByPath(this, path, clazz);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifactModelConfiguration getConfiguration() {
    return getRoot().getConfiguration();
  }

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
  public IRootArtifact getRoot() {

    //
    if (_root == null) {
      _root = (IRootArtifact) getParent(ArtifactType.Root);
    }

    //
    return _root;
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
  public IModularizedSystem getModularizedSystem() {
    return getRoot().getModularizedSystem();
  }

  public IBundleMakerArtifact getParent() {
    return (IBundleMakerArtifact) super.getParent();
  }

  public IBundleMakerArtifact getParent(ArtifactType type) {
    return (IBundleMakerArtifact) super.getParent(type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addArtifact(IBundleMakerArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);
    assertCanAdd(artifact);

    //
    getRoot().invalidateDependencyCache();

    //
    onAddArtifact((IBundleMakerArtifact) artifact);

    //
    getRoot().invalidateDependencyCache();

    // fire
    ((AdapterRoot2IArtifact) getRoot()).fireArtifactModelChanged();

    //
    getModularizedSystem().getTransformations().add(
        new AddArtifactsTransformation(this, new SimpleArtifactSelector(artifact)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifacts(List<? extends IBundleMakerArtifact> artifact) {

    // assert not null
    Assert.isNotNull(artifact);

    //
    for (IBundleMakerArtifact iBundleMakerArtifact : artifact) {
      addArtifact(iBundleMakerArtifact);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifacts(IArtifactSelector artifactSelector) {

    // assert not null
    Assert.isNotNull(artifactSelector);

    // get the list of artifacts
    List<? extends IBundleMakerArtifact> bundleMakerArtifacts = artifactSelector.getBundleMakerArtifacts();

    // add the artifacts
    if (bundleMakerArtifacts != null) {
      addArtifacts(bundleMakerArtifacts);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeArtifact(IBundleMakerArtifact artifact) {
    Assert.isNotNull(artifact);
    Assert.isTrue(canRemove(artifact));

    onRemoveArtifact((IBundleMakerArtifact) artifact);

    //
    getRoot().invalidateDependencyCache();

    // fire model changed
    ((AdapterRoot2IArtifact) getRoot()).fireArtifactModelChanged();

    //
    getModularizedSystem().getTransformations().add(new RemoveTransformation(this, artifact));

    //
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeArtifacts(List<? extends IBundleMakerArtifact> artifact) {

    // assert not null
    Assert.isNotNull(artifact);

    // iterate over all artifacts
    for (IBundleMakerArtifact iBundleMakerArtifact : artifact) {
      removeArtifact(iBundleMakerArtifact);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeArtifacts(IArtifactSelector artifactSelector) {

    // assert not null
    Assert.isNotNull(artifactSelector);

    // get the list of artifacts
    List<? extends IBundleMakerArtifact> bundleMakerArtifacts = artifactSelector.getBundleMakerArtifacts();

    // add the artifacts
    if (bundleMakerArtifacts != null) {
      addArtifacts(bundleMakerArtifacts);
    }
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
    Assert.isTrue(!isParent(artifact));
    Assert.isTrue(!artifact.equals(this));

    // if the artifact has a parent, it has to be removed
    if (artifact.getParent() != null) {
      ((AbstractBundleMakerArtifactContainer) artifact.getParent()).internalRemoveArtifact(artifact);
    }

    // call super
    if (!children.contains(artifact)) {
      children.add(artifact);
    }
    AbstractBundleMakerArtifact modifiableArtifact = (AbstractBundleMakerArtifact) artifact;
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
      ((AbstractBundleMakerArtifact) artifact).setParent(null);
    }

    // call super
    children.remove(artifact);
  }

  @Override
  public boolean contains(IBundleMakerArtifact artifact) {
    if (leafs == null || leafs.isEmpty()) {
      leafs = getLeafs();
    }
    return leafs.contains(artifact);
  }

  @Override
  public Collection<IBundleMakerArtifact> getLeafs() {
    if (leafs == null || leafs.isEmpty()) {
      leafs = new HashSet<IBundleMakerArtifact>();
      for (IBundleMakerArtifact child : children) {
        if (child.getChildren().isEmpty()) {
          leafs.add(child);
        } else {
          if (child instanceof AbstractBundleMakerArtifactContainer) {
            leafs.addAll(((AbstractBundleMakerArtifactContainer) child).getLeafs());
          }
        }
      }

    }
    return leafs;
  }

  // TODO
  public boolean isAncestor(final IBundleMakerArtifact bundleMakerArtifact) {

    //
    if (bundleMakerArtifact == null) {
      return false;
    }

    //
    final boolean[] result = new boolean[1];

    //
    this.accept(new IArtifactTreeVisitor.Adapter() {
      @Override
      public boolean onVisit(IBundleMakerArtifact artifact) {

        //
        if (result[0]) {
          return false;
        }

        //
        if (artifact == bundleMakerArtifact) {
          result[0] = true;
        }

        //
        return !result[0];
      }
    });

    //
    return result[0];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IBundleMakerArtifact> getChildren() {
    return Collections.unmodifiableCollection(new LinkedList<IBundleMakerArtifact>(children));
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
    for (IBundleMakerArtifact child : children) {
      if (clazz.isAssignableFrom(child.getClass())) {
        result.add((T) child);
      }
    }

    //
    return Collections.unmodifiableCollection(result);
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
  protected void assertCanAdd(IBundleMakerArtifact artifact) {

    //
    if (artifact == null) {
      throw new ArtifactModelException("Can not add 'null' to " + this);
    }

    if (artifact == this) {
      throw new ArtifactModelException("Can not artifact to itself. " + this);
    }

    //
    String canAddMessage = handleCanAdd(artifact);

    //
    if (canAddMessage != null) {
      throw new ArtifactModelException("Can not add " + artifact + " to " + this + ":\n" + canAddMessage);
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

    if (!isAncestor(artifact)) {
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

  public Collection<IBundleMakerArtifact> getModifiableChildren() {
    return children;
  }
}
