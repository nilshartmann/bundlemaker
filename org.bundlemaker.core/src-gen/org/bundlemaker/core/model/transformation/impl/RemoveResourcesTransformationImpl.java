/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation.impl;

import java.util.Collection;

import java.util.List;
import org.bundlemaker.core.internal.model.transformation.RemoveResourcesTransformationImplDelegate;
import org.bundlemaker.core.model.module.IModuleIdentifier;
import org.bundlemaker.core.model.transformation.RemoveResourcesTransformation;
import org.bundlemaker.core.model.transformation.ResourceSet;
import org.bundlemaker.core.model.transformation.TransformationPackage;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.ModularizedSystem;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Remove Resources Transformation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.transformation.impl.RemoveResourcesTransformationImpl#getResourcesToRemove <em>Resources To Remove</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RemoveResourcesTransformationImpl extends EObjectImpl implements
		RemoveResourcesTransformation {
	/**
	 * The cached value of the '{@link #getResourcesToRemove() <em>Resources To Remove</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourcesToRemove()
	 * @generated
	 * @ordered
	 */
	protected EList<ResourceSet> resourcesToRemove;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoveResourcesTransformationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformationPackage.Literals.REMOVE_RESOURCES_TRANSFORMATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<ResourceSet> getResourcesToRemove() {
		if (resourcesToRemove == null) {
			resourcesToRemove = new EObjectResolvingEList<ResourceSet>(ResourceSet.class, this, TransformationPackage.REMOVE_RESOURCES_TRANSFORMATION__RESOURCES_TO_REMOVE);
		}
		return resourcesToRemove;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void addResourceSet(String name, String version, String[] includes,
			String[] excludes) {

		//
		RemoveResourcesTransformationImplDelegate.addResourceSet(this, name,
				version, includes, excludes);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void addResourceSet(IModuleIdentifier fromModuleIdentifier,
			String[] includes, String[] excludes) {

		//
		RemoveResourcesTransformationImplDelegate.addResourceSet(this,
				fromModuleIdentifier, includes, excludes);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformationPackage.REMOVE_RESOURCES_TRANSFORMATION__RESOURCES_TO_REMOVE:
				return getResourcesToRemove();
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
			case TransformationPackage.REMOVE_RESOURCES_TRANSFORMATION__RESOURCES_TO_REMOVE:
				getResourcesToRemove().clear();
				getResourcesToRemove().addAll((Collection<? extends ResourceSet>)newValue);
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
			case TransformationPackage.REMOVE_RESOURCES_TRANSFORMATION__RESOURCES_TO_REMOVE:
				getResourcesToRemove().clear();
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
			case TransformationPackage.REMOVE_RESOURCES_TRANSFORMATION__RESOURCES_TO_REMOVE:
				return resourcesToRemove != null && !resourcesToRemove.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void apply(ModularizedSystem modularizedSystem) {

		RemoveResourcesTransformationImplDelegate
				.apply(this, modularizedSystem);
	}

} // RemoveResourcesTransformationImpl
