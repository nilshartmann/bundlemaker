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

import org.bundlemaker.core.internal.model.transformation.ResourceSetBasedTransformationImplDelegate;
import org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition;
import org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation;
import org.bundlemaker.core.model.transformation.TransformationPackage;
import org.bundlemaker.core.modules.ModularizedSystem;
import org.bundlemaker.core.transformation.resourceset.IResourceSetProcessor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Resource Set Based Transformation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.transformation.impl.ResourceSetBasedTransformationImpl#getModuleDefinitions <em>Module Definitions</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.transformation.impl.ResourceSetBasedTransformationImpl#getResourceSetProcessor <em>Resource Set Processor</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceSetBasedTransformationImpl extends EObjectImpl implements
		ResourceSetBasedTransformation {
	/**
	 * The cached value of the '{@link #getModuleDefinitions() <em>Module Definitions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleDefinitions()
	 * @generated
	 * @ordered
	 */
	protected EList<ResourceSetBasedModuleDefinition> moduleDefinitions;

	/**
	 * The default value of the '{@link #getResourceSetProcessor() <em>Resource Set Processor</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getResourceSetProcessor()
	 * @generated
	 * @ordered
	 */
	protected static final IResourceSetProcessor RESOURCE_SET_PROCESSOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResourceSetProcessor() <em>Resource Set Processor</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getResourceSetProcessor()
	 * @generated
	 * @ordered
	 */
	protected IResourceSetProcessor resourceSetProcessor = RESOURCE_SET_PROCESSOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceSetBasedTransformationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformationPackage.Literals.RESOURCE_SET_BASED_TRANSFORMATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<ResourceSetBasedModuleDefinition> getModuleDefinitions() {
		if (moduleDefinitions == null) {
			moduleDefinitions = new EObjectResolvingEList<ResourceSetBasedModuleDefinition>(ResourceSetBasedModuleDefinition.class, this, TransformationPackage.RESOURCE_SET_BASED_TRANSFORMATION__MODULE_DEFINITIONS);
		}
		return moduleDefinitions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public IResourceSetProcessor getResourceSetProcessor() {
		return resourceSetProcessor;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourceSetProcessor(
			IResourceSetProcessor newResourceSetProcessor) {
		IResourceSetProcessor oldResourceSetProcessor = resourceSetProcessor;
		resourceSetProcessor = newResourceSetProcessor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformationPackage.RESOURCE_SET_BASED_TRANSFORMATION__RESOURCE_SET_PROCESSOR, oldResourceSetProcessor, resourceSetProcessor));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ResourceSetBasedModuleDefinition addModuleDefinition(String name,
			String version) {

		//
		return ResourceSetBasedTransformationImplDelegate.addModuleDefinition(
				this, name, version);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	public ResourceSetBasedModuleDefinition addModuleDefinition(String name,
			String version, Map<String, Object> userAttibutes) {
		//
		return ResourceSetBasedTransformationImplDelegate.addModuleDefinition(
				this, name, version, userAttibutes);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformationPackage.RESOURCE_SET_BASED_TRANSFORMATION__MODULE_DEFINITIONS:
				return getModuleDefinitions();
			case TransformationPackage.RESOURCE_SET_BASED_TRANSFORMATION__RESOURCE_SET_PROCESSOR:
				return getResourceSetProcessor();
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
			case TransformationPackage.RESOURCE_SET_BASED_TRANSFORMATION__MODULE_DEFINITIONS:
				getModuleDefinitions().clear();
				getModuleDefinitions().addAll((Collection<? extends ResourceSetBasedModuleDefinition>)newValue);
				return;
			case TransformationPackage.RESOURCE_SET_BASED_TRANSFORMATION__RESOURCE_SET_PROCESSOR:
				setResourceSetProcessor((IResourceSetProcessor)newValue);
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
			case TransformationPackage.RESOURCE_SET_BASED_TRANSFORMATION__MODULE_DEFINITIONS:
				getModuleDefinitions().clear();
				return;
			case TransformationPackage.RESOURCE_SET_BASED_TRANSFORMATION__RESOURCE_SET_PROCESSOR:
				setResourceSetProcessor(RESOURCE_SET_PROCESSOR_EDEFAULT);
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
			case TransformationPackage.RESOURCE_SET_BASED_TRANSFORMATION__MODULE_DEFINITIONS:
				return moduleDefinitions != null && !moduleDefinitions.isEmpty();
			case TransformationPackage.RESOURCE_SET_BASED_TRANSFORMATION__RESOURCE_SET_PROCESSOR:
				return RESOURCE_SET_PROCESSOR_EDEFAULT == null ? resourceSetProcessor != null : !RESOURCE_SET_PROCESSOR_EDEFAULT.equals(resourceSetProcessor);
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
		result.append(" (resourceSetProcessor: ");
		result.append(resourceSetProcessor);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void apply(ModularizedSystem modularizedSystem) {
		ResourceSetBasedTransformationImplDelegate.apply(this,
				modularizedSystem);

	}
} // ResourceSetBasedTransformationImpl
