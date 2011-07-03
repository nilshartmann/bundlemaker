package org.bundlemaker.analysis.ui;

import org.bundlemaker.analysis.ui.internal.AnalysisContext;

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

}
