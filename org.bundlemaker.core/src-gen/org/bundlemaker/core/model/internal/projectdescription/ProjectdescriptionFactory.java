/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.internal.projectdescription;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage
 * @generated
 */
public interface ProjectdescriptionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProjectdescriptionFactory eINSTANCE = org.bundlemaker.core.model.internal.projectdescription.impl.ProjectdescriptionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>EProject Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EProject Description</em>'.
	 * @generated
	 */
	EProjectDescription createEProjectDescription();

	/**
	 * Returns a new object of class '<em>EFile Based Content</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EFile Based Content</em>'.
	 * @generated
	 */
	EFileBasedContent createEFileBasedContent();

	/**
	 * Returns a new object of class '<em>EResource Content</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EResource Content</em>'.
	 * @generated
	 */
	EResourceContent createEResourceContent();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ProjectdescriptionPackage getProjectdescriptionPackage();

} //ProjectdescriptionFactory
