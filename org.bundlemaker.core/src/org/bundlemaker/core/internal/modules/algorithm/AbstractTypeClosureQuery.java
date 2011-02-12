package org.bundlemaker.core.internal.modules.algorithm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.internal.modules.ModularizedSystem;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 */
public abstract class AbstractTypeClosureQuery {

	/** - */
	private Map<String, IType> _typesMap;

	/** - */
	private ModularizedSystem _modularizedSystem;

	/**
	 * <p>
	 * </p>
	 */
	public AbstractTypeClosureQuery(ModularizedSystem modularizedSystem) {

		Assert.isNotNull(modularizedSystem);

		_modularizedSystem = modularizedSystem;

		_typesMap = new HashMap<String, IType>();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected ModularizedSystem getModularizedSystem() {
		return _modularizedSystem;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected Map<String, IType> getTypesMap() {
		return _typesMap;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Collection<IType> getTypes() {
		return _typesMap.values();
	}
}
