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
import org.bundlemaker.core.model.internal.projectdescription.EProjectDescription;
import org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage;

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
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EProject Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.impl.EProjectDescriptionImpl#getCurrentId <em>Current Id</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.impl.EProjectDescriptionImpl#getJre <em>Jre</em>}</li>
 *   <li>{@link org.bundlemaker.core.model.internal.projectdescription.impl.EProjectDescriptionImpl#getFileBasedContent <em>File Based Content</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EProjectDescriptionImpl extends EObjectImpl implements EProjectDescription {
	/**
	 * The default value of the '{@link #getCurrentId() <em>Current Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentId()
	 * @generated
	 * @ordered
	 */
	protected static final int CURRENT_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCurrentId() <em>Current Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentId()
	 * @generated
	 * @ordered
	 */
	protected int currentId = CURRENT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getJre() <em>Jre</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJre()
	 * @generated
	 * @ordered
	 */
	protected static final String JRE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJre() <em>Jre</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJre()
	 * @generated
	 * @ordered
	 */
	protected String jre = JRE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFileBasedContent() <em>File Based Content</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileBasedContent()
	 * @generated
	 * @ordered
	 */
	protected EList<EFileBasedContent> fileBasedContent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EProjectDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProjectdescriptionPackage.Literals.EPROJECT_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCurrentId() {
		return currentId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentId(int newCurrentId) {
		int oldCurrentId = currentId;
		currentId = newCurrentId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectdescriptionPackage.EPROJECT_DESCRIPTION__CURRENT_ID, oldCurrentId, currentId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getJre() {
		return jre;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJre(String newJre) {
		String oldJre = jre;
		jre = newJre;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectdescriptionPackage.EPROJECT_DESCRIPTION__JRE, oldJre, jre));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<EFileBasedContent> getFileBasedContent() {
		if (fileBasedContent == null) {
			fileBasedContent = new EObjectContainmentEList<EFileBasedContent>(EFileBasedContent.class, this, ProjectdescriptionPackage.EPROJECT_DESCRIPTION__FILE_BASED_CONTENT);
		}
		return fileBasedContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION__FILE_BASED_CONTENT:
				return ((InternalEList<?>)getFileBasedContent()).basicRemove(otherEnd, msgs);
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
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION__CURRENT_ID:
				return getCurrentId();
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION__JRE:
				return getJre();
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION__FILE_BASED_CONTENT:
				return getFileBasedContent();
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
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION__CURRENT_ID:
				setCurrentId((Integer)newValue);
				return;
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION__JRE:
				setJre((String)newValue);
				return;
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION__FILE_BASED_CONTENT:
				getFileBasedContent().clear();
				getFileBasedContent().addAll((Collection<? extends EFileBasedContent>)newValue);
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
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION__CURRENT_ID:
				setCurrentId(CURRENT_ID_EDEFAULT);
				return;
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION__JRE:
				setJre(JRE_EDEFAULT);
				return;
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION__FILE_BASED_CONTENT:
				getFileBasedContent().clear();
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
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION__CURRENT_ID:
				return currentId != CURRENT_ID_EDEFAULT;
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION__JRE:
				return JRE_EDEFAULT == null ? jre != null : !JRE_EDEFAULT.equals(jre);
			case ProjectdescriptionPackage.EPROJECT_DESCRIPTION__FILE_BASED_CONTENT:
				return fileBasedContent != null && !fileBasedContent.isEmpty();
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
		result.append(" (currentId: ");
		result.append(currentId);
		result.append(", jre: ");
		result.append(jre);
		result.append(')');
		return result.toString();
	}

} //EProjectDescriptionImpl
