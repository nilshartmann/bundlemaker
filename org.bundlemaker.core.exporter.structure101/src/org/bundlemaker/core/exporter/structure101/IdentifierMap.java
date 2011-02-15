package org.bundlemaker.core.exporter.structure101;

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.modules.IModule;
import org.eclipse.core.runtime.Assert;

public class IdentifierMap {

	/** - */
	private long _uniqueIdCounter = 0;

	/** - */
	private Map<String, String> _idMap;

	/**
	 * <p>
	 * Creates a new instance of type {@link IdentifierMap}.
	 * </p>
	 */
	public IdentifierMap() {

		// create lists and maps
		_idMap = new HashMap<String, String>();

		// reset counter
		_uniqueIdCounter = 0;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeModule
	 * @return
	 */
	public String getModuleId(IModule typeModule) {
		return getShortenId(getModuleName(typeModule));
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeModule
	 * @param packageName
	 * @return
	 */
	public String getPackageId(IModule typeModule, String packageName) {
		return getShortenId(getModuleName(typeModule) + "_" + packageName);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeModule
	 * @param fullyQualifiedType
	 * @return
	 */
	public String getResourceId(IModule typeModule, String resourceName) {
		return getShortenId(getModuleName(typeModule) + "_"
				+ resourceName);
	}
	
	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeModule
	 * @param fullyQualifiedType
	 * @return
	 */
	public String getClassId(IModule typeModule, String fullyQualifiedType) {
		
		Assert.isNotNull(typeModule);
		Assert.isNotNull(fullyQualifiedType);
		
		return getShortenId(getModuleName(typeModule) + "_"
				+ fullyQualifiedType);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param longID
	 * @return
	 */
	private String getShortenId(String longID) {

		//
		if (!_idMap.containsKey(longID)) {
			_idMap.put(longID, Long.toString(_uniqueIdCounter++));
		}

		// get the id
		return _idMap.get(longID);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeModule
	 * @return
	 */
	private String getModuleName(IModule typeModule) {

		// get the id
		return typeModule.getModuleIdentifier().getName() + "_"
				+ typeModule.getModuleIdentifier().getVersion();
	}
}
