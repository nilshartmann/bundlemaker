/**
 * <copyright>
 * </copyright>
 *
 */
package org.bundlemaker.core.transformations.dsl.transformationDsl.impl;

import org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules;
import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Classify Modules</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ClassifyModulesImpl#getModules <em>Modules</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ClassifyModulesImpl#getExcludedModules <em>Excluded Modules</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ClassifyModulesImpl#getClassification <em>Classification</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClassifyModulesImpl extends TransformationImpl implements ClassifyModules
{
  /**
   * The default value of the '{@link #getModules() <em>Modules</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModules()
   * @generated
   * @ordered
   */
  protected static final String MODULES_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getModules() <em>Modules</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModules()
   * @generated
   * @ordered
   */
  protected String modules = MODULES_EDEFAULT;

  /**
   * The default value of the '{@link #getExcludedModules() <em>Excluded Modules</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExcludedModules()
   * @generated
   * @ordered
   */
  protected static final String EXCLUDED_MODULES_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExcludedModules() <em>Excluded Modules</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExcludedModules()
   * @generated
   * @ordered
   */
  protected String excludedModules = EXCLUDED_MODULES_EDEFAULT;

  /**
   * The default value of the '{@link #getClassification() <em>Classification</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getClassification()
   * @generated
   * @ordered
   */
  protected static final String CLASSIFICATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getClassification() <em>Classification</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getClassification()
   * @generated
   * @ordered
   */
  protected String classification = CLASSIFICATION_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ClassifyModulesImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return TransformationDslPackage.Literals.CLASSIFY_MODULES;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getModules()
  {
    return modules;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setModules(String newModules)
  {
    String oldModules = modules;
    modules = newModules;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TransformationDslPackage.CLASSIFY_MODULES__MODULES, oldModules, modules));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getExcludedModules()
  {
    return excludedModules;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExcludedModules(String newExcludedModules)
  {
    String oldExcludedModules = excludedModules;
    excludedModules = newExcludedModules;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TransformationDslPackage.CLASSIFY_MODULES__EXCLUDED_MODULES, oldExcludedModules, excludedModules));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getClassification()
  {
    return classification;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setClassification(String newClassification)
  {
    String oldClassification = classification;
    classification = newClassification;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TransformationDslPackage.CLASSIFY_MODULES__CLASSIFICATION, oldClassification, classification));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case TransformationDslPackage.CLASSIFY_MODULES__MODULES:
        return getModules();
      case TransformationDslPackage.CLASSIFY_MODULES__EXCLUDED_MODULES:
        return getExcludedModules();
      case TransformationDslPackage.CLASSIFY_MODULES__CLASSIFICATION:
        return getClassification();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case TransformationDslPackage.CLASSIFY_MODULES__MODULES:
        setModules((String)newValue);
        return;
      case TransformationDslPackage.CLASSIFY_MODULES__EXCLUDED_MODULES:
        setExcludedModules((String)newValue);
        return;
      case TransformationDslPackage.CLASSIFY_MODULES__CLASSIFICATION:
        setClassification((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case TransformationDslPackage.CLASSIFY_MODULES__MODULES:
        setModules(MODULES_EDEFAULT);
        return;
      case TransformationDslPackage.CLASSIFY_MODULES__EXCLUDED_MODULES:
        setExcludedModules(EXCLUDED_MODULES_EDEFAULT);
        return;
      case TransformationDslPackage.CLASSIFY_MODULES__CLASSIFICATION:
        setClassification(CLASSIFICATION_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case TransformationDslPackage.CLASSIFY_MODULES__MODULES:
        return MODULES_EDEFAULT == null ? modules != null : !MODULES_EDEFAULT.equals(modules);
      case TransformationDslPackage.CLASSIFY_MODULES__EXCLUDED_MODULES:
        return EXCLUDED_MODULES_EDEFAULT == null ? excludedModules != null : !EXCLUDED_MODULES_EDEFAULT.equals(excludedModules);
      case TransformationDslPackage.CLASSIFY_MODULES__CLASSIFICATION:
        return CLASSIFICATION_EDEFAULT == null ? classification != null : !CLASSIFICATION_EDEFAULT.equals(classification);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (modules: ");
    result.append(modules);
    result.append(", excludedModules: ");
    result.append(excludedModules);
    result.append(", classification: ");
    result.append(classification);
    result.append(')');
    return result.toString();
  }

} //ClassifyModulesImpl
