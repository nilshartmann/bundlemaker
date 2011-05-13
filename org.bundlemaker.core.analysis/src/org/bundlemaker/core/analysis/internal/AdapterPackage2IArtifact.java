package org.bundlemaker.core.analysis.internal;

import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.impl.AbstractArtifactContainer;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterPackage2IArtifact extends AbstractAdvancedContainer {

  /** - */
  private String  _qualifiedName;

  /** - */
  private boolean _isFlat = true;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterPackage2IArtifact}.
   * </p>
   * 
   * @param qualifiedName
   * @param parent
   */
  public AdapterPackage2IArtifact(String qualifiedName, IArtifact parent) {
    super(ArtifactType.Package, _getName(qualifiedName));

    // set parent/children dependency
    if (parent != null) {
      setParent(parent);
      ((AbstractArtifactContainer) parent).getChildren().add(this);
    }

    Assert.isNotNull(qualifiedName);

    // set the qualified name
    _qualifiedName = qualifiedName;
  }

  public static String _getName(String qualifiedName) {
    return qualifiedName.indexOf('.') != -1 ? qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1)
        : qualifiedName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQualifiedName() {
    return _qualifiedName;
  }

  @Override
  public String getIdentifier() {
    return _isFlat ? getQualifiedName() : getName();
  }

  @Override
  public boolean canAdd(IArtifact artifact) {
    return artifact != null
        && (artifact.getType().equals(ArtifactType.Resource) || artifact.getType().equals(ArtifactType.Type));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifact(IArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);
    assertCanAdd(artifact);

    //
    super.addArtifact(artifact);

    // TODO: TYPE CHECK??
    AdapterUtils.addArtifactToPackage(this, artifact);
    AdapterUtils.getModularizedSystem(artifact).initializeResourceModules();
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
    AdapterUtils.getModularizedSystem(artifact).initializeResourceModules();

    // return the result
    return result;
  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
  // *
  // */
  // public static class PackageKey {
  //
  // private IModule _resourceModule;
  //
  // private String _packageName;
  //
  // /**
  // * <p>
  // * </p>
  // *
  // * @param resourceModule
  // * @param packageName
  // */
  // public PackageKey(IModule resourceModule, String packageName) {
  //
  // _resourceModule = resourceModule;
  // _packageName = packageName;
  // }
  //
  // public IModule getModule() {
  // return _resourceModule;
  // }
  //
  // public String getPackageName() {
  // return _packageName;
  // }
  //
  // @Override
  // public int hashCode() {
  // final int prime = 31;
  // int result = 1;
  // result = prime * result + ((_packageName == null) ? 0 : _packageName.hashCode());
  // result = prime * result + ((_resourceModule == null) ? 0 : _resourceModule.hashCode());
  // return result;
  // }
  //
  // @Override
  // public boolean equals(Object obj) {
  // if (this == obj)
  // return true;
  // if (obj == null)
  // return false;
  // if (getClass() != obj.getClass())
  // return false;
  // PackageKey other = (PackageKey) obj;
  // if (_packageName == null) {
  // if (other._packageName != null)
  // return false;
  // } else if (!_packageName.equals(other._packageName))
  // return false;
  // if (_resourceModule == null) {
  // if (other._resourceModule != null)
  // return false;
  // } else if (!_resourceModule.equals(other._resourceModule))
  // return false;
  // return true;
  // }
  // }
}
