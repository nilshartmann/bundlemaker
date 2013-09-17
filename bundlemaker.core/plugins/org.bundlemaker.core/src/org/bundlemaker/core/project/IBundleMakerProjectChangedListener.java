package org.bundlemaker.core.project;

/**
 * <p>
 * Listener to track changes of the project state <i>or</i> the project description.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IBundleMakerProjectChangedListener {

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void projectStateChanged(BundleMakerProjectStateChangedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void projectDescriptionChanged(BundleMakerProjectDescriptionChangedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void projectContentChanged(BundleMakerProjectContentChangedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public class Adapter implements IBundleMakerProjectChangedListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void projectStateChanged(BundleMakerProjectStateChangedEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void projectDescriptionChanged(BundleMakerProjectDescriptionChangedEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void projectContentChanged(BundleMakerProjectContentChangedEvent event) {
    }
  }
}
