/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.internal.projectdescription.impl;

import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.model.internal.projectdescription.EResourceContent;
import org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EResource Content</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.impl.EResourceContentImpl#isAnalyzeSourceResources <em>Analyze Source Resources</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.impl.EResourceContentImpl#getSourcePathNames <em>Source Path Names</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EResourceContentImpl extends EObjectImpl implements EResourceContent {
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
	 * The cached value of the '{@link #getSourcePathNames() <em>Source Path Names</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourcePathNames()
	 * @generated
	 * @ordered
	 */
	protected EList<String> sourcePathNames;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EResourceContentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProjectdescriptionPackage.Literals.ERESOURCE_CONTENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAnalyzeSourceResources() {
		return analyzeSourceResources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAnalyzeSourceResources(boolean newAnalyzeSourceResources) {
		boolean oldAnalyzeSourceResources = analyzeSourceResources;
		analyzeSourceResources = newAnalyzeSourceResources;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectdescriptionPackage.ERESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES, oldAnalyzeSourceResources, analyzeSourceResources));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> getSourcePathNames() {
		if (sourcePathNames == null) {
			sourcePathNames = new EDataTypeUniqueEList<String>(String.class, this, ProjectdescriptionPackage.ERESOURCE_CONTENT__SOURCE_PATH_NAMES);
		}
		return sourcePathNames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProjectdescriptionPackage.ERESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES:
				return isAnalyzeSourceResources();
			case ProjectdescriptionPackage.ERESOURCE_CONTENT__SOURCE_PATH_NAMES:
				return getSourcePathNames();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ProjectdescriptionPackage.ERESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES:
				setAnalyzeSourceResources((Boolean)newValue);
				return;
			case ProjectdescriptionPackage.ERESOURCE_CONTENT__SOURCE_PATH_NAMES:
				getSourcePathNames().clear();
				getSourcePathNames().addAll((Collection<? extends String>)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case ProjectdescriptionPackage.ERESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES:
				setAnalyzeSourceResources(ANALYZE_SOURCE_RESOURCES_EDEFAULT);
				return;
			case ProjectdescriptionPackage.ERESOURCE_CONTENT__SOURCE_PATH_NAMES:
				getSourcePathNames().clear();
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ProjectdescriptionPackage.ERESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES:
				return analyzeSourceResources != ANALYZE_SOURCE_RESOURCES_EDEFAULT;
			case ProjectdescriptionPackage.ERESOURCE_CONTENT__SOURCE_PATH_NAMES:
				return sourcePathNames != null && !sourcePathNames.isEmpty();
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
		result.append(" (analyzeSourceResources: ");
		result.append(analyzeSourceResources);
		result.append(", sourcePathNames: ");
		result.append(sourcePathNames);
		result.append(')');
		return result.toString();
	}

} //EResourceContentImpl
