/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation.impl;

import java.util.Map;

import org.bundlemaker.core.model.module.ModulePackage;
import org.bundlemaker.core.model.module.impl.ModulePackageImpl;
import org.bundlemaker.core.model.projectdescription.ProjectdescriptionPackage;
import org.bundlemaker.core.model.projectdescription.impl.ProjectdescriptionPackageImpl;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableprojectdescriptionPackageImpl;
import org.bundlemaker.core.model.transformation.BasicProjectContentTransformation;
import org.bundlemaker.core.model.transformation.EmbedModuleTransformation;
import org.bundlemaker.core.model.transformation.RemoveResourcesTransformation;
import org.bundlemaker.core.model.transformation.ResourceSet;
import org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition;
import org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation;
import org.bundlemaker.core.model.transformation.TransformationFactory;
import org.bundlemaker.core.model.transformation.TransformationPackage;
import org.bundlemaker.core.transformation.ITransformation;
import org.bundlemaker.core.transformation.resourceset.IResourceSetProcessor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
public class TransformationPackageImpl extends EPackageImpl implements TransformationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractETransformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceSetBasedModuleDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceSetBasedTransformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass basicProjectContentTransformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass embedModuleTransformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removeResourcesTransformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringToObjectMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iResourceSetProcessorEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType stringArrayEDataType = null;

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
	 * @see org.bundlemaker.core.model.transformation.TransformationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TransformationPackageImpl() {
		super(eNS_URI, TransformationFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link TransformationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TransformationPackage init() {
		if (isInited) return (TransformationPackage)EPackage.Registry.INSTANCE.getEPackage(TransformationPackage.eNS_URI);

		// Obtain or create and register package
		TransformationPackageImpl theTransformationPackage = (TransformationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TransformationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TransformationPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ProjectdescriptionPackageImpl theProjectdescriptionPackage = (ProjectdescriptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProjectdescriptionPackage.eNS_URI) instanceof ProjectdescriptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProjectdescriptionPackage.eNS_URI) : ProjectdescriptionPackage.eINSTANCE);
		ModifiableprojectdescriptionPackageImpl theModifiableprojectdescriptionPackage = (ModifiableprojectdescriptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModifiableprojectdescriptionPackage.eNS_URI) instanceof ModifiableprojectdescriptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModifiableprojectdescriptionPackage.eNS_URI) : ModifiableprojectdescriptionPackage.eINSTANCE);
		ModulePackageImpl theModulePackage = (ModulePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModulePackage.eNS_URI) instanceof ModulePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModulePackage.eNS_URI) : ModulePackage.eINSTANCE);

		// Create package meta-data objects
		theTransformationPackage.createPackageContents();
		theProjectdescriptionPackage.createPackageContents();
		theModifiableprojectdescriptionPackage.createPackageContents();
		theModulePackage.createPackageContents();

		// Initialize created meta-data
		theTransformationPackage.initializePackageContents();
		theProjectdescriptionPackage.initializePackageContents();
		theModifiableprojectdescriptionPackage.initializePackageContents();
		theModulePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTransformationPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TransformationPackage.eNS_URI, theTransformationPackage);
		return theTransformationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractETransformation() {
		return abstractETransformationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResourceSet() {
		return resourceSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceSet_ModuleIdentifier() {
		return (EReference)resourceSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getResourceSet_Includes() {
		return (EAttribute)resourceSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getResourceSet_Excludes() {
		return (EAttribute)resourceSetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResourceSetBasedModuleDefinition() {
		return resourceSetBasedModuleDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceSetBasedModuleDefinition_ResourceSets() {
		return (EReference)resourceSetBasedModuleDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceSetBasedModuleDefinition_ModuleIdentifier() {
		return (EReference)resourceSetBasedModuleDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceSetBasedModuleDefinition_UserAttributes() {
		return (EReference)resourceSetBasedModuleDefinitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getResourceSetBasedModuleDefinition_Classification() {
		return (EAttribute)resourceSetBasedModuleDefinitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResourceSetBasedTransformation() {
		return resourceSetBasedTransformationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceSetBasedTransformation_ModuleDefinitions() {
		return (EReference)resourceSetBasedTransformationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getResourceSetBasedTransformation_ResourceSetProcessor() {
		return (EAttribute)resourceSetBasedTransformationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBasicProjectContentTransformation() {
		return basicProjectContentTransformationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEmbedModuleTransformation() {
		return embedModuleTransformationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEmbedModuleTransformation_HostModuleIdentifier() {
		return (EReference)embedModuleTransformationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEmbedModuleTransformation_EmbeddedModulesIdentifiers() {
		return (EReference)embedModuleTransformationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoveResourcesTransformation() {
		return removeResourcesTransformationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemoveResourcesTransformation_ResourcesToRemove() {
		return (EReference)removeResourcesTransformationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringToObjectMap() {
		return stringToObjectMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringToObjectMap_Key() {
		return (EAttribute)stringToObjectMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringToObjectMap_Value() {
		return (EAttribute)stringToObjectMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getiResourceSetProcessor() {
		return iResourceSetProcessorEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getstringArray() {
		return stringArrayEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationFactory getTransformationFactory() {
		return (TransformationFactory)getEFactoryInstance();
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
		abstractETransformationEClass = createEClass(ABSTRACT_ETRANSFORMATION);

		resourceSetEClass = createEClass(RESOURCE_SET);
		createEReference(resourceSetEClass, RESOURCE_SET__MODULE_IDENTIFIER);
		createEAttribute(resourceSetEClass, RESOURCE_SET__INCLUDES);
		createEAttribute(resourceSetEClass, RESOURCE_SET__EXCLUDES);

		resourceSetBasedModuleDefinitionEClass = createEClass(RESOURCE_SET_BASED_MODULE_DEFINITION);
		createEReference(resourceSetBasedModuleDefinitionEClass, RESOURCE_SET_BASED_MODULE_DEFINITION__MODULE_IDENTIFIER);
		createEReference(resourceSetBasedModuleDefinitionEClass, RESOURCE_SET_BASED_MODULE_DEFINITION__RESOURCE_SETS);
		createEReference(resourceSetBasedModuleDefinitionEClass, RESOURCE_SET_BASED_MODULE_DEFINITION__USER_ATTRIBUTES);
		createEAttribute(resourceSetBasedModuleDefinitionEClass, RESOURCE_SET_BASED_MODULE_DEFINITION__CLASSIFICATION);

		resourceSetBasedTransformationEClass = createEClass(RESOURCE_SET_BASED_TRANSFORMATION);
		createEReference(resourceSetBasedTransformationEClass, RESOURCE_SET_BASED_TRANSFORMATION__MODULE_DEFINITIONS);
		createEAttribute(resourceSetBasedTransformationEClass, RESOURCE_SET_BASED_TRANSFORMATION__RESOURCE_SET_PROCESSOR);

		basicProjectContentTransformationEClass = createEClass(BASIC_PROJECT_CONTENT_TRANSFORMATION);

		embedModuleTransformationEClass = createEClass(EMBED_MODULE_TRANSFORMATION);
		createEReference(embedModuleTransformationEClass, EMBED_MODULE_TRANSFORMATION__HOST_MODULE_IDENTIFIER);
		createEReference(embedModuleTransformationEClass, EMBED_MODULE_TRANSFORMATION__EMBEDDED_MODULES_IDENTIFIERS);

		removeResourcesTransformationEClass = createEClass(REMOVE_RESOURCES_TRANSFORMATION);
		createEReference(removeResourcesTransformationEClass, REMOVE_RESOURCES_TRANSFORMATION__RESOURCES_TO_REMOVE);

		stringToObjectMapEClass = createEClass(STRING_TO_OBJECT_MAP);
		createEAttribute(stringToObjectMapEClass, STRING_TO_OBJECT_MAP__KEY);
		createEAttribute(stringToObjectMapEClass, STRING_TO_OBJECT_MAP__VALUE);

		// Create data types
		iResourceSetProcessorEDataType = createEDataType(IRESOURCE_SET_PROCESSOR);
		stringArrayEDataType = createEDataType(STRING_ARRAY);
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
		ModulePackage theModulePackage = (ModulePackage)EPackage.Registry.INSTANCE.getEPackage(ModulePackage.eNS_URI);
		ProjectdescriptionPackage theProjectdescriptionPackage = (ProjectdescriptionPackage)EPackage.Registry.INSTANCE.getEPackage(ProjectdescriptionPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		resourceSetBasedTransformationEClass.getESuperTypes().add(this.getAbstractETransformation());
		basicProjectContentTransformationEClass.getESuperTypes().add(this.getAbstractETransformation());
		embedModuleTransformationEClass.getESuperTypes().add(this.getAbstractETransformation());
		removeResourcesTransformationEClass.getESuperTypes().add(this.getAbstractETransformation());

		// Initialize classes and features; add operations and parameters
		initEClass(abstractETransformationEClass, ITransformation.class, "AbstractETransformation", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);

		initEClass(resourceSetEClass, ResourceSet.class, "ResourceSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getResourceSet_ModuleIdentifier(), theModulePackage.getIModuleIdentifier(), null, "moduleIdentifier", null, 0, 1, ResourceSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getResourceSet_Includes(), ecorePackage.getEString(), "includes", null, 0, -1, ResourceSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getResourceSet_Excludes(), ecorePackage.getEString(), "excludes", null, 0, -1, ResourceSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(resourceSetEClass, theProjectdescriptionPackage.getiResourceStandin(), "getMatchingResources", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theModulePackage.getresourcemodule(), "resource", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theProjectdescriptionPackage.getContentType(), "contentType", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(resourceSetBasedModuleDefinitionEClass, ResourceSetBasedModuleDefinition.class, "ResourceSetBasedModuleDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getResourceSetBasedModuleDefinition_ModuleIdentifier(), theModulePackage.getIModuleIdentifier(), null, "moduleIdentifier", null, 0, 1, ResourceSetBasedModuleDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResourceSetBasedModuleDefinition_ResourceSets(), this.getResourceSet(), null, "resourceSets", null, 0, -1, ResourceSetBasedModuleDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResourceSetBasedModuleDefinition_UserAttributes(), this.getStringToObjectMap(), null, "userAttributes", null, 0, -1, ResourceSetBasedModuleDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getResourceSetBasedModuleDefinition_Classification(), theProjectdescriptionPackage.getipath(), "classification", null, 0, 1, ResourceSetBasedModuleDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(resourceSetBasedModuleDefinitionEClass, null, "addResourceSet", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "fromName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "fromVersion", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getstringArray(), "includes", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getstringArray(), "excludes", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(resourceSetBasedModuleDefinitionEClass, null, "addResourceSet", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theModulePackage.getIModuleIdentifier(), "fromModuleIdentifier", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getstringArray(), "includes", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getstringArray(), "excludes", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(resourceSetBasedTransformationEClass, ResourceSetBasedTransformation.class, "ResourceSetBasedTransformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getResourceSetBasedTransformation_ModuleDefinitions(), this.getResourceSetBasedModuleDefinition(), null, "moduleDefinitions", null, 0, -1, ResourceSetBasedTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getResourceSetBasedTransformation_ResourceSetProcessor(), this.getiResourceSetProcessor(), "resourceSetProcessor", null, 0, 1, ResourceSetBasedTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(resourceSetBasedTransformationEClass, this.getResourceSetBasedModuleDefinition(), "addModuleDefinition", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "version", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(resourceSetBasedTransformationEClass, this.getResourceSetBasedModuleDefinition(), "addModuleDefinition", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "version", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStringToObjectMap(), "userAttibutes", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(basicProjectContentTransformationEClass, BasicProjectContentTransformation.class, "BasicProjectContentTransformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(embedModuleTransformationEClass, EmbedModuleTransformation.class, "EmbedModuleTransformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEmbedModuleTransformation_HostModuleIdentifier(), theModulePackage.getIModuleIdentifier(), null, "hostModuleIdentifier", null, 1, 1, EmbedModuleTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEmbedModuleTransformation_EmbeddedModulesIdentifiers(), theModulePackage.getIModuleIdentifier(), null, "embeddedModulesIdentifiers", null, 0, -1, EmbedModuleTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(removeResourcesTransformationEClass, RemoveResourcesTransformation.class, "RemoveResourcesTransformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRemoveResourcesTransformation_ResourcesToRemove(), this.getResourceSet(), null, "resourcesToRemove", null, 0, -1, RemoveResourcesTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(removeResourcesTransformationEClass, null, "addResourceSet", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "fromName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "fromVersion", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getstringArray(), "includes", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getstringArray(), "excludes", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(removeResourcesTransformationEClass, null, "addResourceSet", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theModulePackage.getIModuleIdentifier(), "fromModuleIdentifier", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getstringArray(), "includes", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getstringArray(), "excludes", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(stringToObjectMapEClass, Map.Entry.class, "StringToObjectMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToObjectMap_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStringToObjectMap_Value(), ecorePackage.getEJavaObject(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(iResourceSetProcessorEDataType, IResourceSetProcessor.class, "iResourceSetProcessor", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(stringArrayEDataType, String[].class, "stringArray", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //TransformationPackageImpl
