/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription.modifiableprojectdescription;

import org.bundlemaker.core.model.projectdescription.ProjectdescriptionPackage;

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
 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionFactory
 * @model kind="package"
 * @generated
 */
public interface ModifiableprojectdescriptionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "modifiableprojectdescription";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.bundlemaker.org/org/bundlemaker/core/model/projectdescription/modifiableprojectdescription";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "modifiableprojectdescription";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModifiableprojectdescriptionPackage eINSTANCE = org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableprojectdescriptionPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableBundleMakerProjectDescriptionImpl <em>Modifiable Bundle Maker Project Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableBundleMakerProjectDescriptionImpl
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableprojectdescriptionPackageImpl#getModifiableBundleMakerProjectDescription()
	 * @generated
	 */
	int MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Current Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__CURRENT_ID = ProjectdescriptionPackage.IBUNDLE_MAKER_PROJECT_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initialized</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__INITIALIZED = ProjectdescriptionPackage.IBUNDLE_MAKER_PROJECT_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>JRE</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__JRE = ProjectdescriptionPackage.IBUNDLE_MAKER_PROJECT_DESCRIPTION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Modifiable File Based Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__MODIFIABLE_FILE_BASED_CONTENT = ProjectdescriptionPackage.IBUNDLE_MAKER_PROJECT_DESCRIPTION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Modifiable Bundle Maker Project Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION_FEATURE_COUNT = ProjectdescriptionPackage.IBUNDLE_MAKER_PROJECT_DESCRIPTION_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableFileBasedContentImpl <em>Modifiable File Based Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableFileBasedContentImpl
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableprojectdescriptionPackageImpl#getModifiableFileBasedContent()
	 * @generated
	 */
	int MODIFIABLE_FILE_BASED_CONTENT = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_FILE_BASED_CONTENT__ID = ProjectdescriptionPackage.IFILE_BASED_CONTENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_FILE_BASED_CONTENT__NAME = ProjectdescriptionPackage.IFILE_BASED_CONTENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_FILE_BASED_CONTENT__VERSION = ProjectdescriptionPackage.IFILE_BASED_CONTENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Initialized</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_FILE_BASED_CONTENT__INITIALIZED = ProjectdescriptionPackage.IFILE_BASED_CONTENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Modifiable Binary Path Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATH_NAMES = ProjectdescriptionPackage.IFILE_BASED_CONTENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Modifiable Binary Paths</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATHS = ProjectdescriptionPackage.IFILE_BASED_CONTENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Modifiable Resource Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_RESOURCE_CONTENT = ProjectdescriptionPackage.IFILE_BASED_CONTENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Modifiable File Based Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_FILE_BASED_CONTENT_FEATURE_COUNT = ProjectdescriptionPackage.IFILE_BASED_CONTENT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableResourceContentImpl <em>Modifiable Resource Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableResourceContentImpl
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableprojectdescriptionPackageImpl#getModifiableResourceContent()
	 * @generated
	 */
	int MODIFIABLE_RESOURCE_CONTENT = 2;

	/**
	 * The feature id for the '<em><b>Analyze Source Resources</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_RESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES = ProjectdescriptionPackage.IRESOURCE_CONTENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Modifiable Source Path Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATH_NAMES = ProjectdescriptionPackage.IRESOURCE_CONTENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Modifiable Source Paths</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATHS = ProjectdescriptionPackage.IRESOURCE_CONTENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Modifiable Source Resources</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_RESOURCES = ProjectdescriptionPackage.IRESOURCE_CONTENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Modifiable Binary Resources</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_BINARY_RESOURCES = ProjectdescriptionPackage.IRESOURCE_CONTENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Modifiable Resource Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_RESOURCE_CONTENT_FEATURE_COUNT = ProjectdescriptionPackage.IRESOURCE_CONTENT_FEATURE_COUNT + 5;


	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription <em>Modifiable Bundle Maker Project Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Modifiable Bundle Maker Project Description</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription
	 * @generated
	 */
	EClass getModifiableBundleMakerProjectDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#getCurrentId <em>Current Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Current Id</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#getCurrentId()
	 * @see #getModifiableBundleMakerProjectDescription()
	 * @generated
	 */
	EAttribute getModifiableBundleMakerProjectDescription_CurrentId();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#isInitialized <em>Initialized</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initialized</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#isInitialized()
	 * @see #getModifiableBundleMakerProjectDescription()
	 * @generated
	 */
	EAttribute getModifiableBundleMakerProjectDescription_Initialized();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#getJRE <em>JRE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>JRE</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#getJRE()
	 * @see #getModifiableBundleMakerProjectDescription()
	 * @generated
	 */
	EAttribute getModifiableBundleMakerProjectDescription_JRE();

	/**
	 * Returns the meta object for the containment reference list '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#getModifiableFileBasedContent <em>Modifiable File Based Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Modifiable File Based Content</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription#getModifiableFileBasedContent()
	 * @see #getModifiableBundleMakerProjectDescription()
	 * @generated
	 */
	EReference getModifiableBundleMakerProjectDescription_ModifiableFileBasedContent();

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent <em>Modifiable File Based Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Modifiable File Based Content</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent
	 * @generated
	 */
	EClass getModifiableFileBasedContent();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getId()
	 * @see #getModifiableFileBasedContent()
	 * @generated
	 */
	EAttribute getModifiableFileBasedContent_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getName()
	 * @see #getModifiableFileBasedContent()
	 * @generated
	 */
	EAttribute getModifiableFileBasedContent_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getVersion()
	 * @see #getModifiableFileBasedContent()
	 * @generated
	 */
	EAttribute getModifiableFileBasedContent_Version();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#isInitialized <em>Initialized</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initialized</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#isInitialized()
	 * @see #getModifiableFileBasedContent()
	 * @generated
	 */
	EAttribute getModifiableFileBasedContent_Initialized();

	/**
	 * Returns the meta object for the attribute list '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getModifiableBinaryPathNames <em>Modifiable Binary Path Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Modifiable Binary Path Names</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getModifiableBinaryPathNames()
	 * @see #getModifiableFileBasedContent()
	 * @generated
	 */
	EAttribute getModifiableFileBasedContent_ModifiableBinaryPathNames();

	/**
	 * Returns the meta object for the attribute list '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getModifiableBinaryPaths <em>Modifiable Binary Paths</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Modifiable Binary Paths</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getModifiableBinaryPaths()
	 * @see #getModifiableFileBasedContent()
	 * @generated
	 */
	EAttribute getModifiableFileBasedContent_ModifiableBinaryPaths();

	/**
	 * Returns the meta object for the containment reference '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getModifiableResourceContent <em>Modifiable Resource Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Modifiable Resource Content</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent#getModifiableResourceContent()
	 * @see #getModifiableFileBasedContent()
	 * @generated
	 */
	EReference getModifiableFileBasedContent_ModifiableResourceContent();

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent <em>Modifiable Resource Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Modifiable Resource Content</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent
	 * @generated
	 */
	EClass getModifiableResourceContent();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#isAnalyzeSourceResources <em>Analyze Source Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Analyze Source Resources</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#isAnalyzeSourceResources()
	 * @see #getModifiableResourceContent()
	 * @generated
	 */
	EAttribute getModifiableResourceContent_AnalyzeSourceResources();

	/**
	 * Returns the meta object for the attribute list '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#getModifiableSourcePathNames <em>Modifiable Source Path Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Modifiable Source Path Names</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#getModifiableSourcePathNames()
	 * @see #getModifiableResourceContent()
	 * @generated
	 */
	EAttribute getModifiableResourceContent_ModifiableSourcePathNames();

	/**
	 * Returns the meta object for the attribute list '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#getModifiableSourcePaths <em>Modifiable Source Paths</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Modifiable Source Paths</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#getModifiableSourcePaths()
	 * @see #getModifiableResourceContent()
	 * @generated
	 */
	EAttribute getModifiableResourceContent_ModifiableSourcePaths();

	/**
	 * Returns the meta object for the attribute list '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#getModifiableSourceResources <em>Modifiable Source Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Modifiable Source Resources</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#getModifiableSourceResources()
	 * @see #getModifiableResourceContent()
	 * @generated
	 */
	EAttribute getModifiableResourceContent_ModifiableSourceResources();

	/**
	 * Returns the meta object for the attribute list '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#getModifiableBinaryResources <em>Modifiable Binary Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Modifiable Binary Resources</em>'.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent#getModifiableBinaryResources()
	 * @see #getModifiableResourceContent()
	 * @generated
	 */
	EAttribute getModifiableResourceContent_ModifiableBinaryResources();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModifiableprojectdescriptionFactory getModifiableprojectdescriptionFactory();

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
		 * The meta object literal for the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableBundleMakerProjectDescriptionImpl <em>Modifiable Bundle Maker Project Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableBundleMakerProjectDescriptionImpl
		 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableprojectdescriptionPackageImpl#getModifiableBundleMakerProjectDescription()
		 * @generated
		 */
		EClass MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION = eINSTANCE.getModifiableBundleMakerProjectDescription();

		/**
		 * The meta object literal for the '<em><b>Current Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__CURRENT_ID = eINSTANCE.getModifiableBundleMakerProjectDescription_CurrentId();

		/**
		 * The meta object literal for the '<em><b>Initialized</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__INITIALIZED = eINSTANCE.getModifiableBundleMakerProjectDescription_Initialized();

		/**
		 * The meta object literal for the '<em><b>JRE</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__JRE = eINSTANCE.getModifiableBundleMakerProjectDescription_JRE();

		/**
		 * The meta object literal for the '<em><b>Modifiable File Based Content</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__MODIFIABLE_FILE_BASED_CONTENT = eINSTANCE.getModifiableBundleMakerProjectDescription_ModifiableFileBasedContent();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableFileBasedContentImpl <em>Modifiable File Based Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableFileBasedContentImpl
		 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableprojectdescriptionPackageImpl#getModifiableFileBasedContent()
		 * @generated
		 */
		EClass MODIFIABLE_FILE_BASED_CONTENT = eINSTANCE.getModifiableFileBasedContent();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_FILE_BASED_CONTENT__ID = eINSTANCE.getModifiableFileBasedContent_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_FILE_BASED_CONTENT__NAME = eINSTANCE.getModifiableFileBasedContent_Name();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_FILE_BASED_CONTENT__VERSION = eINSTANCE.getModifiableFileBasedContent_Version();

		/**
		 * The meta object literal for the '<em><b>Initialized</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_FILE_BASED_CONTENT__INITIALIZED = eINSTANCE.getModifiableFileBasedContent_Initialized();

		/**
		 * The meta object literal for the '<em><b>Modifiable Binary Path Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATH_NAMES = eINSTANCE.getModifiableFileBasedContent_ModifiableBinaryPathNames();

		/**
		 * The meta object literal for the '<em><b>Modifiable Binary Paths</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATHS = eINSTANCE.getModifiableFileBasedContent_ModifiableBinaryPaths();

		/**
		 * The meta object literal for the '<em><b>Modifiable Resource Content</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_RESOURCE_CONTENT = eINSTANCE.getModifiableFileBasedContent_ModifiableResourceContent();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableResourceContentImpl <em>Modifiable Resource Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableResourceContentImpl
		 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableprojectdescriptionPackageImpl#getModifiableResourceContent()
		 * @generated
		 */
		EClass MODIFIABLE_RESOURCE_CONTENT = eINSTANCE.getModifiableResourceContent();

		/**
		 * The meta object literal for the '<em><b>Analyze Source Resources</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_RESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES = eINSTANCE.getModifiableResourceContent_AnalyzeSourceResources();

		/**
		 * The meta object literal for the '<em><b>Modifiable Source Path Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATH_NAMES = eINSTANCE.getModifiableResourceContent_ModifiableSourcePathNames();

		/**
		 * The meta object literal for the '<em><b>Modifiable Source Paths</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATHS = eINSTANCE.getModifiableResourceContent_ModifiableSourcePaths();

		/**
		 * The meta object literal for the '<em><b>Modifiable Source Resources</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_RESOURCES = eINSTANCE.getModifiableResourceContent_ModifiableSourceResources();

		/**
		 * The meta object literal for the '<em><b>Modifiable Binary Resources</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_BINARY_RESOURCES = eINSTANCE.getModifiableResourceContent_ModifiableBinaryResources();

	}

} //ModifiableprojectdescriptionPackage
