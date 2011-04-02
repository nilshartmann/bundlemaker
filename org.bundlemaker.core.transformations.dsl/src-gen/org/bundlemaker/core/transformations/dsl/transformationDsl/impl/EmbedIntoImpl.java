/**
 * <copyright>
 * </copyright>
 *

 */
package org.bundlemaker.core.transformations.dsl.transformationDsl.impl;

import java.util.Collection;

import org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto;
import org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier;
import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Embed Into</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.EmbedIntoImpl#getHostModule <em>Host Module</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.EmbedIntoImpl#getModules <em>Modules</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EmbedIntoImpl extends TransformationImpl implements EmbedInto
{
  /**
   * The cached value of the '{@link #getHostModule() <em>Host Module</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHostModule()
   * @generated
   * @ordered
   */
  protected ModuleIdentifier hostModule;

  /**
   * The cached value of the '{@link #getModules() <em>Modules</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModules()
   * @generated
   * @ordered
   */
  protected EList<ModuleIdentifier> modules;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EmbedIntoImpl()
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
    return TransformationDslPackage.Literals.EMBED_INTO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModuleIdentifier getHostModule()
  {
    return hostModule;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetHostModule(ModuleIdentifier newHostModule, NotificationChain msgs)
  {
    ModuleIdentifier oldHostModule = hostModule;
    hostModule = newHostModule;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TransformationDslPackage.EMBED_INTO__HOST_MODULE, oldHostModule, newHostModule);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHostModule(ModuleIdentifier newHostModule)
  {
    if (newHostModule != hostModule)
    {
      NotificationChain msgs = null;
      if (hostModule != null)
        msgs = ((InternalEObject)hostModule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.EMBED_INTO__HOST_MODULE, null, msgs);
      if (newHostModule != null)
        msgs = ((InternalEObject)newHostModule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.EMBED_INTO__HOST_MODULE, null, msgs);
      msgs = basicSetHostModule(newHostModule, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TransformationDslPackage.EMBED_INTO__HOST_MODULE, newHostModule, newHostModule));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ModuleIdentifier> getModules()
  {
    if (modules == null)
    {
      modules = new EObjectContainmentEList<ModuleIdentifier>(ModuleIdentifier.class, this, TransformationDslPackage.EMBED_INTO__MODULES);
    }
    return modules;
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
      case TransformationDslPackage.EMBED_INTO__HOST_MODULE:
        return basicSetHostModule(null, msgs);
      case TransformationDslPackage.EMBED_INTO__MODULES:
        return ((InternalEList<?>)getModules()).basicRemove(otherEnd, msgs);
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
      case TransformationDslPackage.EMBED_INTO__HOST_MODULE:
        return getHostModule();
      case TransformationDslPackage.EMBED_INTO__MODULES:
        return getModules();
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
      case TransformationDslPackage.EMBED_INTO__HOST_MODULE:
        setHostModule((ModuleIdentifier)newValue);
        return;
      case TransformationDslPackage.EMBED_INTO__MODULES:
        getModules().clear();
        getModules().addAll((Collection<? extends ModuleIdentifier>)newValue);
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
      case TransformationDslPackage.EMBED_INTO__HOST_MODULE:
        setHostModule((ModuleIdentifier)null);
        return;
      case TransformationDslPackage.EMBED_INTO__MODULES:
        getModules().clear();
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
      case TransformationDslPackage.EMBED_INTO__HOST_MODULE:
        return hostModule != null;
      case TransformationDslPackage.EMBED_INTO__MODULES:
        return modules != null && !modules.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //EmbedIntoImpl
