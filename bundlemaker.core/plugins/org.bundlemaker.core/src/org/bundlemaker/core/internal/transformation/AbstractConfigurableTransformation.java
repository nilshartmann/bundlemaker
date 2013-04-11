package org.bundlemaker.core.internal.transformation;

import org.bundlemaker.core.internal.modules.modifiable.IModifiableModularizedSystem;
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
public abstract class AbstractConfigurableTransformation<T> extends AbstractUndoableTransformation {

  /** Configuration in JSon */
  private JsonElement _jsonConfigurationElement;

  /** Configuration object created from json */
  private T           _configuration;

  /**
   * <p>
   * Creates a new instance of type {@link AddArtifactsTransformation}.
   * </p>
   * 
   * @param configuration
   */
  public AbstractConfigurableTransformation(JsonElement configuration) {
    Assert.isNotNull(configuration);
    assertConfiguration(configuration);

    //
    _jsonConfigurationElement = configuration;
  }

  /**
   * Might be null, if this transformation has not been run yet
   * 
   * @return
   */
  public T getConfiguration() {
    return _configuration;
  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @return
  // */
  // public JsonElement getJsonConfiguration() {
  // return _configuration;
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {

    super.apply(modularizedSystem, progressMonitor);

    //
    Assert.isNotNull(_jsonConfigurationElement, "Configuration must not be null.");

    //
    _configuration = GsonHelper.gson(modularizedSystem).fromJson(_jsonConfigurationElement, getConfigurationType());

    //
    onApply(_configuration, modularizedSystem, progressMonitor);
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
    result = prime * result + ((_jsonConfigurationElement == null) ? 0 : _jsonConfigurationElement.hashCode());
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
    AbstractConfigurableTransformation other = (AbstractConfigurableTransformation) obj;
    if (_jsonConfigurationElement == null) {
      if (other._jsonConfigurationElement != null)
        return false;
    } else if (!_jsonConfigurationElement.equals(other._jsonConfigurationElement))
      return false;
    return true;
  }
}
