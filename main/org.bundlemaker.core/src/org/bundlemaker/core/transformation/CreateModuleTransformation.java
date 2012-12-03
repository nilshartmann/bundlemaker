package org.bundlemaker.core.transformation;

import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.util.gson.GsonHelper;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class CreateModuleTransformation extends
    AbstractConfigurableTransformation<CreateModuleTransformation.Configuration> {

  /**
   * <p>
   * Creates a new instance of type {@link CreateModuleTransformation}.
   * </p>
   * 
   * @param configuration
   */
  public CreateModuleTransformation(JsonElement configuration) {
    super(configuration);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onApply(Configuration config, IModifiableModularizedSystem modularizedSystem,
      IProgressMonitor progressMonitor) {

    //
    Assert.isTrue(modularizedSystem == config.getGroupAndModuleContainer().getRoot().getModularizedSystem(),
        "Invalid ModularizedSystem");

    //
    config.getGroupAndModuleContainer().getOrCreateModule(config.getQualifiedModuleName(), config.getModuleVersion());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void assertConfiguration(JsonElement element) {
    // TODO
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<Configuration> getConfigurationType() {
    return Configuration.class;
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static class Configuration {

    /** - */
    @Expose
    @SerializedName("parent")
    private IGroupAndModuleContainer _groupAndModuleContainer;

    /** - */
    @Expose
    @SerializedName("name")
    private String                   _qualifiedModuleName;

    /** - */
    @Expose
    @SerializedName("version")
    private String                   _moduleVersion;

    /**
     * <p>
     * Creates a new instance of type {@link Configuration}.
     * </p>
     * 
     * @param groupAndModuleContainer
     * @param qualifiedModuleName
     * @param moduleVersion
     */
    public Configuration(IGroupAndModuleContainer groupAndModuleContainer, String qualifiedModuleName,
        String moduleVersion) {
      Assert.isNotNull(groupAndModuleContainer);
      Assert.isNotNull(qualifiedModuleName);
      Assert.isNotNull(moduleVersion);

      _groupAndModuleContainer = groupAndModuleContainer;
      _qualifiedModuleName = qualifiedModuleName;
      _moduleVersion = moduleVersion;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public IGroupAndModuleContainer getGroupAndModuleContainer() {
      return _groupAndModuleContainer;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public String getQualifiedModuleName() {
      return _qualifiedModuleName;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public String getModuleVersion() {
      return _moduleVersion;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public JsonElement toJsonTree() {
      return GsonHelper.gson(_groupAndModuleContainer.getModularizedSystem()).toJsonTree(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_groupAndModuleContainer == null) ? 0 : _groupAndModuleContainer.hashCode());
      result = prime * result + ((_moduleVersion == null) ? 0 : _moduleVersion.hashCode());
      result = prime * result + ((_qualifiedModuleName == null) ? 0 : _qualifiedModuleName.hashCode());
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
      Configuration other = (Configuration) obj;
      if (_groupAndModuleContainer == null) {
        if (other._groupAndModuleContainer != null)
          return false;
      } else if (!_groupAndModuleContainer.equals(other._groupAndModuleContainer))
        return false;
      if (_moduleVersion == null) {
        if (other._moduleVersion != null)
          return false;
      } else if (!_moduleVersion.equals(other._moduleVersion))
        return false;
      if (_qualifiedModuleName == null) {
        if (other._qualifiedModuleName != null)
          return false;
      } else if (!_qualifiedModuleName.equals(other._qualifiedModuleName))
        return false;
      return true;
    }
  }
}
