package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.impl.AbstractArtifactContainer;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractAdvancedContainer extends AbstractArtifactContainer implements IAdvancedArtifact {

  /**
   * <p>
   * Creates a new instance of type {@link AbstractAdvancedContainer}.
   * </p>
   * 
   * @param type
   * @param name
   */
  public AbstractAdvancedContainer(ArtifactType type, String name) {
    super(type, name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifact getChildByIdentifier(String identifier) {

    //
    Assert.isNotNull(identifier);

    //
    for (IArtifact artifact : getChildren()) {

      //
      Assert.isTrue(artifact instanceof IAdvancedArtifact, artifact.getQualifiedName() + " : " + artifact.getClass());

      if (identifier.equals(((IAdvancedArtifact) artifact).getIdentifier())) {
        return artifact;
      }
    }

    // return null
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifact getChild(String path) {

    //
    Assert.isNotNull(path);

    IPath iPath = new Path(path);

    if (iPath.segmentCount() == 0) {
      return null;
    } else if (iPath.segmentCount() == 1) {
      return getChildByIdentifier(iPath.lastSegment());
    } else {
      IArtifact directChild = getChildByIdentifier(iPath.segment(0));
      if (directChild != null && AbstractAdvancedContainer.class.isAssignableFrom(directChild.getClass())) {
        return ((AbstractAdvancedContainer) directChild).getChild(iPath.removeFirstSegments(1).toString());
      }
    }

    // return null
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getIdentifier() {
    return getName();
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  protected void assertCanAdd(IArtifact artifact) {

    if (artifact == null) {
      throw new RuntimeException("Can not add 'null' to " + this);
    }

    if (!canAdd(artifact)) {
      throw new RuntimeException("Can not add " + artifact + " to " + this);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.IAdvancedArtifact#getModularizedSystem()
   */
  @Override
  public IModularizedSystem getModularizedSystem() {
    return AdapterUtils.getModularizedSystem(this);
  }
}
