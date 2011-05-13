package org.bundlemaker.dependencyanalysis.base.model.impl;

import org.bundlemaker.dependencyanalysis.base.model.IArtifact;

public interface IModifiableArtifact extends IArtifact {
	/**
	 * Setzt den Elternteil des Artefaktes
	 * 
	 * @param parent
	 */
	public void setParent(IArtifact parent);

}
