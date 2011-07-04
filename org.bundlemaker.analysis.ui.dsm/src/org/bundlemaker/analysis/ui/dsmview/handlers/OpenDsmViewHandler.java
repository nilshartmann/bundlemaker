package org.bundlemaker.analysis.ui.dsmview.handlers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;

public class OpenDsmViewHandler extends AbstractDsmViewHandler {

  @Override
  protected HashSet<IArtifact> getArtifactsForDsmView(List<IArtifact> selectedArtifacts) {
    final HashSet<IArtifact> result = new HashSet<IArtifact>();
    Iterator<IArtifact> iterator = selectedArtifacts.iterator();
    while (iterator.hasNext()) {
      result.add(iterator.next());
    }
    return result;
  }

}
