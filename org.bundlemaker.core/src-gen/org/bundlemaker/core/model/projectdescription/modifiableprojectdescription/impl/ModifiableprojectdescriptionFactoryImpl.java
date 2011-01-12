/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl;

import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.*;

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
public class ModifiableprojectdescriptionFactoryImpl extends EFactoryImpl implements ModifiableprojectdescriptionFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ModifiableprojectdescriptionFactory init() {
		try {
			ModifiableprojectdescriptionFactory theModifiableprojectdescriptionFactory = (ModifiableprojectdescriptionFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.bundlemaker.org/org/bundlemaker/core/model/projectdescription/modifiableprojectdescription"); 
			if (theModifiableprojectdescriptionFactory != null) {
				return theModifiableprojectdescriptionFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ModifiableprojectdescriptionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModifiableprojectdescriptionFactoryImpl() {
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
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION: return createModifiableBundleMakerProjectDescription();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT: return createModifiableFileBasedContent();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT: return createModifiableResourceContent();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModifiableBundleMakerProjectDescription createModifiableBundleMakerProjectDescription() {
		ModifiableBundleMakerProjectDescriptionImpl modifiableBundleMakerProjectDescription = new ModifiableBundleMakerProjectDescriptionImpl();
		return modifiableBundleMakerProjectDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModifiableFileBasedContent createModifiableFileBasedContent() {
		ModifiableFileBasedContentImpl modifiableFileBasedContent = new ModifiableFileBasedContentImpl();
		return modifiableFileBasedContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModifiableResourceContent createModifiableResourceContent() {
		ModifiableResourceContentImpl modifiableResourceContent = new ModifiableResourceContentImpl();
		return modifiableResourceContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModifiableprojectdescriptionPackage getModifiableprojectdescriptionPackage() {
		return (ModifiableprojectdescriptionPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ModifiableprojectdescriptionPackage getPackage() {
		return ModifiableprojectdescriptionPackage.eINSTANCE;
	}

} //ModifiableprojectdescriptionFactoryImpl
