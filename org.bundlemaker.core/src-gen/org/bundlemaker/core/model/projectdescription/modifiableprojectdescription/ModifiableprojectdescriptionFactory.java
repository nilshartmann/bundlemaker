/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription.modifiableprojectdescription;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage
 * @generated
 */
public interface ModifiableprojectdescriptionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModifiableprojectdescriptionFactory eINSTANCE = org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableprojectdescriptionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Modifiable Bundle Maker Project Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Modifiable Bundle Maker Project Description</em>'.
	 * @generated
	 */
	ModifiableBundleMakerProjectDescription createModifiableBundleMakerProjectDescription();

	/**
	 * Returns a new object of class '<em>Modifiable File Based Content</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Modifiable File Based Content</em>'.
	 * @generated
	 */
	ModifiableFileBasedContent createModifiableFileBasedContent();

	/**
	 * Returns a new object of class '<em>Modifiable Resource Content</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Modifiable Resource Content</em>'.
	 * @generated
	 */
	ModifiableResourceContent createModifiableResourceContent();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ModifiableprojectdescriptionPackage getModifiableprojectdescriptionPackage();

} //ModifiableprojectdescriptionFactory
