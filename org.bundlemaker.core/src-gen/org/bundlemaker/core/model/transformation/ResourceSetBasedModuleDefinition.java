/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation;

import java.util.List;
import java.util.Map;
import org.bundlemaker.core.model.module.IModuleIdentifier;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Set Based Module Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getModuleIdentifier <em>Module Identifier</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getResourceSets <em>Resource Sets</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getUserAttributes <em>User Attributes</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getClassification <em>Classification</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getResourceSetBasedModuleDefinition()
 * @model
 * @generated
 */
public interface ResourceSetBasedModuleDefinition extends EObject {
	/**
	 * Returns the value of the '<em><b>Resource Sets</b></em>' reference list.
	 * The list contents are of type {@link org.bundlemaker.core.model.transformation.ResourceSet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Sets</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Sets</em>' reference list.
	 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getResourceSetBasedModuleDefinition_ResourceSets()
	 * @model
	 * @generated
	 */
	List<ResourceSet> getResourceSets();

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
	 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getResourceSetBasedModuleDefinition_ModuleIdentifier()
	 * @model
	 * @generated
	 */
	IModuleIdentifier getModuleIdentifier();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getModuleIdentifier <em>Module Identifier</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Identifier</em>' reference.
	 * @see #getModuleIdentifier()
	 * @generated
	 */
	void setModuleIdentifier(IModuleIdentifier value);

	/**
	 * Returns the value of the '<em><b>User Attributes</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.Object},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Attributes</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Attributes</em>' map.
	 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getResourceSetBasedModuleDefinition_UserAttributes()
	 * @model mapType="org.bundlemaker.core.model.transformation.StringToObjectMap<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EJavaObject>"
	 * @generated
	 */
	Map<String, Object> getUserAttributes();

	/**
	 * Returns the value of the '<em><b>Classification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classification</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classification</em>' attribute.
	 * @see #setClassification(IPath)
	 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getResourceSetBasedModuleDefinition_Classification()
	 * @model dataType="org.bundlemaker.core.model.projectdescription.ipath"
	 * @generated
	 */
	IPath getClassification();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getClassification <em>Classification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Classification</em>' attribute.
	 * @see #getClassification()
	 * @generated
	 */
	void setClassification(IPath value);

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

} // ResourceSetBasedModuleDefinition
