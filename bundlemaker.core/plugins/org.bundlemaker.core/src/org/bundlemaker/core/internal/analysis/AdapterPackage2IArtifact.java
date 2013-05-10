package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core._type.utils.JavaUtils;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.modules.IModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterPackage2IArtifact extends AbstractPackageFilteringArtifact implements IPackageArtifact {

  /** - */
  private String  _qualifiedName;

  /** - */
  private boolean _isVirtual;

  /** - */
  private boolean _isHierarchical;

  /** - */
  private IModule _containingModule;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterPackage2IArtifact}.
   * </p>
   * 
   * @param qualifiedName
   * @param parent
   */
  public AdapterPackage2IArtifact(String qualifiedName, IBundleMakerArtifact parent, boolean isVirtual,
      boolean isHierarchical, IModule containingModule, ArtifactCache artifactCache) {
    super(_getName(qualifiedName));

    // set parent/children dependency
    if (parent != null) {
      setParent(parent);
      ((AbstractArtifactContainer) parent).getModifiableChildrenCollection().add(this);
    }

    Assert.isNotNull(qualifiedName);

    // set the qualified name
    _qualifiedName = qualifiedName;
    _isVirtual = isVirtual;
    _containingModule = containingModule;
    _isHierarchical = isHierarchical;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUniquePathIdentifier() {
    return _isHierarchical ? getName() : getQualifiedName();
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
    IBundleMakerArtifact artifact = getParent(IModuleArtifact.class);

    //
    return artifact instanceof IModuleArtifact
        && ((IModuleArtifact) artifact).getAssociatedModule().isResourceModule();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQualifiedName() {
    return _qualifiedName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.IPackageArtifact#getPackageName()
   */
  @Override
  public String getPackageName() {
    return _qualifiedName;
  }

  @Override
  public String handleCanAdd(IBundleMakerArtifact artifact) {

    //
    if (artifact.isInstanceOf(IResourceArtifact.class)) {
      String packageName = JavaUtils.getPackageNameFromDirectory(((IResourceArtifact) artifact).getAssociatedResource()
          .getDirectory());
      if (!packageName.equals(this.getQualifiedName())) {
        return String.format("Can not add resource '%s' to package '%s'.", artifact.getQualifiedName(), packageName);
      } else {
        return null;
      }
    }

    if (artifact.isInstanceOf(ITypeArtifact.class)) {
      String packageName = ((ITypeArtifact) artifact).getAssociatedType().getPackageName();
      if (!packageName.equals(this.getQualifiedName())) {
        return String.format("Can not add type '%s' to package '%s'.", artifact.getQualifiedName(), packageName);
      } else {
        return null;
      }
    }

    // handle packages
    if (artifact.isInstanceOf(IPackageArtifact.class)) {

      // //
      // if (getConfiguration().isHierarchicalPackages()) {
      //
      // IPackageArtifact packageArtifact = ((IPackageArtifact) artifact);
      // int index = packageArtifact.getQualifiedName().lastIndexOf(".");
      // String parentPackageName = index != -1 ? packageArtifact.getQualifiedName().substring(0, index)
      // : packageArtifact
      // .getQualifiedName();
      //
      // if (parentPackageName.equals(this.getQualifiedName())) {
      // return null;
      // }
      // }

      //
      return String.format("Can not add package '%s (%s)' to package '%s (%s)'.", artifact.getQualifiedName(),
          artifact.getParent(IModuleArtifact.class),
          this.getQualifiedName(), this.getParent(IModuleArtifact.class));
    }

    // fail
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

  @Override
  protected void onRemoveArtifact(IBundleMakerArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);

    // TODO: IS THIS CORRECT ??
    AdapterUtils.removeArtifact(artifact, this);
  }

  @Override
  protected String getArtifactType() {
    return "package";
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

  public void accept(IAnalysisModelVisitor... visitors) {
    DispatchingArtifactTreeVisitor artifactTreeVisitor = new DispatchingArtifactTreeVisitor(visitors);
    accept(artifactTreeVisitor);
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
