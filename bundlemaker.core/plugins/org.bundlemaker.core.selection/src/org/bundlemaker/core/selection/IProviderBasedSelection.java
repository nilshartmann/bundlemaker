package org.bundlemaker.core.selection;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IProviderBasedSelection {

  /**
   * <p>
   * Returns the logical identifier of the selection (e.g. {@link Selection#ARTIFACT_STAGE_SELECTION_ID}).
   * </p>
   * 
   * @return
   */
  public String getSelectionId();

  /**
   * @return the selection provider's Id
   */
  public String getProviderId();
}
