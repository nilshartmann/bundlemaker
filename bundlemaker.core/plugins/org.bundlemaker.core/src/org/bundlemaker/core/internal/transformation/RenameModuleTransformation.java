package org.bundlemaker.core.internal.transformation;

import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.internal.api.resource.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.api.resource.IModifiableModule;
import org.bundlemaker.core.internal.gson.GsonHelper;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleIdentifier;
import org.bundlemaker.core.resource.IRenameModuleTransformation;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RenameModuleTransformation extends
    AbstractConfigurableTransformation<RenameModuleTransformation.Configuration> implements IRenameModuleTransformation {

  /** - */
  private IModifiableModule _modifiableModule;

  /** - */
  private IModuleIdentifier _oldModuleIdentifier;

  /**
   * <p>
   * Creates a new instance of type {@link RenameModuleTransformation}.
   * </p>
   * 
   * @param moduleArtifact
   * @param name
   * @param version
   */
  public RenameModuleTransformation(IModuleArtifact moduleArtifact, String name, String version) {
    super(new Configuration(moduleArtifact, name, version).toJsonTree());
  }

  /**
   * <p>
   * Creates a new instance of type {@link RenameModuleTransformation}.
   * </p>
   * 
   * @param configuration
   */
  public RenameModuleTransformation(JsonElement configuration) {
    super(configuration);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onApply(Configuration config, IModifiableModularizedSystem modularizedSystem,
      IProgressMonitor progressMonitor) {

    //
    _modifiableModule = ((IModifiableModule) config.getModuleArtifact().getAssociatedModule());
    _oldModuleIdentifier = _modifiableModule.getModuleIdentifier();

    // set new name/version
    ((IModifiableModule) config.getModuleArtifact().getAssociatedModule()).setModuleIdentifier(config.getName(),
        config.getVersion());
  }

  @Override
  public void undo() {
    _modifiableModule.setModuleIdentifier(_oldModuleIdentifier);
  }

  @Override
  protected void assertConfiguration(JsonElement element) {
    // TODO Auto-generated method stub

  }

  @Override
  protected Class<Configuration> getConfigurationType() {
    return Configuration.class;
  }

  /**
   * @return the oldModuleIdentifier
   */
  public IModuleIdentifier getOldModuleIdentifier() {
    return _oldModuleIdentifier;
  }

  /**
   * @return the modifiableModule
   */
  public IModuleIdentifier getNewModuleIdentifier() {
    return _modifiableModule.getModuleIdentifier();
  }

  /**
   * @return the modifiableModule
   */
  public IModule getModule() {
    return _modifiableModule;
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
    @SerializedName("module")
    private IModuleArtifact _moduleArtifact;

    /** - */
    @Expose
    @SerializedName("name")
    private String          _name;

    /** - */
    @Expose
    @SerializedName("version")
    private String          _version;

    /**
     * <p>
     * Creates a new instance of type {@link Configuration}.
     * </p>
     * 
     * @param moduleArtifact
     * @param name
     * @param version
     */
    public Configuration(IModuleArtifact moduleArtifact, String name, String version) {
      Assert.isNotNull(moduleArtifact);
      Assert.isNotNull(name);
      Assert.isNotNull(version);

      //
      _moduleArtifact = moduleArtifact;
      _name = name;
      _version = version;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public JsonElement toJsonTree() {
      return GsonHelper.gson(_moduleArtifact.getModularizedSystem()).toJsonTree(this);
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public IModuleArtifact getModuleArtifact() {
      return _moduleArtifact;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public String getName() {
      return _name;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public String getVersion() {
      return _version;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_moduleArtifact == null) ? 0 : _moduleArtifact.hashCode());
      result = prime * result + ((_name == null) ? 0 : _name.hashCode());
      result = prime * result + ((_version == null) ? 0 : _version.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Configuration other = (Configuration) obj;
      if (_moduleArtifact == null) {
        if (other._moduleArtifact != null)
          return false;
      } else if (!_moduleArtifact.equals(other._moduleArtifact))
        return false;
      if (_name == null) {
        if (other._name != null)
          return false;
      } else if (!_name.equals(other._name))
        return false;
      if (_version == null) {
        if (other._version != null)
          return false;
      } else if (!_version.equals(other._version))
        return false;
      return true;
    }
  }
}
