package org.bundlemaker.core.transformation.add;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.internal.analysis.AdapterUtils;

public class AddArtifactToGroup {

  public static void add(IGroupArtifact parent, IBundleMakerArtifact artifactToAdd) {

    if (artifactToAdd instanceof IModuleArtifact) {
      AdapterUtils.addModulesIfNecessaryAndResetClassification((IModuleArtifact) artifactToAdd, parent
          .getQualifiedName()
          .replace('|', '/'));

    } else if (artifactToAdd instanceof IGroupArtifact) {
      AdapterUtils.addModulesIfNecessaryAndResetClassification((IGroupArtifact) artifactToAdd,
          (IGroupAndModuleContainer) parent);
    }
  }

}
