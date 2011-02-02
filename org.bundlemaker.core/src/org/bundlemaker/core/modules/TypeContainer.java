package org.bundlemaker.core.modules;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeContainer implements ITypeContainer {

	/** the contained type names */
	private Map<String, IType> _containedTypes;

	/**
	 * <p>
	 * Creates a new instance of type {@link TypeContainer}.
	 * </p>
	 */
	public TypeContainer() {

		// create the contained types sets
		_containedTypes = new HashMap<String, IType>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IType getType(String fullyQualifiedName) {

		//
		return _containedTypes.get(fullyQualifiedName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<IType> getAllContainedTypes() {

		// return an unmodifiable copy
		return Collections.unmodifiableCollection(_containedTypes.values());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getContainedTypeNames() {

		// return an unmodifiable copy
		return Collections.unmodifiableSet(_containedTypes.keySet());
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
		for (String containedType : _containedTypes.keySet()) {

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
		for (String containedType : _containedTypes.keySet()) {

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
	public Map<String, IType> getModifiableContainedTypesMap() {
		return _containedTypes;
	}
}
