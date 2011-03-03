package org.bundlemaker.core.internal.modules;

import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ITypeContainer;


/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeModule extends AbstractModule<ITypeContainer, TypeContainer>
		implements IModule {

	/**
	 * <p>
	 * Creates a new instance of type {@link TypeModule}.
	 * </p>
	 *
	 * @param moduleIdentifier
	 */
	public TypeModule(IModuleIdentifier moduleIdentifier) {
		super(moduleIdentifier, new TypeContainer());
	}
	
}
