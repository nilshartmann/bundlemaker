/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation.impl;

import java.util.Collection;

import java.util.List;
import java.util.Map;
import org.bundlemaker.core.internal.model.transformation.ResourceSetBasedModuleDefinitionImplDelegate;
import org.bundlemaker.core.model.module.IModuleIdentifier;
import org.bundlemaker.core.model.transformation.ResourceSet;
import org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition;
import org.bundlemaker.core.model.transformation.TransformationPackage;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Resource Set Based Module Definition</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.transformation.impl.ResourceSetBasedModuleDefinitionImpl#getModuleIdentifier <em>Module Identifier</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.impl.ResourceSetBasedModuleDefinitionImpl#getResourceSets <em>Resource Sets</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.impl.ResourceSetBasedModuleDefinitionImpl#getUserAttributes <em>User Attributes</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.impl.ResourceSetBasedModuleDefinitionImpl#getClassification <em>Classification</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceSetBasedModuleDefinitionImpl extends EObjectImpl implements
		ResourceSetBasedModuleDefinition {
	/**
	 * The cached value of the '{@link #getModuleIdentifier() <em>Module Identifier</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getModuleIdentifier()
	 * @generated
	 * @ordered
	 */
	protected IModuleIdentifier moduleIdentifier;

	/**
	 * The cached value of the '{@link #getResourceSets() <em>Resource Sets</em>}' reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getResourceSets()
	 * @generated
	 * @ordered
	 */
	protected EList<ResourceSet> resourceSets;

	/**
	 * The cached value of the '{@link #getUserAttributes()
	 * <em>User Attributes</em>}' map. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getUserAttributes()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Object> userAttributes;

	/**
	 * The default value of the '{@link #getClassification() <em>Classification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassification()
	 * @generated
	 * @ordered
	 */
	protected static final IPath CLASSIFICATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassification() <em>Classification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassification()
	 * @generated
	 * @ordered
	 */
	protected IPath classification = CLASSIFICATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceSetBasedModuleDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformationPackage.Literals.RESOURCE_SET_BASED_MODULE_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<ResourceSet> getResourceSets() {
		if (resourceSets == null) {
			resourceSets = new EObjectResolvingEList<ResourceSet>(ResourceSet.class, this, TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__RESOURCE_SETS);
		}
		return resourceSets;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public IModuleIdentifier getModuleIdentifier() {
		if (moduleIdentifier != null && moduleIdentifier.eIsProxy()) {
			InternalEObject oldModuleIdentifier = (InternalEObject)moduleIdentifier;
			moduleIdentifier = (IModuleIdentifier)eResolveProxy(oldModuleIdentifier);
			if (moduleIdentifier != oldModuleIdentifier) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__MODULE_IDENTIFIER, oldModuleIdentifier, moduleIdentifier));
			}
		}
		return moduleIdentifier;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public IModuleIdentifier basicGetModuleIdentifier() {
		return moduleIdentifier;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setModuleIdentifier(IModuleIdentifier newModuleIdentifier) {
		IModuleIdentifier oldModuleIdentifier = moduleIdentifier;
		moduleIdentifier = newModuleIdentifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__MODULE_IDENTIFIER, oldModuleIdentifier, moduleIdentifier));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getUserAttributes() {
		if (userAttributes == null) {
			userAttributes = new EcoreEMap<String,Object>(TransformationPackage.Literals.STRING_TO_OBJECT_MAP, StringToObjectMapImpl.class, this, TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__USER_ATTRIBUTES);
		}
		return userAttributes.map();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IPath getClassification() {
		return classification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClassification(IPath newClassification) {
		IPath oldClassification = classification;
		classification = newClassification;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__CLASSIFICATION, oldClassification, classification));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void addResourceSet(String fromName, String fromVersion,
			String[] includes, String[] excludes) {
		ResourceSetBasedModuleDefinitionImplDelegate.addResourceSet(this,
				fromName, fromVersion, includes, excludes);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void addResourceSet(IModuleIdentifier fromModuleIdentifier,
			String[] includes, String[] excludes) {
		ResourceSetBasedModuleDefinitionImplDelegate.addResourceSet(this,
				fromModuleIdentifier, includes, excludes);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__USER_ATTRIBUTES:
				return ((InternalEList<?>)((EMap.InternalMapView<String, Object>)getUserAttributes()).eMap()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__MODULE_IDENTIFIER:
				if (resolve) return getModuleIdentifier();
				return basicGetModuleIdentifier();
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__RESOURCE_SETS:
				return getResourceSets();
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__USER_ATTRIBUTES:
				if (coreType) return ((EMap.InternalMapView<String, Object>)getUserAttributes()).eMap();
				else return getUserAttributes();
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__CLASSIFICATION:
				return getClassification();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__MODULE_IDENTIFIER:
				setModuleIdentifier((IModuleIdentifier)newValue);
				return;
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__RESOURCE_SETS:
				getResourceSets().clear();
				getResourceSets().addAll((Collection<? extends ResourceSet>)newValue);
				return;
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__USER_ATTRIBUTES:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, Object>)getUserAttributes()).eMap()).set(newValue);
				return;
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__CLASSIFICATION:
				setClassification((IPath)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__MODULE_IDENTIFIER:
				setModuleIdentifier((IModuleIdentifier)null);
				return;
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__RESOURCE_SETS:
				getResourceSets().clear();
				return;
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__USER_ATTRIBUTES:
				getUserAttributes().clear();
				return;
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__CLASSIFICATION:
				setClassification(CLASSIFICATION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__MODULE_IDENTIFIER:
				return moduleIdentifier != null;
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__RESOURCE_SETS:
				return resourceSets != null && !resourceSets.isEmpty();
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__USER_ATTRIBUTES:
				return userAttributes != null && !userAttributes.isEmpty();
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION__CLASSIFICATION:
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
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (classification: ");
		result.append(classification);
		result.append(')');
		return result.toString();
	}

} // ResourceSetBasedModuleDefinitionImpl
