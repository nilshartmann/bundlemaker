package org.bundlemaker.core.resource;


public interface ITransformationRenameModule extends ITransformation {

  IModuleIdentifier getOldModuleIdentifier();

  IModuleIdentifier getNewModuleIdentifier();
}
