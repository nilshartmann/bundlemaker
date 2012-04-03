package org.bundlemaker.core.ui;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.ui.internal.BundleMakerUiUtils;

public class CommonNavigatorUtils {

  public static void refreshProjectExplorer(IBundleMakerProject bundleMakerProject) {
    BundleMakerUiUtils.refreshProjectExplorer(bundleMakerProject);
  }

  public static void refreshProjectExplorer() {
    BundleMakerUiUtils.refreshProjectExplorer();
  }
}
