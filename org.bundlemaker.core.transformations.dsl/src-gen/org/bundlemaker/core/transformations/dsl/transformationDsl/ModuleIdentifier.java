/**
 * <copyright>
 * </copyright>
 *
 */
package org.bundlemaker.core.transformations.dsl.transformationDsl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Module Identifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier#getModulename <em>Modulename</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getModuleIdentifier()
 * @model
 * @generated
 */
public interface ModuleIdentifier extends EObject
{
  /**
   * Returns the value of the '<em><b>Modulename</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Modulename</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Modulename</em>' attribute.
   * @see #setModulename(String)
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getModuleIdentifier_Modulename()
   * @model
   * @generated
   */
  String getModulename();

  /**
   * Sets the value of the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier#getModulename <em>Modulename</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Modulename</em>' attribute.
   * @see #getModulename()
   * @generated
   */
  void setModulename(String value);

  /**
   * Returns the value of the '<em><b>Version</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Version</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Version</em>' attribute.
   * @see #setVersion(String)
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getModuleIdentifier_Version()
   * @model
   * @generated
   */
  String getVersion();

  /**
   * Sets the value of the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier#getVersion <em>Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Version</em>' attribute.
   * @see #getVersion()
   * @generated
   */
  void setVersion(String value);

} // ModuleIdentifier
