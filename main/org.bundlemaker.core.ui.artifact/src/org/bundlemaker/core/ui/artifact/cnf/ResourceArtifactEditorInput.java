package org.bundlemaker.core.ui.artifact.cnf;

import org.bundlemaker.core.analysis.IResourceArtifact;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class ResourceArtifactEditorInput implements IEditorInput {

  /** - */
  private IResourceArtifact _resourceArtifact;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceArtifactEditorInput}.
   * </p>
   * 
   * @param resourceArtifact
   */
  public ResourceArtifactEditorInput(IResourceArtifact resourceArtifact) {
    Assert.isNotNull(resourceArtifact);

    _resourceArtifact = resourceArtifact;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public Object getAdapter(Class adapter) {
    return Platform.getAdapterManager().getAdapter(this, adapter);
  }

  @Override
  public boolean exists() {
    return true;
  }

  @Override
  public ImageDescriptor getImageDescriptor() {
    return null;
  }

  @Override
  public String getName() {
    return _resourceArtifact.getName();
  }

  @Override
  public IPersistableElement getPersistable() {
    return null;
  }

  @Override
  public String getToolTipText() {
    return _resourceArtifact.getQualifiedName();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IResourceArtifact getResourceArtifact() {
    return _resourceArtifact;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_resourceArtifact == null) ? 0 : _resourceArtifact.hashCode());
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
    ResourceArtifactEditorInput other = (ResourceArtifactEditorInput) obj;
    if (_resourceArtifact == null) {
      if (other._resourceArtifact != null)
        return false;
    } else if (!_resourceArtifact.equals(other._resourceArtifact))
      return false;
    return true;
  }
}
