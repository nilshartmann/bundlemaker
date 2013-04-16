package org.bundlemaker.core.ui.editor.dsm;

import java.util.Observable;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.ui.editor.dsm.widget.IDsmContentProvider;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractDsmViewModel extends Observable implements IDsmContentProvider {

  private String[][]            _values;

  private LabelPresentationMode _labelPresentationMode = LabelPresentationMode.qualifiedName;

  public LabelPresentationMode getLabelPresentationMode() {
    return _labelPresentationMode;
  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @return
  // */
  // public final String[] getLabels() {
  //
  // // create
  // if (_labels == null) {
  // _labels = createLabels();
  // }
  //
  // // return values
  // return _labels;
  // }
  //
  // public String[] getDisplayLabels() {
  // if (_displayLabels == null) {
  // _displayLabels = doGetDisplayLabels();
  // }
  //
  // return _displayLabels;
  // }

  public void setLabelPresentationMode(LabelPresentationMode labelPresentationMode) {
    if (labelPresentationMode != _labelPresentationMode) {
      _labelPresentationMode = labelPresentationMode;
      notifyObservers();
    }
  }

  /**
   * Returns the shortend version of a label
   * 
   * @param label
   * @return
   */
  protected String getDisplayLabel(IBundleMakerArtifact artifact) {
    if (_labelPresentationMode == LabelPresentationMode.simpleName) {

      if (artifact instanceof IModuleArtifact) {
        IModuleArtifact moduleArtifact = (IModuleArtifact) artifact;
        return moduleArtifact.getModuleName();
      }

      return artifact.getName();
    }

    String label = artifact.getQualifiedName();

    if (_labelPresentationMode == LabelPresentationMode.qualifiedName) {
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


  /**
   * <p>
   * </p>
   * 
   * @param x
   * @param y
   * @return
   */
  public abstract String getToolTip(int x, int y);
}
