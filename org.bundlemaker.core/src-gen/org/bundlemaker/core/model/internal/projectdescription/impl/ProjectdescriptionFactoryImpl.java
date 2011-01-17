/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.internal.projectdescription.impl;

import org.bundlemaker.core.model.internal.projectdescription.*;

import org.eclipse.emf.ecore.EClass;
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
			ProjectdescriptionFactory theProjectdescriptionFactory = (ProjectdescriptionFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.bundlemaker.org/org/bundlemaker/core/model/internal/projectdescription"); 
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
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION: return createEProjectDescription();
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT: return createEFileBasedContent();
			case ProjectdescriptionPackage.ERESOURCE_CONTENT: return createEResourceContent();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EProjectDescription createEProjectDescription() {
		EProjectDescriptionImpl eProjectDescription = new EProjectDescriptionImpl();
		return eProjectDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFileBasedContent createEFileBasedContent() {
		EFileBasedContentImpl eFileBasedContent = new EFileBasedContentImpl();
		return eFileBasedContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EResourceContent createEResourceContent() {
		EResourceContentImpl eResourceContent = new EResourceContentImpl();
		return eResourceContent;
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
