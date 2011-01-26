package org.bundlemaker.core.resource.internal;

import java.util.concurrent.ConcurrentHashMap;

import org.bundlemaker.core.resource.Reference;
import org.bundlemaker.core.resource.ReferenceAttributes;
import org.bundlemaker.core.resource.ReferenceType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FlyWeightCache {

	/** - */
	private static final int FLY_WEIGHT_STRINGS_INITIAL_CAPACITY = 10000;

	/** - */
	private static final int REFERENCE_CACHE_INITIAL_CAPACITY = 10000;

	/** - */
	private static final int REFERENCE_ATTRIBUTES_CACHE_INITIAL_CAPACITY = 10000;

	/** - */
	ConcurrentHashMap<Reference, Reference> _referenceCache;

	/** - */
	ConcurrentHashMap<String, FlyWeightString> _flyWeightStrings;

	/** - */
	ConcurrentHashMap<ReferenceAttributes, ReferenceAttributes> _referenceAttributesCache;

	/**
	 * <p>
	 * Creates a new instance of type {@link FlyWeightCache}.
	 * </p>
	 */
	public FlyWeightCache() {

		// create the concurrent hash maps
		_referenceCache = new ConcurrentHashMap<Reference, Reference>(
				REFERENCE_CACHE_INITIAL_CAPACITY);

		_flyWeightStrings = new ConcurrentHashMap<String, FlyWeightString>(
				FLY_WEIGHT_STRINGS_INITIAL_CAPACITY);

		_referenceAttributesCache = new ConcurrentHashMap<ReferenceAttributes, ReferenceAttributes>(
				REFERENCE_ATTRIBUTES_CACHE_INITIAL_CAPACITY);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @param referenceType
	 * @param isSourceCodeDependency
	 * @param isByteCodeDependency
	 * @return
	 */
	public Reference getReference(String fullyQualifiedName,
			ReferenceType referenceType, boolean isExtends,
			boolean isImplements, boolean isCompiletimeReference,
			boolean isRuntimeReference) {

		// create the key
		ReferenceAttributes attributes = getReferenceAttributes(referenceType,
				isExtends, isImplements, isCompiletimeReference,
				isRuntimeReference);

		Reference key = new Reference(getFlyWeightString(fullyQualifiedName),
				attributes);

		// return if already there
		if (_referenceCache.containsKey(key)) {
			return _referenceCache.get(key);
		}

		// map doesn't contain key, create one -- note that first writer wins,
		// all others just throw away their value
		_referenceCache.putIfAbsent(key, key);

		// return the value that won
		return _referenceCache.get(key);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param string
	 * @return
	 */
	public FlyWeightString getFlyWeightString(String string) {

		// return if already there
		if (_flyWeightStrings.containsKey(string)) {
			return _flyWeightStrings.get(string);
		}

		// map doesn't contain key, create one -- note that first writer wins,
		// all others just throw away their value
		FlyWeightString flyWeightString = new FlyWeightString(string);
		_flyWeightStrings.putIfAbsent(string, flyWeightString);

		// return the value that won
		return _flyWeightStrings.get(string);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param referenceType
	 * @param isExtends
	 * @param isImplements
	 * @param isSourceCodeDependency
	 * @param isByteCodeDependency
	 * @return
	 */
	ReferenceAttributes getReferenceAttributes(ReferenceType referenceType,
			boolean isExtends, boolean isImplements,
			boolean isCompiletimeReference, boolean isRuntimeReference) {

		// create the key
		ReferenceAttributes attributes = new ReferenceAttributes(referenceType,
				isExtends, isImplements, isCompiletimeReference,
				isRuntimeReference);

		// return if already there
		if (_referenceAttributesCache.containsKey(attributes)) {
			return _referenceAttributesCache.get(attributes);
		}

		// map doesn't contain key, create one -- note that first writer wins,
		// all others just throw away their value
		_referenceAttributesCache.putIfAbsent(attributes, attributes);

		// return the value that won
		return _referenceAttributesCache.get(attributes);
	}

	public ConcurrentHashMap<Reference, Reference> getReferenceCache() {
		return _referenceCache;
	}

	public ConcurrentHashMap<String, FlyWeightString> getFlyWeightStrings() {
		return _flyWeightStrings;
	}

	public ConcurrentHashMap<ReferenceAttributes, ReferenceAttributes> getReferenceAttributesCache() {
		return _referenceAttributesCache;
	}
}
