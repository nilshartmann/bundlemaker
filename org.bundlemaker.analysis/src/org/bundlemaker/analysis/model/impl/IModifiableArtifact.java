package org.bundlemaker.analysis.model.impl;

import org.bundlemaker.analysis.model.IArtifact;

public interface IModifiableArtifact extends IArtifact {
	/**
	 * Setzt den Elternteil des Artefaktes
	 * 
	 * @param parent
	 */
	public void setParent(IArtifact parent);

}
