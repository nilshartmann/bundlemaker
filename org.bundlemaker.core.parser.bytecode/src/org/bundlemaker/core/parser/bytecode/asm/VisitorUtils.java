package org.bundlemaker.core.parser.bytecode.asm;

import org.bundlemaker.core.resource.ReferenceType;
import org.bundlemaker.core.resource.modifiable.ReferenceAttributes;
import org.objectweb.asm.Type;

final class VisitorUtils {

	/**
	 * Gets the package name from the supplied {@link Type}.
	 * 
	 * @param type
	 *            the <code>Type</code>.
	 * @return the package name.
	 */
	public static String getPackageName(Type type) {
		String name;
		if (type.getSort() == Type.OBJECT) {
			name = type.getClassName();
		} else if (type.getSort() == Type.ARRAY) {
			return getPackageName(type.getElementType());
		} else {
			return null;
		}
		int dotIndex = name.lastIndexOf('.');
		if (dotIndex > -1) {
			return name.substring(0, dotIndex);
		} else {
			return null;
		}
	}

	public static String getFullyQualifiedTypeName(Type type) {
		if (type.getSort() == Type.OBJECT) {
			return type.getClassName();
		} else if (type.getSort() == Type.ARRAY) {
			return getFullyQualifiedTypeName(type.getElementType());
		} else {
			return null;
		}
	}

	public static void recordReferencedTypes(AsmReferenceRecorder recorder,
			Type... types) {

		for (Type t : types) {
			recorder.recordReference(getFullyQualifiedTypeName(t),
					new ReferenceAttributes(ReferenceType.TYPE_REFERENCE,
							false, false, false, false, true, true, false));
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param recorder
	 * @param isExtends
	 * @param isImplements
	 * @param isClassAnnotation
	 * @param types
	 */
	public static void recordReferencedTypes(AsmReferenceRecorder recorder,
			boolean isExtends, boolean isImplements, boolean isClassAnnotation,
			Type... types) {

		for (Type t : types) {
			recorder.recordReference(getFullyQualifiedTypeName(t),
					new ReferenceAttributes(ReferenceType.TYPE_REFERENCE,
							isExtends, isImplements, isClassAnnotation, false,
							true, true, false));
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param recorder
	 * @param isExtends
	 * @param isImplements
	 * @param isClassAnnotation
	 * @param fullyQualifiedName
	 */
	public static void recordReferencedTypes(AsmReferenceRecorder recorder,
			boolean isExtends, boolean isImplements, boolean isClassAnnotation,
			String fullyQualifiedName) {

		recorder.recordReference(fullyQualifiedName, new ReferenceAttributes(
				ReferenceType.TYPE_REFERENCE, isExtends, isImplements,
				isClassAnnotation, false, true, true, false));
	}
}
