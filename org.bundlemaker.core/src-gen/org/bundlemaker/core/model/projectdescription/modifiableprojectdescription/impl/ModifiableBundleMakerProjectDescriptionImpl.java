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
import org.bundlemaker.core.internal.model.projectdescription.ModifiableBundleMakerProjectDescriptionImplDelegate;
import org.bundlemaker.core.model.projectdescription.IFileBasedContent;

import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Modifiable Bundle Maker Project Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableBundleMakerProjectDescriptionImpl#getCurrentId <em>Current Id</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableBundleMakerProjectDescriptionImpl#isInitialized <em>Initialized</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableBundleMakerProjectDescriptionImpl#getJRE <em>JRE</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableBundleMakerProjectDescriptionImpl#getModifiableFileBasedContent <em>Modifiable File Based Content</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModifiableBundleMakerProjectDescriptionImpl extends EObjectImpl
		implements ModifiableBundleMakerProjectDescription {
	/**
	 * The default value of the '{@link #getCurrentId() <em>Current Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCurrentId()
	 * @generated
	 * @ordered
	 */
	protected static final long CURRENT_ID_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getCurrentId() <em>Current Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCurrentId()
	 * @generated
	 * @ordered
	 */
	protected long currentId = CURRENT_ID_EDEFAULT;

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
	 * The default value of the '{@link #getJRE() <em>JRE</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getJRE()
	 * @generated
	 * @ordered
	 */
	protected static final String JRE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJRE() <em>JRE</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getJRE()
	 * @generated
	 * @ordered
	 */
	protected String jre = JRE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getModifiableFileBasedContent()
	 * <em>Modifiable File Based Content</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModifiableFileBasedContent()
	 * @generated
	 * @ordered
	 */
	protected EList<ModifiableFileBasedContent> modifiableFileBasedContent;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ModifiableBundleMakerProjectDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModifiableprojectdescriptionPackage.Literals.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public long getCurrentId() {
		return currentId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentId(long newCurrentId) {
		long oldCurrentId = currentId;
		currentId = newCurrentId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__CURRENT_ID, oldCurrentId, currentId));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__INITIALIZED, oldInitialized, initialized));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getJRE() {
		return jre;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setJRE(String newJRE) {
		String oldJRE = jre;
		jre = newJRE;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__JRE, oldJRE, jre));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<ModifiableFileBasedContent> getModifiableFileBasedContent() {
		if (modifiableFileBasedContent == null) {
			modifiableFileBasedContent = new EObjectContainmentEList<ModifiableFileBasedContent>(ModifiableFileBasedContent.class, this, ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__MODIFIABLE_FILE_BASED_CONTENT);
		}
		return modifiableFileBasedContent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isValid() {
		return ModifiableBundleMakerProjectDescriptionImplDelegate
				.isValid(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void initialize(BundleMakerProject bundlemakerProject) {
		ModifiableBundleMakerProjectDescriptionImplDelegate.initialize(this, bundlemakerProject);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ModifiableFileBasedContent addResourceContent(String binaryRoot)
			throws CoreException {
		return ModifiableBundleMakerProjectDescriptionImplDelegate
				.addResourceContent(this, binaryRoot);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ModifiableFileBasedContent addResourceContent(String binaryRoot,
			String sourceRoot) throws CoreException {
		return ModifiableBundleMakerProjectDescriptionImplDelegate
				.addResourceContent(this, binaryRoot, sourceRoot);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ModifiableFileBasedContent addResourceContent(String name,
			String version, String binaryRoot) throws CoreException {
		return ModifiableBundleMakerProjectDescriptionImplDelegate
				.addResourceContent(this, name, version, binaryRoot);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ModifiableFileBasedContent addResourceContent(String name,
			String version, String binaryRoot, String sourceRoot)
			throws CoreException {
		return ModifiableBundleMakerProjectDescriptionImplDelegate
				.addResourceContent(this, name, version, binaryRoot, sourceRoot);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ModifiableFileBasedContent addResourceContent(String name,
			String version, List<String> binaryRoot, List<String> sourceRoot)
			throws CoreException {
		return ModifiableBundleMakerProjectDescriptionImplDelegate
				.addResourceContent(this, name, version, binaryRoot, sourceRoot);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ModifiableFileBasedContent addTypeContent(String binaryRoot)
			throws CoreException {
		return ModifiableBundleMakerProjectDescriptionImplDelegate
				.addTypeContent(this, binaryRoot);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ModifiableFileBasedContent addTypeContent(String name,
			String version, String binaryRoot) throws CoreException {
		return ModifiableBundleMakerProjectDescriptionImplDelegate
				.addTypeContent(this, binaryRoot);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ModifiableFileBasedContent addTypeContent(String name,
			String version, List<String> binaryRoot) throws CoreException {
		return ModifiableBundleMakerProjectDescriptionImplDelegate
				.addTypeContent(this, name, version, binaryRoot);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public List<IFileBasedContent> getFileBasedContent() {
		return ModifiableBundleMakerProjectDescriptionImplDelegate
				.getFileBasedContent(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public IFileBasedContent getFileBasedContent(String id) {
		return ModifiableBundleMakerProjectDescriptionImplDelegate
				.getFileBasedContent(this, id);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__MODIFIABLE_FILE_BASED_CONTENT:
				return ((InternalEList<?>)getModifiableFileBasedContent()).basicRemove(otherEnd, msgs);
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
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__CURRENT_ID:
				return getCurrentId();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__INITIALIZED:
				return isInitialized();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__JRE:
				return getJRE();
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__MODIFIABLE_FILE_BASED_CONTENT:
				return getModifiableFileBasedContent();
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
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__CURRENT_ID:
				setCurrentId((Long)newValue);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__INITIALIZED:
				setInitialized((Boolean)newValue);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__JRE:
				setJRE((String)newValue);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__MODIFIABLE_FILE_BASED_CONTENT:
				getModifiableFileBasedContent().clear();
				getModifiableFileBasedContent().addAll((Collection<? extends ModifiableFileBasedContent>)newValue);
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
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__CURRENT_ID:
				setCurrentId(CURRENT_ID_EDEFAULT);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__INITIALIZED:
				setInitialized(INITIALIZED_EDEFAULT);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__JRE:
				setJRE(JRE_EDEFAULT);
				return;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__MODIFIABLE_FILE_BASED_CONTENT:
				getModifiableFileBasedContent().clear();
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
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__CURRENT_ID:
				return currentId != CURRENT_ID_EDEFAULT;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__INITIALIZED:
				return initialized != INITIALIZED_EDEFAULT;
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__JRE:
				return JRE_EDEFAULT == null ? jre != null : !JRE_EDEFAULT.equals(jre);
			case ModifiableprojectdescriptionPackage.MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__MODIFIABLE_FILE_BASED_CONTENT:
				return modifiableFileBasedContent != null && !modifiableFileBasedContent.isEmpty();
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
		result.append(" (currentId: ");
		result.append(currentId);
		result.append(", initialized: ");
		result.append(initialized);
		result.append(", JRE: ");
		result.append(jre);
		result.append(')');
		return result.toString();
	}

} // ModifiableBundleMakerProjectDescriptionImpl
