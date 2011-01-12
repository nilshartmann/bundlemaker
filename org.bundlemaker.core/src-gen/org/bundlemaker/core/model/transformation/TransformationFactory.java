/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation;

import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.bundlemaker.core.model.transformation.TransformationPackage
 * @generated
 */
public interface TransformationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TransformationFactory eINSTANCE = org.bundlemaker.core.model.transformation.impl.TransformationFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Resource Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Set</em>'.
	 * @generated
	 */
	ResourceSet createResourceSet();

	/**
	 * Returns a new object of class '<em>Resource Set Based Module Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Set Based Module Definition</em>'.
	 * @generated
	 */
	ResourceSetBasedModuleDefinition createResourceSetBasedModuleDefinition();

	/**
	 * Returns a new object of class '<em>Resource Set Based Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Set Based Transformation</em>'.
	 * @generated
	 */
	ResourceSetBasedTransformation createResourceSetBasedTransformation();

	/**
	 * Returns a new object of class '<em>Basic Project Content Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Basic Project Content Transformation</em>'.
	 * @generated
	 */
	BasicProjectContentTransformation createBasicProjectContentTransformation();

	/**
	 * Returns a new object of class '<em>Embed Module Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Embed Module Transformation</em>'.
	 * @generated
	 */
	EmbedModuleTransformation createEmbedModuleTransformation();

	/**
	 * Returns a new object of class '<em>Remove Resources Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remove Resources Transformation</em>'.
	 * @generated
	 */
	RemoveResourcesTransformation createRemoveResourcesTransformation();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TransformationPackage getTransformationPackage();

} //TransformationFactory
