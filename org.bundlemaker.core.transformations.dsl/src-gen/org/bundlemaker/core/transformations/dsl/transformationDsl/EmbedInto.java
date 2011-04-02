/**
 * <copyright>
 * </copyright>
 *

 */
package org.bundlemaker.core.transformations.dsl.transformationDsl;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Embed Into</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto#getHostModule <em>Host Module</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto#getModules <em>Modules</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getEmbedInto()
 * @model
 * @generated
 */
public interface EmbedInto extends Transformation
{
  /**
   * Returns the value of the '<em><b>Host Module</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Host Module</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Host Module</em>' containment reference.
   * @see #setHostModule(ModuleIdentifier)
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getEmbedInto_HostModule()
   * @model containment="true"
   * @generated
   */
  ModuleIdentifier getHostModule();

  /**
   * Sets the value of the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto#getHostModule <em>Host Module</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Host Module</em>' containment reference.
   * @see #getHostModule()
   * @generated
   */
  void setHostModule(ModuleIdentifier value);

  /**
   * Returns the value of the '<em><b>Modules</b></em>' containment reference list.
   * The list contents are of type {@link org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Modules</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Modules</em>' containment reference list.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getEmbedInto_Modules()
   * @model containment="true"
   * @generated
   */
  EList<ModuleIdentifier> getModules();

} // EmbedInto
