package org.bundlemaker.core.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class ReferenceContainer {

	/** the fly weight cahce */
	private FlyWeightCache _flyWeightCache;

	/** the reference map */
	private Map<ReferenceKey, Reference> _referenceMap;

	/** the references */
	private Set<Reference> _references;

	/**
	 * <p>
	 * Creates a new instance of type {@link ReferenceContainer}.
	 * </p>
	 * 
	 * @param cache
	 */
	public ReferenceContainer(FlyWeightCache cache) {
		Assert.isNotNull(cache);

		//
		_flyWeightCache = cache;
		_referenceMap = new HashMap<ReferenceKey, Reference>();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected abstract Set<Reference> createReferencesSet();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 */
	public void recordReference(String fullyQualifiedName,
			ReferenceType referenceType, boolean isExtends,
			boolean isImplements, boolean isClassAnnotation,
			boolean isCompiletime, boolean isRuntime) {

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
					referenceType, isExtends, isImplements, isClassAnnotation,
					isCompiletime, isRuntime);

			references().add(reference);
			_referenceMap.put(key, reference);

			return;
		}

		// return if current dependency matches the requested one
		if (isExtends == reference.isExtends()
				&& isImplements == reference.isImplements()
				&& isCompiletime == reference.isCompileTimeReference()
				&& isRuntime == reference.isRuntimeReference()) {
			return;
		}

		// if current dependency does not match the requested one, we have to
		// request a new one
		references().remove(reference);

		reference = _flyWeightCache.getReference(fullyQualifiedName,
				referenceType, chooseValue(isExtends, reference.isExtends()),
				chooseValue(isImplements, reference.isImplements()),
				chooseValue(isClassAnnotation, reference.isClassAnnotation()),
				chooseValue(isCompiletime, reference.isCompileTimeReference()),
				chooseValue(isRuntime, reference.isRuntimeReference()));

		references().add(reference);
		_referenceMap.put(key, reference);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	private boolean chooseValue(boolean b1, boolean b2) {
		return b1 || b2;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	private Set<Reference> references() {

		// create if necessary
		if (_references == null) {
			_references = createReferencesSet();
		}

		// return the references
		return _references;
	}

}
