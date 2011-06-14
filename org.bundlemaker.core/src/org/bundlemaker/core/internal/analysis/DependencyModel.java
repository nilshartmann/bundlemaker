package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependencyModel;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

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

    try {
      //
      _artifactModel = ModelTransformer.transformWithAggregatedTypes(_modifiableModularizedSystem);
    } catch (CoreException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new RuntimeException(e.getMessage(), e);
    }
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

    //
    try {
      IModifiableModularizedSystem modularizedSystem = (IModifiableModularizedSystem) _bundleMakerProject
          .createModularizedSystemWorkingCopy(name);

      IDependencyModel result = new DependencyModel(_bundleMakerProject, modularizedSystem);

      return result;

    } catch (CoreException e) {
      // TODO
      e.printStackTrace();
      throw new RuntimeException(e.getMessage(), e);
    }
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
      _modifiableModularizedSystem.getModifiableResourceModulesMap().remove(resourceModule.getModuleIdentifier());

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
