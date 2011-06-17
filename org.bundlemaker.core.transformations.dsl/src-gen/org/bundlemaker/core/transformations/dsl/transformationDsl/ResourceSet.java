/**
 * <copyright>
 * </copyright>
 *
 */
package org.bundlemaker.core.transformations.dsl.transformationDsl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet#getModuleIdentifier <em>Module Identifier</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet#getIncludeResources <em>Include Resources</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet#getExcludeResources <em>Exclude Resources</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getResourceSet()
 * @model
 * @generated
 */
public interface ResourceSet extends EObject
{
  /**
   * Returns the value of the '<em><b>Module Identifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Module Identifier</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Module Identifier</em>' containment reference.
   * @see #setModuleIdentifier(ModuleIdentifier)
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getResourceSet_ModuleIdentifier()
   * @model containment="true"
   * @generated
   */
  ModuleIdentifier getModuleIdentifier();

  /**
   * Sets the value of the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet#getModuleIdentifier <em>Module Identifier</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Module Identifier</em>' containment reference.
   * @see #getModuleIdentifier()
   * @generated
   */
  void setModuleIdentifier(ModuleIdentifier value);

  /**
   * Returns the value of the '<em><b>Include Resources</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Include Resources</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Include Resources</em>' containment reference.
   * @see #setIncludeResources(ResourceList)
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getResourceSet_IncludeResources()
   * @model containment="true"
   * @generated
   */
  ResourceList getIncludeResources();

  /**
   * Sets the value of the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet#getIncludeResources <em>Include Resources</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Include Resources</em>' containment reference.
   * @see #getIncludeResources()
   * @generated
   */
  void setIncludeResources(ResourceList value);

  /**
   * Returns the value of the '<em><b>Exclude Resources</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Exclude Resources</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Exclude Resources</em>' containment reference.
   * @see #setExcludeResources(ResourceList)
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getResourceSet_ExcludeResources()
   * @model containment="true"
   * @generated
   */
  ResourceList getExcludeResources();

  /**
   * Sets the value of the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet#getExcludeResources <em>Exclude Resources</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Exclude Resources</em>' containment reference.
   * @see #getExcludeResources()
   * @generated
   */
  void setExcludeResources(ResourceList value);

} // ResourceSet
