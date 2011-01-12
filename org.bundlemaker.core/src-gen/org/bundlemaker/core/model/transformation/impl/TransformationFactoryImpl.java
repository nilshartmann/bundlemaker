/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation.impl;

import java.util.Map;

import org.bundlemaker.core.model.transformation.*;
import org.bundlemaker.core.model.transformation.BasicProjectContentTransformation;
import org.bundlemaker.core.model.transformation.EmbedModuleTransformation;
import org.bundlemaker.core.model.transformation.RemoveResourcesTransformation;
import org.bundlemaker.core.model.transformation.ResourceSet;
import org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition;
import org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation;
import org.bundlemaker.core.model.transformation.TransformationFactory;
import org.bundlemaker.core.model.transformation.TransformationPackage;
import org.bundlemaker.core.transformation.resourceset.IResourceSetProcessor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
public class TransformationFactoryImpl extends EFactoryImpl implements TransformationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TransformationFactory init() {
		try {
			TransformationFactory theTransformationFactory = (TransformationFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.bundlemaker.org/org/bundlemaker/core/model/transformation"); 
			if (theTransformationFactory != null) {
				return theTransformationFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TransformationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TransformationPackage.RESOURCE_SET: return createResourceSet();
			case TransformationPackage.RESOURCE_SET_BASED_MODULE_DEFINITION: return createResourceSetBasedModuleDefinition();
			case TransformationPackage.RESOURCE_SET_BASED_TRANSFORMATION: return createResourceSetBasedTransformation();
			case TransformationPackage.BASIC_PROJECT_CONTENT_TRANSFORMATION: return createBasicProjectContentTransformation();
			case TransformationPackage.EMBED_MODULE_TRANSFORMATION: return createEmbedModuleTransformation();
			case TransformationPackage.REMOVE_RESOURCES_TRANSFORMATION: return createRemoveResourcesTransformation();
			case TransformationPackage.STRING_TO_OBJECT_MAP: return (EObject)createStringToObjectMap();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case TransformationPackage.IRESOURCE_SET_PROCESSOR:
				return createiResourceSetProcessorFromString(eDataType, initialValue);
			case TransformationPackage.STRING_ARRAY:
				return createstringArrayFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case TransformationPackage.IRESOURCE_SET_PROCESSOR:
				return convertiResourceSetProcessorToString(eDataType, instanceValue);
			case TransformationPackage.STRING_ARRAY:
				return convertstringArrayToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceSet createResourceSet() {
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		return resourceSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceSetBasedModuleDefinition createResourceSetBasedModuleDefinition() {
		ResourceSetBasedModuleDefinitionImpl resourceSetBasedModuleDefinition = new ResourceSetBasedModuleDefinitionImpl();
		return resourceSetBasedModuleDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceSetBasedTransformation createResourceSetBasedTransformation() {
		ResourceSetBasedTransformationImpl resourceSetBasedTransformation = new ResourceSetBasedTransformationImpl();
		return resourceSetBasedTransformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasicProjectContentTransformation createBasicProjectContentTransformation() {
		BasicProjectContentTransformationImpl basicProjectContentTransformation = new BasicProjectContentTransformationImpl();
		return basicProjectContentTransformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmbedModuleTransformation createEmbedModuleTransformation() {
		EmbedModuleTransformationImpl embedModuleTransformation = new EmbedModuleTransformationImpl();
		return embedModuleTransformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemoveResourcesTransformation createRemoveResourcesTransformation() {
		RemoveResourcesTransformationImpl removeResourcesTransformation = new RemoveResourcesTransformationImpl();
		return removeResourcesTransformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, Object> createStringToObjectMap() {
		StringToObjectMapImpl stringToObjectMap = new StringToObjectMapImpl();
		return stringToObjectMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IResourceSetProcessor createiResourceSetProcessorFromString(EDataType eDataType, String initialValue) {
		return (IResourceSetProcessor)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertiResourceSetProcessorToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String[] createstringArrayFromString(EDataType eDataType, String initialValue) {
		return (String[])super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertstringArrayToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationPackage getTransformationPackage() {
		return (TransformationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TransformationPackage getPackage() {
		return TransformationPackage.eINSTANCE;
	}

} //TransformationFactoryImpl
