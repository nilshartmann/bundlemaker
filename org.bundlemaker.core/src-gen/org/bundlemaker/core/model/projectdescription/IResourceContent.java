/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription;

import java.util.List;

import org.bundlemaker.core.resource.IResourceStandin;

import org.eclipse.core.runtime.IPath;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IResource Content</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.bundlemaker.core.model.projectdescription.ProjectdescriptionPackage#getIResourceContent()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IResourceContent extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.bundlemaker.core.model.projectdescription.ipath"
	 * @generated
	 */
	List<IPath> getSourcePaths();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isAnalyzeSourceResources();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.bundlemaker.core.model.projectdescription.iResourceStandin" pathDataType="org.bundlemaker.core.model.projectdescription.ipath"
	 * @generated
	 */
	IResourceStandin getResource(IPath path, ContentType type);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.bundlemaker.core.model.projectdescription.iResourceStandin"
	 * @generated
	 */
	List<IResourceStandin> getResources(ContentType type);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.bundlemaker.core.model.projectdescription.iResourceStandin" pathDataType="org.bundlemaker.core.model.projectdescription.ipath"
	 * @generated
	 */
	IResourceStandin getBinaryResource(IPath path);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.bundlemaker.core.model.projectdescription.iResourceStandin"
	 * @generated
	 */
	List<IResourceStandin> getBinaryResources();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.bundlemaker.core.model.projectdescription.iResourceStandin" pathDataType="org.bundlemaker.core.model.projectdescription.ipath"
	 * @generated
	 */
	IResourceStandin getSourceResource(IPath path);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.bundlemaker.core.model.projectdescription.iResourceStandin"
	 * @generated
	 */
	List<IResourceStandin> getSourceResources();

} // IResourceContent
