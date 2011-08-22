package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
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
public class AdapterPackage2IArtifact extends AbstractAdvancedContainer implements IPackageArtifact {

  /** - */
  private String               _qualifiedName;

  /** - */
  private boolean              _isFlat = true;

  /** - */
  private boolean              _isVirtual;

  /** - */
  private DefaultArtifactCache _artifactCache;

  /** - */
  private IModule              _containingModule;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterPackage2IArtifact}.
   * </p>
   * 
   * @param qualifiedName
   * @param parent
   */
  public AdapterPackage2IArtifact(String qualifiedName, IArtifact parent, boolean isVirtual, IModule containingModule,
      DefaultArtifactCache artifactCache) {
    super(ArtifactType.Package, _getName(qualifiedName));

    // set parent/children dependency
    if (parent != null) {
      setParent(parent);
      ((AbstractAdvancedContainer) parent).getModifiableChildren().add(this);
    }

    Assert.isNotNull(qualifiedName);

    // set the qualified name
    _qualifiedName = qualifiedName;

    _isVirtual = isVirtual;

    _artifactCache = artifactCache;

    _containingModule = containingModule;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVirtual() {
    return _isVirtual;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMovable() {

    //
    IArtifact artifact = getParent(ArtifactType.Module);

    //
    return artifact instanceof IModuleArtifact
        && ((IModuleArtifact) artifact).getAssociatedModule() instanceof IResourceModule;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQualifiedName() {
    return _qualifiedName;
  }

  @Override
  public String handleCanAdd(IArtifact artifact) {

    //
    if (artifact.getType().equals(ArtifactType.Resource)) {
      String packageName = ((IResourceArtifact) artifact).getAssociatedResource().getPackageName();
      if (!packageName.equals(this.getQualifiedName())) {
        return String.format("Can not add resource '%s' to package '%s'.", artifact.getQualifiedName(), packageName);
      }
    }

    if (artifact.getType().equals(ArtifactType.Type)) {
      String packageName = ((ITypeArtifact) artifact).getAssociatedType().getPackageName();
      if (!packageName.equals(this.getQualifiedName())) {
        return String.format("Can not add type '%s' to package '%s'.", artifact.getQualifiedName(), packageName);
      }
    }

    if (artifact.getType().equals(ArtifactType.Package)) {
      IPackageArtifact packageArtifact = ((IPackageArtifact) artifact);
      int index = packageArtifact.getQualifiedName().lastIndexOf(".");
      String parentPackageName = index != -1 ? packageArtifact.getQualifiedName().substring(0, index) : packageArtifact
          .getQualifiedName();
      if (!parentPackageName.equals(this.getQualifiedName())) {
        return String.format("Can not add package '%s' to package '%s'.", artifact.getQualifiedName(),
            this.getQualifiedName());
      }
    }

    return String.format("Can not handle artifact '%s'.", artifact.getQualifiedName());
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IModule getContainingModule() {
    return _containingModule;
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
      handlePackage(artifact);
    } else {
      //
      super.addArtifact(artifact);
      // TODO: TYPE CHECK??
      AdapterUtils.addArtifactToPackage(this, artifact);
    }
  }

  public void handlePackage(IArtifact artifact) {

    //
    ModulePackageKey modulePackageKey = new ModulePackageKey(new ModuleKey(_containingModule),
        artifact.getQualifiedName());

    IPackageArtifact packageArtifact = (IPackageArtifact) _artifactCache.getPackageCache()
        .getOrCreate(modulePackageKey);

    // //
    // if (packageArtifact.getParent() != null) {

    // move the children to the new package artifact
    for (IArtifact child : artifact.getChildren()) {
      packageArtifact.addArtifact(child);
    }

    super.addArtifact(packageArtifact);

    // } else {
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

    // get the result
    boolean result = super.removeArtifact(artifact);

    // TODO: TYPE CHECK??
    AdapterUtils.removeArtifactFromPackage(artifact, this);

    // // support for empty packages
    // if (this.getChildren().isEmpty()) {
    // getParent().removeArtifact(this);
    // _artifactCache.getPackageCache();
    // }

    // return the result
    return result;
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

  /**
   * <p>
   * </p>
   * 
   * @param qualifiedName
   * @return
   */
  public static String _getName(String qualifiedName) {
    return qualifiedName.indexOf('.') != -1 ? qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1)
        : qualifiedName;
  }
}
