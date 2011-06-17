/**
 * <copyright>
 * </copyright>
 *
 */
package org.bundlemaker.core.transformations.dsl.transformationDsl.impl;

import java.util.Collection;

import org.bundlemaker.core.transformations.dsl.transformationDsl.Transformation;
import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage;
import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transformation Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationModelImpl#getTransformations <em>Transformations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransformationModelImpl extends MinimalEObjectImpl.Container implements TransformationModel
{
  /**
   * The cached value of the '{@link #getTransformations() <em>Transformations</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTransformations()
   * @generated
   * @ordered
   */
  protected EList<Transformation> transformations;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TransformationModelImpl()
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
    return TransformationDslPackage.Literals.TRANSFORMATION_MODEL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Transformation> getTransformations()
  {
    if (transformations == null)
    {
      transformations = new EObjectContainmentEList<Transformation>(Transformation.class, this, TransformationDslPackage.TRANSFORMATION_MODEL__TRANSFORMATIONS);
    }
    return transformations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case TransformationDslPackage.TRANSFORMATION_MODEL__TRANSFORMATIONS:
        return ((InternalEList<?>)getTransformations()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
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
      case TransformationDslPackage.TRANSFORMATION_MODEL__TRANSFORMATIONS:
        return getTransformations();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case TransformationDslPackage.TRANSFORMATION_MODEL__TRANSFORMATIONS:
        getTransformations().clear();
        getTransformations().addAll((Collection<? extends Transformation>)newValue);
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
      case TransformationDslPackage.TRANSFORMATION_MODEL__TRANSFORMATIONS:
        getTransformations().clear();
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
      case TransformationDslPackage.TRANSFORMATION_MODEL__TRANSFORMATIONS:
        return transformations != null && !transformations.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //TransformationModelImpl
