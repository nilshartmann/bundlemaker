/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation;

import java.util.List;
import java.util.Map;

import org.bundlemaker.core.transformation.ITransformation;
import org.bundlemaker.core.transformation.resourceset.IResourceSetProcessor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Set Based Transformation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation#getModuleDefinitions <em>Module Definitions</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation#getResourceSetProcessor <em>Resource Set Processor</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getResourceSetBasedTransformation()
 * @model superTypes="org.bundlemaker.core.model.transformation.AbstractETransformation"
 * @generated
 */
public interface ResourceSetBasedTransformation extends EObject, ITransformation {
	/**
	 * Returns the value of the '<em><b>Module Definitions</b></em>' reference list.
	 * The list contents are of type {@link org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Definitions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Definitions</em>' reference list.
	 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getResourceSetBasedTransformation_ModuleDefinitions()
	 * @model
	 * @generated
	 */
	List<ResourceSetBasedModuleDefinition> getModuleDefinitions();

	/**
	 * Returns the value of the '<em><b>Resource Set Processor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Set Processor</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Set Processor</em>' attribute.
	 * @see #setResourceSetProcessor(IResourceSetProcessor)
	 * @see org.bundlemaker.core.model.transformation.TransformationPackage#getResourceSetBasedTransformation_ResourceSetProcessor()
	 * @model dataType="org.bundlemaker.core.model.transformation.iResourceSetProcessor"
	 * @generated
	 */
	IResourceSetProcessor getResourceSetProcessor();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation#getResourceSetProcessor <em>Resource Set Processor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Set Processor</em>' attribute.
	 * @see #getResourceSetProcessor()
	 * @generated
	 */
	void setResourceSetProcessor(IResourceSetProcessor value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	ResourceSetBasedModuleDefinition addModuleDefinition(String name, String version);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model userAttibutesMapType="org.bundlemaker.core.model.transformation.StringToObjectMap<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EJavaObject>"
	 * @generated
	 */
	ResourceSetBasedModuleDefinition addModuleDefinition(String name, String version, Map<String, Object> userAttibutes);

} // ResourceSetBasedTransformation
