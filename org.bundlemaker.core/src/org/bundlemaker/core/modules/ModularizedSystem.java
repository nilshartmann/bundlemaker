package org.bundlemaker.core.modules;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bundlemaker.core.internal.JdkModuleCreator;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.spi.resource.Type;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class ModularizedSystem implements IModularizedSystem {

	/** the name of working copy */
	private String _name;

	/** the project description */
	private IBundleMakerProjectDescription _projectDescription;

	/** the list of defined transformations */
	private List<ITransformation> _transformations;

	/** the defined resource modules */
	private Set<ResourceModule> _resourceModules;

	/** the defined type modules */
	private Set<TypeModule> _typeModules;

	/** the execution environment type module */
	private TypeModule _executionEnvironment;

	/** type name -> modules */
	private Map<String, Set<ITypeModule>> _typeToModuleListMap;

	/**
	 * <p>
	 * Creates a new instance of type {@link ModularizedSystem}.
	 * </p>
	 * 
	 * @param name
	 */
	public ModularizedSystem(String name,
			IBundleMakerProjectDescription projectDescription) {

		_name = name;

		_projectDescription = projectDescription;

		_transformations = new LinkedList<ITransformation>();
		_resourceModules = new HashSet<ResourceModule>();
		_typeModules = new HashSet<TypeModule>();
		_typeToModuleListMap = new HashMap<String, Set<ITypeModule>>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return _name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBundleMakerProjectDescription getProjectDescription() {
		return _projectDescription;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ITransformation> getTransformations() {
		return _transformations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void applyTransformations() {

		// step 1: clear prior results
		_typeModules.clear();
		_resourceModules.clear();
		_typeToModuleListMap.clear();

		// step 2: set up the JRE
		// TODO!!!!
		System.out.println("// step 2: set up the JRE");
		try {

			TypeModule jdkModule = JdkModuleCreator.getJdkModules().get(0);

			_typeModules.add(jdkModule);

			_executionEnvironment = jdkModule;

		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// step 3: create the type modules
		System.out.println("// step 3: create the type modules");

		for (IFileBasedContent fileBasedContent : _projectDescription
				.getFileBasedContent()) {

			if (!fileBasedContent.isResourceContent()) {

				IModuleIdentifier identifier = new ModuleIdentifier(
						fileBasedContent.getName(),
						fileBasedContent.getVersion());

				// TODO!!
				TypeModule typeModule = createTypeModule(
						identifier,
						new File[] { fileBasedContent.getBinaryPaths().toArray(
								new IPath[0])[0].toFile() });

				_typeModules.add(typeModule);

			}
		}

		// INITIALIZE TYPE MODULES

		//
		for (TypeModule module : _typeModules) {

			//
			for (String containedType : module.getContainedTypeNames()) {

				//
				if (!_typeToModuleListMap.containsKey(containedType)) {

					Set<ITypeModule> moduleList = new HashSet<ITypeModule>();

					_typeToModuleListMap.put(containedType, moduleList);
				}

				_typeToModuleListMap.get(containedType).add(module);
			}
		}

		// step 4: transform modules
		System.out.println("// step 4: transform modules");
		for (ITransformation transformation : _transformations) {

			// step 4.1: apply transformation
			transformation.apply(this);

			// step 4.2: clean up empty modules
			for (Iterator<ResourceModule> iterator = _resourceModules
					.iterator(); iterator.hasNext();) {

				// get next module
				ResourceModule module = iterator.next();

				// if the module is empty - remove it
				if (module.getResources(ContentType.BINARY).isEmpty()
						&& module.getResources(ContentType.SOURCE).isEmpty()) {

					// remove the module
					iterator.remove();
				}
			}

			// step 4.3: clean up empty modules
			initializedResourceModules();
		}

		System.out.println("// done");

	}

	private void initializedResourceModules() {

		//
		_typeToModuleListMap.clear();

		// step 1: set up the resource modules
		for (ResourceModule module : _resourceModules) {

			// initialize the contained types
			module.initializeContainedTypes();

			// get
			for (String containedType : module.getContainedTypeNames()) {

				//
				if (!_typeToModuleListMap.containsKey(containedType)) {

					Set<ITypeModule> moduleList = new HashSet<ITypeModule>();

					_typeToModuleListMap.put(containedType, moduleList);
				}

				// add
				_typeToModuleListMap.get(containedType).add(module);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<ITypeModule> getAllModules() {

		// create the result list
		Set<ITypeModule> result = new HashSet<ITypeModule>(_typeModules.size()
				+ _resourceModules.size());

		// all all modules
		result.addAll(_typeModules);
		result.addAll(_resourceModules);

		// return an unmodifiable copy
		return Collections.unmodifiableSet(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<ITypeModule> getTypeModules() {
		return Collections
				.unmodifiableSet((Set<? extends ITypeModule>) _typeModules);
	}

	@Override
	public ITypeModule getTypeModule(IModuleIdentifier identifier) {

		//
		for (TypeModule module : _typeModules) {

			if (module.getModuleIdentifier().equals(identifier)) {
				return module;
			}
		}

		//
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<IResourceModule> getResourceModules() {
		return Collections
				.unmodifiableSet((Set<? extends IResourceModule>) _resourceModules);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IResourceModule getResourceModule(IModuleIdentifier identifier) {

		//
		return getModifiableResourceModule(identifier);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param identifier
	 * @return
	 */
	public ResourceModule getModifiableResourceModule(
			IModuleIdentifier identifier) {

		//
		for (ResourceModule module : _resourceModules) {

			if (module.getModuleIdentifier().equals(identifier)) {
				return module;
			}
		}

		//
		return null;
	}

	public Set<ResourceModule> getModifiableResourceModules() {

		//
		return _resourceModules;
	}

	@Override
	public Set<IResource> getUnassignedResources() {

		// TODO

		// Set<IResource> result = new HashSet<IResource>();
		//
		// for (IFileBasedContent content : _projectDescription
		// .getFileBasedContent()) {
		//
		// if (content.isResourceContent()) {
		//
		// for (IResource resourceStandin : content
		// .getResourceContent().getBinaryResources()) {
		//
		// if (resourceStandin.getContainingModules().isEmpty()) {
		// result.add(resourceStandin);
		// }
		// }
		//
		// for (IResource resourceStandin : content
		// .getResourceContent().getSourceResources()) {
		//
		// if (resourceStandin.getContainingModules().isEmpty()) {
		// result.add(resourceStandin);
		// }
		// }
		// }
		// }
		//
		// // return the result
		// return result;

		return null;
	}

	@Override
	public Set<ITypeModule> getContainingModules(String fullyQualifiedName) {

		//
		if (_typeToModuleListMap.containsKey(fullyQualifiedName)) {

			Set<ITypeModule> result = _typeToModuleListMap
					.get(fullyQualifiedName);

			//
			return Collections.unmodifiableSet(result);

		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public ITypeModule getContainingModule(String fullyQualifiedName)
			throws AmbiguousDependencyException {

		Set<ITypeModule> result = getContainingModules(fullyQualifiedName);

		if (result.isEmpty()) {
			return null;
		}

		if (result.size() > 1) {
			throw new AmbiguousDependencyException(
					"AmbiguousModuleDependencyException: " + fullyQualifiedName);
		}

		return result.toArray(new ITypeModule[0])[0];
	}

	@Override
	public IType getType(String fullyQualifiedName)
			throws AmbiguousDependencyException {

		Assert.isNotNull(fullyQualifiedName);

		// get type modules
		Set<ITypeModule> typeModules = _typeToModuleListMap
				.get(fullyQualifiedName);

		// return null if type is unknown
		if (typeModules == null) {
			return null;
		}

		// if multiple type modules exist, throw an exception
		if (typeModules.size() > 1) {

			// TODO
			new AmbiguousDependencyException(fullyQualifiedName);
		}

		// get the type module
		ITypeModule typeModule = typeModules.toArray(new ITypeModule[0])[0];

		// return the type
		return typeModule.getType(fullyQualifiedName);
	}

	@Override
	public IReferencedModulesQueryResult getReferencedModules(
			IResourceModule module, boolean hideContainedTypes,
			boolean includeSourceReferences) {

		// create the result list
		ReferencedModulesQueryResult result = new ReferencedModulesQueryResult(
				module);

		// TODO: getReferencedTypes(???, ???)
		for (IReference reference : module.getAllReferences(hideContainedTypes,
				includeSourceReferences, includeSourceReferences)) {
			_resolveReferencedModules(result, reference);
		}

		// return the result
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IReferencedModulesQueryResult getReferencedModules(IResource resource) {

		// create the result list
		ReferencedModulesQueryResult result = new ReferencedModulesQueryResult();

		//
		for (IReference reference : resource.getReferences()) {
			_resolveReferencedModules(result, reference);
		}

		// return the result
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getUnsatisfiedReferencedTypes(IResourceModule module,
			boolean hideContainedTypes, boolean includeSourceReferences) {

		// create the result
		Set<String> result = new HashSet<String>();

		// iterate over the referenced types
		for (String referencedType : module.getReferencedTypes(
				hideContainedTypes, includeSourceReferences, false)) {

			// get the module list
			Set<ITypeModule> moduleList = _typeToModuleListMap
					.get(referencedType);

			// unsatisfied?
			if (moduleList == null || moduleList.isEmpty()) {
				result.add(referencedType);
			}
		}

		// return the result
		return result;
	}

	@Override
	public Set<String> getUnsatisfiedReferencedPackages(IResourceModule module,
			boolean hideContainedTypes, boolean includeSourceReferences) {

		// create the result
		Set<String> result = new HashSet<String>();

		//
		Set<String> unsatisfiedTypes = getUnsatisfiedReferencedTypes(module,
				hideContainedTypes, includeSourceReferences);

		for (String unsatisfiedType : unsatisfiedTypes) {

			if (unsatisfiedType.indexOf('.') != -1) {

				//
				String packageName = unsatisfiedType.substring(0,
						unsatisfiedType.lastIndexOf('.'));

				//
				if (!result.contains(packageName)) {
					result.add(packageName);
				}
			}
		}

		// // iterate over the packages
		// for (String referencedPackage : module.getReferencedPackages(
		// hideContainedTypes, includeSourceReferences)) {
		//
		// //
		// boolean contained = false;
		//
		// //
		// for (ITypeModule iTypeModule : modularizedSystem.getAllModules()) {
		//
		// System.out.println("bam");
		// if (iTypeModule.getContainedPackages().contains(
		// referencedPackage)) {
		// contained = true;
		// break;
		// }
		// }
		//
		// if (!contained) {
		// result.add(referencedPackage);
		// }
		// }

		// return the result
		return result;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Set<ITypeModule>> getAmbiguousPackages() {

		// create the result map
		Map<String, Set<ITypeModule>> result = new HashMap<String, Set<ITypeModule>>();

		// create the temp map
		Map<String, ITypeModule> tempMap = new HashMap<String, ITypeModule>();

		// iterate over all modules
		for (ITypeModule typeModule : getAllModules()) {

			// iterate over contained packages
			for (String containedPackage : typeModule
					.getContainedPackageNames()) {

				// add
				if (result.containsKey(containedPackage)) {

					result.get(containedPackage).add(typeModule);
				}

				//
				else if (tempMap.containsKey(containedPackage)) {

					//
					Set<ITypeModule> newSet = new HashSet<ITypeModule>();

					//
					result.put(containedPackage, newSet);

					// add module to module list
					newSet.add(typeModule);
					newSet.add(tempMap.remove(containedPackage));
				}

				// put in the temp map
				else {
					tempMap.put(containedPackage, typeModule);
				}
			}
		}

		// return the result
		return result;
	}

	@Override
	public ITypeModule getExecutionEnvironmentTypeModule() {
		return _executionEnvironment;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param identifier
	 * @param file
	 * @return
	 */
	public TypeModule createTypeModule(IModuleIdentifier identifier, File file) {
		return createTypeModule(identifier, new File[] { file });
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param identifier
	 * @param files
	 * @return
	 */
	public TypeModule createTypeModule(IModuleIdentifier identifier,
			File[] files) {

		// create the type module
		TypeModule typeModule = new TypeModule(identifier);

		//
		for (int i = 0; i < files.length; i++) {

			// add all the contained types
			try {

				// TODO DIRECTORIES!!
				// TODO:PARSE!!
				List<String> types = getContainedTypes(files[i]);

				for (String type : types) {

					// TODO: TypeEnum!!
					typeModule.getSelfContainer()
							.getModifiableContainedTypesMap()
							.put(type, new Type(type, TypeEnum.CLASS));
				}

			} catch (IOException e) {

				//
				e.printStackTrace();
			}
		}
		// return the module
		return typeModule;
	}

	public ResourceModule createResourceModule(
			IModuleIdentifier createModuleIdentifier) {

		//
		ResourceModule resourceModule = new ResourceModule(
				createModuleIdentifier);

		//
		_resourceModules.add(resourceModule);

		//
		return resourceModule;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static List<String> getContainedTypes(File file) throws IOException {

		// create the result list
		List<String> result = new LinkedList<String>();

		// create the jar file
		JarFile jarFile = new JarFile(file);

		// get the entries
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry jarEntry = (JarEntry) entries.nextElement();
			if (jarEntry.getName().endsWith(".class")) {
				result.add(jarEntry
						.getName()
						.substring(0,
								jarEntry.getName().length() - ".class".length())
						.replace('/', '.'));
			}
		}

		// return the result
		return result;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 * @param result
	 * @param fullyQualifiedType
	 */
	private void _resolveReferencedModules(ReferencedModulesQueryResult result,
			IReference reference) {

		Assert.isNotNull(result);
		Assert.isNotNull(reference);

		// TODO: already set?

		Set<ITypeModule> containingModules = _getContainingModules(reference
				.getFullyQualifiedName());

		//
		if (containingModules.isEmpty()) {

			//
			result.getUnsatisfiedReferences().add(reference);

		} else if (containingModules.size() > 1) {

			if (!result.getReferencesWithAmbiguousModules().containsKey(
					reference)) {

				result.getReferencesWithAmbiguousModules().put(reference,
						new HashSet<ITypeModule>());
			}

			result.getReferencesWithAmbiguousModules().get(reference)
					.addAll(containingModules);

		} else {

			result.getReferencedModulesMap().put(reference,
					containingModules.toArray(new ITypeModule[0])[0]);
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 * @param fullyQualifiedName
	 * @return
	 */
	private Set<ITypeModule> _getContainingModules(String fullyQualifiedName) {

		//
		if (_typeToModuleListMap.containsKey(fullyQualifiedName)) {
			return _typeToModuleListMap.get(fullyQualifiedName);
		} else {
			return Collections.emptySet();
		}
	}
}
