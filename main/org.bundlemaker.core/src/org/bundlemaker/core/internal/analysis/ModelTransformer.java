package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModelTransformer {

  /** - */
  private IModifiableModularizedSystem _modifiableModularizedSystem;

  /** - */
  private IBundleMakerArtifact         _artifactModel;

  /** - */
  private ArtifactCache                _artifactCache;

  /**
   * <p>
   * Creates a new instance of type {@link ModelTransformer}.
   * </p>
   * 
   * @param bundleMakerProject
   * @param modifiableModularizedSystem
   * @throws CoreException
   */
  public ModelTransformer(IModifiableModularizedSystem modifiableModularizedSystem, ArtifactCache defaultArtifactCache)
      throws CoreException {

    Assert.isNotNull(modifiableModularizedSystem);
    Assert.isNotNull(defaultArtifactCache);

    _artifactCache = defaultArtifactCache;
    _artifactModel = defaultArtifactCache.transform();

    ((AdapterModularizedSystem2IArtifact) _artifactModel).setDependencyModel(this);

    _modifiableModularizedSystem = modifiableModularizedSystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IBundleMakerArtifact getRoot() {
    return _artifactModel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ArtifactCache getArtifactCache() {
    return _artifactCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @param qualifiedName
   * @return
   */
  public AbstractBundleMakerArtifactContainer createGroup(IPath qualifiedName) {
    return (AbstractBundleMakerArtifactContainer) createArtifactContainer(qualifiedName.lastSegment(),
        qualifiedName.toOSString(), ArtifactType.Group);
  }

  public IModuleArtifact createModule(IModuleIdentifier moduleIdentifier) {
    return (IModuleArtifact) createArtifactContainer(moduleIdentifier.toString(), moduleIdentifier.toString(),
        ArtifactType.Module);
  }

  /**
   * {@inheritDoc}
   */
  // TODO: name/qualifiedname
  private IBundleMakerArtifact createArtifactContainer(String name, String qualifiedName, ArtifactType type) {

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

      System.out.println("Create group " + qualifiedName);

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
}
