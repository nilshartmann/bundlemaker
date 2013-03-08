package org.bundlemaker.core.ui.event.selection.internal;

import org.bundlemaker.core.ui.event.selection.IProviderSelection;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AbstractProviderSelection implements IProviderSelection {

  /** the selection identifier */
  private String _selectionId;

  /** the provider that provided this selection */
  private String _providerId;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractProviderSelection}.
   * </p>
   * 
   * @param selectionId
   * @param providerId
   */
  public AbstractProviderSelection(String selectionId, String providerId) {
    Assert.isNotNull(providerId, "The parameter 'providerId' must not be null");
    Assert.isNotNull(selectionId, "The parameter 'selectionId' must not be null");

    _providerId = providerId;
    _selectionId = selectionId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSelectionId() {
    return _selectionId;
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
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_selectionId == null) ? 0 : _selectionId.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbstractProviderSelection other = (AbstractProviderSelection) obj;
    if (_selectionId == null) {
      if (other._selectionId != null)
        return false;
    } else if (!_selectionId.equals(other._selectionId))
      return false;
    return true;
  }
}
