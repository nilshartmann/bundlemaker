package org.bundlemaker.core.parser.jdt.ast;

import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.resource.IResourceKey;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtProblemAdapter implements IProblem {

	/** - */
	private org.eclipse.jdt.core.compiler.IProblem _compilerProblem;

	/** - */
	private IResourceKey _resourceKey;

	/**
	 * <p>
	 * Creates a new instance of type {@link JdtProblemAdapter}.
	 * </p>
	 * 
	 * @param resourceKey
	 * @param compilerProblem
	 */
	public JdtProblemAdapter(IResourceKey resourceKey,
			org.eclipse.jdt.core.compiler.IProblem compilerProblem) {

		// assert
		Assert.isNotNull(resourceKey);
		Assert.isNotNull(compilerProblem);

		// the resource key
		_resourceKey = resourceKey;

		// set the comiler problem
		_compilerProblem = compilerProblem;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IResourceKey getResource() {
		return _resourceKey;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isError() {
		return _compilerProblem.isError();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSourceLineNumber() {
		return _compilerProblem.getSourceLineNumber();
	}

	/**
	 * {@inheritDoc}
	 */
	public int getSourceEnd() {
		return _compilerProblem.getSourceEnd();
	}

	/**
	 * {@inheritDoc}
	 */
	public int getSourceStart() {
		return _compilerProblem.getSourceStart();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMessage() {
		return _compilerProblem.getMessage();
	}

}
