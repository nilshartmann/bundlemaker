package org.bundlemaker.core.ui.editor.sourceviewer;

import org.bundlemaker.core.analysis.IResourceArtifact;
import org.eclipse.jface.text.TypedRegion;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceArtifactReferenceRegion extends TypedRegion {

  /** - */
  private IResourceArtifact _resourceArtifact;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceArtifactReferenceRegion}.
   * </p>
   * 
   * @param offset
   * @param length
   * @param type
   * @param editor
   */
  public ResourceArtifactReferenceRegion(int offset, int length, String type, IResourceArtifact resourceArtifact) {
    super(offset, length, type);

    //
    _resourceArtifact = resourceArtifact;
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
}
