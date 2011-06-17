/**
 * <copyright>
 * </copyright>
 *
 */
package org.bundlemaker.core.transformations.dsl.transformationDsl;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Create Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule#getModule <em>Module</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule#getLayer <em>Layer</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule#getFrom <em>From</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getCreateModule()
 * @model
 * @generated
 */
public interface CreateModule extends Transformation
{
  /**
   * Returns the value of the '<em><b>Module</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Module</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Module</em>' containment reference.
   * @see #setModule(ModuleIdentifier)
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getCreateModule_Module()
   * @model containment="true"
   * @generated
   */
  ModuleIdentifier getModule();

  /**
   * Sets the value of the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule#getModule <em>Module</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Module</em>' containment reference.
   * @see #getModule()
   * @generated
   */
  void setModule(ModuleIdentifier value);

  /**
   * Returns the value of the '<em><b>Layer</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Layer</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Layer</em>' containment reference.
   * @see #setLayer(Layer)
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getCreateModule_Layer()
   * @model containment="true"
   * @generated
   */
  Layer getLayer();

  /**
   * Sets the value of the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule#getLayer <em>Layer</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Layer</em>' containment reference.
   * @see #getLayer()
   * @generated
   */
  void setLayer(Layer value);

  /**
   * Returns the value of the '<em><b>From</b></em>' containment reference list.
   * The list contents are of type {@link org.bundlemaker.core.transformations.dsl.transformationDsl.From}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>From</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>From</em>' containment reference list.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getCreateModule_From()
   * @model containment="true"
   * @generated
   */
  EList<From> getFrom();

} // CreateModule
