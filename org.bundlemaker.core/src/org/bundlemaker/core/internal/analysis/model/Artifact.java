/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.analysis.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.model.IDependency;

/**
 * <p>Diese Klasse repraesentiert die Primaerartefakte innerhalb eines Softwaresystems
 * 
 * <p>Ein Primaerartefakt ist die kleinste Einheit, die in einem Softwaresystem betrachtet wird.
 * Aus den Abhaengigkeiten der Primaerartefakte lassen sich die Abaehngigkeiten der gruppierenden
 * Artefakte ermitteln.
 * 
 * 
 * @author Kai Lehmann
 */
public class Artifact extends AbstractArtifact {
	
	//emptylist
	private static final List<IArtifact> EMPTY_LIST = new ArrayList<IArtifact>();
	
	//Dependencies
	Map<IArtifact, IDependency> dependencies;
	
	public Artifact(ArtifactType type, String name) {
		super(type, name);
		dependencies = new HashMap<IArtifact, IDependency>();
	}	
	
	@Override
	public String getQualifiedName() {
		return getParent().getName() + "." + getName();
	}
	
	@Override
	public Collection<IArtifact> getLeafs() {
		return null;
	}
	
	@Override
	public IDependency getDependency(IArtifact artifact) {
		if( artifact.getLeafs() == null ) {
			return dependencies.get(artifact);
		} else {
			DependencyAlt dependencyContainer = new DependencyAlt(this, artifact, 0);
			for( IArtifact leaf: artifact.getLeafs() ) {
				IDependency dependency = getDependency(leaf);
				if( (dependency != null) && (dependency.getTo().getType() == ArtifactType.Type) ) {
					dependencyContainer.addDependency(dependency);
				}
			}
			return dependencyContainer;
		}
	}
	
	
	@Override
	public void addArtifact(IArtifact artifact) {
		throw new UnsupportedOperationException("Ein Blatt kann keine Kinder haben.");

	}

	@Override
	public DependencyAlt addDependency(IArtifact artifact) {
		DependencyAlt dependency = new DependencyAlt(this, artifact);
		dependencies.put(artifact, dependency);
		
		return dependency;
	}

	@Override
	public boolean contains(IArtifact artifact) {
		return this.equals(artifact);
	}

	@Override
	public Collection<IDependency> getDependencies() {
		return dependencies.values();
	}

	@Override
	public Collection<IArtifact> getChildren() {
		return EMPTY_LIST;
	}
	
	@Override
	public Integer size(){
		return 1;
	}

	@Override
	public boolean removeArtifact(IArtifact artifact) {
		throw new UnsupportedOperationException("Ein Blatt kann keine Kinder haben.");
		
	}

}
