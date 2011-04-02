/**
 * <copyright>
 * </copyright>
 *

 */
package org.bundlemaker.core.transformations.dsl.transformationDsl;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage
 * @generated
 */
public interface TransformationDslFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  TransformationDslFactory eINSTANCE = org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Transformation Model</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Transformation Model</em>'.
   * @generated
   */
  TransformationModel createTransformationModel();

  /**
   * Returns a new object of class '<em>Transformation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Transformation</em>'.
   * @generated
   */
  Transformation createTransformation();

  /**
   * Returns a new object of class '<em>Remove From</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Remove From</em>'.
   * @generated
   */
  RemoveFrom createRemoveFrom();

  /**
   * Returns a new object of class '<em>Embed Into</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Embed Into</em>'.
   * @generated
   */
  EmbedInto createEmbedInto();

  /**
   * Returns a new object of class '<em>Create Module</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Create Module</em>'.
   * @generated
   */
  CreateModule createCreateModule();

  /**
   * Returns a new object of class '<em>From</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>From</em>'.
   * @generated
   */
  From createFrom();

  /**
   * Returns a new object of class '<em>Resource Set</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Resource Set</em>'.
   * @generated
   */
  ResourceSet createResourceSet();

  /**
   * Returns a new object of class '<em>Module Identifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Module Identifier</em>'.
   * @generated
   */
  ModuleIdentifier createModuleIdentifier();

  /**
   * Returns a new object of class '<em>Resource List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Resource List</em>'.
   * @generated
   */
  ResourceList createResourceList();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  TransformationDslPackage getTransformationDslPackage();

} //TransformationDslFactory
