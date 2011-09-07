package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ArtifactTreeChangedEvent;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;
import org.bundlemaker.core.internal.analysis.transformer.ModulePackageKey;
import org.bundlemaker.core.internal.analysis.transformer.caches.ModuleCache.ModuleKey;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterResourceModule2IArtifact extends AdapterModule2IArtifact {

  /** - */
  private DefaultArtifactCache _artifactCache;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterResourceModule2IArtifact}.
   * </p>
   */
  public AdapterResourceModule2IArtifact(IResourceModule resourceModule, IArtifact parent,
      DefaultArtifactCache artifactCache) {
    super(resourceModule, parent);

    //
    Assert.isNotNull(artifactCache);

    // set the default artifact cache
    _artifactCache = artifactCache;
  }

  /**
   * {@inheritDoc}
   */
  public void setName(String name) {
    super.setName(name);
    ((AdapterModularizedSystem2IArtifact) getRoot()).fireArtifactTreeChangedEvent(new ArtifactTreeChangedEvent());
  }

  @Override
  public boolean containsTypesOrResources() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifact(IArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);
    assertCanAdd(artifact);

    // handle package
    if (artifact.getType().equals(ArtifactType.Package)) {
      handleAddPackage(artifact);
    } else if (artifact.getType().equals(ArtifactType.Resource)) {
      handleAddResource(artifact);
    } else if (artifact.getType().equals(ArtifactType.Type)) {
      handleAddType(artifact);
    }
  }

  private void handleAddType(IArtifact artifact) {

    //
    if (artifact.getParent().getType().equals(ArtifactType.Resource)) {

      handleAddResource(artifact.getParent());

    } else {

      // step 1: get the containing package artifact
      IPackageArtifact oldPackageArtifact = (IPackageArtifact) artifact.getParent(ArtifactType.Package);

      //
      ModulePackageKey modulePackageKey = new ModulePackageKey(new ModuleKey(getAssociatedModule()),
          oldPackageArtifact.getQualifiedName());

      //
      IPackageArtifact newPackageArtifact = (IPackageArtifact) _artifactCache.getPackageCache().getOrCreate(
          modulePackageKey);

      //
      newPackageArtifact.addArtifact(artifact);
    }
  }

  private void handleAddResource(IArtifact artifact) {

    // step 1: get the containing package artifact
    IPackageArtifact oldPackageArtifact = (IPackageArtifact) artifact.getParent(ArtifactType.Package);

    //
    ModulePackageKey modulePackageKey = new ModulePackageKey(new ModuleKey(getAssociatedModule()),
        oldPackageArtifact.getQualifiedName());

    //
    IPackageArtifact newPackageArtifact = (IPackageArtifact) _artifactCache.getPackageCache().getOrCreate(
        modulePackageKey);

    //
    newPackageArtifact.addArtifact(artifact);
  }

  private void handleAddPackage(IArtifact artifact) {

    //
    ModulePackageKey modulePackageKey = new ModulePackageKey(new ModuleKey(getAssociatedModule()),
        artifact.getQualifiedName());

    //
    IPackageArtifact packageArtifact = (IPackageArtifact) _artifactCache.getPackageCache()
        .getOrCreate(modulePackageKey);

    // move the children to the new package artifact
    for (IArtifact child : artifact.getChildren()) {
      if (child.getType().equals(ArtifactType.Resource) || child.getType().equals(ArtifactType.Type)) {
        packageArtifact.addArtifact(child);
      } else if (child.getType().equals(ArtifactType.Package)) {
        handleAddPackage(child);
      }
    }

    // else {
    // super.addArtifact(packageArtifact);
    // // TODO: TYPE CHECK??
    // AdapterUtils.addPackageToModule(artifact, this);
    // }
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

  /**
   * {@inheritDoc}
   */
  @Override
  public String handleCanAdd(IArtifact artifact) {

    // a resource module artifact can contain packages, types and resources
    if (!(artifact.getType().equals(ArtifactType.Package) || artifact.getType().equals(ArtifactType.Type) || artifact
        .getType().equals(ArtifactType.Resource))) {

      return "Only packages, types or resources can be added to a resource module.";
    }

    return null;
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(IArtifactTreeVisitor visitor) {

    //
    if (visitor.visit(this)) {
      //
      for (IArtifact artifact : getChildren()) {
        ((IAdvancedArtifact) artifact).accept(visitor);
      }
    }
  }

}
