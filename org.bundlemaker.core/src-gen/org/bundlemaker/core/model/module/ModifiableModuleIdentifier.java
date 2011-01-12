/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.module;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Modifiable Module Identifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.module.ModifiableModuleIdentifier#getName <em>Name</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.module.ModifiableModuleIdentifier#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.model.module.ModulePackage#getModifiableModuleIdentifier()
 * @model
 * @generated
 */
public interface ModifiableModuleIdentifier extends IModuleIdentifier {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.bundlemaker.core.model.module.ModulePackage#getModifiableModuleIdentifier_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.module.ModifiableModuleIdentifier#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.bundlemaker.core.model.module.ModulePackage#getModifiableModuleIdentifier_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.module.ModifiableModuleIdentifier#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // ModifiableModuleIdentifier
