package org.bundlemaker.core.modules.modifiable;

import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModifiableResourceModule extends IResourceModule {

	void setClassification(IPath classification);

	IModifiableResourceContainer getModifiableSelfResourceContainer();
}
