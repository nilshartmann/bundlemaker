package org.bundlemaker.core.ui.experimental.dependencytable.threeway;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class XRefTreeArtifactLabelProvider extends ArtifactTreeLabelProvider implements IColorProvider {

  /** - */
  private Set<IBundleMakerArtifact> _bundleMakerArtifacts;

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

      //
      for (IBundleMakerArtifact artifact : bundleMakerArtifacts) {
        _bundleMakerArtifacts.addAll(artifact.getAncestors());
      }
    }
  }

  @Override
  public Color getForeground(Object element) {

    //
    if (_bundleMakerArtifacts == null || _bundleMakerArtifacts.contains(element) || element instanceof IRootArtifact) {
      return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
    }

    //
    return Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
  }

  @Override
  public Color getBackground(Object element) {
    return null;
  }
}
