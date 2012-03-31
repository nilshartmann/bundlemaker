package org.bundlemaker.core.ui.selection;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IProviderSelection {

  /**
   * <p>
   * Returns the logical identifier of the selection (e.g. {@link Selection#MAIN_ARTIFACT_SELECTION_ID}).
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
