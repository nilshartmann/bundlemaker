/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.internal.projectdescription.impl;

import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent;
import org.bundlemaker.core.model.internal.projectdescription.EResourceContent;
import org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EFile Based Content</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.impl.EFileBasedContentImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.impl.EFileBasedContentImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.impl.EFileBasedContentImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.impl.EFileBasedContentImpl#getBinaryPathNames <em>Binary Path Names</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.impl.EFileBasedContentImpl#getResourceContent <em>Resource Content</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EFileBasedContentImpl extends EObjectImpl implements EFileBasedContent {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBinaryPathNames() <em>Binary Path Names</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBinaryPathNames()
	 * @generated
	 * @ordered
	 */
	protected EList<String> binaryPathNames;

	/**
	 * The cached value of the '{@link #getResourceContent() <em>Resource Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceContent()
	 * @generated
	 * @ordered
	 */
	protected EResourceContent resourceContent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EFileBasedContentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProjectdescriptionPackage.Literals.EFILE_BASED_CONTENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectdescriptionPackage.EFILE_BASED_CONTENT__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectdescriptionPackage.EFILE_BASED_CONTENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectdescriptionPackage.EFILE_BASED_CONTENT__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> getBinaryPathNames() {
		if (binaryPathNames == null) {
			binaryPathNames = new EDataTypeUniqueEList<String>(String.class, this, ProjectdescriptionPackage.EFILE_BASED_CONTENT__BINARY_PATH_NAMES);
		}
		return binaryPathNames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EResourceContent getResourceContent() {
		return resourceContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResourceContent(EResourceContent newResourceContent, NotificationChain msgs) {
		EResourceContent oldResourceContent = resourceContent;
		resourceContent = newResourceContent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProjectdescriptionPackage.EFILE_BASED_CONTENT__RESOURCE_CONTENT, oldResourceContent, newResourceContent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourceContent(EResourceContent newResourceContent) {
		if (newResourceContent != resourceContent) {
			NotificationChain msgs = null;
			if (resourceContent != null)
				msgs = ((InternalEObject)resourceContent).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProjectdescriptionPackage.EFILE_BASED_CONTENT__RESOURCE_CONTENT, null, msgs);
			if (newResourceContent != null)
				msgs = ((InternalEObject)newResourceContent).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProjectdescriptionPackage.EFILE_BASED_CONTENT__RESOURCE_CONTENT, null, msgs);
			msgs = basicSetResourceContent(newResourceContent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectdescriptionPackage.EFILE_BASED_CONTENT__RESOURCE_CONTENT, newResourceContent, newResourceContent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__RESOURCE_CONTENT:
				return basicSetResourceContent(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__ID:
				return getId();
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__NAME:
				return getName();
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__VERSION:
				return getVersion();
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__BINARY_PATH_NAMES:
				return getBinaryPathNames();
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__RESOURCE_CONTENT:
				return getResourceContent();
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
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__ID:
				setId((String)newValue);
				return;
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__NAME:
				setName((String)newValue);
				return;
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__VERSION:
				setVersion((String)newValue);
				return;
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__BINARY_PATH_NAMES:
				getBinaryPathNames().clear();
				getBinaryPathNames().addAll((Collection<? extends String>)newValue);
				return;
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__RESOURCE_CONTENT:
				setResourceContent((EResourceContent)newValue);
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
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__ID:
				setId(ID_EDEFAULT);
				return;
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__BINARY_PATH_NAMES:
				getBinaryPathNames().clear();
				return;
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__RESOURCE_CONTENT:
				setResourceContent((EResourceContent)null);
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
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__BINARY_PATH_NAMES:
				return binaryPathNames != null && !binaryPathNames.isEmpty();
			case ProjectdescriptionPackage.EFILE_BASED_CONTENT__RESOURCE_CONTENT:
				return resourceContent != null;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(", version: ");
		result.append(version);
		result.append(", binaryPathNames: ");
		result.append(binaryPathNames);
		result.append(')');
		return result.toString();
	}

} //EFileBasedContentImpl
