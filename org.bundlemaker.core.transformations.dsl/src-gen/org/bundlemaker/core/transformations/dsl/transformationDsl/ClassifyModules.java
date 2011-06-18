/**
 * <copyright>
 * </copyright>
 *
 */
package org.bundlemaker.core.transformations.dsl.transformationDsl;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Classify Modules</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules#getModules <em>Modules</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules#getExcludedModules <em>Excluded Modules</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules#getClassification <em>Classification</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getClassifyModules()
 * @model
 * @generated
 */
public interface ClassifyModules extends Transformation
{
  /**
   * Returns the value of the '<em><b>Modules</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Modules</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Modules</em>' attribute.
   * @see #setModules(String)
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getClassifyModules_Modules()
   * @model
   * @generated
   */
  String getModules();

  /**
   * Sets the value of the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules#getModules <em>Modules</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Modules</em>' attribute.
   * @see #getModules()
   * @generated
   */
  void setModules(String value);

  /**
   * Returns the value of the '<em><b>Excluded Modules</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Excluded Modules</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Excluded Modules</em>' attribute.
   * @see #setExcludedModules(String)
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getClassifyModules_ExcludedModules()
   * @model
   * @generated
   */
  String getExcludedModules();

  /**
   * Sets the value of the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules#getExcludedModules <em>Excluded Modules</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Excluded Modules</em>' attribute.
   * @see #getExcludedModules()
   * @generated
   */
  void setExcludedModules(String value);

  /**
   * Returns the value of the '<em><b>Classification</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Classification</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Classification</em>' attribute.
   * @see #setClassification(String)
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#getClassifyModules_Classification()
   * @model
   * @generated
   */
  String getClassification();

  /**
   * Sets the value of the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules#getClassification <em>Classification</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Classification</em>' attribute.
   * @see #getClassification()
   * @generated
   */
  void setClassification(String value);

} // ClassifyModules
