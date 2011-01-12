/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl;

import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.internal.model.projectdescription.ModifiableFileBasedContentImplDelegate;
import org.bundlemaker.core.model.projectdescription.IResourceContent;

import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage;

import org.eclipse.core.runtime.IPath;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Modifiable File Based Content</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableFileBasedContentImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableFileBasedContentImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableFileBasedContentImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableFileBasedContentImpl#isInitialized <em>Initialized</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableFileBasedContentImpl#getModifiableBinaryPathNames <em>Modifiable Binary Path Names</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableFileBasedContentImpl#getModifiableBinaryPaths <em>Modifiable Binary Paths</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableFileBasedContentImpl#getModifiableResourceContent <em>Modifiable Resource Content</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModifiableFileBasedContentImpl extends EObjectImpl implements
		ModifiableFileBasedContent {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #isInitialized() <em>Initialized</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isInitialized()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INITIALIZED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInitialized() <em>Initialized</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isInitialized()
	 * @generated
	 * @ordered
	 */
	protected boolean initialized = INITIALIZED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getModifiableBinaryPathNames()
	 * <em>Modifiable Binary Path Names</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModifiableBinaryPathNames()
	 * @generated
	 * @ordered
	 */
	protected EList<String> modifiableBinaryPathNames;

	/**
	 * The cached value of the '{@link #getModifiableBinaryPaths() <em>Modifiable Binary Paths</em>}' attribute list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getModifiableBinaryPaths()
	 * @generated
	 * @ordered
	 */
	protected EList<IPath> modifiableBinaryPaths;

	/**
	 * The cached value of the '{@link #getModifiableResourceContent()
	 * <em>Modifiable Resource Content</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModifiableResourceContent()
	 * @generated
	 * @ordered
	 */
	protected ModifiableResourceContent modifiableResourceContent;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ModifiableFileBasedContentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModifiableprojectdescriptionPackage.Literals.MODIFIABLE_FILE_BASED_CONTENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialized(boolean newInitialized) {
		boolean oldInitialized = initialized;
		initialized = newInitialized;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__INITIALIZED, oldInitialized, initialized));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> getModifiableBinaryPathNames() {
		if (modifiableBinaryPathNames == null) {
			modifiableBinaryPathNames = new EDataTypeUniqueEList<String>(String.class, this, ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATH_NAMES);
		}
		return modifiableBinaryPathNames;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<IPath> getModifiableBinaryPaths() {
		if (modifiableBinaryPaths == null) {
			modifiableBinaryPaths = new EDataTypeUniqueEList<IPath>(IPath.class, this, ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATHS);
		}
		return modifiableBinaryPaths;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ModifiableResourceContent getModifiableResourceContent() {
		return modifiableResourceContent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetModifiableResourceContent(
			ModifiableResourceContent newModifiableResourceContent,
			NotificationChain msgs) {
		ModifiableResourceContent oldModifiableResourceContent = modifiableResourceContent;
		modifiableResourceContent = newModifiableResourceContent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_RESOURCE_CONTENT, oldModifiableResourceContent, newModifiableResourceContent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setModifiableResourceContent(
			ModifiableResourceContent newModifiableResourceContent) {
		if (newModifiableResourceContent != modifiableResourceContent) {
			NotificationChain msgs = null;
			if (modifiableResourceContent != null)
				msgs = ((InternalEObject)modifiableResourceContent).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_RESOURCE_CONTENT, null, msgs);
			if (newModifiableResourceContent != null)
				msgs = ((InternalEObject)newModifiableResourceContent).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_RESOURCE_CONTENT, null, msgs);
			msgs = basicSetModifiableResourceContent(newModifiableResourceContent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_RESOURCE_CONTENT, newModifiableResourceContent, newModifiableResourceContent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void initialize(BundleMakerProject bundleMakerProject) {
		ModifiableFileBasedContentImplDelegate.initialize(this, bundleMakerProject);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public List<IPath> getBinaryPaths() {
		return ModifiableFileBasedContentImplDelegate.getBinaryPaths(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isResourceContent() {
		return ModifiableFileBasedContentImplDelegate.isResourceContent(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public IResourceContent getResourceContent() {
		return ModifiableFileBasedContentImplDelegate.getResourceContent(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_RESOURCE_CONTENT:
				return basicSetModifiableResourceContent(null, msgs);
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
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__ID:
				return getId();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__NAME:
				return getName();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__VERSION:
				return getVersion();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__INITIALIZED:
				return isInitialized();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATH_NAMES:
				return getModifiableBinaryPathNames();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATHS:
				return getModifiableBinaryPaths();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_RESOURCE_CONTENT:
				return getModifiableResourceContent();
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
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__ID:
				setId((String)newValue);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__NAME:
				setName((String)newValue);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__VERSION:
				setVersion((String)newValue);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__INITIALIZED:
				setInitialized((Boolean)newValue);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATH_NAMES:
				getModifiableBinaryPathNames().clear();
				getModifiableBinaryPathNames().addAll((Collection<? extends String>)newValue);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATHS:
				getModifiableBinaryPaths().clear();
				getModifiableBinaryPaths().addAll((Collection<? extends IPath>)newValue);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_RESOURCE_CONTENT:
				setModifiableResourceContent((ModifiableResourceContent)newValue);
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
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__ID:
				setId(ID_EDEFAULT);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__INITIALIZED:
				setInitialized(INITIALIZED_EDEFAULT);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATH_NAMES:
				getModifiableBinaryPathNames().clear();
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATHS:
				getModifiableBinaryPaths().clear();
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_RESOURCE_CONTENT:
				setModifiableResourceContent((ModifiableResourceContent)null);
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
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__INITIALIZED:
				return initialized != INITIALIZED_EDEFAULT;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATH_NAMES:
				return modifiableBinaryPathNames != null && !modifiableBinaryPathNames.isEmpty();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATHS:
				return modifiableBinaryPaths != null && !modifiableBinaryPaths.isEmpty();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_RESOURCE_CONTENT:
				return modifiableResourceContent != null;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(", version: ");
		result.append(version);
		result.append(", initialized: ");
		result.append(initialized);
		result.append(", modifiableBinaryPathNames: ");
		result.append(modifiableBinaryPathNames);
		result.append(", modifiableBinaryPaths: ");
		result.append(modifiableBinaryPaths);
		result.append(')');
		return result.toString();
	}

} // ModifiableFileBasedContentImpl
