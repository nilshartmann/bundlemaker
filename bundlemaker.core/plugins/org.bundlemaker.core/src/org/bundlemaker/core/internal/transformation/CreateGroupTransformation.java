package org.bundlemaker.core.internal.transformation;

import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.AdapterGroup2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterRoot2IArtifact;
import org.bundlemaker.core.internal.gson.GsonHelper;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.internal.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.resource.ICreateGroupTransformation;
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
    AbstractConfigurableTransformation<CreateGroupTransformation.Configuration> implements ICreateGroupTransformation {

  /** - */
  private IGroupArtifact _newGroupArtifact;

  /** New Group Path. Set after tranformation is applied */
  private IPath          _groupPath;

  /** Parent. Set after transformation is applied */
  private IPath          _parentGroupPath;

  /** - */
  private Group          _lastExistingParentGroup;

  /**
   * <p>
   * Creates a new instance of type {@link CreateGroupTransformation}.
   * </p>
   * 
   * @param groupContainer
   * @param path
   */
  public CreateGroupTransformation(IGroupAndModuleContainer groupContainer, IPath path) {
    super(new Configuration(groupContainer, path).toJsonTree());
  }

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
  public void undo() {

    //
    IPath existingGroupPath = _lastExistingParentGroup != null ? _lastExistingParentGroup.getPath() : new Path("");
    IPath newGroupPath = ((AdapterGroup2IArtifact) _newGroupArtifact).getAssociatedGroup().getPath();

    //
    Assert.isTrue(existingGroupPath.isPrefixOf(newGroupPath));

    //
    newGroupPath = newGroupPath.removeFirstSegments(existingGroupPath.segmentCount());

    //
    while (!newGroupPath.isEmpty()) {
      getModularizedSystem().removeGroup(existingGroupPath.append(newGroupPath));
      newGroupPath = newGroupPath.removeLastSegments(1);
    }
  }

  /**
   * @return the groupPath
   */
  public IPath getGroupPath() {
    return _groupPath;
  }

  @Override
  protected void onApply(Configuration config, IModifiableModularizedSystem modularizedSystem,
      IProgressMonitor progressMonitor) {

    _groupPath = new Path(config.getPath());
    //
    _parentGroupPath = config.getGroupContainer() instanceof IRootArtifact ? new Path("") : new Path(
        config.getGroupContainer()
            .getQualifiedName());

    //
    IPath absolutePath = _parentGroupPath.append(config.getPath());

    // find "deepest" existing group
    IPath existingGroupPath = _parentGroupPath;
    for (String segment : absolutePath.segments()) {
      if (getModularizedSystem().getGroup(existingGroupPath.append(segment)) != null) {
        existingGroupPath = existingGroupPath.append(segment);
      }
    }
    _lastExistingParentGroup = getModularizedSystem().getGroup(existingGroupPath);

    //
    Group newGroup = getModularizedSystem().getOrCreateGroup(absolutePath);

    //
    _newGroupArtifact = (IGroupArtifact) ((AdapterRoot2IArtifact) config.getGroupContainer().getRoot())
        .getArtifactCache()
        .getGroupCache()
        .getOrCreate((Group) newGroup);
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
   * @return the parentGroupPath
   */
  public IPath getParentGroupPath() {
    return _parentGroupPath;
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
