package org.bundlemaker.core.internal;

import java.util.Collection;

import org.bundlemaker.core.internal.projectdescription.IResourceStandin;
import org.bundlemaker.core.parser.IParserAwareBundleMakerProject;

public interface IInternalBundleMakerProject extends IParserAwareBundleMakerProject {

  Collection<IResourceStandin> getBinaryResourceStandins();

  Collection<IResourceStandin> getSourceResourceStandins();
}
