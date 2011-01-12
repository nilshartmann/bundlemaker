/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.module;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.bundlemaker.core.model.module.ModulePackage
 * @generated
 */
public interface ModuleFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModuleFactory eINSTANCE = org.bundlemaker.core.model.module.impl.ModuleFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Modifiable Module Identifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Modifiable Module Identifier</em>'.
	 * @generated
	 */
	ModifiableModuleIdentifier createModifiableModuleIdentifier();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ModulePackage getModulePackage();

} //ModuleFactory
