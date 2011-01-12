/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription.modifiableprojectdescription;

import java.util.List;

import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.model.projectdescription.IFileBasedContent;

import org.eclipse.core.runtime.IPath;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Modifiable File Based Content</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getId <em>Id</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getName <em>Name</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getVersion <em>Version</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#isInitialized <em>Initialized</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getModifiableBinaryPathNames <em>Modifiable Binary Path Names</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getModifiableBinaryPaths <em>Modifiable Binary Paths</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getModifiableResourceContent <em>Modifiable Resource Content</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableFileBasedContent()
 * @model
 * @generated
 */
public interface ModifiableFileBasedContent extends IFileBasedContent {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableFileBasedContent_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

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
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableFileBasedContent_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getName <em>Name</em>}' attribute.
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
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableFileBasedContent_Version()
	 * @model required="true"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

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
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableFileBasedContent_Initialized()
	 * @model transient="true"
	 * @generated
	 */
	boolean isInitialized();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#isInitialized <em>Initialized</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initialized</em>' attribute.
	 * @see #isInitialized()
	 * @generated
	 */
	void setInitialized(boolean value);

	/**
	 * Returns the value of the '<em><b>Modifiable Binary Path Names</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modifiable Binary Path Names</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modifiable Binary Path Names</em>' attribute list.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableFileBasedContent_ModifiableBinaryPathNames()
	 * @model required="true"
	 * @generated
	 */
	List<String> getModifiableBinaryPathNames();

	/**
	 * Returns the value of the '<em><b>Modifiable Binary Paths</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.core.runtime.IPath}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modifiable Binary Paths</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modifiable Binary Paths</em>' attribute list.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableFileBasedContent_ModifiableBinaryPaths()
	 * @model dataType="org.bundlemaker.core.model.projectdescription.ipath" required="true" transient="true"
	 * @generated
	 */
	List<IPath> getModifiableBinaryPaths();

	/**
	 * Returns the value of the '<em><b>Modifiable Resource Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modifiable Resource Content</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modifiable Resource Content</em>' containment reference.
	 * @see #setModifiableResourceContent(ModifiableResourceContent)
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#getModifiableFileBasedContent_ModifiableResourceContent()
	 * @model containment="true"
	 * @generated
	 */
	ModifiableResourceContent getModifiableResourceContent();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getModifiableResourceContent <em>Modifiable Resource Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Modifiable Resource Content</em>' containment reference.
	 * @see #getModifiableResourceContent()
	 * @generated
	 */
	void setModifiableResourceContent(ModifiableResourceContent value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model bundleMakerProjectDataType="org.bundlemaker.core.model.projectdescription.bundlemakerproject"
	 * @generated
	 */
	void initialize(BundleMakerProject bundleMakerProject);

} // ModifiableFileBasedContent
