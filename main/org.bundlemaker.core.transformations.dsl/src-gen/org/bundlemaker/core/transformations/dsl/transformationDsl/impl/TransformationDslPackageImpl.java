/**
 * <copyright>
 * </copyright>
 *
 */
package org.bundlemaker.core.transformations.dsl.transformationDsl.impl;

import org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules;
import org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule;
import org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto;
import org.bundlemaker.core.transformations.dsl.transformationDsl.From;
import org.bundlemaker.core.transformations.dsl.transformationDsl.Layer;
import org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier;
import org.bundlemaker.core.transformations.dsl.transformationDsl.RemoveFrom;
import org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceList;
import org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet;
import org.bundlemaker.core.transformations.dsl.transformationDsl.Transformation;
import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslFactory;
import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage;
import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel;

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
public class TransformationDslPackageImpl extends EPackageImpl implements TransformationDslPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass transformationModelEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass transformationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass removeFromEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass embedIntoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass createModuleEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass classifyModulesEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass layerEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass fromEClass = null;

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
  private EClass moduleIdentifierEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass resourceListEClass = null;

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
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private TransformationDslPackageImpl()
  {
    super(eNS_URI, TransformationDslFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link TransformationDslPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static TransformationDslPackage init()
  {
    if (isInited) return (TransformationDslPackage)EPackage.Registry.INSTANCE.getEPackage(TransformationDslPackage.eNS_URI);

    // Obtain or create and register package
    TransformationDslPackageImpl theTransformationDslPackage = (TransformationDslPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TransformationDslPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TransformationDslPackageImpl());

    isInited = true;

    // Create package meta-data objects
    theTransformationDslPackage.createPackageContents();

    // Initialize created meta-data
    theTransformationDslPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theTransformationDslPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(TransformationDslPackage.eNS_URI, theTransformationDslPackage);
    return theTransformationDslPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTransformationModel()
  {
    return transformationModelEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTransformationModel_Transformations()
  {
    return (EReference)transformationModelEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTransformation()
  {
    return transformationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRemoveFrom()
  {
    return removeFromEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRemoveFrom_ResourceSet()
  {
    return (EReference)removeFromEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEmbedInto()
  {
    return embedIntoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEmbedInto_HostModule()
  {
    return (EReference)embedIntoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEmbedInto_Modules()
  {
    return (EReference)embedIntoEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCreateModule()
  {
    return createModuleEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCreateModule_Module()
  {
    return (EReference)createModuleEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCreateModule_Layer()
  {
    return (EReference)createModuleEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCreateModule_From()
  {
    return (EReference)createModuleEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getClassifyModules()
  {
    return classifyModulesEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getClassifyModules_Modules()
  {
    return (EAttribute)classifyModulesEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getClassifyModules_ExcludedModules()
  {
    return (EAttribute)classifyModulesEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getClassifyModules_Classification()
  {
    return (EAttribute)classifyModulesEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLayer()
  {
    return layerEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLayer_Layer()
  {
    return (EAttribute)layerEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFrom()
  {
    return fromEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getFrom_ResourceSet()
  {
    return (EReference)fromEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getResourceSet()
  {
    return resourceSetEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getResourceSet_ModuleIdentifier()
  {
    return (EReference)resourceSetEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getResourceSet_IncludeResources()
  {
    return (EReference)resourceSetEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getResourceSet_ExcludeResources()
  {
    return (EReference)resourceSetEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getModuleIdentifier()
  {
    return moduleIdentifierEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModuleIdentifier_Modulename()
  {
    return (EAttribute)moduleIdentifierEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModuleIdentifier_Version()
  {
    return (EAttribute)moduleIdentifierEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getResourceList()
  {
    return resourceListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getResourceList_Resources()
  {
    return (EAttribute)resourceListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TransformationDslFactory getTransformationDslFactory()
  {
    return (TransformationDslFactory)getEFactoryInstance();
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
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    transformationModelEClass = createEClass(TRANSFORMATION_MODEL);
    createEReference(transformationModelEClass, TRANSFORMATION_MODEL__TRANSFORMATIONS);

    transformationEClass = createEClass(TRANSFORMATION);

    removeFromEClass = createEClass(REMOVE_FROM);
    createEReference(removeFromEClass, REMOVE_FROM__RESOURCE_SET);

    embedIntoEClass = createEClass(EMBED_INTO);
    createEReference(embedIntoEClass, EMBED_INTO__HOST_MODULE);
    createEReference(embedIntoEClass, EMBED_INTO__MODULES);

    createModuleEClass = createEClass(CREATE_MODULE);
    createEReference(createModuleEClass, CREATE_MODULE__MODULE);
    createEReference(createModuleEClass, CREATE_MODULE__LAYER);
    createEReference(createModuleEClass, CREATE_MODULE__FROM);

    classifyModulesEClass = createEClass(CLASSIFY_MODULES);
    createEAttribute(classifyModulesEClass, CLASSIFY_MODULES__MODULES);
    createEAttribute(classifyModulesEClass, CLASSIFY_MODULES__EXCLUDED_MODULES);
    createEAttribute(classifyModulesEClass, CLASSIFY_MODULES__CLASSIFICATION);

    layerEClass = createEClass(LAYER);
    createEAttribute(layerEClass, LAYER__LAYER);

    fromEClass = createEClass(FROM);
    createEReference(fromEClass, FROM__RESOURCE_SET);

    resourceSetEClass = createEClass(RESOURCE_SET);
    createEReference(resourceSetEClass, RESOURCE_SET__MODULE_IDENTIFIER);
    createEReference(resourceSetEClass, RESOURCE_SET__INCLUDE_RESOURCES);
    createEReference(resourceSetEClass, RESOURCE_SET__EXCLUDE_RESOURCES);

    moduleIdentifierEClass = createEClass(MODULE_IDENTIFIER);
    createEAttribute(moduleIdentifierEClass, MODULE_IDENTIFIER__MODULENAME);
    createEAttribute(moduleIdentifierEClass, MODULE_IDENTIFIER__VERSION);

    resourceListEClass = createEClass(RESOURCE_LIST);
    createEAttribute(resourceListEClass, RESOURCE_LIST__RESOURCES);
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
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    removeFromEClass.getESuperTypes().add(this.getTransformation());
    embedIntoEClass.getESuperTypes().add(this.getTransformation());
    createModuleEClass.getESuperTypes().add(this.getTransformation());
    classifyModulesEClass.getESuperTypes().add(this.getTransformation());

    // Initialize classes and features; add operations and parameters
    initEClass(transformationModelEClass, TransformationModel.class, "TransformationModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTransformationModel_Transformations(), this.getTransformation(), null, "transformations", null, 0, -1, TransformationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(transformationEClass, Transformation.class, "Transformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(removeFromEClass, RemoveFrom.class, "RemoveFrom", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getRemoveFrom_ResourceSet(), this.getResourceSet(), null, "resourceSet", null, 0, 1, RemoveFrom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(embedIntoEClass, EmbedInto.class, "EmbedInto", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getEmbedInto_HostModule(), this.getModuleIdentifier(), null, "hostModule", null, 0, 1, EmbedInto.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEmbedInto_Modules(), this.getModuleIdentifier(), null, "modules", null, 0, -1, EmbedInto.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(createModuleEClass, CreateModule.class, "CreateModule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCreateModule_Module(), this.getModuleIdentifier(), null, "module", null, 0, 1, CreateModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCreateModule_Layer(), this.getLayer(), null, "layer", null, 0, 1, CreateModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCreateModule_From(), this.getFrom(), null, "from", null, 0, -1, CreateModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(classifyModulesEClass, ClassifyModules.class, "ClassifyModules", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getClassifyModules_Modules(), ecorePackage.getEString(), "modules", null, 0, 1, ClassifyModules.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getClassifyModules_ExcludedModules(), ecorePackage.getEString(), "excludedModules", null, 0, 1, ClassifyModules.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getClassifyModules_Classification(), ecorePackage.getEString(), "classification", null, 0, 1, ClassifyModules.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(layerEClass, Layer.class, "Layer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLayer_Layer(), ecorePackage.getEString(), "layer", null, 0, 1, Layer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fromEClass, From.class, "From", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getFrom_ResourceSet(), this.getResourceSet(), null, "resourceSet", null, 0, 1, From.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(resourceSetEClass, ResourceSet.class, "ResourceSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getResourceSet_ModuleIdentifier(), this.getModuleIdentifier(), null, "moduleIdentifier", null, 0, 1, ResourceSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourceSet_IncludeResources(), this.getResourceList(), null, "includeResources", null, 0, 1, ResourceSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourceSet_ExcludeResources(), this.getResourceList(), null, "excludeResources", null, 0, 1, ResourceSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(moduleIdentifierEClass, ModuleIdentifier.class, "ModuleIdentifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getModuleIdentifier_Modulename(), ecorePackage.getEString(), "modulename", null, 0, 1, ModuleIdentifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getModuleIdentifier_Version(), ecorePackage.getEString(), "version", null, 0, 1, ModuleIdentifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(resourceListEClass, ResourceList.class, "ResourceList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getResourceList_Resources(), ecorePackage.getEString(), "resources", null, 0, -1, ResourceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //TransformationDslPackageImpl
