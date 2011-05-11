package org.bundlemaker.core.analysis.internal;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependencyModel;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependencyModel implements IDependencyModel {

  /** - */
  private IModifiableModularizedSystem _modifiableModularizedSystem;

  /** - */
  private IBundleMakerProject          _bundleMakerProject;

  /** - */
  private IArtifact                    _artifactModel;

  /**
   * <p>
   * Creates a new instance of type {@link DependencyModel}.
   * </p>
   * 
   * @param bundleMakerProject
   * @param modifiableModularizedSystem
   */
  public DependencyModel(IBundleMakerProject bundleMakerProject,
      IModifiableModularizedSystem modifiableModularizedSystem) {

    Assert.isNotNull(bundleMakerProject);
    Assert.isNotNull(modifiableModularizedSystem);

    _bundleMakerProject = bundleMakerProject;
    _modifiableModularizedSystem = modifiableModularizedSystem;
  }

  @Override
  public String getName() {
    return _modifiableModularizedSystem.getName();
  }

  @Override
  public IArtifact getRoot() {
    return _artifactModel;
  }

  @Override
  public IDependencyModel createDependencyModel(String name) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IArtifact createArtifact(String name, ArtifactType type) {
    throw new RuntimeException("DO WE NEED THIS METHOD??");
  }

  @Override
  public IArtifact createArtifactContainer(String name, String qualifiedName, ArtifactType type) {

    return null;
  }
}
