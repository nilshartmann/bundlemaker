package org.bundlemaker.core.util;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModelUtils {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeName
	 * @return
	 */
	public static String getPackageName(String typeName) {

		// assert
		Assert.isNotNull(typeName);

		if (typeName.indexOf('.') != -1) {
			return typeName.substring(0, typeName.lastIndexOf('.'));
		}

		// return default package
		return "";
	}

	// /**
	// * <p>
	// * </p>
	// *
	// * @param name
	// * @param version
	// * @return
	// */
	// public static ModifiableModuleIdentifier createModuleIdentifier(
	// String name, String version) {
	//
	// Assert.isNotNull(name);
	// Assert.isNotNull(version);
	//
	// // create the module identifier
	// ModifiableModuleIdentifier modifiableModuleIdentifier =
	// ModuleFactory.eINSTANCE
	// .createModifiableModuleIdentifier();
	//
	// // set name and version
	// modifiableModuleIdentifier.setName(name);
	// modifiableModuleIdentifier.setVersion(version);
	//
	// // return the result
	// return modifiableModuleIdentifier;
	// }

	// /**
	// * <p>
	// * </p>
	// *
	// * @param identifier
	// * @return
	// */
	// public static String toString(IModuleIdentifier identifier) {
	// Assert.isNotNull(identifier);
	//
	// StringBuilder stringBuilder = new StringBuilder();
	// stringBuilder.append(identifier.getName());
	// stringBuilder.append("_");
	// stringBuilder.append(identifier.getVersion());
	// return stringBuilder.toString();
	// }

	// /**
	// * <p>
	// * </p>
	// *
	// * @param first
	// * @param second
	// * @return
	// */
	// public static boolean equals(IModuleIdentifier first,
	// IModuleIdentifier second) {
	//
	// Assert.isNotNull(first);
	// Assert.isNotNull(second);
	//
	// return equals(first, second.getName(), second.getVersion());
	// }

	// /**
	// * <p>
	// * </p>
	// *
	// * @param identifier
	// * @param name
	// * @param version
	// * @return
	// */
	// public static boolean equals(IModuleIdentifier identifier, String name,
	// String version) {
	//
	// return identifier.getName().equals(name)
	// && identifier.getVersion().equals(version);
	// }
}
