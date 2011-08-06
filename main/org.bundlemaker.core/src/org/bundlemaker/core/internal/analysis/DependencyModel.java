package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
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
  public DependencyModel(IModifiableModularizedSystem modifiableModularizedSystem, IArtifact artifactModel) {

    Assert.isNotNull(modifiableModularizedSystem);
    Assert.isNotNull(artifactModel);

    ((AdapterModularizedSystem2IArtifact) artifactModel).setDependencyModel(this);

    _bundleMakerProject = modifiableModularizedSystem.getBundleMakerProject();
    _modifiableModularizedSystem = modifiableModularizedSystem;
    _artifactModel = artifactModel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return _modifiableModularizedSystem.getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifact getRoot() {
    return _artifactModel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IDependencyModel createDependencyModel(String name) {
    throw new UnsupportedOperationException(
        "Unsupported method 'public IDependencyModel createDependencyModel(String name)'");
  }

  @Override
  public IArtifact createArtifact(String name, ArtifactType type) {
    throw new RuntimeException("DO WE NEED THIS METHOD??");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  // TODO: name/qualifiedname
  public IArtifact createArtifactContainer(String name, String qualifiedName, ArtifactType type) {

    //
    Assert.isNotNull(name);
    Assert.isNotNull(qualifiedName);
    Assert.isNotNull(type);

    switch (type) {

    //
    case Root: {
      throw new RuntimeException("Can not create IArtifact of type 'ArtifactType.Root'.");
    }

      //
    case Group: {
      return new AdapterGroup2IArtifact(name, getRoot());
    }

      //
    case Module: {

      String[] splitted = qualifiedName.split("_");

      if (splitted.length == 1) {
        splitted = new String[2];
        splitted[0] = qualifiedName;
        splitted[1] = "0.0.0";
      }

      IResourceModule resourceModule = _modifiableModularizedSystem.createResourceModule(new ModuleIdentifier(
          splitted[0], splitted[1]));

      // 'Workaround' - we have to remove the module form the internal list as it not has been added yet
      _modifiableModularizedSystem.removeModule(resourceModule.getModuleIdentifier());

      // create new resource module adapter
      return new AdapterResourceModule2IArtifact(resourceModule, getRoot());
    }

      //
    case Package: {
      return new AdapterPackage2IArtifact(qualifiedName, getRoot());
    }

      //
    case Resource: {
      throw new RuntimeException("Can not create IArtifact of type 'ArtifactType.Resource'.");
    }

      //
    case Type: {
      throw new RuntimeException("Can not create IArtifact of type 'ArtifactType.Type'.");
    }

      //
    default: {
      break;
    }
    }

    return null;
  }
}
