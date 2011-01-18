/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.internal.projectdescription.impl;

import org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent;
import org.bundlemaker.core.model.internal.projectdescription.EProjectDescription;
import org.bundlemaker.core.model.internal.projectdescription.EResourceContent;
import org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionFactory;
import org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProjectdescriptionPackageImpl extends EPackageImpl implements ProjectdescriptionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eProjectDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eFileBasedContentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eResourceContentEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ProjectdescriptionPackageImpl() {
		super(eNS_URI, ProjectdescriptionFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ProjectdescriptionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ProjectdescriptionPackage init() {
		if (isInited) return (ProjectdescriptionPackage)EPackage.Registry.INSTANCE.getEPackage(ProjectdescriptionPackage.eNS_URI);

		// Obtain or create and register package
		ProjectdescriptionPackageImpl theProjectdescriptionPackage = (ProjectdescriptionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ProjectdescriptionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ProjectdescriptionPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theProjectdescriptionPackage.createPackageContents();

		// Initialize created meta-data
		theProjectdescriptionPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theProjectdescriptionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ProjectdescriptionPackage.eNS_URI, theProjectdescriptionPackage);
		return theProjectdescriptionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEProjectDescription() {
		return eProjectDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEProjectDescription_CurrentId() {
		return (EAttribute)eProjectDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEProjectDescription_Jre() {
		return (EAttribute)eProjectDescriptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEProjectDescription_FileBasedContent() {
		return (EReference)eProjectDescriptionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFileBasedContent() {
		return eFileBasedContentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEFileBasedContent_Id() {
		return (EAttribute)eFileBasedContentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEFileBasedContent_Name() {
		return (EAttribute)eFileBasedContentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEFileBasedContent_Version() {
		return (EAttribute)eFileBasedContentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEFileBasedContent_BinaryPathNames() {
		return (EAttribute)eFileBasedContentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEFileBasedContent_ResourceContent() {
		return (EReference)eFileBasedContentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEResourceContent() {
		return eResourceContentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEResourceContent_AnalyzeSourceResources() {
		return (EAttribute)eResourceContentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEResourceContent_SourcePathNames() {
		return (EAttribute)eResourceContentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectdescriptionFactory getProjectdescriptionFactory() {
		return (ProjectdescriptionFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		eProjectDescriptionEClass = createEClass(EPROJECT_DESCRIPTION);
		createEAttribute(eProjectDescriptionEClass, EPROJECT_DESCRIPTION__CURRENT_ID);
		createEAttribute(eProjectDescriptionEClass, EPROJECT_DESCRIPTION__JRE);
		createEReference(eProjectDescriptionEClass, EPROJECT_DESCRIPTION__FILE_BASED_CONTENT);

		eFileBasedContentEClass = createEClass(EFILE_BASED_CONTENT);
		createEAttribute(eFileBasedContentEClass, EFILE_BASED_CONTENT__ID);
		createEAttribute(eFileBasedContentEClass, EFILE_BASED_CONTENT__NAME);
		createEAttribute(eFileBasedContentEClass, EFILE_BASED_CONTENT__VERSION);
		createEAttribute(eFileBasedContentEClass, EFILE_BASED_CONTENT__BINARY_PATH_NAMES);
		createEReference(eFileBasedContentEClass, EFILE_BASED_CONTENT__RESOURCE_CONTENT);

		eResourceContentEClass = createEClass(ERESOURCE_CONTENT);
		createEAttribute(eResourceContentEClass, ERESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES);
		createEAttribute(eResourceContentEClass, ERESOURCE_CONTENT__SOURCE_PATH_NAMES);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(eProjectDescriptionEClass, EProjectDescription.class, "EProjectDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEProjectDescription_CurrentId(), ecorePackage.getEInt(), "currentId", null, 0, 1, EProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEProjectDescription_Jre(), ecorePackage.getEString(), "jre", null, 0, 1, EProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEProjectDescription_FileBasedContent(), this.getEFileBasedContent(), null, "fileBasedContent", null, 0, -1, EProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eFileBasedContentEClass, EFileBasedContent.class, "EFileBasedContent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEFileBasedContent_Id(), ecorePackage.getEString(), "id", null, 0, 1, EFileBasedContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEFileBasedContent_Name(), ecorePackage.getEString(), "name", null, 0, 1, EFileBasedContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEFileBasedContent_Version(), ecorePackage.getEString(), "version", null, 0, 1, EFileBasedContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEFileBasedContent_BinaryPathNames(), ecorePackage.getEString(), "binaryPathNames", null, 0, -1, EFileBasedContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEFileBasedContent_ResourceContent(), this.getEResourceContent(), null, "resourceContent", null, 0, 1, EFileBasedContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eResourceContentEClass, EResourceContent.class, "EResourceContent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEResourceContent_AnalyzeSourceResources(), ecorePackage.getEBoolean(), "analyzeSourceResources", null, 0, 1, EResourceContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEResourceContent_SourcePathNames(), ecorePackage.getEString(), "sourcePathNames", null, 0, -1, EResourceContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //ProjectdescriptionPackageImpl
