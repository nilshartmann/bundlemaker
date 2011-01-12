/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription.modifiableprojectdescription;

import java.util.List;

import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.model.projectdescription.IBundleMakerProjectDescription;

import org.eclipse.core.runtime.CoreException;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Modifiable Bundle Maker Project Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#getCurrentId <em>Current Id</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#isInitialized <em>Initialized</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#getJRE <em>JRE</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#getModifiableFileBasedContent <em>Modifiable File Based Content</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableBundleMakerProjectDescription()
 * @model
 * @generated
 */
public interface ModifiableBundleMakerProjectDescription extends IBundleMakerProjectDescription {
	/**
	 * Returns the value of the '<em><b>Current Id</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Id</em>' attribute.
	 * @see #setCurrentId(long)
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableBundleMakerProjectDescription_CurrentId()
	 * @model default="0" required="true"
	 * @generated
	 */
	long getCurrentId();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#getCurrentId <em>Current Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Id</em>' attribute.
	 * @see #getCurrentId()
	 * @generated
	 */
	void setCurrentId(long value);

	/**
	 * Returns the value of the '<em><b>Initialized</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initialized</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initialized</em>' attribute.
	 * @see #setInitialized(boolean)
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableBundleMakerProjectDescription_Initialized()
	 * @model transient="true"
	 * @generated
	 */
	boolean isInitialized();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#isInitialized <em>Initialized</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initialized</em>' attribute.
	 * @see #isInitialized()
	 * @generated
	 */
	void setInitialized(boolean value);

	/**
	 * Returns the value of the '<em><b>JRE</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>JRE</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>JRE</em>' attribute.
	 * @see #setJRE(String)
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableBundleMakerProjectDescription_JRE()
	 * @model required="true"
	 * @generated
	 */
	String getJRE();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#getJRE <em>JRE</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>JRE</em>' attribute.
	 * @see #getJRE()
	 * @generated
	 */
	void setJRE(String value);

	/**
	 * Returns the value of the '<em><b>Modifiable File Based Content</b></em>' containment reference list.
	 * The list contents are of type {@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modifiable File Based Content</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modifiable File Based Content</em>' containment reference list.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableBundleMakerProjectDescription_ModifiableFileBasedContent()
	 * @model containment="true"
	 * @generated
	 */
	List<ModifiableFileBasedContent> getModifiableFileBasedContent();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isValid();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model bundlemakerProjectDataType="org.bundlemaker.core.model.projectdescription.bundlemakerproject"
	 * @generated
	 */
	void initialize(BundleMakerProject bundlemakerProject);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="org.bundlemaker.core.model.projectdescription.coreexception"
	 * @generated
	 */
	ModifiableFileBasedContent addResourceContent(String binaryRoot) throws CoreException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="org.bundlemaker.core.model.projectdescription.coreexception"
	 * @generated
	 */
	ModifiableFileBasedContent addResourceContent(String binaryRoot, String sourceRoot) throws CoreException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="org.bundlemaker.core.model.projectdescription.coreexception" binaryRootRequired="true"
	 * @generated
	 */
	ModifiableFileBasedContent addResourceContent(String name, String version, String binaryRoot) throws CoreException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="org.bundlemaker.core.model.projectdescription.coreexception" binaryRootRequired="true"
	 * @generated
	 */
	ModifiableFileBasedContent addResourceContent(String name, String version, String binaryRoot, String sourceRoot) throws CoreException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="org.bundlemaker.core.model.projectdescription.coreexception" binaryRootRequired="true" binaryRootMany="true" sourceRootMany="true"
	 * @generated
	 */
	ModifiableFileBasedContent addResourceContent(String name, String version, List<String> binaryRoot, List<String> sourceRoot) throws CoreException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="org.bundlemaker.core.model.projectdescription.coreexception"
	 * @generated
	 */
	ModifiableFileBasedContent addTypeContent(String binaryRoot) throws CoreException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="org.bundlemaker.core.model.projectdescription.coreexception" binaryRootRequired="true"
	 * @generated
	 */
	ModifiableFileBasedContent addTypeContent(String name, String version, String binaryRoot) throws CoreException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="org.bundlemaker.core.model.projectdescription.coreexception" binaryRootRequired="true" binaryRootMany="true"
	 * @generated
	 */
	ModifiableFileBasedContent addTypeContent(String name, String version, List<String> binaryRoot) throws CoreException;

} // ModifiableBundleMakerProjectDescription
