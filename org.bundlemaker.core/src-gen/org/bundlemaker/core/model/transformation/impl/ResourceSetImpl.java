/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation.impl;

import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.internal.model.transformation.ResourceSetImplDelegate;
import org.bundlemaker.core.model.module.IModuleIdentifier;
import org.bundlemaker.core.model.projectdescription.ContentType;
import org.bundlemaker.core.model.transformation.ResourceSet;
import org.bundlemaker.core.model.transformation.TransformationPackage;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IResourceStandin;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Resource Set</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.transformation.impl.ResourceSetImpl#getModuleIdentifier <em>Module Identifier</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.impl.ResourceSetImpl#getIncludes <em>Includes</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.impl.ResourceSetImpl#getExcludes <em>Excludes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceSetImpl extends EObjectImpl implements ResourceSet {
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
	 * The cached value of the '{@link #getIncludes() <em>Includes</em>}' attribute list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIncludes()
	 * @generated
	 * @ordered
	 */
	protected EList<String> includes;

	/**
	 * The cached value of the '{@link #getExcludes() <em>Excludes</em>}' attribute list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getExcludes()
	 * @generated
	 * @ordered
	 */
	protected EList<String> excludes;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceSetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformationPackage.Literals.RESOURCE_SET;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TransformationPackage.RESOURCE_SET__MODULE_IDENTIFIER, oldModuleIdentifier, moduleIdentifier));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TransformationPackage.RESOURCE_SET__MODULE_IDENTIFIER, oldModuleIdentifier, moduleIdentifier));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> getIncludes() {
		if (includes == null) {
			includes = new EDataTypeUniqueEList<String>(String.class, this, TransformationPackage.RESOURCE_SET__INCLUDES);
		}
		return includes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> getExcludes() {
		if (excludes == null) {
			excludes = new EDataTypeUniqueEList<String>(String.class, this, TransformationPackage.RESOURCE_SET__EXCLUDES);
		}
		return excludes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public List<IResourceStandin> getMatchingResources(
			IResourceModule resource, ContentType contentType) {

		//
		return ResourceSetImplDelegate.getMatchingResources(this, resource,
				contentType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformationPackage.RESOURCE_SET__MODULE_IDENTIFIER:
				if (resolve) return getModuleIdentifier();
				return basicGetModuleIdentifier();
			case TransformationPackage.RESOURCE_SET__INCLUDES:
				return getIncludes();
			case TransformationPackage.RESOURCE_SET__EXCLUDES:
				return getExcludes();
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
			case TransformationPackage.RESOURCE_SET__MODULE_IDENTIFIER:
				setModuleIdentifier((IModuleIdentifier)newValue);
				return;
			case TransformationPackage.RESOURCE_SET__INCLUDES:
				getIncludes().clear();
				getIncludes().addAll((Collection<? extends String>)newValue);
				return;
			case TransformationPackage.RESOURCE_SET__EXCLUDES:
				getExcludes().clear();
				getExcludes().addAll((Collection<? extends String>)newValue);
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
			case TransformationPackage.RESOURCE_SET__MODULE_IDENTIFIER:
				setModuleIdentifier((IModuleIdentifier)null);
				return;
			case TransformationPackage.RESOURCE_SET__INCLUDES:
				getIncludes().clear();
				return;
			case TransformationPackage.RESOURCE_SET__EXCLUDES:
				getExcludes().clear();
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
			case TransformationPackage.RESOURCE_SET__MODULE_IDENTIFIER:
				return moduleIdentifier != null;
			case TransformationPackage.RESOURCE_SET__INCLUDES:
				return includes != null && !includes.isEmpty();
			case TransformationPackage.RESOURCE_SET__EXCLUDES:
				return excludes != null && !excludes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (includes: ");
		result.append(includes);
		result.append(", excludes: ");
		result.append(excludes);
		result.append(')');
		return result.toString();
	}

} // ResourceSetImpl
