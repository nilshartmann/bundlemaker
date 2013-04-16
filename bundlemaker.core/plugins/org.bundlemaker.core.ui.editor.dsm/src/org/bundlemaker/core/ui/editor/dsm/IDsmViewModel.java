package org.bundlemaker.core.ui.editor.dsm;

import java.util.Collection;
import java.util.Observer;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.editor.dsm.widget.IDsmContentProvider;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IDsmViewModel extends IDsmContentProvider {

  void refresh(Collection<IBundleMakerArtifact> artifacts);

  @Override
  int getItemCount();

  String[] getLabels();

  /**
   * @return the labels according to the user-selected presentation mode
   */
  String[] getDisplayLabels();

  String[][] getValues();

  LabelPresentationMode getLabelPresentationMode();
}
