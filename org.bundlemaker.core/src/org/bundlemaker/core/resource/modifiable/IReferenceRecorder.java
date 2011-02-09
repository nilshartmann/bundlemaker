package org.bundlemaker.core.resource.modifiable;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IReferenceRecorder {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @param referenceAttributes
	 */
	public void recordReference(String fullyQualifiedName,
			ReferenceAttributes referenceAttributes);
}
