package org.bundlemaker.core.transformation;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.internal.analysis.AbstractBundleMakerArtifactContainer;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.IProgressMonitor;

public class RemoveTransformation implements ITransformation {

  IBundleMakerArtifact abstractBundleMakerArtifactContainer;

  IBundleMakerArtifact artifact;

  /**
   * <p>
   * Creates a new instance of type {@link RemoveTransformation}.
   * </p>
   * 
   * @param abstractBundleMakerArtifactContainer
   * @param artifact
   */
  public RemoveTransformation(AbstractBundleMakerArtifactContainer abstractBundleMakerArtifactContainer,
      IBundleMakerArtifact artifact) {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {
    // TODO Auto-generated method stub

  }

}
