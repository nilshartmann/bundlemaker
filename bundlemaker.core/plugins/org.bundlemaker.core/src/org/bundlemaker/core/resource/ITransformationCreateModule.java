package org.bundlemaker.core.resource;

import org.bundlemaker.core.analysis.IModuleArtifact;

public interface ITransformationCreateModule extends ITransformation {

  // TODO: REMOVE
  IModuleArtifact getModuleArtifact();
}
