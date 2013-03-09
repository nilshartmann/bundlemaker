package org.bundlemaker.core.ui.editor.sourceviewer;

import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.ui.artifact.tree.EditorHelper;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReferenceHyperlink implements IHyperlink {

  /** - */
  private ResourceArtifactReferenceRegion _region;

  /**
   * <p>
   * Creates a new instance of type {@link ReferenceHyperlink}.
   * </p>
   * 
   * @param region
   */
  public ReferenceHyperlink(ResourceArtifactReferenceRegion region) {
    Assert.isNotNull(region);

    _region = region;
  }

  @Override
  public IRegion getHyperlinkRegion() {
    return _region;
  }

  @Override
  public String getTypeLabel() {
    return _region.getType();
  }

  @Override
  public String getHyperlinkText() {
    return _region.getType();
  }

  @Override
  public void open() {

    //
    EditorHelper.openArtifactInEditor(_region.getResourceArtifact());
  }
}
