package org.bundlemaker.core.internal.api.project;

import java.util.Collection;

import org.bundlemaker.core.internal.api.resource.IResourceStandin;
import org.bundlemaker.core.parser.IParserAwareBundleMakerProject;

public interface IInternalBundleMakerProject extends IParserAwareBundleMakerProject {

  Collection<IResourceStandin> getBinaryResourceStandins();

  Collection<IResourceStandin> getSourceResourceStandins();
}
