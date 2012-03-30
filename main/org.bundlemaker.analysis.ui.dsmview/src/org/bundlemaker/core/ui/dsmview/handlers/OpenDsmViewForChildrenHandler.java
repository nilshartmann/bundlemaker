package org.bundlemaker.core.ui.dsmview.handlers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;

public class OpenDsmViewForChildrenHandler extends AbstractDsmViewHandler {

  @Override
  protected List<IBundleMakerArtifact> getArtifactsForDsmView(List<IBundleMakerArtifact> selectedArtifacts) {
    final List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();
    Iterator<IBundleMakerArtifact> iterator = selectedArtifacts.iterator();
    while (iterator.hasNext()) {
      result.addAll(iterator.next().getChildren());
    }
    return result;
  }
}
