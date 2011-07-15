/**
 * <copyright>
 * </copyright>
 *
 */
package org.bundlemaker.core.transformations.dsl.transformationDsl.impl;

import java.util.Collection;

import org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule;
import org.bundlemaker.core.transformations.dsl.transformationDsl.From;
import org.bundlemaker.core.transformations.dsl.transformationDsl.Layer;
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
 * An implementation of the model object '<em><b>Create Module</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.CreateModuleImpl#getModule <em>Module</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.CreateModuleImpl#getLayer <em>Layer</em>}</li>
 *   <li>{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.CreateModuleImpl#getFrom <em>From</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CreateModuleImpl extends TransformationImpl implements CreateModule
{
  /**
   * The cached value of the '{@link #getModule() <em>Module</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModule()
   * @generated
   * @ordered
   */
  protected ModuleIdentifier module;

  /**
   * The cached value of the '{@link #getLayer() <em>Layer</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLayer()
   * @generated
   * @ordered
   */
  protected Layer layer;

  /**
   * The cached value of the '{@link #getFrom() <em>From</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFrom()
   * @generated
   * @ordered
   */
  protected EList<From> from;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CreateModuleImpl()
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
    return TransformationDslPackage.Literals.CREATE_MODULE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModuleIdentifier getModule()
  {
    return module;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetModule(ModuleIdentifier newModule, NotificationChain msgs)
  {
    ModuleIdentifier oldModule = module;
    module = newModule;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TransformationDslPackage.CREATE_MODULE__MODULE, oldModule, newModule);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setModule(ModuleIdentifier newModule)
  {
    if (newModule != module)
    {
      NotificationChain msgs = null;
      if (module != null)
        msgs = ((InternalEObject)module).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.CREATE_MODULE__MODULE, null, msgs);
      if (newModule != null)
        msgs = ((InternalEObject)newModule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.CREATE_MODULE__MODULE, null, msgs);
      msgs = basicSetModule(newModule, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TransformationDslPackage.CREATE_MODULE__MODULE, newModule, newModule));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Layer getLayer()
  {
    return layer;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLayer(Layer newLayer, NotificationChain msgs)
  {
    Layer oldLayer = layer;
    layer = newLayer;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TransformationDslPackage.CREATE_MODULE__LAYER, oldLayer, newLayer);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLayer(Layer newLayer)
  {
    if (newLayer != layer)
    {
      NotificationChain msgs = null;
      if (layer != null)
        msgs = ((InternalEObject)layer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.CREATE_MODULE__LAYER, null, msgs);
      if (newLayer != null)
        msgs = ((InternalEObject)newLayer).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TransformationDslPackage.CREATE_MODULE__LAYER, null, msgs);
      msgs = basicSetLayer(newLayer, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TransformationDslPackage.CREATE_MODULE__LAYER, newLayer, newLayer));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<From> getFrom()
  {
    if (from == null)
    {
      from = new EObjectContainmentEList<From>(From.class, this, TransformationDslPackage.CREATE_MODULE__FROM);
    }
    return from;
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
      case TransformationDslPackage.CREATE_MODULE__MODULE:
        return basicSetModule(null, msgs);
      case TransformationDslPackage.CREATE_MODULE__LAYER:
        return basicSetLayer(null, msgs);
      case TransformationDslPackage.CREATE_MODULE__FROM:
        return ((InternalEList<?>)getFrom()).basicRemove(otherEnd, msgs);
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
      case TransformationDslPackage.CREATE_MODULE__MODULE:
        return getModule();
      case TransformationDslPackage.CREATE_MODULE__LAYER:
        return getLayer();
      case TransformationDslPackage.CREATE_MODULE__FROM:
        return getFrom();
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
      case TransformationDslPackage.CREATE_MODULE__MODULE:
        setModule((ModuleIdentifier)newValue);
        return;
      case TransformationDslPackage.CREATE_MODULE__LAYER:
        setLayer((Layer)newValue);
        return;
      case TransformationDslPackage.CREATE_MODULE__FROM:
        getFrom().clear();
        getFrom().addAll((Collection<? extends From>)newValue);
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
      case TransformationDslPackage.CREATE_MODULE__MODULE:
        setModule((ModuleIdentifier)null);
        return;
      case TransformationDslPackage.CREATE_MODULE__LAYER:
        setLayer((Layer)null);
        return;
      case TransformationDslPackage.CREATE_MODULE__FROM:
        getFrom().clear();
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
      case TransformationDslPackage.CREATE_MODULE__MODULE:
        return module != null;
      case TransformationDslPackage.CREATE_MODULE__LAYER:
        return layer != null;
      case TransformationDslPackage.CREATE_MODULE__FROM:
        return from != null && !from.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //CreateModuleImpl
