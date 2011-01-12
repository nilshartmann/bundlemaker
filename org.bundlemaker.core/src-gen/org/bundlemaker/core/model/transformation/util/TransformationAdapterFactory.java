/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation.util;

import java.util.Map;
import org.bundlemaker.core.model.transformation.*;

import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.bundlemaker.core.model.transformation.TransformationPackage
 * @generated
 */
public class TransformationAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TransformationPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TransformationPackage.eINSTANCE;
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
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
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
	protected TransformationSwitch<Adapter> modelSwitch =
		new TransformationSwitch<Adapter>() {
			@Override
			public Adapter caseAbstractETransformation(ITransformation object) {
				return createAbstractETransformationAdapter();
			}
			@Override
			public Adapter caseResourceSet(ResourceSet object) {
				return createResourceSetAdapter();
			}
			@Override
			public Adapter caseResourceSetBasedModuleDefinition(ResourceSetBasedModuleDefinition object) {
				return createResourceSetBasedModuleDefinitionAdapter();
			}
			@Override
			public Adapter caseResourceSetBasedTransformation(ResourceSetBasedTransformation object) {
				return createResourceSetBasedTransformationAdapter();
			}
			@Override
			public Adapter caseBasicProjectContentTransformation(BasicProjectContentTransformation object) {
				return createBasicProjectContentTransformationAdapter();
			}
			@Override
			public Adapter caseEmbedModuleTransformation(EmbedModuleTransformation object) {
				return createEmbedModuleTransformationAdapter();
			}
			@Override
			public Adapter caseRemoveResourcesTransformation(RemoveResourcesTransformation object) {
				return createRemoveResourcesTransformationAdapter();
			}
			@Override
			public Adapter caseStringToObjectMap(Map.Entry<String, Object> object) {
				return createStringToObjectMapAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
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
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.bundlemaker.core.transformation.ITransformation <em>Abstract ETransformation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.bundlemaker.core.transformation.ITransformation
	 * @generated
	 */
	public Adapter createAbstractETransformationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.bundlemaker.core.model.transformation.ResourceSet <em>Resource Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.bundlemaker.core.model.transformation.ResourceSet
	 * @generated
	 */
	public Adapter createResourceSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition <em>Resource Set Based Module Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition
	 * @generated
	 */
	public Adapter createResourceSetBasedModuleDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation <em>Resource Set Based Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation
	 * @generated
	 */
	public Adapter createResourceSetBasedTransformationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.bundlemaker.core.model.transformation.BasicProjectContentTransformation <em>Basic Project Content Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.bundlemaker.core.model.transformation.BasicProjectContentTransformation
	 * @generated
	 */
	public Adapter createBasicProjectContentTransformationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.bundlemaker.core.model.transformation.EmbedModuleTransformation <em>Embed Module Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.bundlemaker.core.model.transformation.EmbedModuleTransformation
	 * @generated
	 */
	public Adapter createEmbedModuleTransformationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.bundlemaker.core.model.transformation.RemoveResourcesTransformation <em>Remove Resources Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.bundlemaker.core.model.transformation.RemoveResourcesTransformation
	 * @generated
	 */
	public Adapter createRemoveResourcesTransformationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>String To Object Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createStringToObjectMapAdapter() {
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
	public Adapter createEObjectAdapter() {
		return null;
	}

} //TransformationAdapterFactory
