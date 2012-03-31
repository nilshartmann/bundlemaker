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
public class RootArtifactSelection extends AbstractProviderSelection implements IRootArtifactSelection {

  /** - */
  private IRootArtifact _rootArtifact;

  /**
   * <p>
   * Creates a new instance of type {@link RootArtifactSelection}.
   * </p>
   * 
   * @param selectionId
   * @param providerId
   */
  public RootArtifactSelection(String selectionId, String providerId) {
    super(selectionId, providerId);
  }

  /**
   * <p>
   * Creates a new instance of type {@link RootArtifactSelection}.
   * </p>
   * 
   * @param providerId
   * @param rootArtifact
   */
  public RootArtifactSelection(String selectionId, String providerId, IRootArtifact rootArtifact) {
    super(selectionId, providerId);

    //
    Assert.isNotNull(rootArtifact);

    //
    _rootArtifact = rootArtifact;
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

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((_rootArtifact == null) ? 0 : _rootArtifact.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    RootArtifactSelection other = (RootArtifactSelection) obj;
    if (_rootArtifact == null) {
      if (other._rootArtifact != null)
        return false;
    } else if (!_rootArtifact.equals(other._rootArtifact))
      return false;
    return true;
  }
}
