/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.util;

import org.bundlemaker.core.model.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.model.projectdescription.IFileBasedContent;
import org.bundlemaker.core.model.projectdescription.IResourceContent;

import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage
 * @generated
 */
public class ModifiableprojectdescriptionAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ModifiableprojectdescriptionPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModifiableprojectdescriptionAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ModifiableprojectdescriptionPackage.eINSTANCE;
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
	protected ModifiableprojectdescriptionSwitch<Adapter> modelSwitch =
		new ModifiableprojectdescriptionSwitch<Adapter>() {
			@Override
			public Adapter caseModifiableBundleMakerProjectDescription(ModifiableBundleMakerProjectDescription object) {
				return createModifiableBundleMakerProjectDescriptionAdapter();
			}
			@Override
			public Adapter caseModifiableFileBasedContent(ModifiableFileBasedContent object) {
				return createModifiableFileBasedContentAdapter();
			}
			@Override
			public Adapter caseModifiableResourceContent(ModifiableResourceContent object) {
				return createModifiableResourceContentAdapter();
			}
			@Override
			public Adapter caseIBundleMakerProjectDescription(IBundleMakerProjectDescription object) {
				return createIBundleMakerProjectDescriptionAdapter();
			}
			@Override
			public Adapter caseIFileBasedContent(IFileBasedContent object) {
				return createIFileBasedContentAdapter();
			}
			@Override
			public Adapter caseIResourceContent(IResourceContent object) {
				return createIResourceContentAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription <em>Modifiable Bundle Maker Project Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription
	 * @generated
	 */
	public Adapter createModifiableBundleMakerProjectDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent <em>Modifiable File Based Content</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent
	 * @generated
	 */
	public Adapter createModifiableFileBasedContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent <em>Modifiable Resource Content</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent
	 * @generated
	 */
	public Adapter createModifiableResourceContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.bundlemaker.core.model.projectdescription.IBundleMakerProjectDescription <em>IBundle Maker Project Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.bundlemaker.core.model.projectdescription.IBundleMakerProjectDescription
	 * @generated
	 */
	public Adapter createIBundleMakerProjectDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.bundlemaker.core.model.projectdescription.IFileBasedContent <em>IFile Based Content</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.bundlemaker.core.model.projectdescription.IFileBasedContent
	 * @generated
	 */
	public Adapter createIFileBasedContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.bundlemaker.core.model.projectdescription.IResourceContent <em>IResource Content</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.bundlemaker.core.model.projectdescription.IResourceContent
	 * @generated
	 */
	public Adapter createIResourceContentAdapter() {
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

} //ModifiableprojectdescriptionAdapterFactory
