package org.bundlemaker.analysis.ui.dsmview.handlers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;

public class OpenDsmViewForChildrenHandler extends AbstractDsmViewHandler {

  @Override
  protected List<IArtifact> getArtifactsForDsmView(List<IArtifact> selectedArtifacts) {
    final List<IArtifact> result = new LinkedList<IArtifact>();
    Iterator<IArtifact> iterator = selectedArtifacts.iterator();
    while (iterator.hasNext()) {
      result.addAll(iterator.next().getChildren());
    }
    return result;
  }
}
