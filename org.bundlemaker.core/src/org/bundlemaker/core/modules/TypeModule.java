package org.bundlemaker.core.modules;


/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeModule extends AbstractModule<ITypeContainer, TypeContainer>
		implements ITypeModule {

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
