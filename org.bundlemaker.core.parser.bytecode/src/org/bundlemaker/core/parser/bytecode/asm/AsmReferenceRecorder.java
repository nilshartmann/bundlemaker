package org.bundlemaker.core.parser.bytecode.asm;

import org.bundlemaker.core.resource.ReferenceType;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.resource.modifiable.IModifiableResource;
import org.bundlemaker.core.resource.modifiable.IModifiableType;
import org.bundlemaker.core.resource.modifiable.IReferenceRecorder;
import org.bundlemaker.core.resource.modifiable.ReferenceAttributes;
import org.bundlemaker.core.util.JavaTypeUtils;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AsmReferenceRecorder implements IReferenceRecorder {

	/** - */
	private IModifiableResource _resource;

	/** - */
	private IModifiableResource _enclosingClassFileResource;

	/** - */
	private IModifiableType _bundleMakerType;

	/** - */
	private String _fullQualifiedTypeName;

	/** - */
	private String _fullQualifiedEnclosingTypeName;

	/**
	 * <p>
	 * Creates a new instance of type {@link AsmReferenceRecorder}.
	 * </p>
	 * 
	 * @param resource
	 * @param enclosingClassFileResource
	 */
	public AsmReferenceRecorder(IModifiableResource resource,
			IModifiableResource enclosingClassFileResource) {

		Assert.isNotNull(resource);
		Assert.isNotNull(enclosingClassFileResource);

		_resource = resource;
		_enclosingClassFileResource = enclosingClassFileResource;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 */
	public void recordContainedType(String fullyQualifiedName, TypeEnum typeEnum) {

		//
		Assert.isNotNull(fullyQualifiedName);

		//
		if (JavaTypeUtils.isLocalOrAnonymousTypeName(fullyQualifiedName)) {

			_resource.getOrCreateType(fullyQualifiedName, typeEnum);
			_bundleMakerType = ((IModifiableType[]) _enclosingClassFileResource
					.getContainedTypes().toArray(new IModifiableType[0]))[0];

			// add as sticky
			_enclosingClassFileResource.addStickyResource(_resource);

		} else {

			_bundleMakerType = _resource.getOrCreateType(fullyQualifiedName,
					typeEnum);
		}
	}

	@Override
	public void recordReference(String fullyQualifiedName,
			ReferenceAttributes attributes) {

		if (fullyQualifiedName != null
				&& !fullyQualifiedName.equals(_fullQualifiedTypeName)
				&& !fullyQualifiedName.equals(_fullQualifiedEnclosingTypeName)) {

			//
			if (!_resource.equals(_enclosingClassFileResource)) {

				attributes = new ReferenceAttributes(
						ReferenceType.TYPE_REFERENCE, false, false, false,
						attributes.isCompileTime(), attributes.isRuntimeTime(),
						attributes.isDirectlyReferenced(),
						attributes.isIndirectlyReferenced());
			}

			//
			_bundleMakerType.recordReference(VisitorUtils
					.getFullyQualifiedTypeName(org.objectweb.asm.Type
							.getObjectType(fullyQualifiedName)), attributes);
		}
	}
}
