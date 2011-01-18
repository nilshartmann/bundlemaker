package org.bundlemaker.core.resource;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FlyWeightCache {

	/** - */
	Map<Reference, Reference> _referenceCache;

	/** - */
	Map<String, FlyWeightString> _flyWeightStrings;

	/**
	 * <p>
	 * Creates a new instance of type {@link FlyWeightCache}.
	 * </p>
	 */
	public FlyWeightCache() {

		//
		_referenceCache = new HashMap<Reference, Reference>();
		_flyWeightStrings = new HashMap<String, FlyWeightString>();
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
			ReferenceType referenceType, boolean isSourceCodeDependency,
			boolean isByteCodeDependency) {

		// create the key
		Reference key = new Reference(getFlyWeightString(fullyQualifiedName),
				referenceType, isSourceCodeDependency, isByteCodeDependency);

		//
		synchronized (this) {

			// get the reference
			Reference result = _referenceCache.get(key);

			// return result if not null
			if (result != null) {
				return result;
			}
			// else return the key
			else {
				_referenceCache.put(key, key);
				return key;
			}
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param string
	 * @return
	 */
	public FlyWeightString getFlyWeightString(String string) {

		synchronized (this) {

			FlyWeightString result = _flyWeightStrings.get(string);

			//
			if (result != null) {
				return result;
			}

			//
			else {
				FlyWeightString flyWeightString = new FlyWeightString(string);
				_flyWeightStrings.put(string, flyWeightString);
				return flyWeightString;
			}
		}
	}
}
