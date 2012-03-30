package org.bundlemaker.core.ui.selection.internal;

import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.selection.IRootArtifactSelection;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class RootArtifactSelection implements IRootArtifactSelection {

  /** - */
  private String        _providerId;

  /** - */
  private IRootArtifact _rootArtifact;
  
  /**
   * <p>
   * Creates a new instance of type {@link RootArtifactSelection}.
   * </p>
   *
   * @param providerId
   */
  public RootArtifactSelection(String providerId) {
    Assert.isNotNull(providerId);
    
    _providerId = providerId;
  }

  /**
   * <p>
   * Creates a new instance of type {@link RootArtifactSelection}.
   * </p>
   *
   * @param providerId
   * @param rootArtifact
   */
  public RootArtifactSelection(String providerId, IRootArtifact rootArtifact) {
    Assert.isNotNull(providerId);
    Assert.isNotNull(rootArtifact);
    
    _providerId = providerId;
    _rootArtifact = rootArtifact;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getProviderId() {
    return _providerId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getSelectedRootArtifact() {
    return _rootArtifact;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSelectedRootArtifact() {
    return _rootArtifact != null;
  }
}
