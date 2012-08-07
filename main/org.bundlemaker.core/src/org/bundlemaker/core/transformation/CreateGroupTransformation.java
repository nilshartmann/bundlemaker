package org.bundlemaker.core.transformation;

import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.AdapterRoot2IArtifact;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractTransformationAwareModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.util.gson.GsonHelper;
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
public class CreateGroupTransformation extends
    AbstractJSonConfiguredTransformation<CreateGroupTransformation.Configuration> {

  /** - */
  private IGroupArtifact _newGroupArtifact;

  /**
   * <p>
   * Creates a new instance of type {@link CreateGroupTransformation}.
   * </p>
   * 
   * @param configuration
   */
  public CreateGroupTransformation(JsonElement configuration) {
    super(configuration);
  }

  @Override
  protected void onApply(Configuration config, IModifiableModularizedSystem modularizedSystem,
      IProgressMonitor progressMonitor) {

    //
    IPath parentGroupPath = config.getGroupContainer() instanceof IRootArtifact ? new Path("") : new Path(
        config.getGroupContainer()
            .getQualifiedName());

    //
    IPath absolutePath = parentGroupPath.append(config.getPath());

    //
    Group group = ((AbstractTransformationAwareModularizedSystem) config.getGroupContainer().getRoot()
        .getModularizedSystem())
        .getOrCreateGroup(absolutePath);

    //
    _newGroupArtifact = (IGroupArtifact) ((AdapterRoot2IArtifact) config.getGroupContainer().getRoot())
        .getArtifactCache()
        .getGroupCache()
        .getOrCreate(group);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void assertConfiguration(JsonElement element) {
    // TODO Auto-generated method stub

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
   * @return
   */
  public IGroupArtifact getGroupArtifact() {
    return _newGroupArtifact;
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
    private IGroupAndModuleContainer _groupContainer;

    /** - */
    @Expose
    @SerializedName("path")
    private String                   _path;

    /**
     * <p>
     * Creates a new instance of type {@link CreateGroupTransformation}.
     * </p>
     * 
     * @param groupAndModuleContainerDelegate
     * @param path
     */
    public Configuration(IGroupAndModuleContainer groupContainer, IPath path) {
      Assert.isNotNull(groupContainer);
      Assert.isNotNull(path);

      //
      _groupContainer = groupContainer;
      _path = path.toPortableString();
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public IGroupAndModuleContainer getGroupContainer() {
      return _groupContainer;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public String getPath() {
      return _path;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public JsonElement toJsonTree() {
      return GsonHelper.gson(_groupContainer.getModularizedSystem()).toJsonTree(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_groupContainer == null) ? 0 : _groupContainer.hashCode());
      result = prime * result + ((_path == null) ? 0 : _path.hashCode());
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
      if (_groupContainer == null) {
        if (other._groupContainer != null)
          return false;
      } else if (!_groupContainer.equals(other._groupContainer))
        return false;
      if (_path == null) {
        if (other._path != null)
          return false;
      } else if (!_path.equals(other._path))
        return false;
      return true;
    }
  }
}
