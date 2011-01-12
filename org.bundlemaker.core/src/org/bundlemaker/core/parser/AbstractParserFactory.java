package org.bundlemaker.core.parser;

import org.bundlemaker.core.IBundleMakerProject;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractParserFactory implements IParserFactory {

	@Override
	public void initialize() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void initialize(IBundleMakerProject bundleMakerProject)
			throws CoreException {

	}

	@Override
	public boolean isInitialized(IBundleMakerProject bundleMakerProject) {
		return true;
	}

	@Override
	public void dispose(IBundleMakerProject bundleMakerProject) {
	}
}
