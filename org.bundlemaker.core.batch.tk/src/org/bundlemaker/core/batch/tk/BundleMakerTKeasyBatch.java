package org.bundlemaker.core.batch.tk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.batch.tk.exporter.Structure101Exporter;
import org.bundlemaker.core.batch.tk.products.ProductBasedTransformationCreator;
import org.bundlemaker.core.batch.tk.products.ProductUtils;
import org.bundlemaker.core.batch.tk.products.ProductsType;
import org.bundlemaker.core.batch.tk.transformations.DefaultClassificationTransformation;
import org.bundlemaker.core.batch.tk.transformations.DeleteDuplicateTupleTransformation;
import org.bundlemaker.core.batch.tk.transformations.DeleteTkEasyOriginTransformation;
import org.bundlemaker.core.batch.tk.transformations.TKeasyResourceSetProcessor;
import org.bundlemaker.core.exporter.ModuleExporterContext;
import org.bundlemaker.core.exporter.SimpleReportExporter;
import org.bundlemaker.core.exporter.bundle.BinaryBundleExporter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.tk.structure101.xml.DependencyType;
import org.bundlemaker.core.tk.structure101.xml.ModuleType;
import org.bundlemaker.core.transformation.resourceset.ResourceSetBasedModuleDefinition;
import org.bundlemaker.core.transformation.resourceset.ResourceSetBasedTransformation;
import org.bundlemaker.core.util.BundleMakerProjectUtils;
import org.bundlemaker.core.util.EclipseProjectUtils;
import org.bundlemaker.core.util.ProgressMonitor;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

/**
 *
 */
public class BundleMakerTKeasyBatch {

	public static final String TKEASY_VERSION = "1.0.0";

	public static final String TKEASY_NAME = "TkEasy";

	public static final IModuleIdentifier TKEASY_MODULE_IDENTIFIER = new ModuleIdentifier(
			TKEASY_NAME, TKEASY_VERSION);

	/** - */
	public static final String PROJECT_NAME = "BUNDLEMAKER_BATCH";

	private static final String PROP_TKEASY_PROJECT_SOURCES_DEV = "tkeasy.project.sources.dev";
	private static final String PROP_TKEASY_PROJECT_SOURCES_GEN = "tkeasy.project.sources.gen";
	private static final String PROP_TKEASY_PROJECT_SOURCES_QS = "tkeasy.project.sources.qs";

	private static final String PROP_TKEASY_PROJECT_SOURCES = "tkeasy.project.sources";
	private static final String PROP_TKEASY_PROJECT_CLASSES = "tkeasy.project.classes";
	private static final String PROP_TKEASY_PROJECT_CLASSPATHPROPERTIES = "tkeasy.project.classpathproperties";
	private static final String PROP_TKEASY_PROJECT_JAVAROOT = "tkeasy.project.javaroot";

	private static final String PROP_TKEASY_PRODUCTS_FILE = "tkeasy.products.file";
	private static final String PROP_TKEASY_FRAMEWORKPRODUCTS_FILE = "tkeasy.frameworkproducts.file";

	/** - */
	private String _configurationPath;

	/** - */
	private boolean _parse;

	/** - */
	private Configuration _configuration;

	/** - */
	private IModularizedSystem _modularizedSystem;

