/**
 * <copyright>
 * </copyright>
 *
 */
package org.bundlemaker.core.transformations.dsl.transformationDsl;

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
 * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslFactory
 * @model kind="package"
 * @generated
 */
public interface TransformationDslPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "transformationDsl";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.bundlemaker.org/core/transformations/dsl/TransformationDsl";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "transformationDsl";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  TransformationDslPackage eINSTANCE = org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl.init();

  /**
   * The meta object id for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationModelImpl <em>Transformation Model</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationModelImpl
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getTransformationModel()
   * @generated
   */
  int TRANSFORMATION_MODEL = 0;

  /**
   * The feature id for the '<em><b>Transformations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSFORMATION_MODEL__TRANSFORMATIONS = 0;

  /**
   * The number of structural features of the '<em>Transformation Model</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSFORMATION_MODEL_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationImpl <em>Transformation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationImpl
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getTransformation()
   * @generated
   */
  int TRANSFORMATION = 1;

  /**
   * The number of structural features of the '<em>Transformation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSFORMATION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.RemoveFromImpl <em>Remove From</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.RemoveFromImpl
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getRemoveFrom()
   * @generated
   */
  int REMOVE_FROM = 2;

  /**
   * The feature id for the '<em><b>Resource Set</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REMOVE_FROM__RESOURCE_SET = TRANSFORMATION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Remove From</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REMOVE_FROM_FEATURE_COUNT = TRANSFORMATION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.EmbedIntoImpl <em>Embed Into</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.EmbedIntoImpl
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getEmbedInto()
   * @generated
   */
  int EMBED_INTO = 3;

  /**
   * The feature id for the '<em><b>Host Module</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EMBED_INTO__HOST_MODULE = TRANSFORMATION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Modules</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EMBED_INTO__MODULES = TRANSFORMATION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Embed Into</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EMBED_INTO_FEATURE_COUNT = TRANSFORMATION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.CreateModuleImpl <em>Create Module</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.CreateModuleImpl
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getCreateModule()
   * @generated
   */
  int CREATE_MODULE = 4;

  /**
   * The feature id for the '<em><b>Module</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CREATE_MODULE__MODULE = TRANSFORMATION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Layer</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CREATE_MODULE__LAYER = TRANSFORMATION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>From</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CREATE_MODULE__FROM = TRANSFORMATION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Create Module</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CREATE_MODULE_FEATURE_COUNT = TRANSFORMATION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ClassifyModulesImpl <em>Classify Modules</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ClassifyModulesImpl
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getClassifyModules()
   * @generated
   */
  int CLASSIFY_MODULES = 5;

  /**
   * The feature id for the '<em><b>Modules</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASSIFY_MODULES__MODULES = TRANSFORMATION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Excluded Modules</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASSIFY_MODULES__EXCLUDED_MODULES = TRANSFORMATION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Classification</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASSIFY_MODULES__CLASSIFICATION = TRANSFORMATION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Classify Modules</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASSIFY_MODULES_FEATURE_COUNT = TRANSFORMATION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.LayerImpl <em>Layer</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.LayerImpl
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getLayer()
   * @generated
   */
  int LAYER = 6;

  /**
   * The feature id for the '<em><b>Layer</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LAYER__LAYER = 0;

  /**
   * The number of structural features of the '<em>Layer</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LAYER_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.FromImpl <em>From</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.FromImpl
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getFrom()
   * @generated
   */
  int FROM = 7;

  /**
   * The feature id for the '<em><b>Resource Set</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FROM__RESOURCE_SET = 0;

  /**
   * The number of structural features of the '<em>From</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FROM_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ResourceSetImpl <em>Resource Set</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ResourceSetImpl
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getResourceSet()
   * @generated
   */
  int RESOURCE_SET = 8;

  /**
   * The feature id for the '<em><b>Module Identifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESOURCE_SET__MODULE_IDENTIFIER = 0;

  /**
   * The feature id for the '<em><b>Include Resources</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESOURCE_SET__INCLUDE_RESOURCES = 1;

  /**
   * The feature id for the '<em><b>Exclude Resources</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESOURCE_SET__EXCLUDE_RESOURCES = 2;

  /**
   * The number of structural features of the '<em>Resource Set</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESOURCE_SET_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ModuleIdentifierImpl <em>Module Identifier</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ModuleIdentifierImpl
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getModuleIdentifier()
   * @generated
   */
  int MODULE_IDENTIFIER = 9;

  /**
   * The feature id for the '<em><b>Modulename</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_IDENTIFIER__MODULENAME = 0;

  /**
   * The feature id for the '<em><b>Version</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_IDENTIFIER__VERSION = 1;

  /**
   * The number of structural features of the '<em>Module Identifier</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_IDENTIFIER_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ResourceListImpl <em>Resource List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ResourceListImpl
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getResourceList()
   * @generated
   */
  int RESOURCE_LIST = 10;

  /**
   * The feature id for the '<em><b>Resources</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESOURCE_LIST__RESOURCES = 0;

  /**
   * The number of structural features of the '<em>Resource List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESOURCE_LIST_FEATURE_COUNT = 1;


  /**
   * Returns the meta object for class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel <em>Transformation Model</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Transformation Model</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel
   * @generated
   */
  EClass getTransformationModel();

  /**
   * Returns the meta object for the containment reference list '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel#getTransformations <em>Transformations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Transformations</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel#getTransformations()
   * @see #getTransformationModel()
   * @generated
   */
  EReference getTransformationModel_Transformations();

  /**
   * Returns the meta object for class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.Transformation <em>Transformation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Transformation</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.Transformation
   * @generated
   */
  EClass getTransformation();

  /**
   * Returns the meta object for class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.RemoveFrom <em>Remove From</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Remove From</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.RemoveFrom
   * @generated
   */
  EClass getRemoveFrom();

  /**
   * Returns the meta object for the containment reference '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.RemoveFrom#getResourceSet <em>Resource Set</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Resource Set</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.RemoveFrom#getResourceSet()
   * @see #getRemoveFrom()
   * @generated
   */
  EReference getRemoveFrom_ResourceSet();

  /**
   * Returns the meta object for class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto <em>Embed Into</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Embed Into</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto
   * @generated
   */
  EClass getEmbedInto();

  /**
   * Returns the meta object for the containment reference '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto#getHostModule <em>Host Module</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Host Module</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto#getHostModule()
   * @see #getEmbedInto()
   * @generated
   */
  EReference getEmbedInto_HostModule();

  /**
   * Returns the meta object for the containment reference list '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto#getModules <em>Modules</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Modules</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto#getModules()
   * @see #getEmbedInto()
   * @generated
   */
  EReference getEmbedInto_Modules();

  /**
   * Returns the meta object for class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule <em>Create Module</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Create Module</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule
   * @generated
   */
  EClass getCreateModule();

  /**
   * Returns the meta object for the containment reference '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule#getModule <em>Module</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Module</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule#getModule()
   * @see #getCreateModule()
   * @generated
   */
  EReference getCreateModule_Module();

  /**
   * Returns the meta object for the containment reference '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule#getLayer <em>Layer</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Layer</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule#getLayer()
   * @see #getCreateModule()
   * @generated
   */
  EReference getCreateModule_Layer();

  /**
   * Returns the meta object for the containment reference list '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule#getFrom <em>From</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>From</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule#getFrom()
   * @see #getCreateModule()
   * @generated
   */
  EReference getCreateModule_From();

  /**
   * Returns the meta object for class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules <em>Classify Modules</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Classify Modules</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules
   * @generated
   */
  EClass getClassifyModules();

  /**
   * Returns the meta object for the attribute '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules#getModules <em>Modules</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Modules</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules#getModules()
   * @see #getClassifyModules()
   * @generated
   */
  EAttribute getClassifyModules_Modules();

  /**
   * Returns the meta object for the attribute '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules#getExcludedModules <em>Excluded Modules</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Excluded Modules</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules#getExcludedModules()
   * @see #getClassifyModules()
   * @generated
   */
  EAttribute getClassifyModules_ExcludedModules();

  /**
   * Returns the meta object for the attribute '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules#getClassification <em>Classification</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Classification</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ClassifyModules#getClassification()
   * @see #getClassifyModules()
   * @generated
   */
  EAttribute getClassifyModules_Classification();

  /**
   * Returns the meta object for class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.Layer <em>Layer</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Layer</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.Layer
   * @generated
   */
  EClass getLayer();

  /**
   * Returns the meta object for the attribute '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.Layer#getLayer <em>Layer</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Layer</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.Layer#getLayer()
   * @see #getLayer()
   * @generated
   */
  EAttribute getLayer_Layer();

  /**
   * Returns the meta object for class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.From <em>From</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>From</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.From
   * @generated
   */
  EClass getFrom();

  /**
   * Returns the meta object for the containment reference '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.From#getResourceSet <em>Resource Set</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Resource Set</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.From#getResourceSet()
   * @see #getFrom()
   * @generated
   */
  EReference getFrom_ResourceSet();

  /**
   * Returns the meta object for class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet <em>Resource Set</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Resource Set</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet
   * @generated
   */
  EClass getResourceSet();

  /**
   * Returns the meta object for the containment reference '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet#getModuleIdentifier <em>Module Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Module Identifier</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet#getModuleIdentifier()
   * @see #getResourceSet()
   * @generated
   */
  EReference getResourceSet_ModuleIdentifier();

  /**
   * Returns the meta object for the containment reference '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet#getIncludeResources <em>Include Resources</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Include Resources</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet#getIncludeResources()
   * @see #getResourceSet()
   * @generated
   */
  EReference getResourceSet_IncludeResources();

  /**
   * Returns the meta object for the containment reference '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet#getExcludeResources <em>Exclude Resources</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Exclude Resources</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet#getExcludeResources()
   * @see #getResourceSet()
   * @generated
   */
  EReference getResourceSet_ExcludeResources();

  /**
   * Returns the meta object for class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier <em>Module Identifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Module Identifier</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier
   * @generated
   */
  EClass getModuleIdentifier();

  /**
   * Returns the meta object for the attribute '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier#getModulename <em>Modulename</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Modulename</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier#getModulename()
   * @see #getModuleIdentifier()
   * @generated
   */
  EAttribute getModuleIdentifier_Modulename();

  /**
   * Returns the meta object for the attribute '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier#getVersion <em>Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Version</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier#getVersion()
   * @see #getModuleIdentifier()
   * @generated
   */
  EAttribute getModuleIdentifier_Version();

  /**
   * Returns the meta object for class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceList <em>Resource List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Resource List</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceList
   * @generated
   */
  EClass getResourceList();

  /**
   * Returns the meta object for the attribute list '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceList#getResources <em>Resources</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Resources</em>'.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceList#getResources()
   * @see #getResourceList()
   * @generated
   */
  EAttribute getResourceList_Resources();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  TransformationDslFactory getTransformationDslFactory();

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
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationModelImpl <em>Transformation Model</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationModelImpl
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getTransformationModel()
     * @generated
     */
    EClass TRANSFORMATION_MODEL = eINSTANCE.getTransformationModel();

    /**
     * The meta object literal for the '<em><b>Transformations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSFORMATION_MODEL__TRANSFORMATIONS = eINSTANCE.getTransformationModel_Transformations();

    /**
     * The meta object literal for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationImpl <em>Transformation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationImpl
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getTransformation()
     * @generated
     */
    EClass TRANSFORMATION = eINSTANCE.getTransformation();

    /**
     * The meta object literal for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.RemoveFromImpl <em>Remove From</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.RemoveFromImpl
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getRemoveFrom()
     * @generated
     */
    EClass REMOVE_FROM = eINSTANCE.getRemoveFrom();

    /**
     * The meta object literal for the '<em><b>Resource Set</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REMOVE_FROM__RESOURCE_SET = eINSTANCE.getRemoveFrom_ResourceSet();

    /**
     * The meta object literal for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.EmbedIntoImpl <em>Embed Into</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.EmbedIntoImpl
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getEmbedInto()
     * @generated
     */
    EClass EMBED_INTO = eINSTANCE.getEmbedInto();

    /**
     * The meta object literal for the '<em><b>Host Module</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EMBED_INTO__HOST_MODULE = eINSTANCE.getEmbedInto_HostModule();

    /**
     * The meta object literal for the '<em><b>Modules</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EMBED_INTO__MODULES = eINSTANCE.getEmbedInto_Modules();

    /**
     * The meta object literal for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.CreateModuleImpl <em>Create Module</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.CreateModuleImpl
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getCreateModule()
     * @generated
     */
    EClass CREATE_MODULE = eINSTANCE.getCreateModule();

    /**
     * The meta object literal for the '<em><b>Module</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CREATE_MODULE__MODULE = eINSTANCE.getCreateModule_Module();

    /**
     * The meta object literal for the '<em><b>Layer</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CREATE_MODULE__LAYER = eINSTANCE.getCreateModule_Layer();

    /**
     * The meta object literal for the '<em><b>From</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CREATE_MODULE__FROM = eINSTANCE.getCreateModule_From();

    /**
     * The meta object literal for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ClassifyModulesImpl <em>Classify Modules</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ClassifyModulesImpl
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getClassifyModules()
     * @generated
     */
    EClass CLASSIFY_MODULES = eINSTANCE.getClassifyModules();

    /**
     * The meta object literal for the '<em><b>Modules</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CLASSIFY_MODULES__MODULES = eINSTANCE.getClassifyModules_Modules();

    /**
     * The meta object literal for the '<em><b>Excluded Modules</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CLASSIFY_MODULES__EXCLUDED_MODULES = eINSTANCE.getClassifyModules_ExcludedModules();

    /**
     * The meta object literal for the '<em><b>Classification</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CLASSIFY_MODULES__CLASSIFICATION = eINSTANCE.getClassifyModules_Classification();

    /**
     * The meta object literal for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.LayerImpl <em>Layer</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.LayerImpl
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getLayer()
     * @generated
     */
    EClass LAYER = eINSTANCE.getLayer();

    /**
     * The meta object literal for the '<em><b>Layer</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LAYER__LAYER = eINSTANCE.getLayer_Layer();

    /**
     * The meta object literal for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.FromImpl <em>From</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.FromImpl
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getFrom()
     * @generated
     */
    EClass FROM = eINSTANCE.getFrom();

    /**
     * The meta object literal for the '<em><b>Resource Set</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FROM__RESOURCE_SET = eINSTANCE.getFrom_ResourceSet();

    /**
     * The meta object literal for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ResourceSetImpl <em>Resource Set</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ResourceSetImpl
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getResourceSet()
     * @generated
     */
    EClass RESOURCE_SET = eINSTANCE.getResourceSet();

    /**
     * The meta object literal for the '<em><b>Module Identifier</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RESOURCE_SET__MODULE_IDENTIFIER = eINSTANCE.getResourceSet_ModuleIdentifier();

    /**
     * The meta object literal for the '<em><b>Include Resources</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RESOURCE_SET__INCLUDE_RESOURCES = eINSTANCE.getResourceSet_IncludeResources();

    /**
     * The meta object literal for the '<em><b>Exclude Resources</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RESOURCE_SET__EXCLUDE_RESOURCES = eINSTANCE.getResourceSet_ExcludeResources();

    /**
     * The meta object literal for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ModuleIdentifierImpl <em>Module Identifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ModuleIdentifierImpl
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getModuleIdentifier()
     * @generated
     */
    EClass MODULE_IDENTIFIER = eINSTANCE.getModuleIdentifier();

    /**
     * The meta object literal for the '<em><b>Modulename</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODULE_IDENTIFIER__MODULENAME = eINSTANCE.getModuleIdentifier_Modulename();

    /**
     * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODULE_IDENTIFIER__VERSION = eINSTANCE.getModuleIdentifier_Version();

    /**
     * The meta object literal for the '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ResourceListImpl <em>Resource List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.ResourceListImpl
     * @see org.bundlemaker.core.transformations.dsl.transformationDsl.impl.TransformationDslPackageImpl#getResourceList()
     * @generated
     */
    EClass RESOURCE_LIST = eINSTANCE.getResourceList();

    /**
     * The meta object literal for the '<em><b>Resources</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RESOURCE_LIST__RESOURCES = eINSTANCE.getResourceList_Resources();

  }

} //TransformationDslPackage
