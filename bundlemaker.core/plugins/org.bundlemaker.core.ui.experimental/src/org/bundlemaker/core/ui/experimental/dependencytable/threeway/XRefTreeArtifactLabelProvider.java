package org.bundlemaker.core.ui.experimental.dependencytable.threeway;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.TreeViewer;
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

  private final TreeViewer          _treeViewer;

  /** - */
  private Set<IBundleMakerArtifact> _bundleMakerArtifacts;

  public XRefTreeArtifactLabelProvider(TreeViewer treeViewer) {
    _treeViewer = treeViewer;
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

      //
      // for (IBundleMakerArtifact artifact : bundleMakerArtifacts) {
      // _bundleMakerArtifacts.addAll(artifact.getAncestors());
      // }
    }
  }

  @Override
  public Color getForeground(Object element) {

    System.out.println("getForeground " + element);

    // if (_bundleMakerArtifacts != null) {
    // for (IBundleMakerArtifact a : _bundleMakerArtifacts) {
    // System.out.println(" - artifact -> " + a);
    // }
    // }

    if (_bundleMakerArtifacts != null && _bundleMakerArtifacts.contains(element)) {
      return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
    }

    // if (element instanceof IBundleMakerArtifact) {
    // IBundleMakerArtifact thisArtifact = (IBundleMakerArtifact) element;
    //
    // IStructuredSelection selection = (IStructuredSelection) _treeViewer.getSelection();
    // if (!selection.isEmpty()) {
    // boolean child = false;
    // Iterator<?> iterator = selection.iterator();
    // while (iterator.hasNext()) {
    // IBundleMakerArtifact artifact = (IBundleMakerArtifact) iterator.next();
    //
    // while (artifact != null) {
    // if (thisArtifact.equals(artifact)) {
    // child = true;
    // break;
    // }
    // artifact = artifact.getParent();
    // }
    // if (child) {
    // break;
    // }
    // }
    //
    // if (child) {
    // return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
    // }
    // }
    //
    // }

    // //
    // if (_bundleMakerArtifacts == null || _bundleMakerArtifacts.contains(element) || element instanceof IRootArtifact)
    // {
    // return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
    // }
    //
    // //
    // return Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);

    return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
  }

  @Override
  public Color getBackground(Object element) {
    return null;
  }
}
