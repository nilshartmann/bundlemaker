package org.bundlemaker.core.analysis.internal;

import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.impl.AbstractArtifactContainer;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

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
   * <p>
   * </p>
   * 
   * @param name
   * @return
   */
  public IArtifact getChildByName(String name) {

    //
    Assert.isNotNull(name);

    //
    for (IArtifact artifact : getChildren()) {
      if (artifact.getName().equals(name)) {
        return artifact;
      }
    }

    // return null
    return null;
  }

  public IArtifact getChild(IPath path) {

    //
    Assert.isNotNull(path);

    if (path.segmentCount() == 0) {
      return null;
    } else if (path.segmentCount() == 1) {
      return getChildByName(path.lastSegment());
    } else {
      IArtifact directChild = getChildByName(path.segment(0));
      if (directChild instanceof AbstractAdvancedContainer) {
        return ((AbstractAdvancedContainer) directChild).getChild(path.removeFirstSegments(1));
      }
    }

    // return null
    return null;
  }

  protected void assertCanAdd(IArtifact artifact) {
    if (!canAdd(artifact)) {
      throw new RuntimeException("Can not add " + artifact + " to " + this);
    }
  }
}
