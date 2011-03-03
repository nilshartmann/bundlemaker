package org.bundlemaker.core.parser.bytecode;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.parser.AbstractParserFactory;
import org.bundlemaker.core.parser.IParser;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ByteCodeParserFactory extends AbstractParserFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IParser createParser(IBundleMakerProject bundleMakerProject,
			boolean parseIndirectReferences)
			throws CoreException {

		// return the new byte code parser
		return new ByteCodeParser();
	}
}
