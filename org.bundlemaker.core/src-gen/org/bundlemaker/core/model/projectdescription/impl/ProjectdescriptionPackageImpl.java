/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.projectdescription.impl;

import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.model.module.ModulePackage;

import org.bundlemaker.core.model.module.impl.ModulePackageImpl;

import org.bundlemaker.core.model.projectdescription.ContentType;
import org.bundlemaker.core.model.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.model.projectdescription.IFileBasedContent;
import org.bundlemaker.core.model.projectdescription.IResourceContent;
import org.bundlemaker.core.model.projectdescription.ProjectdescriptionFactory;
import org.bundlemaker.core.model.projectdescription.ProjectdescriptionPackage;

import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionPackage;

import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.impl.ModifiableprojectdescriptionPackageImpl;

import org.bundlemaker.core.model.transformation.TransformationPackage;

import org.bundlemaker.core.model.transformation.impl.TransformationPackageImpl;

import org.bundlemaker.core.resource.IResourceStandin;
import org.bundlemaker.core.resource.ResourceStandin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProjectdescriptionPackageImpl extends EPackageImpl implements ProjectdescriptionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iFileBasedContentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iResourceContentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBundleMakerProjectDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum contentTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType ipathEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType coreexceptionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iResourceStandinEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType resourceStandinEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType bundlemakerprojectEDataType = null;

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
	 * @see org.bundlemaker.core.model.projectdescription.ProjectdescriptionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ProjectdescriptionPackageImpl() {
		super(eNS_URI, ProjectdescriptionFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ProjectdescriptionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ProjectdescriptionPackage init() {
		if (isInited) return (ProjectdescriptionPackage)EPackage.Registry.INSTANCE.getEPackage(ProjectdescriptionPackage.eNS_URI);

		// Obtain or create and register package
		ProjectdescriptionPackageImpl theProjectdescriptionPackage = (ProjectdescriptionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ProjectdescriptionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ProjectdescriptionPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModifiableprojectdescriptionPackageImpl theModifiableprojectdescriptionPackage = (ModifiableprojectdescriptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModifiableprojectdescriptionPackage.eNS_URI) instanceof ModifiableprojectdescriptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModifiableprojectdescriptionPackage.eNS_URI) : ModifiableprojectdescriptionPackage.eINSTANCE);
		ModulePackageImpl theModulePackage = (ModulePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModulePackage.eNS_URI) instanceof ModulePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModulePackage.eNS_URI) : ModulePackage.eINSTANCE);
		TransformationPackageImpl theTransformationPackage = (TransformationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TransformationPackage.eNS_URI) instanceof TransformationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TransformationPackage.eNS_URI) : TransformationPackage.eINSTANCE);

		// Create package meta-data objects
		theProjectdescriptionPackage.createPackageContents();
		theModifiableprojectdescriptionPackage.createPackageContents();
		theModulePackage.createPackageContents();
		theTransformationPackage.createPackageContents();

		// Initialize created meta-data
		theProjectdescriptionPackage.initializePackageContents();
		theModifiableprojectdescriptionPackage.initializePackageContents();
		theModulePackage.initializePackageContents();
		theTransformationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theProjectdescriptionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ProjectdescriptionPackage.eNS_URI, theProjectdescriptionPackage);
		return theProjectdescriptionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIFileBasedContent() {
		return iFileBasedContentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIResourceContent() {
		return iResourceContentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBundleMakerProjectDescription() {
		return iBundleMakerProjectDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getContentType() {
		return contentTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getipath() {
		return ipathEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getcoreexception() {
		return coreexceptionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getiResourceStandin() {
		return iResourceStandinEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getresourceStandin() {
		return resourceStandinEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getbundlemakerproject() {
		return bundlemakerprojectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectdescriptionFactory getProjectdescriptionFactory() {
		return (ProjectdescriptionFactory)getEFactoryInstance();
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
		iFileBasedContentEClass = createEClass(IFILE_BASED_CONTENT);

		iResourceContentEClass = createEClass(IRESOURCE_CONTENT);

		iBundleMakerProjectDescriptionEClass = createEClass(IBUNDLE_MAKER_PROJECT_DESCRIPTION);

		// Create enums
		contentTypeEEnum = createEEnum(CONTENT_TYPE);

		// Create data types
		ipathEDataType = createEDataType(IPATH);
		coreexceptionEDataType = createEDataType(COREEXCEPTION);
		iResourceStandinEDataType = createEDataType(IRESOURCE_STANDIN);
		resourceStandinEDataType = createEDataType(RESOURCE_STANDIN);
		bundlemakerprojectEDataType = createEDataType(BUNDLEMAKERPROJECT);
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
		ModifiableprojectdescriptionPackage theModifiableprojectdescriptionPackage = (ModifiableprojectdescriptionPackage)EPackage.Registry.INSTANCE.getEPackage(ModifiableprojectdescriptionPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theModifiableprojectdescriptionPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(iFileBasedContentEClass, IFileBasedContent.class, "IFileBasedContent", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(iFileBasedContentEClass, ecorePackage.getEString(), "getId", 1, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(iFileBasedContentEClass, ecorePackage.getEString(), "getName", 1, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(iFileBasedContentEClass, ecorePackage.getEString(), "getVersion", 1, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(iFileBasedContentEClass, this.getipath(), "getBinaryPaths", 0, -1, IS_UNIQUE, IS_ORDERED);

		addEOperation(iFileBasedContentEClass, ecorePackage.getEBoolean(), "isResourceContent", 1, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(iFileBasedContentEClass, this.getIResourceContent(), "getResourceContent", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(iResourceContentEClass, IResourceContent.class, "IResourceContent", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(iResourceContentEClass, this.getipath(), "getSourcePaths", 0, -1, IS_UNIQUE, IS_ORDERED);

		addEOperation(iResourceContentEClass, ecorePackage.getEBoolean(), "isAnalyzeSourceResources", 0, 1, IS_UNIQUE, IS_ORDERED);

		EOperation op = addEOperation(iResourceContentEClass, this.getiResourceStandin(), "getResource", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getipath(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getContentType(), "type", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iResourceContentEClass, this.getiResourceStandin(), "getResources", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getContentType(), "type", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iResourceContentEClass, this.getiResourceStandin(), "getBinaryResource", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getipath(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(iResourceContentEClass, this.getiResourceStandin(), "getBinaryResources", 0, -1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iResourceContentEClass, this.getiResourceStandin(), "getSourceResource", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getipath(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(iResourceContentEClass, this.getiResourceStandin(), "getSourceResources", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(iBundleMakerProjectDescriptionEClass, IBundleMakerProjectDescription.class, "IBundleMakerProjectDescription", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(iBundleMakerProjectDescriptionEClass, this.getIFileBasedContent(), "getFileBasedContent", 0, -1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iBundleMakerProjectDescriptionEClass, this.getIFileBasedContent(), "getFileBasedContent", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "id", 0, 1, IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(contentTypeEEnum, ContentType.class, "ContentType");
		addEEnumLiteral(contentTypeEEnum, ContentType.SOURCE);
		addEEnumLiteral(contentTypeEEnum, ContentType.BINARY);

		// Initialize data types
		initEDataType(ipathEDataType, IPath.class, "ipath", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(coreexceptionEDataType, CoreException.class, "coreexception", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(iResourceStandinEDataType, IResourceStandin.class, "iResourceStandin", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(resourceStandinEDataType, ResourceStandin.class, "resourceStandin", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(bundlemakerprojectEDataType, BundleMakerProject.class, "bundlemakerproject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //ProjectdescriptionPackageImpl
