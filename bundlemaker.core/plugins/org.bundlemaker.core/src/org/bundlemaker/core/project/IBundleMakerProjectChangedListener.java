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
  void projectStateChanged(StateChangedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void projectDescriptionChanged(DescriptionChangedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void projectContentChanged(ContentChangedEvent event);

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
    public void projectStateChanged(StateChangedEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void projectDescriptionChanged(DescriptionChangedEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void projectContentChanged(ContentChangedEvent event) {
    }
  }
}
