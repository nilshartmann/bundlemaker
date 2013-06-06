package org.bundlemaker.core.resource;


public interface IRenameModuleTransformation extends ITransformation {

  IModuleIdentifier getOldModuleIdentifier();

  IModuleIdentifier getNewModuleIdentifier();
}
