package org.bundlemaker.dependencyanalysis.rules.impl;

import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.bundlemaker.dependencyanalysis.base.rules.Severity;
import org.bundlemaker.dependencyanalysis.base.rules.Violation;

/**
 * 
 */
public class OrdinalRule extends Rule {
	
	
	@Override
	public boolean isViolatedBy(IDependency dependency) {
		IArtifact from = dependency.getFrom();
		IArtifact to = dependency.getTo();

		Integer fromOrdinal = from.getOrdinal();
		Integer toOrdinal = to.getOrdinal();
		
		if(fromOrdinal == null || toOrdinal == null){
			return false;
		}

		return fromOrdinal < toOrdinal && dependency.getWeight() != 0;

	}

	@Override
	public Violation getViolation() {
		return new Violation(Severity.ERROR, "Die Rangordnung der Artefakte verbietet eine Abhaengigkeit");
	}

}
