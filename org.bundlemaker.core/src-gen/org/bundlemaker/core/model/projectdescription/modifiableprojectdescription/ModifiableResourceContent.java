/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription.modifiableprojectdescription;

import java.util.List;

import org.bundlemaker.core.model.projectdescription.IResourceContent;

import org.bundlemaker.core.resource.ResourceStandin;

import org.eclipse.core.runtime.IPath;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Modifiable Resource Content</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#isAnalyzeSourceResources <em>Analyze Source Resources</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#getModifiableSourcePathNames <em>Modifiable Source Path Names</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#getModifiableSourcePaths <em>Modifiable Source Paths</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#getModifiableSourceResources <em>Modifiable Source Resources</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#getModifiableBinaryResources <em>Modifiable Binary Resources</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableResourceContent()
 * @model
 * @generated
 */
public interface ModifiableResourceContent extends IResourceContent {
	/**
	 * Returns the value of the '<em><b>Analyze Source Resources</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Analyze Source Resources</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Analyze Source Resources</em>' attribute.
	 * @see #setAnalyzeSourceResources(boolean)
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableResourceContent_AnalyzeSourceResources()
	 * @model
	 * @generated
	 */
	boolean isAnalyzeSourceResources();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#isAnalyzeSourceResources <em>Analyze Source Resources</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Analyze Source Resources</em>' attribute.
	 * @see #isAnalyzeSourceResources()
	 * @generated
	 */
	void setAnalyzeSourceResources(boolean value);

	/**
	 * Returns the value of the '<em><b>Modifiable Source Path Names</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modifiable Source Path Names</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modifiable Source Path Names</em>' attribute list.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableResourceContent_ModifiableSourcePathNames()
	 * @model
	 * @generated
	 */
	List<String> getModifiableSourcePathNames();

	/**
	 * Returns the value of the '<em><b>Modifiable Source Paths</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.core.runtime.IPath}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modifiable Source Paths</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modifiable Source Paths</em>' attribute list.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableResourceContent_ModifiableSourcePaths()
	 * @model dataType="org.bundlemaker.core.model.projectdescription.ipath" transient="true"
	 * @generated
	 */
	List<IPath> getModifiableSourcePaths();

	/**
	 * Returns the value of the '<em><b>Modifiable Source Resources</b></em>' attribute list.
	 * The list contents are of type {@link org.bundlemaker.core.resource.ResourceStandin}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modifiable Source Resources</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modifiable Source Resources</em>' attribute list.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableResourceContent_ModifiableSourceResources()
	 * @model dataType="org.bundlemaker.core.model.projectdescription.resourceStandin" transient="true"
	 * @generated
	 */
	List<ResourceStandin> getModifiableSourceResources();

	/**
	 * Returns the value of the '<em><b>Modifiable Binary Resources</b></em>' attribute list.
	 * The list contents are of type {@link org.bundlemaker.core.resource.ResourceStandin}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modifiable Binary Resources</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modifiable Binary Resources</em>' attribute list.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableResourceContent_ModifiableBinaryResources()
	 * @model dataType="org.bundlemaker.core.model.projectdescription.resourceStandin" transient="true"
	 * @generated
	 */
	List<ResourceStandin> getModifiableBinaryResources();

} // ModifiableResourceContent
