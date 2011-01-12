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
 * A representation of the model object '<em><b>Remove Resources Transformation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.transformation.RemoveResourcesTransformation#getResourcesToRemove <em>Resources To Remove</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getRemoveResourcesTransformation()
 * @model superTypes="org.bundlemaker.core.model.transformation.AbstractETransformation"
 * @generated
 */
public interface RemoveResourcesTransformation extends EObject, ITransformation {
	/**
	 * Returns the value of the '<em><b>Resources To Remove</b></em>' reference list.
	 * The list contents are of type {@link org.bundlemaker.core.model.transformation.ResourceSet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resources To Remove</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resources To Remove</em>' reference list.
	 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getRemoveResourcesTransformation_ResourcesToRemove()
	 * @model
	 * @generated
	 */
	List<ResourceSet> getResourcesToRemove();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model includesDataType="org.bundlemaker.core.model.transformation.stringArray" includesRequired="true" excludesDataType="org.bundlemaker.core.model.transformation.stringArray" excludesRequired="true"
	 * @generated
	 */
	void addResourceSet(String fromName, String fromVersion, String[] includes, String[] excludes);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model fromModuleIdentifierRequired="true" includesDataType="org.bundlemaker.core.model.transformation.stringArray" includesRequired="true" excludesDataType="org.bundlemaker.core.model.transformation.stringArray" excludesRequired="true"
	 * @generated
	 */
	void addResourceSet(IModuleIdentifier fromModuleIdentifier, String[] includes, String[] excludes);

} // RemoveResourcesTransformation
