package org.bundlemaker.core.internal.analysis;

import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.model.IDependency;
import org.bundlemaker.core.internal.analysis.model.Dependency;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterResourceModule2IArtifact extends AbstractArtifactContainer implements IArtifact {

  /** the resource module */
  private IResourceModule _resourceModule;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterResourceModule2IArtifact}.
   * </p>
   */
  public AdapterResourceModule2IArtifact(IResourceModule resourceModule) {
    super(ArtifactType.Module);

    Assert.isNotNull(resourceModule);

    // set the resource module
    _resourceModule = resourceModule;
  }

  @Override
  public String getName() {
    return _resourceModule.getModuleIdentifier().toString();
  }

  @Override
  public String getQualifiedName() {

    String classification = "";

    if (_resourceModule.hasClassification()) {
      classification = _resourceModule.getClassification().toString() + "/";
    }

    return classification + _resourceModule.getModuleIdentifier().toString();
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
  public Dependency addDependency(IArtifact artifact) {
    // TODO Auto-generated method stub
    return super.addDependency(artifact);
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
