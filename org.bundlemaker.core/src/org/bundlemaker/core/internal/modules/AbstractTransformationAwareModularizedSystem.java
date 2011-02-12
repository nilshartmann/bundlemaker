package org.bundlemaker.core.internal.modules;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bundlemaker.core.internal.JdkModuleCreator;
import org.bundlemaker.core.internal.resource.Type;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractTransformationAwareModularizedSystem extends
		AbstractModularizedSystem {

	/**
	 * <p>
	 * Creates a new instance of type
	 * {@link AbstractTransformationAwareModularizedSystem}.
	 * </p>
	 * 
	 * @param name
	 * @param projectDescription
	 */
	public AbstractTransformationAwareModularizedSystem(String name,
			IBundleMakerProjectDescription projectDescription) {

		//
		super(name, projectDescription);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void applyTransformations() {

		// step 1: clear prior results
		getModifiableResourceModules().clear();
		getModifiableNonResourceModules().clear();

		preApplyTransformations();

		// step 2: set up the JRE
		// TODO!!!!
		System.out.println("// step 2: set up the JRE");
		try {

			TypeModule jdkModule = JdkModuleCreator.getJdkModules().get(0);
			getModifiableNonResourceModules().put(
					jdkModule.getModuleIdentifier(), jdkModule);
			setExecutionEnvironment(jdkModule);

		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// step 3: create the type modules
		System.out.println("// step 3: create the type modules");

		for (IFileBasedContent fileBasedContent : getProjectDescription()
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

				getModifiableNonResourceModules().put(
						typeModule.getModuleIdentifier(), typeModule);

			}
		}

		try {
			initializeModules();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// step 4: transform modules
		System.out.println("// step 4: transform modules");
		for (ITransformation transformation : getTransformations()) {

			// step 4.1: apply transformation
			transformation.apply((ModularizedSystem) this);

			// step 4.2: clean up empty modules
			for (Iterator<Entry<IModuleIdentifier, ResourceModule>> iterator = getModifiableResourceModules()
					.entrySet().iterator(); iterator.hasNext();) {

				// get next module
				Entry<IModuleIdentifier, ResourceModule> module = iterator
						.next();

				// if the module is empty - remove it
				if (module.getValue().getResources(ContentType.BINARY)
						.isEmpty()
						&& module.getValue().getResources(ContentType.SOURCE)
								.isEmpty()) {

					// remove the module
					iterator.remove();
				}
			}

			// step 4.3: initialize
			for (ResourceModule module : getModifiableResourceModules()
					.values()) {
				module.initializeContainedTypes();
			}
		}

		System.out.println("// done");

	}

	/**
	 * <p>
	 * </p>
	 * 
	 */
	protected void preApplyTransformations() {
		//
	}

	/**
	 * <p>
	 * </p>
	 * 
	 */
	protected void postApplyTransformations() {
		//
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws Exception
	 */
	protected void initializeModules() throws Exception {
		//
	}

	public ResourceModule createResourceModule(
			IModuleIdentifier createModuleIdentifier) {

		//
		ResourceModule resourceModule = new ResourceModule(
				createModuleIdentifier);

		//
		getModifiableResourceModules().put(
				resourceModule.getModuleIdentifier(), resourceModule);

		//
		return resourceModule;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param identifier
	 * @param file
	 * @return
	 */
	protected TypeModule createTypeModule(IModuleIdentifier identifier,
			File file) {
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
	protected TypeModule createTypeModule(IModuleIdentifier identifier,
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
					Type type2 = new Type(type, TypeEnum.CLASS);
					type2.setTypeModule(typeModule);

					typeModule.getSelfContainer()
							.getModifiableContainedTypesMap().put(type, type2);
				}

			} catch (IOException e) {

				//
				e.printStackTrace();
			}
		}
		// return the module
		return typeModule;
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
}
