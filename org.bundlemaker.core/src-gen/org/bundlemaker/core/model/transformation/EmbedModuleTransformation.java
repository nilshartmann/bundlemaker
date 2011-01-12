/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation;

import java.util.List;
import org.bundlemaker.core.model.module.IModuleIdentifier;

import org.bundlemaker.core.transformation.ITransformation;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Embed Module Transformation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.transformation.EmbedModuleTransformation#getHostModuleIdentifier <em>Host Module Identifier</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.EmbedModuleTransformation#getEmbeddedModulesIdentifiers <em>Embedded Modules Identifiers</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getEmbedModuleTransformation()
 * @model superTypes="org.bundlemaker.core.model.transformation.AbstractETransformation"
 * @generated
 */
public interface EmbedModuleTransformation extends EObject, ITransformation {
	/**
	 * Returns the value of the '<em><b>Host Module Identifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Host Module Identifier</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Host Module Identifier</em>' reference.
	 * @see #setHostModuleIdentifier(IModuleIdentifier)
	 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getEmbedModuleTransformation_HostModuleIdentifier()
	 * @model required="true"
	 * @generated
	 */
	IModuleIdentifier getHostModuleIdentifier();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.transformation.EmbedModuleTransformation#getHostModuleIdentifier <em>Host Module Identifier</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Host Module Identifier</em>' reference.
	 * @see #getHostModuleIdentifier()
	 * @generated
	 */
	void setHostModuleIdentifier(IModuleIdentifier value);

	/**
	 * Returns the value of the '<em><b>Embedded Modules Identifiers</b></em>' reference list.
	 * The list contents are of type {@link org.bundlemaker.core.model.module.IModuleIdentifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Embedded Modules Identifiers</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Embedded Modules Identifiers</em>' reference list.
	 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getEmbedModuleTransformation_EmbeddedModulesIdentifiers()
	 * @model
	 * @generated
	 */
	List<IModuleIdentifier> getEmbeddedModulesIdentifiers();

} // EmbedModuleTransformation
