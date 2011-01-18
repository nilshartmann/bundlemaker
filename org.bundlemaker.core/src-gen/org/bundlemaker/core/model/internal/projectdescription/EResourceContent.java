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
 * A representation of the model object '<em><b>EResource Content</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.EResourceContent#isAnalyzeSourceResources <em>Analyze Source Resources</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.EResourceContent#getSourcePathNames <em>Source Path Names</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#getEResourceContent()
 * @model
 * @generated
 */
public interface EResourceContent extends EObject {
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
	 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#getEResourceContent_AnalyzeSourceResources()
	 * @model
	 * @generated
	 */
	boolean isAnalyzeSourceResources();

	/**
	 * Sets the value of the '{@link org.bundlemaker.core.model.internal.projectdescription.EResourceContent#isAnalyzeSourceResources <em>Analyze Source Resources</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Analyze Source Resources</em>' attribute.
	 * @see #isAnalyzeSourceResources()
	 * @generated
	 */
	void setAnalyzeSourceResources(boolean value);

	/**
	 * Returns the value of the '<em><b>Source Path Names</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Path Names</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Path Names</em>' attribute list.
	 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#getEResourceContent_SourcePathNames()
	 * @model
	 * @generated
	 */
	List<String> getSourcePathNames();

} // EResourceContent
