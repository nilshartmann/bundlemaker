package org.bundlemaker.core.internal.transformation;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.internal.analysis.AdapterRoot2IArtifact;
import org.bundlemaker.core.internal.api.resource.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.api.resource.IModifiableModule;
import org.bundlemaker.core.internal.gson.GsonHelper;
import org.bundlemaker.core.internal.resource.ModuleIdentifier;
import org.bundlemaker.core.resource.ICreateModuleTransformation;
import org.bundlemaker.core.resource.IModuleIdentifier;
import org.bundlemaker.core.spi.analysis.AbstractArtifactContainer;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

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
    AbstractConfigurableTransformation<CreateModuleTransformation.Configuration> implements ICreateModuleTransformation {

  /** - */
  private IModuleArtifact           _moduleArtifact;

  /** - */
  private CreateGroupTransformation _nestedCreateGroupTransformation;

  /**
   * <p>
   * Creates a new instance of type {@link CreateModuleTransformation}.
   * </p>
   * 
   * @param groupAndModuleContainer
   * @param qualifiedModuleName
   * @param moduleVersion
   */
  public CreateModuleTransformation(IGroupAndModuleContainer groupAndModuleContainer, String qualifiedModuleName,
      String moduleVersion) {
    super(new Configuration(groupAndModuleContainer, qualifiedModuleName, moduleVersion).toJsonTree());
  }

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
  public void undo() {

    //
    if (_moduleArtifact != null) {
      getModularizedSystem().removeModule(_moduleArtifact.getAssociatedModule());
      _moduleArtifact = null;
    }

    //
    if (_nestedCreateGroupTransformation != null) {
      _nestedCreateGroupTransformation.undo();
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IModuleArtifact getModuleArtifact() {
    return _moduleArtifact;
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

    // normalize the qualified module name
    String qualifiedModuleName = config.getQualifiedModuleName().replace('\\', '/');

    // the root container is 'this' GroupAndModuleContainer
    IBundleMakerArtifact rootContainer = config.getGroupAndModuleContainer();

    // if the qualified name starts with '/', it is an absolute path
    if (qualifiedModuleName.startsWith("/")) {
      qualifiedModuleName = qualifiedModuleName.substring(1);
      rootContainer = rootContainer.getRoot();
    }

    //
    String moduleName = qualifiedModuleName;
    AbstractArtifactContainer moduleParentArtifact = (AbstractArtifactContainer) rootContainer;

    // if the qualified name contains groups, we have to create the groups first
    int index = moduleName.lastIndexOf('/');
    if (index != -1) {

      // create the group
      _nestedCreateGroupTransformation = new CreateGroupTransformation(new CreateGroupTransformation.Configuration(
          (IGroupAndModuleContainer) rootContainer, new Path(qualifiedModuleName.substring(0, index))).toJsonTree());

      _nestedCreateGroupTransformation.apply(modularizedSystem, null);

      moduleParentArtifact = (AbstractArtifactContainer) _nestedCreateGroupTransformation.getGroupArtifact();

      // set the simple module name
      moduleName = qualifiedModuleName.substring(index + 1);
    }

    // try to get the module...
    IModuleIdentifier moduleIdentifier = new ModuleIdentifier(moduleName, config.getModuleVersion());
    IModuleArtifact moduleArtifact = (IModuleArtifact) moduleParentArtifact.getChild(moduleIdentifier.toString());

    // ...and create it if necessary
    if (moduleArtifact == null) {

      // create the module
      IModifiableModule resourceModule = ((IModifiableModularizedSystem) config.getGroupAndModuleContainer()
          .getRoot()
          .getModularizedSystem()).createResourceModule(moduleIdentifier);

      // set the classification
      IPath classification = moduleParentArtifact.getFullPath();
      if (!".".equals(classification.toOSString())) {
        resourceModule.setClassification(classification);
      }

      //
      moduleArtifact = ((AdapterRoot2IArtifact) config.getGroupAndModuleContainer().getRoot()).getArtifactCache()
          .getModuleArtifact(resourceModule);
    }

    //
    _moduleArtifact = moduleArtifact;
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
