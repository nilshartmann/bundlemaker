/**
 * <copyright>
 * </copyright>
 *

 */
package org.bundlemaker.core.transformations.dsl.transformationDsl.impl;

import org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier;
import org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceList;
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
 * An implementation of the model object '<em><b>Resource Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ResourceSetImpl#getModuleIdentifier <em>Module Identifier</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ResourceSetImpl#getIncludeResources <em>Include Resources</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ResourceSetImpl#getExcludeResources <em>Exclude Resources</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceSetImpl extends MinimalEObjectImpl.Container implements ResourceSet
{
  /**
   * The cached value of the '{@link #getModuleIdentifier() <em>Module Identifier</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModuleIdentifier()
   * @generated
   * @ordered
   */
  protected ModuleIdentifier moduleIdentifier;

  /**
   * The cached value of the '{@link #getIncludeResources() <em>Include Resources</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIncludeResources()
   * @generated
   * @ordered
   */
  protected ResourceList includeResources;

  /**
   * The cached value of the '{@link #getExcludeResources() <em>Exclude Resources</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExcludeResources()
   * @generated
   * @ordered
   */
  protected ResourceList excludeResources;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ResourceSetImpl()
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
    return TransformationDslPackage.Literals.RESOURCE_SET;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModuleIdentifier getModuleIdentifier()
  {
    return moduleIdentifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetModuleIdentifier(ModuleIdentifier newModuleIdentifier, NotificationChain msgs)
  {
    ModuleIdentifier oldModuleIdentifier = moduleIdentifier;
    moduleIdentifier = newModuleIdentifier;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TransformationDslPackage.RESOURCE_SET__MODULE_IDENTIFIER, oldModuleIdentifier, newModuleIdentifier);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setModuleIdentifier(ModuleIdentifier newModuleIdentifier)
  {
    if (newModuleIdentifier != moduleIdentifier)
    {
      NotificationChain msgs = null;
      if (moduleIdentifier != null)
        msgs = ((InternalEObject)moduleIdentifier).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.RESOURCE_SET__MODULE_IDENTIFIER, null, msgs);
      if (newModuleIdentifier != null)
        msgs = ((InternalEObject)newModuleIdentifier).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.RESOURCE_SET__MODULE_IDENTIFIER, null, msgs);
      msgs = basicSetModuleIdentifier(newModuleIdentifier, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TransformationDslPackage.RESOURCE_SET__MODULE_IDENTIFIER, newModuleIdentifier, newModuleIdentifier));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ResourceList getIncludeResources()
  {
    return includeResources;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetIncludeResources(ResourceList newIncludeResources, NotificationChain msgs)
  {
    ResourceList oldIncludeResources = includeResources;
    includeResources = newIncludeResources;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TransformationDslPackage.RESOURCE_SET__INCLUDE_RESOURCES, oldIncludeResources, newIncludeResources);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIncludeResources(ResourceList newIncludeResources)
  {
    if (newIncludeResources != includeResources)
    {
      NotificationChain msgs = null;
      if (includeResources != null)
        msgs = ((InternalEObject)includeResources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.RESOURCE_SET__INCLUDE_RESOURCES, null, msgs);
      if (newIncludeResources != null)
        msgs = ((InternalEObject)newIncludeResources).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.RESOURCE_SET__INCLUDE_RESOURCES, null, msgs);
      msgs = basicSetIncludeResources(newIncludeResources, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TransformationDslPackage.RESOURCE_SET__INCLUDE_RESOURCES, newIncludeResources, newIncludeResources));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ResourceList getExcludeResources()
  {
    return excludeResources;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetExcludeResources(ResourceList newExcludeResources, NotificationChain msgs)
  {
    ResourceList oldExcludeResources = excludeResources;
    excludeResources = newExcludeResources;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TransformationDslPackage.RESOURCE_SET__EXCLUDE_RESOURCES, oldExcludeResources, newExcludeResources);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExcludeResources(ResourceList newExcludeResources)
  {
    if (newExcludeResources != excludeResources)
    {
      NotificationChain msgs = null;
      if (excludeResources != null)
        msgs = ((InternalEObject)excludeResources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.RESOURCE_SET__EXCLUDE_RESOURCES, null, msgs);
      if (newExcludeResources != null)
        msgs = ((InternalEObject)newExcludeResources).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.RESOURCE_SET__EXCLUDE_RESOURCES, null, msgs);
      msgs = basicSetExcludeResources(newExcludeResources, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TransformationDslPackage.RESOURCE_SET__EXCLUDE_RESOURCES, newExcludeResources, newExcludeResources));
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
      case TransformationDslPackage.RESOURCE_SET__MODULE_IDENTIFIER:
        return basicSetModuleIdentifier(null, msgs);
      case TransformationDslPackage.RESOURCE_SET__INCLUDE_RESOURCES:
        return basicSetIncludeResources(null, msgs);
      case TransformationDslPackage.RESOURCE_SET__EXCLUDE_RESOURCES:
        return basicSetExcludeResources(null, msgs);
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
      case TransformationDslPackage.RESOURCE_SET__MODULE_IDENTIFIER:
        return getModuleIdentifier();
      case TransformationDslPackage.RESOURCE_SET__INCLUDE_RESOURCES:
        return getIncludeResources();
      case TransformationDslPackage.RESOURCE_SET__EXCLUDE_RESOURCES:
        return getExcludeResources();
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
      case TransformationDslPackage.RESOURCE_SET__MODULE_IDENTIFIER:
        setModuleIdentifier((ModuleIdentifier)newValue);
        return;
      case TransformationDslPackage.RESOURCE_SET__INCLUDE_RESOURCES:
        setIncludeResources((ResourceList)newValue);
        return;
      case TransformationDslPackage.RESOURCE_SET__EXCLUDE_RESOURCES:
        setExcludeResources((ResourceList)newValue);
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
      case TransformationDslPackage.RESOURCE_SET__MODULE_IDENTIFIER:
        setModuleIdentifier((ModuleIdentifier)null);
        return;
      case TransformationDslPackage.RESOURCE_SET__INCLUDE_RESOURCES:
        setIncludeResources((ResourceList)null);
        return;
      case TransformationDslPackage.RESOURCE_SET__EXCLUDE_RESOURCES:
        setExcludeResources((ResourceList)null);
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
      case TransformationDslPackage.RESOURCE_SET__MODULE_IDENTIFIER:
        return moduleIdentifier != null;
      case TransformationDslPackage.RESOURCE_SET__INCLUDE_RESOURCES:
        return includeResources != null;
      case TransformationDslPackage.RESOURCE_SET__EXCLUDE_RESOURCES:
        return excludeResources != null;
    }
    return super.eIsSet(featureID);
  }

} //ResourceSetImpl
