package org.bundlemaker.core.parser.jdt.internal.ecj;

import java.io.IOException;

import org.bundlemaker.core.parser.jdt.internal.JdtParser;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
@SuppressWarnings("restriction")
public class ModifiableResourceCompilationUnit implements ICompilationUnit {

	/** - */
	private IResource _resource;

	/** - */
	private char[] _content;

	/**
	 * <p>
	 * Creates a new instance of type {@link ModifiableResourceCompilationUnit}.
	 * </p>
	 * 
	 * @param resource
	 * @throws IOException
	 */
	public ModifiableResourceCompilationUnit(IResource resource, char[] content)
			throws IOException {
		Assert.isNotNull(resource);

		// assign the resource
		_resource = resource;

		//
		if (content != null) {
			_content = content;
		} else {
			_content = JdtParser.getCharsFromInputStream(_resource
					.getInputStream());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public char[] getFileName() {
		return _resource.getPath().toCharArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public char[] getContents() {
		return _content;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public char[] getMainTypeName() {
		String result = _resource.getName();
		result = result.substring(0, result.length() - ".java".length());
		return result.toCharArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public char[][] getPackageName() {
		String packageName = _resource.getPackageName();
		String[] splittedPackageName = packageName.split("\\.");
		char[][] result = new char[splittedPackageName.length][];
		for (int i = 0; i < splittedPackageName.length; i++) {
			String name = splittedPackageName[i];
			result[i] = name.toCharArray();
		}
		return result;
	}
}