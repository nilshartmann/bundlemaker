package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core._type.analysis.ITypeArtifact;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.resource.IModule;

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
  public AdapterResourceModule2IArtifact(IModule resourceModule, IBundleMakerArtifact parent) {
    super(resourceModule, parent);
  }

  /**
   * {@inheritDoc}
   */
  public void setName(String name) {
    super.setName(name);
  }

  @Override
  protected void onRemoveArtifact(IBundleMakerArtifact artifact) {
    AdapterUtils.removeArtifact(artifact, this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String handleCanAdd(IBundleMakerArtifact artifact) {

    // a resource module artifact can contain packages, types and resources
    if (!(artifact.isInstanceOf(IPackageArtifact.class) || artifact.isInstanceOf(ITypeArtifact.class) || artifact
        .isInstanceOf(IResourceArtifact.class))) {

      return "Only packages, types or resources can be added to a resource module.";
    }

    //
    IModuleArtifact moduleArtifact = (IModuleArtifact) artifact.getParent(IModuleArtifact.class);

    // TODO
    if (moduleArtifact != null && !(moduleArtifact.getAssociatedModule().isResourceModule())) {
      return "Can not add packages, types or resources from a non-resource module.";
    }
    return null;
  }

  @Override
  public boolean isResourceModule() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(IAnalysisModelVisitor visitor) {

    //
    if (visitor.visit(this)) {

      //
      for (IBundleMakerArtifact artifact : getChildren()) {
        ((IBundleMakerArtifact) artifact).accept(visitor);
      }
    }
  }
}
