package org.bundlemaker.dependencyanalysis.base.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;

public class CachedTransitiveClosure {
	private Collection<IArtifact> artifacts;
	private Map<IArtifact,IArtifact[]> cachedTransitiveClosues = new HashMap<IArtifact, IArtifact[]>();
	
	public CachedTransitiveClosure( Collection<IArtifact> artifacts ) {
		this.artifacts = artifacts;
		initCache();
	}
	
	private void initCache() {
		for( IArtifact artifact: artifacts) {
			Collection<? extends IDependency> dependencies = artifact.getDependencies(artifacts);
			IArtifact[] dependentArtifacts = new IArtifact[dependencies.size()];
			int index = 0;
			for( IDependency dependency: dependencies) {
				dependentArtifacts[index++] = dependency.getTo();
			}
			cachedTransitiveClosues.put(artifact, dependentArtifacts);			
		}
	}
	private IArtifact[] getDependencies( IArtifact artifact) {
		return cachedTransitiveClosues.get(artifact);
	}
	
	public Collection<IArtifact> usesArtifacts( IArtifact artifact ) {
		Set<IArtifact> dependencies = new HashSet<IArtifact>();
		internalUsesArtifacts(artifact, new ArrayList<IArtifact>(), dependencies);
		
		return dependencies;		
	}

	private void internalUsesArtifacts(
			IArtifact rootArtifact, 
		ArrayList<IArtifact> visitedArtifacts,
		Set<IArtifact> dependencies) 
	{
		dependencies.add(rootArtifact);
		for(IArtifact artifact : getDependencies(rootArtifact)){
			if(!visitedArtifacts.contains(artifact)){
				visitedArtifacts.add(artifact);					
				internalUsesArtifacts( artifact, visitedArtifacts, dependencies );
			}			
		}		
	}
}
