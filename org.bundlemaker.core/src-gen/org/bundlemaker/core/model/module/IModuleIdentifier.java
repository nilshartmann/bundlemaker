/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.module;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IModule Identifier</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.bundlemaker.core.model.module.ModulePackage#getIModuleIdentifier()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IModuleIdentifier extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getVersion();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getName();

} // IModuleIdentifier
