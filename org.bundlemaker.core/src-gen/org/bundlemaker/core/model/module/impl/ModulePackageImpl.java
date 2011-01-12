/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.module.impl;

import org.bundlemaker.core.model.module.IModuleIdentifier;
import org.bundlemaker.core.model.module.ModifiableModuleIdentifier;
import org.bundlemaker.core.model.module.ModuleFactory;
import org.bundlemaker.core.model.module.ModulePackage;
import org.bundlemaker.core.model.projectdescription.ProjectdescriptionPackage;
import org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableprojectdescriptionPackageImpl;
import org.bundlemaker.core.model.transformation.TransformationPackage;
import org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ITypeModule;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModulePackageImpl extends EPackageImpl implements ModulePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iModuleIdentifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modifiableModuleIdentifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType typemoduleEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType resourcemoduleEDataType = null;

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
	 * @see org.bundlemaker.core.model.module.ModulePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModulePackageImpl() {
		super(eNS_URI, ModuleFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ModulePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModulePackage init() {
		if (isInited) return (ModulePackage)EPackage.Registry.INSTANCE.getEPackage(ModulePackage.eNS_URI);

		// Obtain or create and register package
		ModulePackageImpl theModulePackage = (ModulePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ModulePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ModulePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ProjectdescriptionPackageImpl theProjectdescriptionPackage = (ProjectdescriptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProjectdescriptionPackage.eNS_URI) instanceof ProjectdescriptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProjectdescriptionPackage.eNS_URI) : ProjectdescriptionPackage.eINSTANCE);
		ModifiableprojectdescriptionPackageImpl theModifiableprojectdescriptionPackage = (ModifiableprojectdescriptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModifiableprojectdescriptionPackage.eNS_URI) instanceof ModifiableprojectdescriptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModifiableprojectdescriptionPackage.eNS_URI) : ModifiableprojectdescriptionPackage.eINSTANCE);
		TransformationPackageImpl theTransformationPackage = (TransformationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TransformationPackage.eNS_URI) instanceof TransformationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TransformationPackage.eNS_URI) : TransformationPackage.eINSTANCE);

		// Create package meta-data objects
		theModulePackage.createPackageContents();
		theProjectdescriptionPackage.createPackageContents();
		theModifiableprojectdescriptionPackage.createPackageContents();
		theTransformationPackage.createPackageContents();

		// Initialize created meta-data
		theModulePackage.initializePackageContents();
		theProjectdescriptionPackage.initializePackageContents();
		theModifiableprojectdescriptionPackage.initializePackageContents();
		theTransformationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModulePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModulePackage.eNS_URI, theModulePackage);
		return theModulePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIModuleIdentifier() {
		return iModuleIdentifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModifiableModuleIdentifier() {
		return modifiableModuleIdentifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableModuleIdentifier_Name() {
		return (EAttribute)modifiableModuleIdentifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModifiableModuleIdentifier_Version() {
		return (EAttribute)modifiableModuleIdentifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType gettypemodule() {
		return typemoduleEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getresourcemodule() {
		return resourcemoduleEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleFactory getModuleFactory() {
		return (ModuleFactory)getEFactoryInstance();
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
		iModuleIdentifierEClass = createEClass(IMODULE_IDENTIFIER);

		modifiableModuleIdentifierEClass = createEClass(MODIFIABLE_MODULE_IDENTIFIER);
		createEAttribute(modifiableModuleIdentifierEClass, MODIFIABLE_MODULE_IDENTIFIER__NAME);
		createEAttribute(modifiableModuleIdentifierEClass, MODIFIABLE_MODULE_IDENTIFIER__VERSION);

		// Create data types
		typemoduleEDataType = createEDataType(TYPEMODULE);
		resourcemoduleEDataType = createEDataType(RESOURCEMODULE);
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
		modifiableModuleIdentifierEClass.getESuperTypes().add(this.getIModuleIdentifier());

		// Initialize classes and features; add operations and parameters
		initEClass(iModuleIdentifierEClass, IModuleIdentifier.class, "IModuleIdentifier", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(iModuleIdentifierEClass, ecorePackage.getEString(), "getVersion", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(iModuleIdentifierEClass, ecorePackage.getEString(), "getName", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(modifiableModuleIdentifierEClass, ModifiableModuleIdentifier.class, "ModifiableModuleIdentifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModifiableModuleIdentifier_Name(), ecorePackage.getEString(), "name", null, 0, 1, ModifiableModuleIdentifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModifiableModuleIdentifier_Version(), ecorePackage.getEString(), "version", null, 0, 1, ModifiableModuleIdentifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(typemoduleEDataType, ITypeModule.class, "typemodule", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(resourcemoduleEDataType, IResourceModule.class, "resourcemodule", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //ModulePackageImpl
