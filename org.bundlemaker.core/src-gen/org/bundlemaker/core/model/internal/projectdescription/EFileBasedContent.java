/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.internal.projectdescription;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EFile Based Content</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getId <em>Id</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getName <em>Name</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getVersion <em>Version</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getBinaryPathNames <em>Binary Path Names</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getResourceContent <em>Resource Content</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#getEFileBasedContent()
 * @model
 * @generated
 */
public interface EFileBasedContent extends EObject {
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
	 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#getEFileBasedContent_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getId <em>Id</em>}' attribute.
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
	 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#getEFileBasedContent_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getName <em>Name</em>}' attribute.
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
	 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#getEFileBasedContent_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Binary Path Names</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Binary Path Names</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Binary Path Names</em>' attribute list.
	 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#getEFileBasedContent_BinaryPathNames()
	 * @model
	 * @generated
	 */
	List<String> getBinaryPathNames();

	/**
	 * Returns the value of the '<em><b>Resource Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Content</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Content</em>' containment reference.
	 * @see #setResourceContent(EResourceContent)
	 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#getEFileBasedContent_ResourceContent()
	 * @model containment="true"
	 * @generated
	 */
	EResourceContent getResourceContent();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getResourceContent <em>Resource Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Content</em>' containment reference.
	 * @see #getResourceContent()
	 * @generated
	 */
	void setResourceContent(EResourceContent value);

} // EFileBasedContent
