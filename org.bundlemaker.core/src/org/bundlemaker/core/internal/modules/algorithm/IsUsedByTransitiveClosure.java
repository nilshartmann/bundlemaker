package org.bundlemaker.core.internal.modules.algorithm;

import java.util.Set;

import org.bundlemaker.core.internal.modules.ModularizedSystem;
import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * </p>
 */
public class IsUsedByTransitiveClosure extends AbstractTypeQuery {
	
	/**
	 * <p>
	 * Creates a new instance of type {@link IsUsedByTransitiveClosure}.
	 * </p>
	 *
	 * @param modularizedSystem
	 */
	public IsUsedByTransitiveClosure(ModularizedSystem modularizedSystem) {
		super(modularizedSystem);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeName
	 */
	public void resolveType(String typeName) {

		System.out.println(String.format("Resolving type '%s'.", typeName));

		Set<IType> types = getModularizedSystem().getTypeNameToReferringCache()
				.get(typeName);

		if (types == null || types.isEmpty()) {
			System.out.println("NO TYPE REFERS TO '" + typeName + "'.");
			return;
		}

		for (IType type : types) {

			if (!getTypesMap().containsKey(type.getFullyQualifiedName())) {
				getTypesMap().put(type.getFullyQualifiedName(), type);
				resolveType(type.getFullyQualifiedName());
			}
		}
	}

}
