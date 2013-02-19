package org.bundlemaker.core.ui.editor.sourceviewer.old;

import org.bundlemaker.core.analysis.IResourceArtifact;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.internal.ui.javaeditor.InternalClassFileEditorInput;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerClassFileEditorInput extends InternalClassFileEditorInput implements
    IBundleMakerArtifactEditorInput {

  /** - */
  private IResourceArtifact _resourceArtifact;

  /**
   * <p>
   * Creates a new instance of type {@link BundleMakerClassFileEditorInput}.
   * </p>
   * 
   * @param classFile
   * @param resourceArtifact
   */
  public BundleMakerClassFileEditorInput(IClassFile classFile, IResourceArtifact resourceArtifact) {
    super(classFile);

    _resourceArtifact = resourceArtifact;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return _resourceArtifact.getName();
  }

  @Override
  public String getToolTipText() {
    return super.getToolTipText();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public IResourceArtifact getResourceArtifact() {
    return _resourceArtifact;
  }
}
