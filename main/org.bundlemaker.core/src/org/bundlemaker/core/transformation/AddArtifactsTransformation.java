package org.bundlemaker.core.transformation;

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AddArtifactsTransformation implements ITransformation {

  /** - */
  private IBundleMakerArtifact                         _parent;

  /** - */
  private IArtifactSelector                            _artifactSelector;

  /** - */
  private Map<IResourceArtifact, IBundleMakerArtifact> _movedResourcesWithFormerParents;

  /**
   * <p>
   * Creates a new instance of type {@link AddArtifactsTransformation}.
   * </p>
   * 
   * @param parent
   * @param artifact
   */
  public AddArtifactsTransformation(IBundleMakerArtifact parent,
      IArtifactSelector artifactSelector) {

    Assert.isNotNull(parent);
    Assert.isNotNull(artifactSelector);

    //
    _parent = parent;
    _artifactSelector = artifactSelector;

    //
    _movedResourcesWithFormerParents = new HashMap<IResourceArtifact, IBundleMakerArtifact>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {

    //
    Assert.isTrue(modularizedSystem == _parent.getRoot().getModularizedSystem(), "Invalid ModularizedSystem");

    //
    _parent.addArtifacts(_artifactSelector);

    // // TODO
    // for (IBundleMakerArtifact resourceArtifact : _artifactSelector.getBundleMakerArtifacts()) {
    //
    // //
    // if (_targetArtifact.canAdd(resourceArtifact)) {
    // _movedResourcesWithFormerParents.put(resourceArtifact, resourceArtifact.getParent());
    // _targetArtifact.addArtifact(resourceArtifact);
    // }
    // }
  }
}
