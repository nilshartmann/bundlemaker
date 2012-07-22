package org.bundlemaker.core.transformation;

import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.AdapterRoot2IArtifact;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractTransformationAwareModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class CreateGroupTransformation implements ITransformation {

  /** - */
  private IGroupAndModuleContainer _groupContainer;

  /** - */
  private IPath                    _path;

  /** - */
  private IGroupArtifact           _newArtifact;

  /**
   * <p>
   * Creates a new instance of type {@link CreateGroupTransformation}.
   * </p>
   * 
   * @param groupAndModuleContainerDelegate
   * @param path
   */
  public CreateGroupTransformation(IGroupAndModuleContainer groupContainer, IPath path) {
    Assert.isNotNull(groupContainer);
    Assert.isNotNull(path);

    _groupContainer = groupContainer;
    _path = path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {

    //
    IPath parentGroupPath = _groupContainer instanceof IRootArtifact ? new Path("") : new Path(
        _groupContainer
            .getQualifiedName());

    //
    IPath absolutePath = parentGroupPath.append(_path);

    //
    Group group = ((AbstractTransformationAwareModularizedSystem) _groupContainer.getRoot()
        .getModularizedSystem())
        .getOrCreateGroup(absolutePath);

    _newArtifact = (IGroupArtifact) ((AdapterRoot2IArtifact) _groupContainer.getRoot())
        .getArtifactCache()
        .getGroupCache()
        .getOrCreate(group);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IGroupArtifact getGroupArtifact() {
    return _newArtifact;
  }
}
