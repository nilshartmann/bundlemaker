package org.bundlemaker.core.transformation.add;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.AdapterRoot2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterUtils;

public class AddArtifactToRoot {

  public static void add(IRootArtifact parent, IBundleMakerArtifact artifactToAdd) {
    if (artifactToAdd instanceof IModuleArtifact) {
      if (!AdapterUtils.addModulesIfNecessaryAndResetClassification((IModuleArtifact) artifactToAdd, null)) {
        ((AdapterRoot2IArtifact) parent).internalAddArtifact(artifactToAdd);
      }
    } else if (artifactToAdd instanceof IGroupArtifact) {
      AdapterUtils.addModulesIfNecessaryAndResetClassification((IGroupArtifact) artifactToAdd,
          (AdapterRoot2IArtifact) parent);
    }
  }
}
