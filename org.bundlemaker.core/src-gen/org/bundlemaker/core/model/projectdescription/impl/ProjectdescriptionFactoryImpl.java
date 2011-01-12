/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription.impl;

import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.model.projectdescription.*;

import org.bundlemaker.core.resource.IResourceStandin;
import org.bundlemaker.core.resource.ResourceStandin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProjectdescriptionFactoryImpl extends EFactoryImpl implements ProjectdescriptionFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProjectdescriptionFactory init() {
		try {
			ProjectdescriptionFactory theProjectdescriptionFactory = (ProjectdescriptionFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.bundlemaker.org/org/bundlemaker/core/model/projectdescription"); 
			if (theProjectdescriptionFactory != null) {
				return theProjectdescriptionFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ProjectdescriptionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectdescriptionFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ProjectdescriptionPackage.CONTENT_TYPE:
				return createContentTypeFromString(eDataType, initialValue);
			case ProjectdescriptionPackage.IPATH:
				return createipathFromString(eDataType, initialValue);
			case ProjectdescriptionPackage.COREEXCEPTION:
				return createcoreexceptionFromString(eDataType, initialValue);
			case ProjectdescriptionPackage.IRESOURCE_STANDIN:
				return createiResourceStandinFromString(eDataType, initialValue);
			case ProjectdescriptionPackage.RESOURCE_STANDIN:
				return createresourceStandinFromString(eDataType, initialValue);
			case ProjectdescriptionPackage.BUNDLEMAKERPROJECT:
				return createbundlemakerprojectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ProjectdescriptionPackage.CONTENT_TYPE:
				return convertContentTypeToString(eDataType, instanceValue);
			case ProjectdescriptionPackage.IPATH:
				return convertipathToString(eDataType, instanceValue);
			case ProjectdescriptionPackage.COREEXCEPTION:
				return convertcoreexceptionToString(eDataType, instanceValue);
			case ProjectdescriptionPackage.IRESOURCE_STANDIN:
				return convertiResourceStandinToString(eDataType, instanceValue);
			case ProjectdescriptionPackage.RESOURCE_STANDIN:
				return convertresourceStandinToString(eDataType, instanceValue);
			case ProjectdescriptionPackage.BUNDLEMAKERPROJECT:
				return convertbundlemakerprojectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContentType createContentTypeFromString(EDataType eDataType, String initialValue) {
		ContentType result = ContentType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertContentTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IPath createipathFromString(EDataType eDataType, String initialValue) {
		return (IPath)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertipathToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CoreException createcoreexceptionFromString(EDataType eDataType, String initialValue) {
		return (CoreException)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertcoreexceptionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IResourceStandin createiResourceStandinFromString(EDataType eDataType, String initialValue) {
		return (IResourceStandin)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertiResourceStandinToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceStandin createresourceStandinFromString(EDataType eDataType, String initialValue) {
		return (ResourceStandin)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertresourceStandinToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BundleMakerProject createbundlemakerprojectFromString(EDataType eDataType, String initialValue) {
		return (BundleMakerProject)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertbundlemakerprojectToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectdescriptionPackage getProjectdescriptionPackage() {
		return (ProjectdescriptionPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ProjectdescriptionPackage getPackage() {
		return ProjectdescriptionPackage.eINSTANCE;
	}

} //ProjectdescriptionFactoryImpl
