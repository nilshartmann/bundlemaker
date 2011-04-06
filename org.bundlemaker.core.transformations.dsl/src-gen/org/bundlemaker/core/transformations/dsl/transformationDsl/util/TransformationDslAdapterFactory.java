/**
 * <copyright>
 * </copyright>
 *

 */
package org.bundlemaker.core.transformations.dsl.transformationDsl.util;

import org.bundlemaker.core.transformations.dsl.transformationDsl.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage
 * @generated
 */
public class TransformationDslAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static TransformationDslPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TransformationDslAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = TransformationDslPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TransformationDslSwitch<Adapter> modelSwitch =
    new TransformationDslSwitch<Adapter>()
    {
      @Override
      public Adapter caseTransformationModel(TransformationModel object)
      {
        return createTransformationModelAdapter();
      }
      @Override
      public Adapter caseTransformation(Transformation object)
      {
        return createTransformationAdapter();
      }
      @Override
      public Adapter caseRemoveFrom(RemoveFrom object)
      {
        return createRemoveFromAdapter();
      }
      @Override
      public Adapter caseEmbedInto(EmbedInto object)
      {
        return createEmbedIntoAdapter();
      }
      @Override
      public Adapter caseCreateModule(CreateModule object)
      {
        return createCreateModuleAdapter();
      }
      @Override
      public Adapter caseFrom(From object)
      {
        return createFromAdapter();
      }
      @Override
      public Adapter caseResourceSet(ResourceSet object)
      {
        return createResourceSetAdapter();
      }
      @Override
      public Adapter caseModuleIdentifier(ModuleIdentifier object)
      {
        return createModuleIdentifierAdapter();
      }
      @Override
      public Adapter caseResourceList(ResourceList object)
      {
        return createResourceListAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel <em>Transformation Model</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel
   * @generated
   */
  public Adapter createTransformationModelAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.Transformation <em>Transformation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.Transformation
   * @generated
   */
  public Adapter createTransformationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.RemoveFrom <em>Remove From</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.RemoveFrom
   * @generated
   */
  public Adapter createRemoveFromAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto <em>Embed Into</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto
   * @generated
   */
  public Adapter createEmbedIntoAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule <em>Create Module</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule
   * @generated
   */
  public Adapter createCreateModuleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.From <em>From</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.From
   * @generated
   */
  public Adapter createFromAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet <em>Resource Set</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet
   * @generated
   */
  public Adapter createResourceSetAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier <em>Module Identifier</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier
   * @generated
   */
  public Adapter createModuleIdentifierAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceList <em>Resource List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceList
   * @generated
   */
  public Adapter createResourceListAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //TransformationDslAdapterFactory
