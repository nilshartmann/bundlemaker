package org.bundlemaker.core.internal.analysis;

import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.model.IDependency;
import org.bundlemaker.core.modules.IModularizedSystem;

public class AdapterGroup2IArtifact extends AbstractArtifact implements IArtifact {

  /**
   * <p>
   * Creates a new instance of type {@link AdapterGroup2IArtifact}.
   * </p>
   *
   * @param modularizedSystem
   */
  public AdapterGroup2IArtifact() {
    super( ArtifactType.Group);
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArtifactType getType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getQualifiedName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addArtifact(IArtifact artifact) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean removeArtifact(IArtifact artifact) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setParent(IArtifact parent) {
    // TODO Auto-generated method stub

  }

  @Override
  public Integer size() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IArtifact getParent(ArtifactType type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IArtifact> getChildren() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean contains(IArtifact artifact) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public IDependency getDependency(IArtifact artifact) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<IDependency> getDependencies(Collection<IArtifact> artifacts) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IDependency addDependency(IArtifact artifact) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IDependency> getDependencies() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setOrdinal(Integer ordinal) {
    // TODO Auto-generated method stub

  }

  @Override
  public Integer getOrdinal() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IArtifact> getLeafs() {
    // TODO Auto-generated method stub
    return null;
  }
}
