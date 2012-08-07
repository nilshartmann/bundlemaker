package org.bundlemaker.core.transformation;

import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.util.gson.GsonHelper;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;

import com.google.gson.JsonElement;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractJSonConfiguredTransformation<T> implements ITransformation {

  /** - */
  private JsonElement _configuration;

  /**
   * <p>
   * Creates a new instance of type {@link AddArtifactsTransformation}.
   * </p>
   * 
   * @param configuration
   */
  public AbstractJSonConfiguredTransformation(JsonElement configuration) {
    Assert.isNotNull(configuration);
    assertConfiguration(configuration);

    //
    _configuration = configuration;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public JsonElement getJsonConfiguration() {
    return _configuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {

    //
    Assert.isNotNull(_configuration, "Configuration must not be null.");

    //
    T config = GsonHelper.gson(modularizedSystem).fromJson(_configuration, getConfigurationType());

    //
    onApply(config, modularizedSystem, progressMonitor);
  }

  /**
   * <p>
   * </p>
   * 
   * @param config
   * @param modularizedSystem
   * @param progressMonitor
   */
  protected abstract void onApply(T config, IModifiableModularizedSystem modularizedSystem,
      IProgressMonitor progressMonitor);

  /**
   * <p>
   * </p>
   * 
   * @param element
   */
  protected abstract void assertConfiguration(JsonElement element);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected abstract Class<T> getConfigurationType();

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_configuration == null) ? 0 : _configuration.hashCode());
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
    AbstractJSonConfiguredTransformation other = (AbstractJSonConfiguredTransformation) obj;
    if (_configuration == null) {
      if (other._configuration != null)
        return false;
    } else if (!_configuration.equals(other._configuration))
      return false;
    return true;
  }
}
