/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBundle Maker Project Description</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.bundlemaker.core.model.projectdescription.ProjectdescriptionPackage#getIBundleMakerProjectDescription()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IBundleMakerProjectDescription extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	List<IFileBasedContent> getFileBasedContent();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	IFileBasedContent getFileBasedContent(String id);

} // IBundleMakerProjectDescription
