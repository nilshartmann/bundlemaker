package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

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
  private IArtifact                    _artifactModel;

  /** - */
  private DefaultArtifactCache         _artifactCache;

  /**
   * <p>
   * Creates a new instance of type {@link DependencyModel}.
   * </p>
   * 
   * @param bundleMakerProject
   * @param modifiableModularizedSystem
   * @throws CoreException
   */
  public DependencyModel(IModifiableModularizedSystem modifiableModularizedSystem,
      DefaultArtifactCache defaultArtifactCache) throws CoreException {

    Assert.isNotNull(modifiableModularizedSystem);
    Assert.isNotNull(defaultArtifactCache);

    _artifactCache = defaultArtifactCache;
    _artifactModel = defaultArtifactCache.transform();

    ((AdapterModularizedSystem2IArtifact) _artifactModel).setDependencyModel(this);

    _modifiableModularizedSystem = modifiableModularizedSystem;
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
      // new AdapterGroup2IArtifact(name, getRoot())
      return _artifactCache.getGroupCache().getOrCreate(new Path(qualifiedName));
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

      return _artifactCache.getModuleArtifact(resourceModule);
    }

      //
    case Package: {
      throw new RuntimeException("Can not create IArtifact of type 'ArtifactType.Resource'.");
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

  public DefaultArtifactCache getArtifactCache() {
    return _artifactCache;
  }
}
