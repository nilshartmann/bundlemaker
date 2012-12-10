package org.bundlemaker.core.transformation;

import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
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
public class RemoveArtifactsTransformation extends
    AbstractConfigurableTransformation<RemoveArtifactsTransformation.Configuration> {

  /**
   * <p>
   * Creates a new instance of type {@link RemoveArtifactsTransformation}.
   * </p>
   * 
   * @param configuration
   */
  public RemoveArtifactsTransformation(JsonElement configuration) {
    super(configuration);
  }

  @Override
  public void undo() {
    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  protected void onApply(Configuration config, IModifiableModularizedSystem modularizedSystem,
      IProgressMonitor progressMonitor) {

    //
    config.getParent().removeArtifacts(config.getArtifactSelector());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void assertConfiguration(JsonElement element) {

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
    private IBundleMakerArtifact _parent;

    /** - */
    @Expose
    @SerializedName("selector")
    private IArtifactSelector    _artifactSelector;

    /**
     * <p>
     * Creates a new instance of type {@link Configuration}.
     * </p>
     * 
     * @param parent
     * @param artifactSelector
     */
    public Configuration(IBundleMakerArtifact parent, IArtifactSelector artifactSelector) {

      Assert.isNotNull(parent);
      Assert.isNotNull(artifactSelector);

      //
      _parent = parent;
      _artifactSelector = artifactSelector;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public IBundleMakerArtifact getParent() {
      return _parent;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public IArtifactSelector getArtifactSelector() {
      return _artifactSelector;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public JsonElement toJsonTree() {
      return GsonHelper.gson(_parent.getModularizedSystem()).toJsonTree(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_artifactSelector == null) ? 0 : _artifactSelector.hashCode());
      result = prime * result + ((_parent == null) ? 0 : _parent.hashCode());
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
      if (_artifactSelector == null) {
        if (other._artifactSelector != null)
          return false;
      } else if (!_artifactSelector.equals(other._artifactSelector))
        return false;
      if (_parent == null) {
        if (other._parent != null)
          return false;
      } else if (!_parent.equals(other._parent))
        return false;
      return true;
    }
  }
}
