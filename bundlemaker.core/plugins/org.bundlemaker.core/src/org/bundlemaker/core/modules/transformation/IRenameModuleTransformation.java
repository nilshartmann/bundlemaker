package org.bundlemaker.core.modules.transformation;

import org.bundlemaker.core.resource.IModuleIdentifier;

public interface IRenameModuleTransformation extends ITransformation {

  IModuleIdentifier getOldModuleIdentifier();

  IModuleIdentifier getNewModuleIdentifier();
}
