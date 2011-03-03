package org.bundlemaker.core.resource.modifiable;

import org.bundlemaker.core.internal.resource.Type;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.TypeEnum;

/**
 * <p>
 * 
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IModifiableResource extends IResource, IReferenceRecorder {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return
	 */
	public Type getOrCreateType(String fullyQualifiedName, TypeEnum typeEnum);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return
	 */
	public Type getType(String fullyQualifiedName);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param stickyResource
	 */
	public void addStickyResource(IModifiableResource stickyResource);
}