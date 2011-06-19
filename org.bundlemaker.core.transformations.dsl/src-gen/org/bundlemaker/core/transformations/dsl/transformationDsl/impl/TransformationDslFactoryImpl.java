/**
 * <copyright>
 * </copyright>
 *
 */
package org.bundlemaker.core.transformations.dsl.transformationDsl.impl;

import org.bundlemaker.core.transformations.dsl.transformationDsl.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TransformationDslFactoryImpl extends EFactoryImpl implements TransformationDslFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static TransformationDslFactory init()
  {
    try
    {
      TransformationDslFactory theTransformationDslFactory = (TransformationDslFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.bundlemaker.org/core/transformations/dsl/TransformationDsl"); 
      if (theTransformationDslFactory != null)
      {
        return theTransformationDslFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new TransformationDslFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TransformationDslFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case TransformationDslPackage.TRANSFORMATION_MODEL: return createTransformationModel();
      case TransformationDslPackage.TRANSFORMATION: return createTransformation();
      case TransformationDslPackage.REMOVE_FROM: return createRemoveFrom();
      case TransformationDslPackage.EMBED_INTO: return createEmbedInto();
      case TransformationDslPackage.CREATE_MODULE: return createCreateModule();
      case TransformationDslPackage.CLASSIFY_MODULES: return createClassifyModules();
      case TransformationDslPackage.LAYER: return createLayer();
      case TransformationDslPackage.FROM: return createFrom();
      case TransformationDslPackage.RESOURCE_SET: return createResourceSet();
      case TransformationDslPackage.MODULE_IDENTIFIER: return createModuleIdentifier();
      case TransformationDslPackage.RESOURCE_LIST: return createResourceList();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TransformationModel createTransformationModel()
  {
    TransformationModelImpl transformationModel = new TransformationModelImpl();
    return transformationModel;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Transformation createTransformation()
  {
    TransformationImpl transformation = new TransformationImpl();
    return transformation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RemoveFrom createRemoveFrom()
  {
    RemoveFromImpl removeFrom = new RemoveFromImpl();
    return removeFrom;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EmbedInto createEmbedInto()
  {
    EmbedIntoImpl embedInto = new EmbedIntoImpl();
    return embedInto;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CreateModule createCreateModule()
  {
    CreateModuleImpl createModule = new CreateModuleImpl();
    return createModule;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ClassifyModules createClassifyModules()
  {
    ClassifyModulesImpl classifyModules = new ClassifyModulesImpl();
    return classifyModules;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Layer createLayer()
  {
    LayerImpl layer = new LayerImpl();
    return layer;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public From createFrom()
  {
    FromImpl from = new FromImpl();
    return from;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ResourceSet createResourceSet()
  {
    ResourceSetImpl resourceSet = new ResourceSetImpl();
    return resourceSet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModuleIdentifier createModuleIdentifier()
  {
    ModuleIdentifierImpl moduleIdentifier = new ModuleIdentifierImpl();
    return moduleIdentifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ResourceList createResourceList()
  {
    ResourceListImpl resourceList = new ResourceListImpl();
    return resourceList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TransformationDslPackage getTransformationDslPackage()
  {
    return (TransformationDslPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static TransformationDslPackage getPackage()
  {
    return TransformationDslPackage.eINSTANCE;
  }

} //TransformationDslFactoryImpl
