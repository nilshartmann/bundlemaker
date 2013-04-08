package org.bundlemaker.core.modules.transformation;

import org.bundlemaker.core.modules.IModuleIdentifier;

public interface IRenameModuleTransformation extends IUndoableTransformation {

  IModuleIdentifier getOldModuleIdentifier();

  IModuleIdentifier getNewModuleIdentifier();

}
