/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl;

import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.internal.model.projectdescription.ModifiableResourceContentImplDelegate;
import org.bundlemaker.core.model.projectdescription.ContentType;

import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage;

import org.bundlemaker.core.resource.IResourceStandin;
import org.bundlemaker.core.resource.ResourceStandin;

import org.eclipse.core.runtime.IPath;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Modifiable Resource Content</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableResourceContentImpl#isAnalyzeSourceResources <em>Analyze Source Resources</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableResourceContentImpl#getModifiableSourcePathNames <em>Modifiable Source Path Names</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableResourceContentImpl#getModifiableSourcePaths <em>Modifiable Source Paths</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableResourceContentImpl#getModifiableSourceResources <em>Modifiable Source Resources</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableResourceContentImpl#getModifiableBinaryResources <em>Modifiable Binary Resources</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModifiableResourceContentImpl extends EObjectImpl implements
		ModifiableResourceContent {
	/**
	 * The default value of the '{@link #isAnalyzeSourceResources() <em>Analyze Source Resources</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAnalyzeSourceResources()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ANALYZE_SOURCE_RESOURCES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAnalyzeSourceResources() <em>Analyze Source Resources</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAnalyzeSourceResources()
	 * @generated
	 * @ordered
	 */
	protected boolean analyzeSourceResources = ANALYZE_SOURCE_RESOURCES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getModifiableSourcePathNames()
	 * <em>Modifiable Source Path Names</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModifiableSourcePathNames()
	 * @generated
	 * @ordered
	 */
	protected EList<String> modifiableSourcePathNames;

	/**
	 * The cached value of the '{@link #getModifiableSourcePaths() <em>Modifiable Source Paths</em>}' attribute list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getModifiableSourcePaths()
	 * @generated
	 * @ordered
	 */
	protected EList<IPath> modifiableSourcePaths;

	/**
	 * The cached value of the '{@link #getModifiableSourceResources()
	 * <em>Modifiable Source Resources</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModifiableSourceResources()
	 * @generated
	 * @ordered
	 */
	protected EList<ResourceStandin> modifiableSourceResources;

	/**
	 * The cached value of the '{@link #getModifiableBinaryResources()
	 * <em>Modifiable Binary Resources</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModifiableBinaryResources()
	 * @generated
	 * @ordered
	 */
	protected EList<ResourceStandin> modifiableBinaryResources;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ModifiableResourceContentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModifiableprojectdescriptionPackage.Literals.MODIFIABLE_RESOURCE_CONTENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAnalyzeSourceResources() {
		return analyzeSourceResources;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setAnalyzeSourceResources(boolean newAnalyzeSourceResources) {
		boolean oldAnalyzeSourceResources = analyzeSourceResources;
		analyzeSourceResources = newAnalyzeSourceResources;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES, oldAnalyzeSourceResources, analyzeSourceResources));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> getModifiableSourcePathNames() {
		if (modifiableSourcePathNames == null) {
			modifiableSourcePathNames = new EDataTypeUniqueEList<String>(String.class, this, ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATH_NAMES);
		}
		return modifiableSourcePathNames;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<IPath> getModifiableSourcePaths() {
		if (modifiableSourcePaths == null) {
			modifiableSourcePaths = new EDataTypeUniqueEList<IPath>(IPath.class, this, ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATHS);
		}
		return modifiableSourcePaths;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<ResourceStandin> getModifiableSourceResources() {
		if (modifiableSourceResources == null) {
			modifiableSourceResources = new EDataTypeUniqueEList<ResourceStandin>(ResourceStandin.class, this, ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_RESOURCES);
		}
		return modifiableSourceResources;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<ResourceStandin> getModifiableBinaryResources() {
		if (modifiableBinaryResources == null) {
			modifiableBinaryResources = new EDataTypeUniqueEList<ResourceStandin>(ResourceStandin.class, this, ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_BINARY_RESOURCES);
		}
		return modifiableBinaryResources;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public List<IPath> getSourcePaths() {
		return ModifiableResourceContentImplDelegate.getSourcePaths(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public IResourceStandin getResource(IPath path, ContentType type) {
		return ModifiableResourceContentImplDelegate.getResource(this, path,
				type);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public List<IResourceStandin> getResources(ContentType type) {
		return ModifiableResourceContentImplDelegate.getResources(this, type);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public IResourceStandin getBinaryResource(IPath path) {
		return ModifiableResourceContentImplDelegate.getBinaryResource(this,
				path);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public List<IResourceStandin> getBinaryResources() {
		return ModifiableResourceContentImplDelegate.getBinaryResources(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public IResourceStandin getSourceResource(IPath path) {
		return ModifiableResourceContentImplDelegate.getSourceResource(this,
				path);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public List<IResourceStandin> getSourceResources() {
		return ModifiableResourceContentImplDelegate.getSourceResources(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES:
				return isAnalyzeSourceResources();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATH_NAMES:
				return getModifiableSourcePathNames();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATHS:
				return getModifiableSourcePaths();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_RESOURCES:
				return getModifiableSourceResources();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_BINARY_RESOURCES:
				return getModifiableBinaryResources();
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
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES:
				setAnalyzeSourceResources((Boolean)newValue);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATH_NAMES:
				getModifiableSourcePathNames().clear();
				getModifiableSourcePathNames().addAll((Collection<? extends String>)newValue);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATHS:
				getModifiableSourcePaths().clear();
				getModifiableSourcePaths().addAll((Collection<? extends IPath>)newValue);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_RESOURCES:
				getModifiableSourceResources().clear();
				getModifiableSourceResources().addAll((Collection<? extends ResourceStandin>)newValue);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_BINARY_RESOURCES:
				getModifiableBinaryResources().clear();
				getModifiableBinaryResources().addAll((Collection<? extends ResourceStandin>)newValue);
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
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES:
				setAnalyzeSourceResources(ANALYZE_SOURCE_RESOURCES_EDEFAULT);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATH_NAMES:
				getModifiableSourcePathNames().clear();
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATHS:
				getModifiableSourcePaths().clear();
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_RESOURCES:
				getModifiableSourceResources().clear();
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_BINARY_RESOURCES:
				getModifiableBinaryResources().clear();
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
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES:
				return analyzeSourceResources != ANALYZE_SOURCE_RESOURCES_EDEFAULT;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATH_NAMES:
				return modifiableSourcePathNames != null && !modifiableSourcePathNames.isEmpty();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATHS:
				return modifiableSourcePaths != null && !modifiableSourcePaths.isEmpty();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_RESOURCES:
				return modifiableSourceResources != null && !modifiableSourceResources.isEmpty();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_BINARY_RESOURCES:
				return modifiableBinaryResources != null && !modifiableBinaryResources.isEmpty();
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
		result.append(" (analyzeSourceResources: ");
		result.append(analyzeSourceResources);
		result.append(", modifiableSourcePathNames: ");
		result.append(modifiableSourcePathNames);
		result.append(", modifiableSourcePaths: ");
		result.append(modifiableSourcePaths);
		result.append(", modifiableSourceResources: ");
		result.append(modifiableSourceResources);
		result.append(", modifiableBinaryResources: ");
		result.append(modifiableBinaryResources);
		result.append(')');
		return result.toString();
	}

} // ModifiableResourceContentImpl
