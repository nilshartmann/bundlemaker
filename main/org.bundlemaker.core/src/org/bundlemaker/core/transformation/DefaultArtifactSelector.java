package org.bundlemaker.core.transformation;

import java.util.Arrays;
import java.util.List;

import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SimpleArtifactSelector implements IArtifactSelector {

  /** - */
  private List<IBundleMakerArtifact> _bundleMakerArtifacts;

  /**
   * <p>
   * Creates a new instance of type {@link SimpleArtifactSelector}.
   * </p>
   * 
   * @param bundleMakerArtifacts
   */
  public SimpleArtifactSelector(List<IBundleMakerArtifact> bundleMakerArtifacts) {
    Assert.isNotNull(bundleMakerArtifacts);

    _bundleMakerArtifacts = bundleMakerArtifacts;
  }

  /**
   * <p>
   * Creates a new instance of type {@link SimpleArtifactSelector}.
   * </p>
   * 
   * @param bundleMakerArtifacts
   */
  public SimpleArtifactSelector(IBundleMakerArtifact... bundleMakerArtifacts) {
    Assert.isNotNull(bundleMakerArtifacts);

    _bundleMakerArtifacts = Arrays.asList(bundleMakerArtifacts);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<? extends IBundleMakerArtifact> getBundleMakerArtifacts() {
    return _bundleMakerArtifacts;
  }
}
