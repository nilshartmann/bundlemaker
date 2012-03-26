package org.bundlemaker.analysis.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Implementiert eines gruppierenden Artefaktes
 * 
 * <p>
 * Gruppierende Artefakte koennen anderen gruppierenden Artefakte oder Primarartefakte beinhalten
 * 
 * @author Kai Lehmann
 * @author Frank Schl&uuml;ter
 */
public abstract class AbstractArtifactContainer extends AbstractArtifact {

  private Collection<IArtifact>                 children;

  private Collection<IDependency>               dependencies;

  private Collection<IArtifact>                 leafs;

  private transient Map<IArtifact, IDependency> cachedDependencies;

  public AbstractArtifactContainer(ArtifactType type, String name) {
    super(type, name);

    children = new ArrayList<IArtifact>();
    cachedDependencies = new HashMap<IArtifact, IDependency>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifact getChild(String path) {

    // assert not null
    Assert.isNotNull(path);

    //
    String[] splittedString = path.split("\\|");

    //
    return getChild(splittedString);
  }

  @Override
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
  private IArtifact getChild(String[] splittedString) {

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
      IArtifact directChild = getDirectChild(splittedString[0]);

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

  @Override
  public IDependency getDependency(IArtifact to) {

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
    for (IArtifact child : children) {

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
  public List<IArtifact> invalidateDependencyCache() {

    List<IArtifact> result = new LinkedList<IArtifact>();
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

  public final Map<IArtifact, IDependency> getCachedDependencies() {
    return cachedDependencies;
  }

  @Override
  public void addArtifact(IArtifact artifact) {
    if (!children.contains(artifact)) {
      children.add(artifact);
    }
    AbstractArtifact modifiableArtifact = (AbstractArtifact) artifact;
    modifiableArtifact.setParent(this);
  }

  @Override
  public boolean removeArtifact(IArtifact artifact) {
    return children.remove(artifact);
  }

  @Override
  public boolean contains(IArtifact artifact) {
    if (leafs == null || leafs.isEmpty()) {
      leafs = getLeafs();
    }
    return leafs.contains(artifact);
  }

  @Override
  public Collection<IArtifact> getLeafs() {
    if (leafs == null || leafs.isEmpty()) {
      leafs = new HashSet<IArtifact>();
      for (IArtifact child : children) {
        if (child.getChildren().isEmpty()) {
          leafs.add(child);
        } else {
          if (child instanceof AbstractArtifactContainer) {
            leafs.addAll(((AbstractArtifactContainer) child).getLeafs());
          }
        }
      }

    }
    return leafs;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<? extends IArtifact> getChildren() {
    return Collections.unmodifiableCollection(new LinkedList<IArtifact>(children));
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Collection<IArtifact> getModifiableChildren() {
    return children;
  }

  /**
   * <p>
   * </p>
   * 
   * @param identifier
   * @return
   */
  private IArtifact getDirectChild(String identifier) {

    // assert not null
    Assert.isNotNull(identifier);

    //
    IArtifact result = null;

    // step 1a: search for the qualified name
    for (IArtifact artifact : getChildren()) {

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
    for (IArtifact artifact : getChildren()) {

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
}
