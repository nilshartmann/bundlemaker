package org.bundlemaker.core.transformation;

import java.util.List;

import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifactContainer;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.transformation.add.AddArtifactToGroup;
import org.bundlemaker.core.transformation.add.AddArtifactToModule;
import org.bundlemaker.core.transformation.add.AddArtifactToPackage;
import org.bundlemaker.core.transformation.add.AddArtifactToRoot;
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
public class AddArtifactsTransformation extends
    AbstractConfigurableTransformation<AddArtifactsTransformation.Configuration> implements IUndoableTransformation {

  /**
   * <p>
   * Creates a new instance of type {@link AddArtifactsTransformation}.
   * </p>
   * 
   * @param configuration
   */
  public AddArtifactsTransformation(IBundleMakerArtifact parent, IArtifactSelector artifactSelector) {
    super(new Configuration(parent, artifactSelector).toJsonTree());
  }

  /**
   * <p>
   * Creates a new instance of type {@link AddArtifactsTransformation}.
   * </p>
   * 
   * @param configuration
   */
  public AddArtifactsTransformation(JsonElement configuration) {
    super(configuration);
  }

  @Override
  public void undo() {
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
   * {@inheritDoc}
   */
  @Override
  protected void assertConfiguration(JsonElement element) {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onApply(Configuration config, IModifiableModularizedSystem modularizedSystem,
      IProgressMonitor progressMonitor) {

    // we have to set the model modification handling to 'false'
    ((IModifiableModularizedSystem) getModularizedSystem()).setHandleModelModification(false);

    //
    List<? extends IBundleMakerArtifact> artifacts = config.getArtifactSelector().getBundleMakerArtifacts();

    // add the artifacts
    for (IBundleMakerArtifact artifact : config.getArtifactSelector().getBundleMakerArtifacts()) {
      ((AbstractArtifactContainer) config.getParent()).assertCanAdd(artifact);
    }

    for (IBundleMakerArtifact artifactToAdd : artifacts) {

      // add to root artifact
      if (config.getParent() instanceof IRootArtifact) {
        AddArtifactToRoot.add((IRootArtifact) config.getParent(), artifactToAdd);
      }
      // add to group artifact
      else if (config.getParent() instanceof IGroupArtifact) {
        AddArtifactToGroup.add((IGroupArtifact) config.getParent(), artifactToAdd);
      }
      // add to module artifact
      else if (config.getParent() instanceof IModuleArtifact) {
        AddArtifactToModule.add((IModuleArtifact) config.getParent(), artifactToAdd);
      }
      // add to package artifact
      else if (config.getParent() instanceof IPackageArtifact) {
        AddArtifactToPackage.add((IPackageArtifact) config.getParent(), artifactToAdd);
      }

      //
      else {
        throw new RuntimeException("Unsupported add operation");
      }
    }

    //
    ((IModifiableModularizedSystem) getModularizedSystem()).setHandleModelModification(true);
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
