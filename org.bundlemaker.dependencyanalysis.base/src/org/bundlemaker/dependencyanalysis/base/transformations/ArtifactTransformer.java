package org.bundlemaker.dependencyanalysis.base.transformations;

import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependencyModel;

public interface ArtifactTransformer {
	public BundlePathName getArtifactBundlePath(IArtifact artifact,	BundlePathName pathName);
	
	public void initialize(IDependencyModel dependencyModel);
}
