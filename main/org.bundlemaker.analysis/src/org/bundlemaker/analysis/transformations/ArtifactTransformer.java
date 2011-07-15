package org.bundlemaker.analysis.transformations;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;

public interface ArtifactTransformer {
	public BundlePathName getArtifactBundlePath(IArtifact artifact,	BundlePathName pathName);
	
	public void initialize(IDependencyModel dependencyModel);
}
