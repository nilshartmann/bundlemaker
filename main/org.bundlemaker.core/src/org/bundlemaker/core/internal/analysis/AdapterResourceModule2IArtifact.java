package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterResourceModule2IArtifact extends AdapterModule2IArtifact {

  /**
   * <p>
   * Creates a new instance of type {@link AdapterResourceModule2IArtifact}.
   * </p>
   */
  public AdapterResourceModule2IArtifact(IResourceModule resourceModule, IArtifact parent) {
    super(resourceModule, parent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifact(IArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);
    assertCanAdd(artifact);

    //
    super.addArtifact(artifact);

    // TODO: TYPE CHECK??
    AdapterUtils.addPackageToModule(artifact, this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeArtifact(IArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);

    boolean result = false;

    // package type
    if (artifact.getType().equals(ArtifactType.Package)) {

      //
      if (getChildren().contains(artifact)) {
        result = super.removeArtifact(artifact);
        AdapterUtils.removePackageFromModule(artifact, this);
      }
    }

    // package type
    else if (artifact.getType().equals(ArtifactType.Type)) {
      IArtifact packageArtifact = artifact.getParent();
      packageArtifact.removeArtifact(packageArtifact);
    }

    // return the result
    return result;
  }

  @Override
  public boolean canAdd(IArtifact artifact) {
    return artifact != null
        && (artifact.getType().equals(ArtifactType.Package) || artifact.getType().equals(ArtifactType.Type));
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IResourceModule getResourceModule() {
    return (IResourceModule) getModule();
  }
}
