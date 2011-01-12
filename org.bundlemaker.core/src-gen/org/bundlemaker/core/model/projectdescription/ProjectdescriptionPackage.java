/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.bundlemaker.core.model.projectdescription.ProjectdescriptionFactory
 * @model kind="package"
 * @generated
 */
public interface ProjectdescriptionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "projectdescription";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.bundlemaker.org/org/bundlemaker/core/model/projectdescription";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "projectdescription";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProjectdescriptionPackage eINSTANCE = org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.projectdescription.IFileBasedContent <em>IFile Based Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.projectdescription.IFileBasedContent
	 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getIFileBasedContent()
	 * @generated
	 */
	int IFILE_BASED_CONTENT = 0;

	/**
	 * The number of structural features of the '<em>IFile Based Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IFILE_BASED_CONTENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.projectdescription.IResourceContent <em>IResource Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.projectdescription.IResourceContent
	 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getIResourceContent()
	 * @generated
	 */
	int IRESOURCE_CONTENT = 1;

	/**
	 * The number of structural features of the '<em>IResource Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IRESOURCE_CONTENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.projectdescription.IBundleMakerProjectDescription <em>IBundle Maker Project Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.projectdescription.IBundleMakerProjectDescription
	 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getIBundleMakerProjectDescription()
	 * @generated
	 */
	int IBUNDLE_MAKER_PROJECT_DESCRIPTION = 2;

	/**
	 * The number of structural features of the '<em>IBundle Maker Project Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBUNDLE_MAKER_PROJECT_DESCRIPTION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.projectdescription.ContentType <em>Content Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.projectdescription.ContentType
	 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getContentType()
	 * @generated
	 */
	int CONTENT_TYPE = 3;

	/**
	 * The meta object id for the '<em>ipath</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.core.runtime.IPath
	 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getipath()
	 * @generated
	 */
	int IPATH = 4;

	/**
	 * The meta object id for the '<em>coreexception</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.core.runtime.CoreException
	 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getcoreexception()
	 * @generated
	 */
	int COREEXCEPTION = 5;

	/**
	 * The meta object id for the '<em>iResource Standin</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.resource.IResourceStandin
	 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getiResourceStandin()
	 * @generated
	 */
	int IRESOURCE_STANDIN = 6;

	/**
	 * The meta object id for the '<em>resource Standin</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.resource.ResourceStandin
	 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getresourceStandin()
	 * @generated
	 */
	int RESOURCE_STANDIN = 7;


	/**
	 * The meta object id for the '<em>bundlemakerproject</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.internal.BundleMakerProject
	 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getbundlemakerproject()
	 * @generated
	 */
	int BUNDLEMAKERPROJECT = 8;


	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.projectdescription.IFileBasedContent <em>IFile Based Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IFile Based Content</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.IFileBasedContent
	 * @generated
	 */
	EClass getIFileBasedContent();

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.projectdescription.IResourceContent <em>IResource Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IResource Content</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.IResourceContent
	 * @generated
	 */
	EClass getIResourceContent();

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.projectdescription.IBundleMakerProjectDescription <em>IBundle Maker Project Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBundle Maker Project Description</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.IBundleMakerProjectDescription
	 * @generated
	 */
	EClass getIBundleMakerProjectDescription();

	/**
	 * Returns the meta object for enum '{@link org.bundlemaker.core.model.projectdescription.ContentType <em>Content Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Content Type</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.ContentType
	 * @generated
	 */
	EEnum getContentType();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.core.runtime.IPath <em>ipath</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ipath</em>'.
	 * @see org.eclipse.core.runtime.IPath
	 * @model instanceClass="org.eclipse.core.runtime.IPath"
	 * @generated
	 */
	EDataType getipath();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.core.runtime.CoreException <em>coreexception</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>coreexception</em>'.
	 * @see org.eclipse.core.runtime.CoreException
	 * @model instanceClass="org.eclipse.core.runtime.CoreException"
	 * @generated
	 */
	EDataType getcoreexception();

	/**
	 * Returns the meta object for data type '{@link org.bundlemaker.core.resource.IResourceStandin <em>iResource Standin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>iResource Standin</em>'.
	 * @see org.bundlemaker.core.resource.IResourceStandin
	 * @model instanceClass="org.bundlemaker.core.resource.IResourceStandin"
	 * @generated
	 */
	EDataType getiResourceStandin();

	/**
	 * Returns the meta object for data type '{@link org.bundlemaker.core.resource.ResourceStandin <em>resource Standin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>resource Standin</em>'.
	 * @see org.bundlemaker.core.resource.ResourceStandin
	 * @model instanceClass="org.bundlemaker.core.resource.ResourceStandin"
	 * @generated
	 */
	EDataType getresourceStandin();

	/**
	 * Returns the meta object for data type '{@link org.bundlemaker.core.internal.BundleMakerProject <em>bundlemakerproject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>bundlemakerproject</em>'.
	 * @see org.bundlemaker.core.internal.BundleMakerProject
	 * @model instanceClass="org.bundlemaker.core.internal.BundleMakerProject"
	 * @generated
	 */
	EDataType getbundlemakerproject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ProjectdescriptionFactory getProjectdescriptionFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.projectdescription.IFileBasedContent <em>IFile Based Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.projectdescription.IFileBasedContent
		 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getIFileBasedContent()
		 * @generated
		 */
		EClass IFILE_BASED_CONTENT = eINSTANCE.getIFileBasedContent();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.projectdescription.IResourceContent <em>IResource Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.projectdescription.IResourceContent
		 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getIResourceContent()
		 * @generated
		 */
		EClass IRESOURCE_CONTENT = eINSTANCE.getIResourceContent();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.projectdescription.IBundleMakerProjectDescription <em>IBundle Maker Project Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.projectdescription.IBundleMakerProjectDescription
		 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getIBundleMakerProjectDescription()
		 * @generated
		 */
		EClass IBUNDLE_MAKER_PROJECT_DESCRIPTION = eINSTANCE.getIBundleMakerProjectDescription();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.projectdescription.ContentType <em>Content Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.projectdescription.ContentType
		 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getContentType()
		 * @generated
		 */
		EEnum CONTENT_TYPE = eINSTANCE.getContentType();

		/**
		 * The meta object literal for the '<em>ipath</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.core.runtime.IPath
		 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getipath()
		 * @generated
		 */
		EDataType IPATH = eINSTANCE.getipath();

		/**
		 * The meta object literal for the '<em>coreexception</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.core.runtime.CoreException
		 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getcoreexception()
		 * @generated
		 */
		EDataType COREEXCEPTION = eINSTANCE.getcoreexception();

		/**
		 * The meta object literal for the '<em>iResource Standin</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.resource.IResourceStandin
		 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getiResourceStandin()
		 * @generated
		 */
		EDataType IRESOURCE_STANDIN = eINSTANCE.getiResourceStandin();

		/**
		 * The meta object literal for the '<em>resource Standin</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.resource.ResourceStandin
		 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getresourceStandin()
		 * @generated
		 */
		EDataType RESOURCE_STANDIN = eINSTANCE.getresourceStandin();

		/**
		 * The meta object literal for the '<em>bundlemakerproject</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.internal.BundleMakerProject
		 * @see org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl#getbundlemakerproject()
		 * @generated
		 */
		EDataType BUNDLEMAKERPROJECT = eINSTANCE.getbundlemakerproject();

	}

} //ProjectdescriptionPackage
