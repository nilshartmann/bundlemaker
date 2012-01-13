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
 * A representation of the model object '<em><b>Resource List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceList#getResources <em>Resources</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getResourceList()
 * @model
 * @generated
 */
public interface ResourceList extends EObject
{
  /**
   * Returns the value of the '<em><b>Resources</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Resources</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Resources</em>' attribute list.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getResourceList_Resources()
   * @model unique="false"
   * @generated
   */
  EList<String> getResources();

} // ResourceList
