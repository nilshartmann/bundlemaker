/**
 * <copyright>
 * </copyright>
 *
 */
package org.bundlemaker.core.transformations.dsl.transformationDsl;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove From</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.RemoveFrom#getResourceSet <em>Resource Set</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getRemoveFrom()
 * @model
 * @generated
 */
public interface RemoveFrom extends Transformation
{
  /**
   * Returns the value of the '<em><b>Resource Set</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Resource Set</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Resource Set</em>' containment reference.
   * @see #setResourceSet(ResourceSet)
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getRemoveFrom_ResourceSet()
   * @model containment="true"
   * @generated
   */
  ResourceSet getResourceSet();

  /**
   * Sets the value of the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.RemoveFrom#getResourceSet <em>Resource Set</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Resource Set</em>' containment reference.
   * @see #getResourceSet()
   * @generated
   */
  void setResourceSet(ResourceSet value);

} // RemoveFrom
