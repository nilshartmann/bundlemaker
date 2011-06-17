/**
 * <copyright>
 * </copyright>
 *
 */
package org.bundlemaker.core.transformations.dsl.transformationDsl.impl;

import org.bundlemaker.core.transformations.dsl.transformationDsl.From;
import org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet;
import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>From</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.FromImpl#getResourceSet <em>Resource Set</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FromImpl extends MinimalEObjectImpl.Container implements From
{
  /**
   * The cached value of the '{@link #getResourceSet() <em>Resource Set</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getResourceSet()
   * @generated
   * @ordered
   */
  protected ResourceSet resourceSet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FromImpl()
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
    return TransformationDslPackage.Literals.FROM;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ResourceSet getResourceSet()
  {
    return resourceSet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetResourceSet(ResourceSet newResourceSet, NotificationChain msgs)
  {
    ResourceSet oldResourceSet = resourceSet;
    resourceSet = newResourceSet;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TransformationDslPackage.FROM__RESOURCE_SET, oldResourceSet, newResourceSet);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setResourceSet(ResourceSet newResourceSet)
  {
    if (newResourceSet != resourceSet)
    {
      NotificationChain msgs = null;
      if (resourceSet != null)
        msgs = ((InternalEObject)resourceSet).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.FROM__RESOURCE_SET, null, msgs);
      if (newResourceSet != null)
        msgs = ((InternalEObject)newResourceSet).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.FROM__RESOURCE_SET, null, msgs);
      msgs = basicSetResourceSet(newResourceSet, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TransformationDslPackage.FROM__RESOURCE_SET, newResourceSet, newResourceSet));
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
      case TransformationDslPackage.FROM__RESOURCE_SET:
        return basicSetResourceSet(null, msgs);
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
      case TransformationDslPackage.FROM__RESOURCE_SET:
        return getResourceSet();
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
      case TransformationDslPackage.FROM__RESOURCE_SET:
        setResourceSet((ResourceSet)newValue);
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
      case TransformationDslPackage.FROM__RESOURCE_SET:
        setResourceSet((ResourceSet)null);
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
      case TransformationDslPackage.FROM__RESOURCE_SET:
        return resourceSet != null;
    }
    return super.eIsSet(featureID);
  }

} //FromImpl
