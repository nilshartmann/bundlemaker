package org.bundlemaker.core.internal.api.project;

import java.util.Collection;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.api.resource.IResourceStandin;

public interface IInternalBundleMakerProject extends IBundleMakerProject {

  Collection<IResourceStandin> getBinaryResourceStandins();

  Collection<IResourceStandin> getSourceResourceStandins();
}
