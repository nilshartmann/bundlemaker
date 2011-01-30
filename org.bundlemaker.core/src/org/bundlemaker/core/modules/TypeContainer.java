package org.bundlemaker.core.modules;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeContainer implements ITypeContainer {

	/** the contained types */
	private Set<String> _containedTypes;

	/**
	 * <p>
	 * Creates a new instance of type {@link TypeContainer}.
	 * </p>
	 */
	public TypeContainer() {

		// create the contained types list
		_containedTypes = new HashSet<String>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getContainedTypeNames() {

		// return an unmodifiable copy
		return Collections.unmodifiableSet(_containedTypes);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getContainedTypeNames(IQueryFilter filter) {

		// assert
		Assert.isNotNull(filter);

		// create the result
		Set<String> result = new HashSet<String>();

		//
		for (String containedType : _containedTypes) {

			if (!result.contains(containedType)
					&& filter.matches(containedType)) {

				// add the result
				result.add(containedType);
			}
		}

		// return result
		return Collections.unmodifiableSet(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getContainedPackageNames() {

		return getContainedPackageNames(new IQueryFilter() {
			@Override
			public boolean matches(String content) {
				return true;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getContainedPackageNames(IQueryFilter filter) {
		Assert.isNotNull(filter);

		// create the result
		Set<String> result = new HashSet<String>();

		//
		for (String containedType : _containedTypes) {

			//
			String packageName = "";

			//
			if (containedType.indexOf('.') != -1) {

				// get the packageName
				packageName = containedType.substring(0,
						containedType.lastIndexOf('.'));
			}

			//
			if (!result.contains(packageName) && filter.matches(packageName)) {
				result.add(packageName);
			}

		}

		// return result
		return Collections.unmodifiableSet(result);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Set<String> getModifiableContainedTypes() {
		return _containedTypes;
	}
}
