package org.bundlemaker.core.ui.editor.xref3;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class XRefTreeArtifactLabelProvider extends ArtifactTreeLabelProvider implements IColorProvider, IFontProvider {

  /** - */
  private Set<IBundleMakerArtifact> _bundleMakerArtifacts;

  public XRefTreeArtifactLabelProvider() {
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerArtifacts
   */
  public void setBundleMakerArtifacts(Set<IBundleMakerArtifact> bundleMakerArtifacts) {

    //
    if (bundleMakerArtifacts == null) {
      _bundleMakerArtifacts = null;
    }

    //
    else {

      //
      _bundleMakerArtifacts = new HashSet<IBundleMakerArtifact>(bundleMakerArtifacts);

    }
  }

  /**
   * @return the bundleMakerArtifacts
   */
  public Set<IBundleMakerArtifact> getBundleMakerArtifacts() {
    return _bundleMakerArtifacts;
  }

  @Override
  public Color getForeground(Object element) {
    if (_bundleMakerArtifacts != null && _bundleMakerArtifacts.contains(element)) {
      return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED);
    }
    return null;
  }

  @Override
  public Color getBackground(Object element) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
   */
  @Override
  public Font getFont(Object element) {
    if (_bundleMakerArtifacts != null && _bundleMakerArtifacts.contains(element)) {
      return JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT);
    }

    return null;
  }
}
