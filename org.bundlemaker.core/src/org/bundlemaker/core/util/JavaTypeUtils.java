package org.bundlemaker.core.util;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JavaTypeUtils {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullQualifiedName
	 * @return
	 */
	public static boolean isLocalOrAnonymousTypeName(String fullQualifiedName) {
		Assert.isNotNull(fullQualifiedName);

		return fullQualifiedName.matches(".*\\$\\d.*");
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullQualifiedName
	 * @return
	 */
	public static String getEnclosingNonLocalAndNonAnonymousTypeName(
			String fullQualifiedName) {
		Assert.isNotNull(fullQualifiedName);

		// local or anonymous?
		if (isLocalOrAnonymousTypeName(fullQualifiedName)) {

			String[] parts = fullQualifiedName.split("\\$\\d");
			return parts[0];

		} else {
			return fullQualifiedName;
		}
	}
}
