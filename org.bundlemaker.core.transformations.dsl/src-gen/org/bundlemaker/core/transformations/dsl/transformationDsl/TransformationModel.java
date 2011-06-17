/**
 * <copyright>
 * </copyright>
 *
 */
package org.bundlemaker.core.transformations.dsl.transformationDsl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transformation Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel#getTransformations <em>Transformations</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getTransformationModel()
 * @model
 * @generated
 */
public interface TransformationModel extends EObject
{
  /**
   * Returns the value of the '<em><b>Transformations</b></em>' containment reference list.
   * The list contents are of type {@link org.bundlemaker.core.transformations.dsl.transformationDsl.Transformation}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Transformations</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Transformations</em>' containment reference list.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getTransformationModel_Transformations()
   * @model containment="true"
   * @generated
   */
  EList<Transformation> getTransformations();

} // TransformationModel