	private IBundleMakerProject _bundleMakerProject;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param configurationPath
	 * @param parse
	 */
	public BundleMakerTKeasyBatch(String configurationPath, boolean parse) {
		Assert.isNotNull(configurationPath);

		_configurationPath = configurationPath;
		_parse = parse;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public IModularizedSystem getModularizedSystem() {
		return _modularizedSystem;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws Exception
	 */
	public void execute(boolean generateResourceArtifacts) throws Exception {

		// initialize configuration
		_configuration = new PropertiesConfiguration(_configurationPath);

		// early check
		File productFile = getFileFromConfiguration(PROP_TKEASY_PRODUCTS_FILE);
		File frameworkProductFile = getFileFromConfiguration(PROP_TKEASY_FRAMEWORKPRODUCTS_FILE);
		// File templateDirectory =
		// getDirectoryFromConfiguration(PROP_TEMPLATE_DIRECTORY);

		// delete the project if specified
		if (_parse) {
			info("deleting project '" + PROJECT_NAME + "'");
			EclipseProjectUtils.deleteProjectIfExists(PROJECT_NAME);
		}

		// create simple project
		info("creating simple project '" + PROJECT_NAME + "'");
		IProject simpleProject = BundleMakerCore
				.getOrCreateSimpleProjectWithBundleMakerNature(PROJECT_NAME);

		// get the BundleMaker project
		info("adding bundlemaker nature for project '" + PROJECT_NAME + "'");
		_bundleMakerProject = BundleMakerCore.getBundleMakerProject(
				simpleProject, null);

		// create the project description if specified
		if (_parse) {

			info("creating project description for '" + PROJECT_NAME + "'");
			List<String> sources = getFileEntriesFromConfiguration(PROP_TKEASY_PROJECT_SOURCES);
			List<String> classes = getFileEntriesFromConfiguration(PROP_TKEASY_PROJECT_CLASSES);
			List<String> classpathEntries = readClasspathEntries();
			createProjectDescription(
					_bundleMakerProject.getProjectDescription(), classes,
					sources, classpathEntries);
		}

		// initialize the project
		info("initializing bundlemaker project");
		_bundleMakerProject.initialize(null);

		// parse the project if specified
		if (_parse) {

			info("parsing bundlemaker project");
			List<? extends IProblem> problems = _bundleMakerProject
					.parse(new ParseProgressMonitor());

			// dump the problems
			BundleMakerProjectUtils.dumpProblems(problems);

			// return OK
			return;
		}

		// open the project
		info("opening bundlemaker project");
		_bundleMakerProject.open(new ProgressMonitor());

		// get the default modularized system
		info("creating working copy");
		_bundleMakerProject.createModularizedSystemWorkingCopy(PROJECT_NAME);

		_modularizedSystem = _bundleMakerProject
				.getModularizedSystemWorkingCopy(PROJECT_NAME);

		// COMMENT: START
		ResourceSetBasedTransformation transformation = new ResourceSetBasedTransformation();
		ResourceSetBasedModuleDefinition moduleDefinition = transformation
				.addModuleDefinition("DbTuple", "1.0.0");
		moduleDefinition.addResourceSet(TKEASY_NAME, TKEASY_VERSION,
				new String[] { "de/tk/db/tuple/**" }, new String[] {});
		_modularizedSystem.getTransformations().add(transformation);

		// add the 'products transformation'
		info("creating products based transformation");
		ProductsType products = ProductUtils.getMergedProducts(productFile,
				frameworkProductFile);
		ResourceSetBasedTransformation productsTransformation = new ProductBasedTransformationCreator(
				products).createResourceSetBasedTransformation();

		// set the custom tk resource processor (this is necessary because we
		// have three source directories but only one (merged) classes
		// directory).
		productsTransformation
				.setResourceSetProcessor(new TKeasyResourceSetProcessor(
						_configuration
								.getString(PROP_TKEASY_PROJECT_SOURCES_DEV),
						_configuration
								.getString(PROP_TKEASY_PROJECT_SOURCES_QS),
						_configuration
								.getString(PROP_TKEASY_PROJECT_SOURCES_GEN)));

		_modularizedSystem.getTransformations().add(productsTransformation);

		// // add the 'CIDClassTransformation'
		// modularizedSystem.getTransformations()
		// .add(new CIDClassTransformation());

		// TODO: WORKAROUND
		_modularizedSystem.getTransformations().add(
				new DeleteTkEasyOriginTransformation());

		_modularizedSystem.getTransformations().add(
				new DeleteDuplicateTupleTransformation());

		// add the default classification
		_modularizedSystem.getTransformations().add(
				new DefaultClassificationTransformation("EXT"));

		// add the transformation adapter
		// _modularizedSystem.getTransformations().add(
		// new TransformationAdapter(generateResourceArtifacts));

		// apply transformations
		info("applying transformations");
		_modularizedSystem.applyTransformations();

		// // analysis
		// AmbiguousPackages ambiguousPackages = new AmbiguousPackages(
		// modularizedSystem);
		//
		// Map<IModule, String[]> moduleMap =
		// ambiguousPackages.getModuleMap();
		// for (IModule module : moduleMap.keySet()) {
		//
		// if ((module.getClassification() != null && module
		// .getClassification().segment(0).equals("EXT"))
		// || module.getModuleIdentifier().getName()
		// .startsWith("jre6")) {
		//
		// // dump
		// System.out.println(" - "
		// + module.getModuleIdentifier().toString() + " : "
		// + module.getClassification());
		//
		// System.out
		// .println("   " + Arrays.asList(moduleMap.get(module)));
		// }
		// }

		//
		// //
		// for (IResourceModule resourceModule : modularizedSystem
		// .getResourceModules()) {
		//
		// System.out.println(ModelUtils.toString(resourceModule
		// .getModuleIdentifier()));
		//
		// //
		// List<String> unsatisfiedReferencedTypes = modularizedSystem
		// .getUnsatisfiedReferencedTypes(resourceModule, true, true);
		//
		// //
		// for (String type : unsatisfiedReferencedTypes) {
		// System.out.println(type);
		// }
		// }
		//
		// exporterContext.put(
		// StandardBundlorBasedBinaryBundleExporter.TEMPLATE_DIRECTORY,
		// templateDirectory);
		//
		// // export
		// // new StandardBundlorBasedBinaryBundleExporter().export(
		// // modularizedSystem, exporterContext);
		//
		// new PdePluginProjectModuleExporter().export(modularizedSystem,
		// exporterContext);

		// export();
		// COMMENT: STOP

	}

	public void export() throws Exception {

		// create the exporter context
		ModuleExporterContext context = new ModuleExporterContext(
				_bundleMakerProject, new File("c:/temp/bm"), _modularizedSystem);

		// exportStructure101(context);

		SimpleReportExporter simpleReportExporter = new SimpleReportExporter();
		simpleReportExporter.export(_modularizedSystem, context);

		exportBinaryBundles1(context);
	}

	private void exportBinaryBundles1(ModuleExporterContext context)
			throws Exception {
		//
		BinaryBundleExporter binaryBundleExporter = new BinaryBundleExporter();
		binaryBundleExporter.export(_modularizedSystem, context);
	}

	private void exportStructure101(ModuleExporterContext context)
			throws Exception {
		// export structure 101 file
		Structure101Exporter stucture101Exporter = new Structure101Exporter() {

			@Override
			protected void addCustomDependencyAttributes(
					DependencyType dependencyType, IReference reference) {

				if (reference.isImplements()) {
					dependencyType.setImplements(reference.isImplements());
				}
				if (reference.isExtends()) {
					dependencyType.setExtends(reference.isExtends());
				}
				if (reference.isClassAnnotation()) {
					dependencyType.setClassAnnotation(reference
							.isClassAnnotation());
				}
			}

			@Override
			protected void addCustomBundleAttributes(ModuleType bundleModule,
					IModule typeModule) {

				// step: add the department
				if (typeModule.getUserAttributes().containsKey(
						ProductAttributes.KEY)) {

					// get the product attributes
					ProductAttributes productAttributes = (ProductAttributes) typeModule
							.getUserAttributes().get(ProductAttributes.KEY);

					// set the department
					bundleModule.setDepartment(productAttributes
							.getDepartment());
				}
			}

			@Override
			protected boolean isFiltered(IModule typeModule) {

				// we have to filter both the 'jre' and all the 'EXT' modules
				return typeModule.getModuleIdentifier().getName()
						.startsWith("jre")
						|| (typeModule.getClassification() != null && typeModule
								.getClassification().segment(0).equals("EXT"));
			}

			@Override
			protected String getTypeModuleName(IModule typeModule) {

				//
				return typeModule.getClassification() != null ? typeModule
						.getClassification().toString()
						+ "/"
						+ typeModule.getModuleIdentifier().getName()
						: typeModule.getModuleIdentifier().getName();
			}
		};

		stucture101Exporter.export(_modularizedSystem, context);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private List<String> readClasspathEntries() throws IOException,
			FileNotFoundException {

		// load the cp-properties file
		Properties classpathProperties = new Properties();
		classpathProperties.load(new FileInputStream(_configuration
				.getString(PROP_TKEASY_PROJECT_CLASSPATHPROPERTIES)));

		// read the class path properties
		List<String> classpathEntries = ClasspathPropertiesReader
				.readClasspathProperties(classpathProperties,
						_configuration.getString(PROP_TKEASY_PROJECT_JAVAROOT));

		return classpathEntries;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param classpathEntries
	 * 
	 * @return
	 * @throws CoreException
	 */
	private IBundleMakerProjectDescription createProjectDescription(
			IBundleMakerProjectDescription projectDescription,
			List<String> binary, List<String> source,
			List<String> classpathEntries) throws CoreException {

		// step 1: add the source
		projectDescription.addResourceContent(TKEASY_NAME, TKEASY_VERSION,
				binary, source);

		// step 2: add the entries
		for (String classpathEntry : classpathEntries) {
			projectDescription.addResourceContent(classpathEntry);
		}

		// step 3: add the entries
		projectDescription.setJre("jdk16");

		// step 4: return the project description
		return projectDescription;
	}

	/**
	 * @param propTkeasyProjectSources
	 * @return
	 */
	private EList<String> getFileEntriesFromConfiguration(String propertyName) {

		if (!_configuration.containsKey(String.format(
				"The specified property '%s' does not exist!", propertyName))) {

		}

		//
		EList<String> result = new BasicEList<String>();

		String[] entries = _configuration.getStringArray(propertyName);

		for (String entry : entries) {

			//
			File file = new File(entry);

			//
			if (!file.exists()) {
				throw new RuntimeException(String.format(
						"The specified file '%s' does not exist!",
						file.getAbsolutePath()));
			}

			//
			result.add(entry);
		}

		// return the result
		return result;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	private File getDirectoryFromConfiguration(String propertyName) {

		if (!_configuration.containsKey(String.format(
				"The specified property '%s' does not exist!", propertyName))) {

		}

		//
		File directory = new File(_configuration.getString(propertyName));

		//
		if (!directory.isDirectory()) {
			throw new RuntimeException(String.format(
					"The specified file '%s' is not a directory!",
					directory.getAbsolutePath()));
		}

		//
		return directory;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param option
	 * @return
	 */
	private File getFileFromConfiguration(String propertyName) {

		if (!_configuration.containsKey(String.format(
				"The specified property '%s' does not exist!", propertyName))) {

		}

		//
		File file = new File(_configuration.getString(propertyName));

		//
		if (!file.exists()) {
			throw new RuntimeException(String.format(
					"The specified file '%s' does not exist!",
					file.getAbsolutePath()));
		}

		//
		return file;
	}

	/**
	 * @param msg
	 */
	private void info(String msg) {
		System.out.println(msg);
	}
}
