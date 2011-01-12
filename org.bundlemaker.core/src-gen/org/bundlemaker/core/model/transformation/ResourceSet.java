/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation;

import java.util.List;

import org.bundlemaker.core.model.module.IModuleIdentifier;
import org.bundlemaker.core.model.projectdescription.ContentType;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IResourceStandin;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.transformation.ResourceSet#getModuleIdentifier <em>Module Identifier</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.ResourceSet#getIncludes <em>Includes</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.ResourceSet#getExcludes <em>Excludes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getResourceSet()
 * @model
 * @generated
 */
public interface ResourceSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Module Identifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Identifier</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Identifier</em>' reference.
	 * @see #setModuleIdentifier(IModuleIdentifier)
	 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getResourceSet_ModuleIdentifier()
	 * @model
	 * @generated
	 */
	IModuleIdentifier getModuleIdentifier();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.transformation.ResourceSet#getModuleIdentifier <em>Module Identifier</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Identifier</em>' reference.
	 * @see #getModuleIdentifier()
	 * @generated
	 */
	void setModuleIdentifier(IModuleIdentifier value);

	/**
	 * Returns the value of the '<em><b>Includes</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Includes</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Includes</em>' attribute list.
	 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getResourceSet_Includes()
	 * @model
	 * @generated
	 */
	List<String> getIncludes();

	/**
	 * Returns the value of the '<em><b>Excludes</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Excludes</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Excludes</em>' attribute list.
	 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getResourceSet_Excludes()
	 * @model
	 * @generated
	 */
	List<String> getExcludes();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.bundlemaker.core.model.projectdescription.iResourceStandin" resourceDataType="org.bundlemaker.core.model.module.resourcemodule" resourceRequired="true"
	 * @generated
	 */
	List<IResourceStandin> getMatchingResources(IResourceModule resource, ContentType contentType);

} // ResourceSet
