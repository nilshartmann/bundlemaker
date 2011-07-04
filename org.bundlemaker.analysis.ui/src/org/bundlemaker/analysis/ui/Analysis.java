package org.bundlemaker.analysis.ui;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.internal.Activator;
import org.bundlemaker.analysis.ui.internal.AnalysisContext;
import org.eclipse.swt.graphics.Image;

public class Analysis {

  private static Analysis        _instance;

  private final IAnalysisContext _analysisContext;

  public static Analysis instance() {
    if (_instance == null) {
      _instance = new Analysis();
    }

    return _instance;
  }

  private Analysis() {
    _analysisContext = new AnalysisContext();
  }

  public IAnalysisContext getContext() {
    return _analysisContext;
  }

  public Image getIconForArtifact(IArtifact artifact) {
    if (artifact == null) {
      return null;
    }

    return Activator.getDefault().getIcon(artifact.getType().getKuerzel());
  }

}
