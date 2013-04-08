package org.bundlemaker.core.modules.transformation;

import org.bundlemaker.core.analysis.IModuleArtifact;

public interface ICreateModuleTransformation extends ITransformation {

  IModuleArtifact getModuleArtifact();
}
