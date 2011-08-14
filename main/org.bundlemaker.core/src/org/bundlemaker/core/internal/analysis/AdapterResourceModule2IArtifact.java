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
import org.bundlemaker.core.modules.IModule;
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

    _artifactCache = artifactCache;
  }

  /**
   * {@inheritDoc}
   */
  public void setName(String name) {
    super.setName(name);

    ((AdapterModularizedSystem2IArtifact) getRoot()).fireArtifactTreeChangedEvent(new ArtifactTreeChangedEvent());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifact(IArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);
    assertCanAdd(artifact);

    System.out.println("Name " + artifact.getName());
    System.out.println("Qualified Name " + artifact.getQualifiedName());

    if (artifact.getType().equals(ArtifactType.Package)) {

      //
      IModule module = getAssociatedModule();
      String packageName = artifact.getQualifiedName();
      ModulePackageKey modulePackageKey = new ModulePackageKey(new ModuleKey(module), packageName);
      IPackageArtifact packageArtifact = (IPackageArtifact) _artifactCache.getPackageCache().getOrCreate(
          modulePackageKey);
      //
      if (packageArtifact.getParent() != null) {

        // move the children to the new package artifact
        for (IArtifact child : artifact.getChildren()) {
          packageArtifact.addArtifact(child);
        }

      } else {
        super.addArtifact(packageArtifact);
        // TODO: TYPE CHECK??
        AdapterUtils.addPackageToModule(artifact, this);
      }
    }
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
  public boolean handleCanAdd(IArtifact artifact) {
    return artifact != null
        && (artifact.getType().equals(ArtifactType.Package) || artifact.getType().equals(ArtifactType.Type) || artifact
            .getType().equals(ArtifactType.Resource));
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
