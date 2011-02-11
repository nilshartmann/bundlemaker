package org.bundlemaker.core.transformation;

import org.bundlemaker.core.internal.modules.ModularizedSystem;

/**
 * <p>
 * </p>
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ITransformation {

	/**
	 * <p>
	 * </p>
	 *
	 * @param modularizedSystem
	 */
	public void apply(ModularizedSystem modularizedSystem);
}
