package org.bundlemaker.core.resource.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.resource.Reference;
import org.bundlemaker.core.resource.ReferenceKey;
import org.bundlemaker.core.resource.ReferenceType;
import org.eclipse.core.runtime.Assert;

public abstract class ReferenceContainer {

	/** - */
	private FlyWeightCache _flyWeightCache;

	/** - */
	private Map<ReferenceKey, Reference> _referenceMap;

	/** - */
	private Set<Reference> _references;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param cache
	 */
	public ReferenceContainer(FlyWeightCache cache) {

		_flyWeightCache = cache;
		_referenceMap = new HashMap<ReferenceKey, Reference>();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 */
	public void recordReference(String fullyQualifiedName,
			ReferenceType referenceType, Boolean isExtends,
			Boolean isImplements, Boolean isCompiletime, Boolean isRuntime) {

		//
		Assert.isNotNull(fullyQualifiedName);

		//
		if (fullyQualifiedName.startsWith("java.")) {

			// do nothing
			return;
		}

		// create the key
		Assert.isNotNull(referenceType);
		ReferenceKey key = new ReferenceKey(fullyQualifiedName, referenceType);

		// get the reference
		Assert.isNotNull(_referenceMap, "Reference Map is null");
		Reference reference = _referenceMap.get(key);

		Assert.isNotNull(_flyWeightCache, "_flyWeightCache is not set!");

		// create completely new one
		if (reference == null) {

			reference = _flyWeightCache.getReference(fullyQualifiedName,
					referenceType, isExtends != null ? isExtends : false,
					isImplements != null ? isImplements : false,
					isCompiletime != null ? isCompiletime : false,
					isRuntime != null ? isRuntime : false);

			references().add(reference);
			_referenceMap.put(key, reference);

			return;
		}

		// return if current dependency matches the requested one
		// TODO !!!!
		// TODO !!!!
		// TODO !!!!
		if (equals(
				isExtends,
				reference.isExtends()
						&& equals(isImplements, reference.isImplements())
						&& equals(isCompiletime,
								reference.isCompileTimeReference())
						&& equals(isRuntime, reference.isRuntimeReference()))) {
			return;
		}

		// if current dependency does not match the requested one, we have to
		// request a new one
		references().remove(reference);

		reference = _flyWeightCache.getReference(fullyQualifiedName,
				referenceType, chooseValue(isExtends, reference.isExtends()),
				chooseValue(isImplements, reference.isImplements()),
				chooseValue(isCompiletime, reference.isCompileTimeReference()),
				chooseValue(isRuntime, reference.isRuntimeReference()));

		references().add(reference);
		_referenceMap.put(key, reference);
	}

	protected abstract Set<Reference> createReferencesSet();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	private boolean equals(Boolean b1, boolean b2) {

		//
		return b1 != null && b1.booleanValue() == b2;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	private boolean chooseValue(Boolean b1, boolean b2) {

		//
		return b2 || b1 != null && b1.booleanValue();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	private Set<Reference> references() {

		if (_references == null) {
			_references = createReferencesSet();
		}

		return _references;
	}

}
