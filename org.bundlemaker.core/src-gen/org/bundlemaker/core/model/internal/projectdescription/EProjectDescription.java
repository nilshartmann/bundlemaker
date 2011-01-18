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
 * A representation of the model object '<em><b>EProject Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.EProjectDescription#getCurrentId <em>Current Id</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.EProjectDescription#getJre <em>Jre</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.EProjectDescription#getFileBasedContent <em>File Based Content</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#getEProjectDescription()
 * @model
 * @generated
 */
public interface EProjectDescription extends EObject {
	/**
	 * Returns the value of the '<em><b>Current Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Id</em>' attribute.
	 * @see #setCurrentId(int)
	 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#getEProjectDescription_CurrentId()
	 * @model
	 * @generated
	 */
	int getCurrentId();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.internal.projectdescription.EProjectDescription#getCurrentId <em>Current Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Id</em>' attribute.
	 * @see #getCurrentId()
	 * @generated
	 */
	void setCurrentId(int value);

	/**
	 * Returns the value of the '<em><b>Jre</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jre</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jre</em>' attribute.
	 * @see #setJre(String)
	 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#getEProjectDescription_Jre()
	 * @model
	 * @generated
	 */
	String getJre();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.internal.projectdescription.EProjectDescription#getJre <em>Jre</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jre</em>' attribute.
	 * @see #getJre()
	 * @generated
	 */
	void setJre(String value);

	/**
	 * Returns the value of the '<em><b>File Based Content</b></em>' containment reference list.
	 * The list contents are of type {@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Based Content</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Based Content</em>' containment reference list.
	 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#getEProjectDescription_FileBasedContent()
	 * @model containment="true"
	 * @generated
	 */
	List<EFileBasedContent> getFileBasedContent();

} // EProjectDescription
