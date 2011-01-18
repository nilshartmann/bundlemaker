package org.bundlemaker.core.exporter.structure101;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.bundlemaker.core.exporter.IModularizedSystemExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.structure101.xml.DataType;
import org.bundlemaker.core.exporter.structure101.xml.DependenciesType;
import org.bundlemaker.core.exporter.structure101.xml.DependencyType;
import org.bundlemaker.core.exporter.structure101.xml.ModuleType;
import org.bundlemaker.core.exporter.structure101.xml.ModulesType;
import org.bundlemaker.core.exporter.structure101.xml.ObjectFactory;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IReferencedModulesQueryResult;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ITypeModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResourceStandin;
import org.bundlemaker.core.util.StopWatch;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Structure101Exporter implements IModularizedSystemExporter {

	/** - */
	private static final String TYPE_REQUIRES = "requires";

	/** - */
	private static final String TYPE_CLASS = "class";

	/** - */
	private static final String TYPE_PACKAGE = "package";

	/** - */
	private static final String TYPE_OSGIBUNDLE = "osgibundle";

	/** - */
	private long _uniqueIdCounter = 0;

	/** - */
	private Map<String, String> _idMap;

	/** - */
	private DataType _dataType;

	/**
	 * <p>
	 * Creates a new instance of type {@link Structure101Exporter}.
	 * </p>
	 */
	public Structure101Exporter() {

		// create lists and maps
		_idMap = new HashMap<String, String>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void export(IModularizedSystem modularizedSystem,
			IModuleExporterContext context) throws Exception {

		// set up the DataType element
		_dataType = new DataType();
		_dataType.setFlavor("com.wuetherich.test");
		_dataType.setModules(new ModulesType());
		_dataType.setDependencies(new DependenciesType());

		// reset counter and map
		_uniqueIdCounter = 0;
		_idMap.clear();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		//
		for (ITypeModule typeModule : modularizedSystem.getAllModules()) {

			// create the entries
			createEntries(typeModule, modularizedSystem);
		}

		stopWatch.stop();
		System.out.println("Elapsed Time : " + stopWatch.getElapsedTime());

		//
		FileOutputStream outputStream = new FileOutputStream(new File(
				context.getDestinationDirectory(), "structure101.xml"));

		//
		marshal(_dataType, outputStream);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeModule
	 * @param packageList
	 */
	private void createEntries(ITypeModule typeModule,
			IModularizedSystem modularizedSystem) {

		// step 1: extract the package list
		Map<String, List<String>> packageList = new HashMap<String, List<String>>();
		for (String fullyQualifiedType : typeModule.getContainedTypes()) {

			// get the package
			String packageName = getPackageName(fullyQualifiedType);

			// create the package list
			if (!packageList.containsKey(packageName)) {
				packageList.put(packageName, new LinkedList<String>());
			}

			// add to package
			packageList.get(packageName).add(fullyQualifiedType);
		}

		// step 2: create the ModuleType
		ModuleType module = new ModuleType();

		module.setType(TYPE_OSGIBUNDLE);
		module.setName(typeModule.getClassification() != null ? typeModule
				.getClassification().toString()
				+ "/"
				+ typeModule.getModuleIdentifier().toString() : typeModule
				.getModuleIdentifier().toString());
		module.setId(getModuleId(typeModule));

		for (Entry<String, List<String>> entry : packageList.entrySet()) {

			ModuleType packageSubmodule = new ModuleType();
			packageSubmodule.setType(TYPE_PACKAGE);
			packageSubmodule.setName(entry.getKey());
			packageSubmodule.setId(getPackageId(typeModule, entry.getKey()));

			// add all class names to to package
			for (String fullyQualifiedTypeName : entry.getValue()) {

				ModuleType classSubmodule = new ModuleType();

				// set the type
				classSubmodule.setType(TYPE_CLASS);

				// set the class name
				String classname = fullyQualifiedTypeName.indexOf('.') != -1 ? fullyQualifiedTypeName
						.substring(fullyQualifiedTypeName.lastIndexOf('.') + 1)
						: fullyQualifiedTypeName;
				classSubmodule.setName(classname);

				// set the id
				classSubmodule.setId(getClassId(typeModule,
						fullyQualifiedTypeName));

				packageSubmodule.getSubmodule().add(classSubmodule);
			}

			module.getSubmodule().add(packageSubmodule);
		}

		_dataType.getModules().getModule().add(module);

		if (typeModule instanceof IResourceModule) {

			//
			IResourceModule resourceModule = (IResourceModule) typeModule;

			IReferencedModulesQueryResult referencedModules = modularizedSystem
					.getReferencedModules(resourceModule);

			//
			for (IResourceStandin resourceStandin : resourceModule
					.getResources(ContentType.BINARY)) {

				//
				for (String containedType : resourceStandin.getResource()
						.getContainedTypes()) {

					for (IReference reference : resourceStandin.getResource()
							.getReferences()) {

						ITypeModule referencedModule = referencedModules
								.getReferencedModulesMap().get(
										reference.getFullyQualifiedName());

						if (referencedModule != null) {

							DependencyType dependency = new DependencyType();
							dependency.setType(TYPE_REQUIRES);
							dependency.setFrom(getClassId(resourceModule,
									containedType));
							dependency.setTo(getClassId(referencedModule,
									reference.getFullyQualifiedName()));

							_dataType.getDependencies().getDependency()
									.add(dependency);
						}
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeModule
	 * @return
	 */
	private String getModuleId(ITypeModule typeModule) {
		return getShortenId(getModuleName(typeModule));
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeModule
	 * @param packageName
	 * @return
	 */
	private String getPackageId(ITypeModule typeModule, String packageName) {
		return getShortenId(getModuleName(typeModule) + "_" + packageName);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeModule
	 * @param fullyQualifiedType
	 * @return
	 */
	private String getClassId(ITypeModule typeModule, String fullyQualifiedType) {
		return getShortenId(getModuleName(typeModule) + "_"
				+ fullyQualifiedType);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param longID
	 * @return
	 */
	private String getShortenId(String longID) {

		//
		if (!_idMap.containsKey(longID)) {
			_idMap.put(longID, Long.toString(_uniqueIdCounter++));
		}

		// get the id
		return _idMap.get(longID);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedType
	 * @return
	 */
	private static String getPackageName(String fullyQualifiedType) {

		//
		return fullyQualifiedType.indexOf('.') != -1 ? fullyQualifiedType
				.substring(0, fullyQualifiedType.lastIndexOf('.')) : "";
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeModule
	 * @return
	 */
	private static String getModuleName(ITypeModule typeModule) {

		// get the id
		return typeModule.getModuleIdentifier().getName() + "_"
				+ typeModule.getModuleIdentifier().getVersion();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param productsType
	 * @param outputStream
	 */
	private static void marshal(DataType dataType, OutputStream outputStream) {

		try {

			//
			JAXBContext jc = JAXBContext.newInstance(DataType.class,
					DependenciesType.class, DependencyType.class,
					ModulesType.class, ModuleType.class, ObjectFactory.class);

			//
			Marshaller marshaller = jc.createMarshaller();

			// set formatted output
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//
			marshaller.marshal(new ObjectFactory().createData(dataType),
					outputStream);

		} catch (JAXBException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
