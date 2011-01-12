/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl;

import org.bundlemaker.core.model.module.ModulePackage;

import org.bundlemaker.core.model.module.impl.ModulePackageImpl;

import org.bundlemaker.core.model.projectdescription.ProjectdescriptionPackage;

import org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl;

import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionFactory;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage;

import org.bundlemaker.core.model.transformation.TransformationPackage;

import org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModifiableprojectdescriptionPackageImpl extends EPackageImpl implements ModifiableprojectdescriptionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modifiableBundleMakerProjectDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modifiableFileBasedContentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modifiableResourceContentEClass = null;

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
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModifiableprojectdescriptionPackageImpl() {
		super(eNS_URI, ModifiableprojectdescriptionFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ModifiableprojectdescriptionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModifiableprojectdescriptionPackage init() {
		if (isInited) return (ModifiableprojectdescriptionPackage)EPackage.Registry.INSTANCE.getEPackage(ModifiableprojectdescriptionPackage.eNS_URI);

		// Obtain or create and register package
		ModifiableprojectdescriptionPackageImpl theModifiableprojectdescriptionPackage = (ModifiableprojectdescriptionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ModifiableprojectdescriptionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ModifiableprojectdescriptionPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ProjectdescriptionPackageImpl theProjectdescriptionPackage = (ProjectdescriptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProjectdescriptionPackage.eNS_URI) instanceof ProjectdescriptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProjectdescriptionPackage.eNS_URI) : ProjectdescriptionPackage.eINSTANCE);
		ModulePackageImpl theModulePackage = (ModulePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModulePackage.eNS_URI) instanceof ModulePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModulePackage.eNS_URI) : ModulePackage.eINSTANCE);
		TransformationPackageImpl theTransformationPackage = (TransformationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TransformationPackage.eNS_URI) instanceof TransformationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TransformationPackage.eNS_URI) : TransformationPackage.eINSTANCE);

		// Create package meta-data objects
		theModifiableprojectdescriptionPackage.createPackageContents();
		theProjectdescriptionPackage.createPackageContents();
		theModulePackage.createPackageContents();
		theTransformationPackage.createPackageContents();

		// Initialize created meta-data
		theModifiableprojectdescriptionPackage.initializePackageContents();
		theProjectdescriptionPackage.initializePackageContents();
		theModulePackage.initializePackageContents();
		theTransformationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModifiableprojectdescriptionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModifiableprojectdescriptionPackage.eNS_URI, theModifiableprojectdescriptionPackage);
		return theModifiableprojectdescriptionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModifiableBundleMakerProjectDescription() {
		return modifiableBundleMakerProjectDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableBundleMakerProjectDescription_CurrentId() {
		return (EAttribute)modifiableBundleMakerProjectDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableBundleMakerProjectDescription_Initialized() {
		return (EAttribute)modifiableBundleMakerProjectDescriptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableBundleMakerProjectDescription_JRE() {
		return (EAttribute)modifiableBundleMakerProjectDescriptionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModifiableBundleMakerProjectDescription_ModifiableFileBasedContent() {
		return (EReference)modifiableBundleMakerProjectDescriptionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModifiableFileBasedContent() {
		return modifiableFileBasedContentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableFileBasedContent_Id() {
		return (EAttribute)modifiableFileBasedContentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableFileBasedContent_Name() {
		return (EAttribute)modifiableFileBasedContentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableFileBasedContent_Version() {
		return (EAttribute)modifiableFileBasedContentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableFileBasedContent_Initialized() {
		return (EAttribute)modifiableFileBasedContentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableFileBasedContent_ModifiableBinaryPathNames() {
		return (EAttribute)modifiableFileBasedContentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableFileBasedContent_ModifiableBinaryPaths() {
		return (EAttribute)modifiableFileBasedContentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModifiableFileBasedContent_ModifiableResourceContent() {
		return (EReference)modifiableFileBasedContentEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModifiableResourceContent() {
		return modifiableResourceContentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableResourceContent_AnalyzeSourceResources() {
		return (EAttribute)modifiableResourceContentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableResourceContent_ModifiableSourcePathNames() {
		return (EAttribute)modifiableResourceContentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableResourceContent_ModifiableSourcePaths() {
		return (EAttribute)modifiableResourceContentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableResourceContent_ModifiableSourceResources() {
		return (EAttribute)modifiableResourceContentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableResourceContent_ModifiableBinaryResources() {
		return (EAttribute)modifiableResourceContentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModifiableprojectdescriptionFactory getModifiableprojectdescriptionFactory() {
		return (ModifiableprojectdescriptionFactory)getEFactoryInstance();
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
		modifiableBundleMakerProjectDescriptionEClass = createEClass(MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION);
		createEAttribute(modifiableBundleMakerProjectDescriptionEClass, MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__CURRENT_ID);
		createEAttribute(modifiableBundleMakerProjectDescriptionEClass, MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__INITIALIZED);
		createEAttribute(modifiableBundleMakerProjectDescriptionEClass, MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__JRE);
		createEReference(modifiableBundleMakerProjectDescriptionEClass, MODIFIABLE_BUNDLE_MAKER_PROJECT_DESCRIPTION__MODIFIABLE_FILE_BASED_CONTENT);

		modifiableFileBasedContentEClass = createEClass(MODIFIABLE_FILE_BASED_CONTENT);
		createEAttribute(modifiableFileBasedContentEClass, MODIFIABLE_FILE_BASED_CONTENT__ID);
		createEAttribute(modifiableFileBasedContentEClass, MODIFIABLE_FILE_BASED_CONTENT__NAME);
		createEAttribute(modifiableFileBasedContentEClass, MODIFIABLE_FILE_BASED_CONTENT__VERSION);
		createEAttribute(modifiableFileBasedContentEClass, MODIFIABLE_FILE_BASED_CONTENT__INITIALIZED);
		createEAttribute(modifiableFileBasedContentEClass, MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATH_NAMES);
		createEAttribute(modifiableFileBasedContentEClass, MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_BINARY_PATHS);
		createEReference(modifiableFileBasedContentEClass, MODIFIABLE_FILE_BASED_CONTENT__MODIFIABLE_RESOURCE_CONTENT);

		modifiableResourceContentEClass = createEClass(MODIFIABLE_RESOURCE_CONTENT);
		createEAttribute(modifiableResourceContentEClass, MODIFIABLE_RESOURCE_CONTENT__ANALYZE_SOURCE_RESOURCES);
		createEAttribute(modifiableResourceContentEClass, MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATH_NAMES);
		createEAttribute(modifiableResourceContentEClass, MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_PATHS);
		createEAttribute(modifiableResourceContentEClass, MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_SOURCE_RESOURCES);
		createEAttribute(modifiableResourceContentEClass, MODIFIABLE_RESOURCE_CONTENT__MODIFIABLE_BINARY_RESOURCES);
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

		// Obtain other dependent packages
		ProjectdescriptionPackage theProjectdescriptionPackage = (ProjectdescriptionPackage)EPackage.Registry.INSTANCE.getEPackage(ProjectdescriptionPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		modifiableBundleMakerProjectDescriptionEClass.getESuperTypes().add(theProjectdescriptionPackage.getIBundleMakerProjectDescription());
		modifiableFileBasedContentEClass.getESuperTypes().add(theProjectdescriptionPackage.getIFileBasedContent());
		modifiableResourceContentEClass.getESuperTypes().add(theProjectdescriptionPackage.getIResourceContent());

		// Initialize classes and features; add operations and parameters
		initEClass(modifiableBundleMakerProjectDescriptionEClass, ModifiableBundleMakerProjectDescription.class, "ModifiableBundleMakerProjectDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModifiableBundleMakerProjectDescription_CurrentId(), ecorePackage.getELong(), "currentId", "0", 1, 1, ModifiableBundleMakerProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModifiableBundleMakerProjectDescription_Initialized(), ecorePackage.getEBoolean(), "initialized", null, 0, 1, ModifiableBundleMakerProjectDescription.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModifiableBundleMakerProjectDescription_JRE(), ecorePackage.getEString(), "JRE", null, 1, 1, ModifiableBundleMakerProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModifiableBundleMakerProjectDescription_ModifiableFileBasedContent(), this.getModifiableFileBasedContent(), null, "modifiableFileBasedContent", null, 0, -1, ModifiableBundleMakerProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(modifiableBundleMakerProjectDescriptionEClass, ecorePackage.getEBoolean(), "isValid", 0, 1, IS_UNIQUE, IS_ORDERED);

		EOperation op = addEOperation(modifiableBundleMakerProjectDescriptionEClass, null, "initialize", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theProjectdescriptionPackage.getbundlemakerproject(), "bundlemakerProject", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(modifiableBundleMakerProjectDescriptionEClass, this.getModifiableFileBasedContent(), "addResourceContent", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "binaryRoot", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, theProjectdescriptionPackage.getcoreexception());

		op = addEOperation(modifiableBundleMakerProjectDescriptionEClass, this.getModifiableFileBasedContent(), "addResourceContent", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "binaryRoot", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "sourceRoot", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, theProjectdescriptionPackage.getcoreexception());

		op = addEOperation(modifiableBundleMakerProjectDescriptionEClass, this.getModifiableFileBasedContent(), "addResourceContent", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "version", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "binaryRoot", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, theProjectdescriptionPackage.getcoreexception());

		op = addEOperation(modifiableBundleMakerProjectDescriptionEClass, this.getModifiableFileBasedContent(), "addResourceContent", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "version", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "binaryRoot", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "sourceRoot", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, theProjectdescriptionPackage.getcoreexception());

		op = addEOperation(modifiableBundleMakerProjectDescriptionEClass, this.getModifiableFileBasedContent(), "addResourceContent", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "version", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "binaryRoot", 1, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "sourceRoot", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEException(op, theProjectdescriptionPackage.getcoreexception());

		op = addEOperation(modifiableBundleMakerProjectDescriptionEClass, this.getModifiableFileBasedContent(), "addTypeContent", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "binaryRoot", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, theProjectdescriptionPackage.getcoreexception());

		op = addEOperation(modifiableBundleMakerProjectDescriptionEClass, this.getModifiableFileBasedContent(), "addTypeContent", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "version", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "binaryRoot", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, theProjectdescriptionPackage.getcoreexception());

		op = addEOperation(modifiableBundleMakerProjectDescriptionEClass, this.getModifiableFileBasedContent(), "addTypeContent", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "version", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "binaryRoot", 1, -1, IS_UNIQUE, IS_ORDERED);
		addEException(op, theProjectdescriptionPackage.getcoreexception());

		initEClass(modifiableFileBasedContentEClass, ModifiableFileBasedContent.class, "ModifiableFileBasedContent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModifiableFileBasedContent_Id(), ecorePackage.getEString(), "id", null, 1, 1, ModifiableFileBasedContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModifiableFileBasedContent_Name(), ecorePackage.getEString(), "name", null, 1, 1, ModifiableFileBasedContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModifiableFileBasedContent_Version(), ecorePackage.getEString(), "version", null, 1, 1, ModifiableFileBasedContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModifiableFileBasedContent_Initialized(), ecorePackage.getEBoolean(), "initialized", null, 0, 1, ModifiableFileBasedContent.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModifiableFileBasedContent_ModifiableBinaryPathNames(), ecorePackage.getEString(), "modifiableBinaryPathNames", null, 1, -1, ModifiableFileBasedContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModifiableFileBasedContent_ModifiableBinaryPaths(), theProjectdescriptionPackage.getipath(), "modifiableBinaryPaths", null, 1, -1, ModifiableFileBasedContent.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModifiableFileBasedContent_ModifiableResourceContent(), this.getModifiableResourceContent(), null, "modifiableResourceContent", null, 0, 1, ModifiableFileBasedContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(modifiableFileBasedContentEClass, null, "initialize", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theProjectdescriptionPackage.getbundlemakerproject(), "bundleMakerProject", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(modifiableResourceContentEClass, ModifiableResourceContent.class, "ModifiableResourceContent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModifiableResourceContent_AnalyzeSourceResources(), ecorePackage.getEBoolean(), "analyzeSourceResources", null, 0, 1, ModifiableResourceContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModifiableResourceContent_ModifiableSourcePathNames(), ecorePackage.getEString(), "modifiableSourcePathNames", null, 0, -1, ModifiableResourceContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModifiableResourceContent_ModifiableSourcePaths(), theProjectdescriptionPackage.getipath(), "modifiableSourcePaths", null, 0, -1, ModifiableResourceContent.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModifiableResourceContent_ModifiableSourceResources(), theProjectdescriptionPackage.getresourceStandin(), "modifiableSourceResources", null, 0, -1, ModifiableResourceContent.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModifiableResourceContent_ModifiableBinaryResources(), theProjectdescriptionPackage.getresourceStandin(), "modifiableBinaryResources", null, 0, -1, ModifiableResourceContent.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //ModifiableprojectdescriptionPackageImpl
