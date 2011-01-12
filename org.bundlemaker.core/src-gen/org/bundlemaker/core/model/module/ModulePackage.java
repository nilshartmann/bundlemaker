/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.module;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

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
 * @see org.bundlemaker.core.model.module.ModuleFactory
 * @model kind="package"
 * @generated
 */
public interface ModulePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "module";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.bundlemaker.org/org/bundlemaker/core/model/module";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "module";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModulePackage eINSTANCE = org.bundlemaker.core.model.module.impl.ModulePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.module.IModuleIdentifier <em>IModule Identifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.module.IModuleIdentifier
	 * @see org.bundlemaker.core.model.module.impl.ModulePackageImpl#getIModuleIdentifier()
	 * @generated
	 */
	int IMODULE_IDENTIFIER = 0;

	/**
	 * The number of structural features of the '<em>IModule Identifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODULE_IDENTIFIER_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.bundlemaker.core.model.module.impl.ModifiableModuleIdentifierImpl <em>Modifiable Module Identifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.model.module.impl.ModifiableModuleIdentifierImpl
	 * @see org.bundlemaker.core.model.module.impl.ModulePackageImpl#getModifiableModuleIdentifier()
	 * @generated
	 */
	int MODIFIABLE_MODULE_IDENTIFIER = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_MODULE_IDENTIFIER__NAME = IMODULE_IDENTIFIER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_MODULE_IDENTIFIER__VERSION = IMODULE_IDENTIFIER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Modifiable Module Identifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_MODULE_IDENTIFIER_FEATURE_COUNT = IMODULE_IDENTIFIER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '<em>typemodule</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.modules.ITypeModule
	 * @see org.bundlemaker.core.model.module.impl.ModulePackageImpl#gettypemodule()
	 * @generated
	 */
	int TYPEMODULE = 2;


	/**
	 * The meta object id for the '<em>resourcemodule</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.bundlemaker.core.modules.IResourceModule
	 * @see org.bundlemaker.core.model.module.impl.ModulePackageImpl#getresourcemodule()
	 * @generated
	 */
	int RESOURCEMODULE = 3;


	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.module.IModuleIdentifier <em>IModule Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IModule Identifier</em>'.
	 * @see org.bundlemaker.core.model.module.IModuleIdentifier
	 * @generated
	 */
	EClass getIModuleIdentifier();

	/**
	 * Returns the meta object for class '{@link org.bundlemaker.core.model.module.ModifiableModuleIdentifier <em>Modifiable Module Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Modifiable Module Identifier</em>'.
	 * @see org.bundlemaker.core.model.module.ModifiableModuleIdentifier
	 * @generated
	 */
	EClass getModifiableModuleIdentifier();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.module.ModifiableModuleIdentifier#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.bundlemaker.core.model.module.ModifiableModuleIdentifier#getName()
	 * @see #getModifiableModuleIdentifier()
	 * @generated
	 */
	EAttribute getModifiableModuleIdentifier_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.bundlemaker.core.model.module.ModifiableModuleIdentifier#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.bundlemaker.core.model.module.ModifiableModuleIdentifier#getVersion()
	 * @see #getModifiableModuleIdentifier()
	 * @generated
	 */
	EAttribute getModifiableModuleIdentifier_Version();

	/**
	 * Returns the meta object for data type '{@link org.bundlemaker.core.modules.ITypeModule <em>typemodule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>typemodule</em>'.
	 * @see org.bundlemaker.core.modules.ITypeModule
	 * @model instanceClass="org.bundlemaker.core.modules.ITypeModule"
	 * @generated
	 */
	EDataType gettypemodule();

	/**
	 * Returns the meta object for data type '{@link org.bundlemaker.core.modules.IResourceModule <em>resourcemodule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>resourcemodule</em>'.
	 * @see org.bundlemaker.core.modules.IResourceModule
	 * @model instanceClass="org.bundlemaker.core.modules.IResourceModule"
	 * @generated
	 */
	EDataType getresourcemodule();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModuleFactory getModuleFactory();

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
		 * The meta object literal for the '{@link org.bundlemaker.core.model.module.IModuleIdentifier <em>IModule Identifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.module.IModuleIdentifier
		 * @see org.bundlemaker.core.model.module.impl.ModulePackageImpl#getIModuleIdentifier()
		 * @generated
		 */
		EClass IMODULE_IDENTIFIER = eINSTANCE.getIModuleIdentifier();

		/**
		 * The meta object literal for the '{@link org.bundlemaker.core.model.module.impl.ModifiableModuleIdentifierImpl <em>Modifiable Module Identifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.model.module.impl.ModifiableModuleIdentifierImpl
		 * @see org.bundlemaker.core.model.module.impl.ModulePackageImpl#getModifiableModuleIdentifier()
		 * @generated
		 */
		EClass MODIFIABLE_MODULE_IDENTIFIER = eINSTANCE.getModifiableModuleIdentifier();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_MODULE_IDENTIFIER__NAME = eINSTANCE.getModifiableModuleIdentifier_Name();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_MODULE_IDENTIFIER__VERSION = eINSTANCE.getModifiableModuleIdentifier_Version();

		/**
		 * The meta object literal for the '<em>typemodule</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.modules.ITypeModule
		 * @see org.bundlemaker.core.model.module.impl.ModulePackageImpl#gettypemodule()
		 * @generated
		 */
		EDataType TYPEMODULE = eINSTANCE.gettypemodule();

		/**
		 * The meta object literal for the '<em>resourcemodule</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.bundlemaker.core.modules.IResourceModule
		 * @see org.bundlemaker.core.model.module.impl.ModulePackageImpl#getresourcemodule()
		 * @generated
		 */
		EDataType RESOURCEMODULE = eINSTANCE.getresourcemodule();

	}

} //ModulePackage
