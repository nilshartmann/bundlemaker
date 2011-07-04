package org.bundlemaker.dependencyanalysis.model;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.rules.Violation;
import org.sonar.graph.Edge;

public class DependencyEdge implements Edge<IArtifact> {
	private IDependency dependency;
	
	public DependencyEdge( IDependency dependency ) {
		this.dependency = dependency;
	}
	
	public IDependency getDependency() {
		return dependency;
	}
	
	/**
	 * Fuegt der Abhaengigkeit einen Regelverstoﬂ hinzu
	 * 
	 * @param violation
	 *            Die Violation
	 */
	public void addViolation(Violation violation) {
		dependency.addViolation(violation);
	}

	@Override
	public IArtifact getFrom() {
		return dependency.getFrom();
	}
	
	@Override
	public IArtifact getTo() {
		return dependency.getTo();
	}
	
	@Override
	public int getWeight() {
		return dependency.getWeight();
	}
}
