/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation.impl;

import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.internal.model.transformation.EmbedModuleTransformationImplDelegate;
import org.bundlemaker.core.model.module.IModuleIdentifier;
import org.bundlemaker.core.model.transformation.EmbedModuleTransformation;
import org.bundlemaker.core.model.transformation.TransformationPackage;
import org.bundlemaker.core.modules.ModularizedSystem;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Embed Module Transformation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.transformation.impl.EmbedModuleTransformationImpl#getHostModuleIdentifier <em>Host Module Identifier</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.impl.EmbedModuleTransformationImpl#getEmbeddedModulesIdentifiers <em>Embedded Modules Identifiers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EmbedModuleTransformationImpl extends EObjectImpl implements
		EmbedModuleTransformation {
	/**
	 * The cached value of the '{@link #getHostModuleIdentifier() <em>Host Module Identifier</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getHostModuleIdentifier()
	 * @generated
	 * @ordered
	 */
	protected IModuleIdentifier hostModuleIdentifier;

	/**
	 * The cached value of the '{@link #getEmbeddedModulesIdentifiers()
	 * <em>Embedded Modules Identifiers</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEmbeddedModulesIdentifiers()
	 * @generated
	 * @ordered
	 */
	protected EList<IModuleIdentifier> embeddedModulesIdentifiers;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EmbedModuleTransformationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformationPackage.Literals.EMBED_MODULE_TRANSFORMATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public IModuleIdentifier getHostModuleIdentifier() {
		if (hostModuleIdentifier != null && hostModuleIdentifier.eIsProxy()) {
			InternalEObject oldHostModuleIdentifier = (InternalEObject)hostModuleIdentifier;
			hostModuleIdentifier = (IModuleIdentifier)eResolveProxy(oldHostModuleIdentifier);
			if (hostModuleIdentifier != oldHostModuleIdentifier) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TransformationPackage.EMBED_MODULE_TRANSFORMATION__HOST_MODULE_IDENTIFIER, oldHostModuleIdentifier, hostModuleIdentifier));
			}
		}
		return hostModuleIdentifier;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public IModuleIdentifier basicGetHostModuleIdentifier() {
		return hostModuleIdentifier;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setHostModuleIdentifier(
			IModuleIdentifier newHostModuleIdentifier) {
		IModuleIdentifier oldHostModuleIdentifier = hostModuleIdentifier;
		hostModuleIdentifier = newHostModuleIdentifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformationPackage.EMBED_MODULE_TRANSFORMATION__HOST_MODULE_IDENTIFIER, oldHostModuleIdentifier, hostModuleIdentifier));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<IModuleIdentifier> getEmbeddedModulesIdentifiers() {
		if (embeddedModulesIdentifiers == null) {
			embeddedModulesIdentifiers = new EObjectResolvingEList<IModuleIdentifier>(IModuleIdentifier.class, this, TransformationPackage.EMBED_MODULE_TRANSFORMATION__EMBEDDED_MODULES_IDENTIFIERS);
		}
		return embeddedModulesIdentifiers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformationPackage.EMBED_MODULE_TRANSFORMATION__HOST_MODULE_IDENTIFIER:
				if (resolve) return getHostModuleIdentifier();
				return basicGetHostModuleIdentifier();
			case TransformationPackage.EMBED_MODULE_TRANSFORMATION__EMBEDDED_MODULES_IDENTIFIERS:
				return getEmbeddedModulesIdentifiers();
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
			case TransformationPackage.EMBED_MODULE_TRANSFORMATION__HOST_MODULE_IDENTIFIER:
				setHostModuleIdentifier((IModuleIdentifier)newValue);
				return;
			case TransformationPackage.EMBED_MODULE_TRANSFORMATION__EMBEDDED_MODULES_IDENTIFIERS:
				getEmbeddedModulesIdentifiers().clear();
				getEmbeddedModulesIdentifiers().addAll((Collection<? extends IModuleIdentifier>)newValue);
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
			case TransformationPackage.EMBED_MODULE_TRANSFORMATION__HOST_MODULE_IDENTIFIER:
				setHostModuleIdentifier((IModuleIdentifier)null);
				return;
			case TransformationPackage.EMBED_MODULE_TRANSFORMATION__EMBEDDED_MODULES_IDENTIFIERS:
				getEmbeddedModulesIdentifiers().clear();
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
			case TransformationPackage.EMBED_MODULE_TRANSFORMATION__HOST_MODULE_IDENTIFIER:
				return hostModuleIdentifier != null;
			case TransformationPackage.EMBED_MODULE_TRANSFORMATION__EMBEDDED_MODULES_IDENTIFIERS:
				return embeddedModulesIdentifiers != null && !embeddedModulesIdentifiers.isEmpty();
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

		EmbedModuleTransformationImplDelegate.apply(this, modularizedSystem);
	}

} // EmbedModuleTransformationImpl
