package org.bundlemaker.core.ui.editor.dsm;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.eclipse.jface.viewers.LabelProvider;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactLabelProvider extends LabelProvider {

  /** - */
  private DsmViewModel _model;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactLabelProvider}.
   * </p>
   * 
   * @param labelPresentationMode
   */
  public ArtifactLabelProvider(DsmViewModel model) {
    _model = model;
  }
  
  public void setModel(DsmViewModel model) {
    _model = model;
  }

  @Override
  public String getText(Object element) {

    IBundleMakerArtifact artifact = (IBundleMakerArtifact) element;

    if (_model.getLabelPresentationMode() == LabelPresentationMode.simpleName) {

      if (artifact instanceof IModuleArtifact) {
        IModuleArtifact moduleArtifact = (IModuleArtifact) artifact;
        return moduleArtifact.getModuleName();
      }

      return artifact.getName();
    }

    String label = artifact.getQualifiedName();

    if (_model.getLabelPresentationMode() == LabelPresentationMode.qualifiedName) {
      return label;
    }

    int v = label.indexOf("_");
    if (v > 0) {
      label = label.substring(0, v);
    }

    boolean lastPart = true;

    String shortendLabel = "";
    label = "." + label;
    char previousChar = label.charAt(label.length() - 1);
    for (int i = label.length() - 1; i >= 0; i--) {
      char c = label.charAt(i);
      if (c == '.' || c == '/') {
        if (!lastPart) {
          shortendLabel = previousChar + shortendLabel;
        }
        if (i > 0) {
          shortendLabel = c + shortendLabel;
        }
        lastPart = false;
      } else {
        if (lastPart) {
          shortendLabel = c + shortendLabel;
        }

      }

      previousChar = c;
    }
    return shortendLabel;
  }
}