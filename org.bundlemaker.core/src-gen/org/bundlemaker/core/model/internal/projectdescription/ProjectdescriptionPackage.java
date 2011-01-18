/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.internal.projectdescription;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionFactory
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
	String eNS_URI = "http://www.bundlemaker.org/org/bundlemaker/core/model/internal/projectdescription";

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
	ProjectdescriptionPackage eINSTANCE = org.bundlemaker.core.model.internal.projectdescription.impl.ProjectdescriptionPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.internal.projectdescription.impl.EProjectDescriptionImpl <em>EProject Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.internal.projectdescription.impl.EProjectDescriptionImpl
	 * @see org.bundlemaker.core.model.internal.projectdescription.impl.ProjectdescriptionPackageImpl#getEProjectDescription()
	 * @generated
	 */
	int EPROJECT_DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Current Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPROJECT_DESCRIPTION__CURRENT_ID = 0;

	/**
	 * The feature id for the '<em><b>Jre</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPROJECT_DESCRIPTION__JRE = 1;

	/**
	 * The feature id for the '<em><b>File Based Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPROJECT_DESCRIPTION__FILE_BASED_CONTENT = 2;

	/**
	 * The number of structural features of the '<em>EProject Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPROJECT_DESCRIPTION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.internal.projectdescription.impl.EFileBasedContentImpl <em>EFile Based Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.internal.projectdescription.impl.EFileBasedContentImpl
	 * @see org.bundlemaker.core.model.internal.projectdescription.impl.ProjectdescriptionPackageImpl#getEFileBasedContent()
	 * @generated
	 */
	int EFILE_BASED_CONTENT = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFILE_BASED_CONTENT__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFILE_BASED_CONTENT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFILE_BASED_CONTENT__VERSION = 2;

	/**
	 * The feature id for the '<em><b>Binary Path Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFILE_BASED_CONTENT__BINARY_PATH_NAMES = 3;

	/**
	 * The feature id for the '<em><b>Resource Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFILE_BASED_CONTENT__RESOURCE_CONTENT = 4;

	/**
	 * The number of structural features of the '<em>EFile Based Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFILE_BASED_CONTENT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.internal.projectdescription.impl.EResourceContentImpl <em>EResource Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.internal.projectdescription.impl.EResourceContentImpl
	 * @see org.bundlemaker.core.model.internal.projectdescription.impl.ProjectdescriptionPackageImpl#getEResourceContent()
	 * @generated
	 */
	int ERESOURCE_CONTENT = 2;

	/**
	 * The feature id for the '<em><b>Analyze Source Resources</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES = 0;

	/**
	 * The feature id for the '<em><b>Source Path Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERESOURCE_CONTENT__SOURCE_PATH_NAMES = 1;

	/**
	 * The number of structural features of the '<em>EResource Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERESOURCE_CONTENT_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.internal.projectdescription.EProjectDescription <em>EProject Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EProject Description</em>'.
	 * @see org.bundlemaker.core.model.internal.projectdescription.EProjectDescription
	 * @generated
	 */
	EClass getEProjectDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.internal.projectdescription.EProjectDescription#getCurrentId <em>Current Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Current Id</em>'.
	 * @see org.bundlemaker.core.model.internal.projectdescription.EProjectDescription#getCurrentId()
	 * @see #getEProjectDescription()
	 * @generated
	 */
	EAttribute getEProjectDescription_CurrentId();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.internal.projectdescription.EProjectDescription#getJre <em>Jre</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Jre</em>'.
	 * @see org.bundlemaker.core.model.internal.projectdescription.EProjectDescription#getJre()
	 * @see #getEProjectDescription()
	 * @generated
	 */
	EAttribute getEProjectDescription_Jre();

	/**
	 * Returns the meta object for the containment reference list '{@link org.bundlemaker.core.model.internal.projectdescription.EProjectDescription#getFileBasedContent <em>File Based Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>File Based Content</em>'.
	 * @see org.bundlemaker.core.model.internal.projectdescription.EProjectDescription#getFileBasedContent()
	 * @see #getEProjectDescription()
	 * @generated
	 */
	EReference getEProjectDescription_FileBasedContent();

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent <em>EFile Based Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EFile Based Content</em>'.
	 * @see org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent
	 * @generated
	 */
	EClass getEFileBasedContent();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getId()
	 * @see #getEFileBasedContent()
	 * @generated
	 */
	EAttribute getEFileBasedContent_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getName()
	 * @see #getEFileBasedContent()
	 * @generated
	 */
	EAttribute getEFileBasedContent_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getVersion()
	 * @see #getEFileBasedContent()
	 * @generated
	 */
	EAttribute getEFileBasedContent_Version();

	/**
	 * Returns the meta object for the attribute list '{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getBinaryPathNames <em>Binary Path Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Binary Path Names</em>'.
	 * @see org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getBinaryPathNames()
	 * @see #getEFileBasedContent()
	 * @generated
	 */
	EAttribute getEFileBasedContent_BinaryPathNames();

	/**
	 * Returns the meta object for the containment reference '{@link org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getResourceContent <em>Resource Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Resource Content</em>'.
	 * @see org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent#getResourceContent()
	 * @see #getEFileBasedContent()
	 * @generated
	 */
	EReference getEFileBasedContent_ResourceContent();

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.internal.projectdescription.EResourceContent <em>EResource Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EResource Content</em>'.
	 * @see org.bundlemaker.core.model.internal.projectdescription.EResourceContent
	 * @generated
	 */
	EClass getEResourceContent();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.internal.projectdescription.EResourceContent#isAnalyzeSourceResources <em>Analyze Source Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Analyze Source Resources</em>'.
	 * @see org.bundlemaker.core.model.internal.projectdescription.EResourceContent#isAnalyzeSourceResources()
	 * @see #getEResourceContent()
	 * @generated
	 */
	EAttribute getEResourceContent_AnalyzeSourceResources();

	/**
	 * Returns the meta object for the attribute list '{@link org.bundlemaker.core.model.internal.projectdescription.EResourceContent#getSourcePathNames <em>Source Path Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Source Path Names</em>'.
	 * @see org.bundlemaker.core.model.internal.projectdescription.EResourceContent#getSourcePathNames()
	 * @see #getEResourceContent()
	 * @generated
	 */
	EAttribute getEResourceContent_SourcePathNames();

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
		 * The meta object literal for the '{@link org.bundlemaker.core.model.internal.projectdescription.impl.EProjectDescriptionImpl <em>EProject Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.internal.projectdescription.impl.EProjectDescriptionImpl
		 * @see org.bundlemaker.core.model.internal.projectdescription.impl.ProjectdescriptionPackageImpl#getEProjectDescription()
		 * @generated
		 */
		EClass EPROJECT_DESCRIPTION = eINSTANCE.getEProjectDescription();

		/**
		 * The meta object literal for the '<em><b>Current Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EPROJECT_DESCRIPTION__CURRENT_ID = eINSTANCE.getEProjectDescription_CurrentId();

		/**
		 * The meta object literal for the '<em><b>Jre</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EPROJECT_DESCRIPTION__JRE = eINSTANCE.getEProjectDescription_Jre();

		/**
		 * The meta object literal for the '<em><b>File Based Content</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EPROJECT_DESCRIPTION__FILE_BASED_CONTENT = eINSTANCE.getEProjectDescription_FileBasedContent();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.internal.projectdescription.impl.EFileBasedContentImpl <em>EFile Based Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.internal.projectdescription.impl.EFileBasedContentImpl
		 * @see org.bundlemaker.core.model.internal.projectdescription.impl.ProjectdescriptionPackageImpl#getEFileBasedContent()
		 * @generated
		 */
		EClass EFILE_BASED_CONTENT = eINSTANCE.getEFileBasedContent();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EFILE_BASED_CONTENT__ID = eINSTANCE.getEFileBasedContent_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EFILE_BASED_CONTENT__NAME = eINSTANCE.getEFileBasedContent_Name();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EFILE_BASED_CONTENT__VERSION = eINSTANCE.getEFileBasedContent_Version();

		/**
		 * The meta object literal for the '<em><b>Binary Path Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EFILE_BASED_CONTENT__BINARY_PATH_NAMES = eINSTANCE.getEFileBasedContent_BinaryPathNames();

		/**
		 * The meta object literal for the '<em><b>Resource Content</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EFILE_BASED_CONTENT__RESOURCE_CONTENT = eINSTANCE.getEFileBasedContent_ResourceContent();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.internal.projectdescription.impl.EResourceContentImpl <em>EResource Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.internal.projectdescription.impl.EResourceContentImpl
		 * @see org.bundlemaker.core.model.internal.projectdescription.impl.ProjectdescriptionPackageImpl#getEResourceContent()
		 * @generated
		 */
		EClass ERESOURCE_CONTENT = eINSTANCE.getEResourceContent();

		/**
		 * The meta object literal for the '<em><b>Analyze Source Resources</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ERESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES = eINSTANCE.getEResourceContent_AnalyzeSourceResources();

		/**
		 * The meta object literal for the '<em><b>Source Path Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ERESOURCE_CONTENT__SOURCE_PATH_NAMES = eINSTANCE.getEResourceContent_SourcePathNames();

	}

} //ProjectdescriptionPackage
