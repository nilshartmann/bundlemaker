/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see org.bundlemaker.core.model.transformation.TransformationFactory
 * @model kind="package"
 * @generated
 */
public interface TransformationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "transformation";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.bundlemaker.org/org/bundlemaker/core/model/transformation";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "transformation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TransformationPackage eINSTANCE = org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.transformation.ITransformation <em>Abstract ETransformation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.transformation.ITransformation
	 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getAbstractETransformation()
	 * @generated
	 */
	int ABSTRACT_ETRANSFORMATION = 0;

	/**
	 * The number of structural features of the '<em>Abstract ETransformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ETRANSFORMATION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.transformation.impl.ResourceSetImpl <em>Resource Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.transformation.impl.ResourceSetImpl
	 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getResourceSet()
	 * @generated
	 */
	int RESOURCE_SET = 1;

	/**
	 * The feature id for the '<em><b>Module Identifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET__MODULE_IDENTIFIER = 0;

	/**
	 * The feature id for the '<em><b>Includes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET__INCLUDES = 1;

	/**
	 * The feature id for the '<em><b>Excludes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET__EXCLUDES = 2;

	/**
	 * The number of structural features of the '<em>Resource Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.transformation.impl.ResourceSetBasedModuleDefinitionImpl <em>Resource Set Based Module Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.transformation.impl.ResourceSetBasedModuleDefinitionImpl
	 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getResourceSetBasedModuleDefinition()
	 * @generated
	 */
	int RESOURCE_SET_BASED_MODULE_DEFINITION = 2;

	/**
	 * The feature id for the '<em><b>Module Identifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET_BASED_MODULE_DEFINITION__MODULE_IDENTIFIER = 0;

	/**
	 * The feature id for the '<em><b>Resource Sets</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET_BASED_MODULE_DEFINITION__RESOURCE_SETS = 1;

	/**
	 * The feature id for the '<em><b>User Attributes</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET_BASED_MODULE_DEFINITION__USER_ATTRIBUTES = 2;

	/**
	 * The feature id for the '<em><b>Classification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET_BASED_MODULE_DEFINITION__CLASSIFICATION = 3;

	/**
	 * The number of structural features of the '<em>Resource Set Based Module Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET_BASED_MODULE_DEFINITION_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.transformation.impl.ResourceSetBasedTransformationImpl <em>Resource Set Based Transformation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.transformation.impl.ResourceSetBasedTransformationImpl
	 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getResourceSetBasedTransformation()
	 * @generated
	 */
	int RESOURCE_SET_BASED_TRANSFORMATION = 3;

	/**
	 * The feature id for the '<em><b>Module Definitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET_BASED_TRANSFORMATION__MODULE_DEFINITIONS = ABSTRACT_ETRANSFORMATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Resource Set Processor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET_BASED_TRANSFORMATION__RESOURCE_SET_PROCESSOR = ABSTRACT_ETRANSFORMATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Resource Set Based Transformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET_BASED_TRANSFORMATION_FEATURE_COUNT = ABSTRACT_ETRANSFORMATION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.transformation.impl.BasicProjectContentTransformationImpl <em>Basic Project Content Transformation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.transformation.impl.BasicProjectContentTransformationImpl
	 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getBasicProjectContentTransformation()
	 * @generated
	 */
	int BASIC_PROJECT_CONTENT_TRANSFORMATION = 4;

	/**
	 * The number of structural features of the '<em>Basic Project Content Transformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_PROJECT_CONTENT_TRANSFORMATION_FEATURE_COUNT = ABSTRACT_ETRANSFORMATION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.transformation.impl.EmbedModuleTransformationImpl <em>Embed Module Transformation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.transformation.impl.EmbedModuleTransformationImpl
	 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getEmbedModuleTransformation()
	 * @generated
	 */
	int EMBED_MODULE_TRANSFORMATION = 5;

	/**
	 * The feature id for the '<em><b>Host Module Identifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMBED_MODULE_TRANSFORMATION__HOST_MODULE_IDENTIFIER = ABSTRACT_ETRANSFORMATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Embedded Modules Identifiers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMBED_MODULE_TRANSFORMATION__EMBEDDED_MODULES_IDENTIFIERS = ABSTRACT_ETRANSFORMATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Embed Module Transformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMBED_MODULE_TRANSFORMATION_FEATURE_COUNT = ABSTRACT_ETRANSFORMATION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.transformation.impl.RemoveResourcesTransformationImpl <em>Remove Resources Transformation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.transformation.impl.RemoveResourcesTransformationImpl
	 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getRemoveResourcesTransformation()
	 * @generated
	 */
	int REMOVE_RESOURCES_TRANSFORMATION = 6;

	/**
	 * The feature id for the '<em><b>Resources To Remove</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_RESOURCES_TRANSFORMATION__RESOURCES_TO_REMOVE = ABSTRACT_ETRANSFORMATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Remove Resources Transformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_RESOURCES_TRANSFORMATION_FEATURE_COUNT = ABSTRACT_ETRANSFORMATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.transformation.impl.StringToObjectMapImpl <em>String To Object Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.transformation.impl.StringToObjectMapImpl
	 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getStringToObjectMap()
	 * @generated
	 */
	int STRING_TO_OBJECT_MAP = 7;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_OBJECT_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_OBJECT_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Object Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_OBJECT_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '<em>iResource Set Processor</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.transformation.resourceset.IResourceSetProcessor
	 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getiResourceSetProcessor()
	 * @generated
	 */
	int IRESOURCE_SET_PROCESSOR = 8;

	/**
	 * The meta object id for the '<em>string Array</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getstringArray()
	 * @generated
	 */
	int STRING_ARRAY = 9;

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.transformation.ITransformation <em>Abstract ETransformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract ETransformation</em>'.
	 * @see org.bundlemaker.core.transformation.ITransformation
	 * @model instanceClass="org.bundlemaker.core.transformation.ITransformation"
	 * @generated
	 */
	EClass getAbstractETransformation();

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.transformation.ResourceSet <em>Resource Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Set</em>'.
	 * @see org.bundlemaker.core.model.transformation.ResourceSet
	 * @generated
	 */
	EClass getResourceSet();

	/**
	 * Returns the meta object for the reference '{@link org.bundlemaker.core.model.transformation.ResourceSet#getModuleIdentifier <em>Module Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Module Identifier</em>'.
	 * @see org.bundlemaker.core.model.transformation.ResourceSet#getModuleIdentifier()
	 * @see #getResourceSet()
	 * @generated
	 */
	EReference getResourceSet_ModuleIdentifier();

	/**
	 * Returns the meta object for the attribute list '{@link org.bundlemaker.core.model.transformation.ResourceSet#getIncludes <em>Includes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Includes</em>'.
	 * @see org.bundlemaker.core.model.transformation.ResourceSet#getIncludes()
	 * @see #getResourceSet()
	 * @generated
	 */
	EAttribute getResourceSet_Includes();

	/**
	 * Returns the meta object for the attribute list '{@link org.bundlemaker.core.model.transformation.ResourceSet#getExcludes <em>Excludes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Excludes</em>'.
	 * @see org.bundlemaker.core.model.transformation.ResourceSet#getExcludes()
	 * @see #getResourceSet()
	 * @generated
	 */
	EAttribute getResourceSet_Excludes();

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition <em>Resource Set Based Module Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Set Based Module Definition</em>'.
	 * @see org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition
	 * @generated
	 */
	EClass getResourceSetBasedModuleDefinition();

	/**
	 * Returns the meta object for the reference list '{@link org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getResourceSets <em>Resource Sets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Resource Sets</em>'.
	 * @see org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getResourceSets()
	 * @see #getResourceSetBasedModuleDefinition()
	 * @generated
	 */
	EReference getResourceSetBasedModuleDefinition_ResourceSets();

	/**
	 * Returns the meta object for the reference '{@link org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getModuleIdentifier <em>Module Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Module Identifier</em>'.
	 * @see org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getModuleIdentifier()
	 * @see #getResourceSetBasedModuleDefinition()
	 * @generated
	 */
	EReference getResourceSetBasedModuleDefinition_ModuleIdentifier();

	/**
	 * Returns the meta object for the map '{@link org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getUserAttributes <em>User Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>User Attributes</em>'.
	 * @see org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getUserAttributes()
	 * @see #getResourceSetBasedModuleDefinition()
	 * @generated
	 */
	EReference getResourceSetBasedModuleDefinition_UserAttributes();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getClassification <em>Classification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Classification</em>'.
	 * @see org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition#getClassification()
	 * @see #getResourceSetBasedModuleDefinition()
	 * @generated
	 */
	EAttribute getResourceSetBasedModuleDefinition_Classification();

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation <em>Resource Set Based Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Set Based Transformation</em>'.
	 * @see org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation
	 * @generated
	 */
	EClass getResourceSetBasedTransformation();

	/**
	 * Returns the meta object for the reference list '{@link org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation#getModuleDefinitions <em>Module Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Module Definitions</em>'.
	 * @see org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation#getModuleDefinitions()
	 * @see #getResourceSetBasedTransformation()
	 * @generated
	 */
	EReference getResourceSetBasedTransformation_ModuleDefinitions();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation#getResourceSetProcessor <em>Resource Set Processor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource Set Processor</em>'.
	 * @see org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation#getResourceSetProcessor()
	 * @see #getResourceSetBasedTransformation()
	 * @generated
	 */
	EAttribute getResourceSetBasedTransformation_ResourceSetProcessor();

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.transformation.BasicProjectContentTransformation <em>Basic Project Content Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic Project Content Transformation</em>'.
	 * @see org.bundlemaker.core.model.transformation.BasicProjectContentTransformation
	 * @generated
	 */
	EClass getBasicProjectContentTransformation();

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.transformation.EmbedModuleTransformation <em>Embed Module Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Embed Module Transformation</em>'.
	 * @see org.bundlemaker.core.model.transformation.EmbedModuleTransformation
	 * @generated
	 */
	EClass getEmbedModuleTransformation();

	/**
	 * Returns the meta object for the reference '{@link org.bundlemaker.core.model.transformation.EmbedModuleTransformation#getHostModuleIdentifier <em>Host Module Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Host Module Identifier</em>'.
	 * @see org.bundlemaker.core.model.transformation.EmbedModuleTransformation#getHostModuleIdentifier()
	 * @see #getEmbedModuleTransformation()
	 * @generated
	 */
	EReference getEmbedModuleTransformation_HostModuleIdentifier();

	/**
	 * Returns the meta object for the reference list '{@link org.bundlemaker.core.model.transformation.EmbedModuleTransformation#getEmbeddedModulesIdentifiers <em>Embedded Modules Identifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Embedded Modules Identifiers</em>'.
	 * @see org.bundlemaker.core.model.transformation.EmbedModuleTransformation#getEmbeddedModulesIdentifiers()
	 * @see #getEmbedModuleTransformation()
	 * @generated
	 */
	EReference getEmbedModuleTransformation_EmbeddedModulesIdentifiers();

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.transformation.RemoveResourcesTransformation <em>Remove Resources Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remove Resources Transformation</em>'.
	 * @see org.bundlemaker.core.model.transformation.RemoveResourcesTransformation
	 * @generated
	 */
	EClass getRemoveResourcesTransformation();

	/**
	 * Returns the meta object for the reference list '{@link org.bundlemaker.core.model.transformation.RemoveResourcesTransformation#getResourcesToRemove <em>Resources To Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Resources To Remove</em>'.
	 * @see org.bundlemaker.core.model.transformation.RemoveResourcesTransformation#getResourcesToRemove()
	 * @see #getRemoveResourcesTransformation()
	 * @generated
	 */
	EReference getRemoveResourcesTransformation_ResourcesToRemove();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Object Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Object Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.emf.ecore.EJavaObject"
	 * @generated
	 */
	EClass getStringToObjectMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToObjectMap()
	 * @generated
	 */
	EAttribute getStringToObjectMap_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToObjectMap()
	 * @generated
	 */
	EAttribute getStringToObjectMap_Value();

	/**
	 * Returns the meta object for data type '{@link org.bundlemaker.core.transformation.resourceset.IResourceSetProcessor <em>iResource Set Processor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>iResource Set Processor</em>'.
	 * @see org.bundlemaker.core.transformation.resourceset.IResourceSetProcessor
	 * @model instanceClass="org.bundlemaker.core.transformation.resourceset.IResourceSetProcessor"
	 * @generated
	 */
	EDataType getiResourceSetProcessor();

	/**
	 * Returns the meta object for data type '<em>string Array</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>string Array</em>'.
	 * @model instanceClass="java.lang.String[]"
	 * @generated
	 */
	EDataType getstringArray();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TransformationFactory getTransformationFactory();

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
		 * The meta object literal for the '{@link org.bundlemaker.core.transformation.ITransformation <em>Abstract ETransformation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.transformation.ITransformation
		 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getAbstractETransformation()
		 * @generated
		 */
		EClass ABSTRACT_ETRANSFORMATION = eINSTANCE.getAbstractETransformation();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.transformation.impl.ResourceSetImpl <em>Resource Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.transformation.impl.ResourceSetImpl
		 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getResourceSet()
		 * @generated
		 */
		EClass RESOURCE_SET = eINSTANCE.getResourceSet();

		/**
		 * The meta object literal for the '<em><b>Module Identifier</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_SET__MODULE_IDENTIFIER = eINSTANCE.getResourceSet_ModuleIdentifier();

		/**
		 * The meta object literal for the '<em><b>Includes</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE_SET__INCLUDES = eINSTANCE.getResourceSet_Includes();

		/**
		 * The meta object literal for the '<em><b>Excludes</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE_SET__EXCLUDES = eINSTANCE.getResourceSet_Excludes();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.transformation.impl.ResourceSetBasedModuleDefinitionImpl <em>Resource Set Based Module Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.transformation.impl.ResourceSetBasedModuleDefinitionImpl
		 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getResourceSetBasedModuleDefinition()
		 * @generated
		 */
		EClass RESOURCE_SET_BASED_MODULE_DEFINITION = eINSTANCE.getResourceSetBasedModuleDefinition();

		/**
		 * The meta object literal for the '<em><b>Resource Sets</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_SET_BASED_MODULE_DEFINITION__RESOURCE_SETS = eINSTANCE.getResourceSetBasedModuleDefinition_ResourceSets();

		/**
		 * The meta object literal for the '<em><b>Module Identifier</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_SET_BASED_MODULE_DEFINITION__MODULE_IDENTIFIER = eINSTANCE.getResourceSetBasedModuleDefinition_ModuleIdentifier();

		/**
		 * The meta object literal for the '<em><b>User Attributes</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_SET_BASED_MODULE_DEFINITION__USER_ATTRIBUTES = eINSTANCE.getResourceSetBasedModuleDefinition_UserAttributes();

		/**
		 * The meta object literal for the '<em><b>Classification</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE_SET_BASED_MODULE_DEFINITION__CLASSIFICATION = eINSTANCE.getResourceSetBasedModuleDefinition_Classification();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.transformation.impl.ResourceSetBasedTransformationImpl <em>Resource Set Based Transformation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.transformation.impl.ResourceSetBasedTransformationImpl
		 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getResourceSetBasedTransformation()
		 * @generated
		 */
		EClass RESOURCE_SET_BASED_TRANSFORMATION = eINSTANCE.getResourceSetBasedTransformation();

		/**
		 * The meta object literal for the '<em><b>Module Definitions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_SET_BASED_TRANSFORMATION__MODULE_DEFINITIONS = eINSTANCE.getResourceSetBasedTransformation_ModuleDefinitions();

		/**
		 * The meta object literal for the '<em><b>Resource Set Processor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE_SET_BASED_TRANSFORMATION__RESOURCE_SET_PROCESSOR = eINSTANCE.getResourceSetBasedTransformation_ResourceSetProcessor();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.transformation.impl.BasicProjectContentTransformationImpl <em>Basic Project Content Transformation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.transformation.impl.BasicProjectContentTransformationImpl
		 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getBasicProjectContentTransformation()
		 * @generated
		 */
		EClass BASIC_PROJECT_CONTENT_TRANSFORMATION = eINSTANCE.getBasicProjectContentTransformation();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.transformation.impl.EmbedModuleTransformationImpl <em>Embed Module Transformation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.transformation.impl.EmbedModuleTransformationImpl
		 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getEmbedModuleTransformation()
		 * @generated
		 */
		EClass EMBED_MODULE_TRANSFORMATION = eINSTANCE.getEmbedModuleTransformation();

		/**
		 * The meta object literal for the '<em><b>Host Module Identifier</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EMBED_MODULE_TRANSFORMATION__HOST_MODULE_IDENTIFIER = eINSTANCE.getEmbedModuleTransformation_HostModuleIdentifier();

		/**
		 * The meta object literal for the '<em><b>Embedded Modules Identifiers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EMBED_MODULE_TRANSFORMATION__EMBEDDED_MODULES_IDENTIFIERS = eINSTANCE.getEmbedModuleTransformation_EmbeddedModulesIdentifiers();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.transformation.impl.RemoveResourcesTransformationImpl <em>Remove Resources Transformation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.transformation.impl.RemoveResourcesTransformationImpl
		 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getRemoveResourcesTransformation()
		 * @generated
		 */
		EClass REMOVE_RESOURCES_TRANSFORMATION = eINSTANCE.getRemoveResourcesTransformation();

		/**
		 * The meta object literal for the '<em><b>Resources To Remove</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMOVE_RESOURCES_TRANSFORMATION__RESOURCES_TO_REMOVE = eINSTANCE.getRemoveResourcesTransformation_ResourcesToRemove();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.transformation.impl.StringToObjectMapImpl <em>String To Object Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.transformation.impl.StringToObjectMapImpl
		 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getStringToObjectMap()
		 * @generated
		 */
		EClass STRING_TO_OBJECT_MAP = eINSTANCE.getStringToObjectMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_OBJECT_MAP__KEY = eINSTANCE.getStringToObjectMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_OBJECT_MAP__VALUE = eINSTANCE.getStringToObjectMap_Value();

		/**
		 * The meta object literal for the '<em>iResource Set Processor</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.transformation.resourceset.IResourceSetProcessor
		 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getiResourceSetProcessor()
		 * @generated
		 */
		EDataType IRESOURCE_SET_PROCESSOR = eINSTANCE.getiResourceSetProcessor();

		/**
		 * The meta object literal for the '<em>string Array</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl#getstringArray()
		 * @generated
		 */
		EDataType STRING_ARRAY = eINSTANCE.getstringArray();

	}

} //TransformationPackage
