package org.bundlemaker.analysis.model.dependencies;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.impl.Dependency;
import org.sonar.graph.EdgeFactory;

public class ArtifactEdgeFactory implements EdgeFactory<IArtifact, DependencyEdge> {
	@Override
	public DependencyEdge createEdge(IArtifact from, IArtifact to) {
		return new DependencyEdge( new Dependency(from, to) );
	}

	@Override
	public DependencyEdge createEdge(IArtifact from, IArtifact to, int weight) {
		return new DependencyEdge( new Dependency(from, to, weight) );
	}

}
