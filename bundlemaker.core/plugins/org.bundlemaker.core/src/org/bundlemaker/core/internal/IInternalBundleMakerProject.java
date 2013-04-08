package org.bundlemaker.core.internal;

import java.util.Collection;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.projectdescription.IResourceStandin;

public interface IInternalBundleMakerProject extends IBundleMakerProject {

  Collection<IResourceStandin> getBinaryResourceStandins();

  Collection<IResourceStandin> getSourceResourceStandins();
}
