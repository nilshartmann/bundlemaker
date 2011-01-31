package org.bundlemaker.core.parser.bytecode;

import org.bundlemaker.core.resource.ReferenceType;
import org.bundlemaker.core.resource.Resource;
import org.bundlemaker.core.resource.Type;
import org.bundlemaker.core.resource.TypeEnum;
import org.eclipse.core.runtime.Assert;

import com.springsource.bundlor.support.partialmanifest.PartialManifest;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundlorPartialManifest implements PartialManifest {

	/** - */
	private Resource _resource;

	private Type _type;

	/** - */
	private String _fullQualifiedTypeName;

	/** - */
	private String _fullQualifiedEnclosingTypeName;

	/**
	 * <p>
	 * Creates a new instance of type {@link BundlorPartialManifest}.
	 * </p>
	 * 
	 * @param fullQualifiedTypeName
	 * @param fullQualifiedEnclosingTypeName
	 * @param resource
	 */
	public BundlorPartialManifest(String fullQualifiedTypeName,
			String fullQualifiedEnclosingTypeName, Resource resource) {

		Assert.isNotNull(fullQualifiedTypeName);
		Assert.isNotNull(fullQualifiedEnclosingTypeName);
		Assert.isNotNull(resource);

		_fullQualifiedTypeName = fullQualifiedTypeName;
		_fullQualifiedEnclosingTypeName = fullQualifiedEnclosingTypeName;
		_resource = resource;
	}

	/**
	 * @see com.springsource.bundlor.support.partialmanifest.PartialManifest#recordReferencedType(java.lang.String)
	 */
	public void recordReferencedType(String type) {

		if (type != null && !type.equals(_fullQualifiedTypeName)
				&& !type.equals(_fullQualifiedEnclosingTypeName)
		// && !CoreUtil.isLocalOrAnonymousType((type))
		) {

			// TODO!!!
			_type.recordReference(type, ReferenceType.TYPE_REFERENCE, false,
					false, false, false, true);
		}
	}

	/**
	 * @see com.springsource.bundlor.support.partialmanifest.PartialManifest#recordUsesPackage(java.lang.String,
	 *      java.lang.String)
	 */
	public void recordUsesPackage(String arg0, String arg1) {
		// System.out.println("recordUsesPackage(" + arg0 + ", " + arg1 + ")");
	}

	/**
	 * @see com.springsource.bundlor.support.partialmanifest.PartialManifest#recordExportPackage(java.lang.String)
	 */
	public void recordExportPackage(String arg0) {
		//
	}

	/**
	 * @see com.springsource.bundlor.support.partialmanifest.PartialManifest#recordReferencedPackage(java.lang.String)
	 */
	public void recordReferencedPackage(String arg0) {
		//
	}

	/**
	 * @see com.springsource.bundlor.support.partialmanifest.PartialManifest#recordType(java.lang.String)
	 */
	public void recordType(String arg0) {
		//
	}

	//
	public void recordType(String arg0, TypeEnum typeEnum) {
		
		// TODO
		_type = _resource.getOrCreateType(_fullQualifiedTypeName, typeEnum);
	}
}
