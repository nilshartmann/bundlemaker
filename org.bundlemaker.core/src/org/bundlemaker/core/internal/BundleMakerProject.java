package org.bundlemaker.core.internal;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.internal.parser.ProjectParser;
import org.bundlemaker.core.model.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent;
import org.bundlemaker.core.model.transformation.TransformationFactory;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.ModularizedSystem;
import org.bundlemaker.core.parser.IParserFactory;
import org.bundlemaker.core.resource.Resource;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.resource.ResourceStandin;
import org.bundlemaker.core.resource.StringCache;
import org.bundlemaker.core.store.IDependencyStore;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

import com.springsource.util.osgi.manifest.parse.DummyParserLogger;

/**
 * <p>
 * Implementation of the interface {@link IBundleMakerProject}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProject implements IBundleMakerProject {

	public final StringCache DEFAULT_STRING_CACHE = new StringCache();

	/** the associated eclipse project (the bundle make project) */
	private IProject _project;

	/** the bundle maker project description */
	private ModifiableBundleMakerProjectDescription _projectDescription;

	/** the associated info store */
	private IDependencyStore _additionalInfoStore;

	/** the state the project is in */
	private BundleMakerProjectState _projectState;

	/** the project description working copies */
	private Map<String, ModularizedSystem> _modifiableModualizedSystemWorkingCopies;

	/** - */
	private IProgressMonitor _currentProgressMonitor;

	private StringCache _stringCache;

	/**
	 * <p>
	 * Creates a new instance of type {@link BundleMakerProject}.
	 * </p>
	 * 
	 * @param project
	 * @throws Exception
	 */
	public BundleMakerProject(IProject project) throws CoreException {

		// TODO: CoreException
		Assert.isTrue(project.hasNature(BundleMakerCore.NATURE_ID));

		// set the project
		_project = project;

		// read the projectDescription
		_projectDescription = loadProjectDescription();

		// create the working copies map
		_modifiableModualizedSystemWorkingCopies = new HashMap<String, ModularizedSystem>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveProjectDescription() throws CoreException {

		org.eclipse.emf.ecore.resource.Resource resource = getBundleMakerProjectDescriptionURI(PROJECT_DESCRIPTION_NAME);
		resource.getContents().add(_projectDescription);
		try {
			resource.save(null);
		} catch (IOException e) {
			// TODO: MSG
			throw new CoreException(new Status(IStatus.ERROR,
					BundleMakerCore.BUNDLE_ID, ""));
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BundleMakerProjectState getState() {
		return _projectState;
	}

	/**
	 * {@inheritDoc}
	 */
	public void initialize(IProgressMonitor progressMonitor)
			throws CoreException {

		// set the progress monitor
		_currentProgressMonitor = progressMonitor;

		// save the project description
		saveProjectDescription();

		// reload the project description
		_projectDescription.initialize(this);

		// set the initialized flag
		_projectState = BundleMakerProjectState.INITIALIZED;

		// step 4: call initialize on the parser factories
		for (IParserFactory parserFactory : Activator.getDefault()
				.getParserFactoryRegistry().getParserFactories()) {
			parserFactory.initialize(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IProblem> parse(IProgressMonitor progressMonitor)
			throws CoreException {

		// set the progress monitor
		_currentProgressMonitor = progressMonitor;

		// assert
		assertState(BundleMakerProjectState.INITIALIZED,
				BundleMakerProjectState.PARSED, BundleMakerProjectState.OPENED);

		// create the project parser
		ProjectParser projectParser = new ProjectParser(this);

		// parse the project
		List<IProblem> problems = projectParser
				.parseBundleMakerProject(progressMonitor);

		_projectState = BundleMakerProjectState.PARSED;

		// return the problems
		return problems;
	}

	/**
	 * {@inheritDoc}
	 */
	public void open(IProgressMonitor progressMonitor) throws CoreException {

		// set the progress monitor
		_currentProgressMonitor = progressMonitor;

		// assert
		assertState(BundleMakerProjectState.INITIALIZED,
				BundleMakerProjectState.PARSED);

		// TODO: up-to-date-check

		// get the dependency store
		IDependencyStore dependencyStore = getDependencyStore(null);

		System.out.println("get resources");

		List<Resource> resources = dependencyStore.getResources();

		Map<Resource, Resource> map = new HashMap<Resource, Resource>();

		System.out.println("put resources to map");

		for (Resource resource : resources) {
			map.put(resource, resource);
		}

		System.out.println("set up resource standin");

		List<ModifiableFileBasedContent> fileBasedContents = _projectDescription
				.getModifiableFileBasedContent();

		for (ModifiableFileBasedContent fileBasedContent : fileBasedContents) {

			if (fileBasedContent.isResourceContent()) {

				for (ResourceStandin resourceStandin : fileBasedContent
						.getModifiableResourceContent()
						.getModifiableBinaryResources()) {

					setupResourceStandin(resourceStandin, map);
					Assert.isNotNull(resourceStandin.getResource());
				}

				for (ResourceStandin resourceStandin : fileBasedContent
						.getModifiableResourceContent()
						.getModifiableSourceResources()) {

					setupResourceStandin(resourceStandin, map);
					Assert.isNotNull(resourceStandin.getResource());
				}
			}
		}

		// set 'READY' state
		_projectState = BundleMakerProjectState.OPENED;

		// create default working copy
		createModularizedSystemWorkingCopy(DEFAULT_MODULARIZED_SYSTEM_WORKING_COPY_ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws CoreException
	 */
	@SuppressWarnings("serial")
	@Override
	public void createModularizedSystemWorkingCopy(String name)
			throws CoreException {

		// assert
		assertState(BundleMakerProjectState.OPENED);

		// TODO
		Copier copier = new Copier(true, true) {

			@Override
			protected void copyReference(EReference eReference,
					EObject eObject, EObject copyEObject) {
				super.copyReference(eReference, eObject, copyEObject);
			}

			@Override
			protected void copyContainment(EReference eReference,
					EObject eObject, EObject copyEObject) {
				super.copyContainment(eReference, eObject, copyEObject);
			}

			@Override
			protected void copyAttribute(EAttribute eAttribute,
					EObject eObject, EObject copyEObject) {
				super.copyAttribute(eAttribute, eObject, copyEObject);
			}

		};

		EObject projectDescriptionWorkingCopy = copier
				.copy(_projectDescription);
		copier.copyReferences();

		// create the modularized system
		ModularizedSystem modularizedSystem = new ModularizedSystem(name,
				(IBundleMakerProjectDescription) projectDescriptionWorkingCopy);

		// create the default transformation
		ITransformation basicContentTransformation = TransformationFactory.eINSTANCE
				.createBasicProjectContentTransformation();
		modularizedSystem.getTransformations().add(basicContentTransformation);

		// add the result to the hash map
		_modifiableModualizedSystemWorkingCopies.put(name, modularizedSystem);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws CoreException
	 */
	@Override
	public boolean hasModularizedSystemWorkingCopy(String name)
			throws CoreException {

		// assert
		assertState(BundleMakerProjectState.OPENED);

		return _modifiableModualizedSystemWorkingCopies.containsKey(name);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws CoreException
	 */
	@Override
	public IModularizedSystem getModularizedSystemWorkingCopy(String name)
			throws CoreException {

		// assert
		assertState(BundleMakerProjectState.OPENED);

		if (!hasModularizedSystemWorkingCopy(name)) {
			// TODO
			throw new RuntimeException(name);
		}

		return _modifiableModualizedSystemWorkingCopies.get(name);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws CoreException
	 */
	@Override
	public void deleteModularizedSystemWorkingCopy(String name)
			throws CoreException {

		// assert
		assertState(BundleMakerProjectState.OPENED);

		if (_modifiableModualizedSystemWorkingCopies.containsKey(name)) {
			_modifiableModualizedSystemWorkingCopies.remove(name);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public IProject getProject() {
		return _project.getProject();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public ModifiableBundleMakerProjectDescription getProjectDescription() {
		return _projectDescription;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param progressMonitor
	 * @return
	 * @throws CoreException
	 */
	public IDependencyStore getDependencyStore(IProgressMonitor progressMonitor)
			throws CoreException {

		// set the progress monitor
		_currentProgressMonitor = progressMonitor;

		// assert
		assertState(BundleMakerProjectState.INITIALIZED,
				BundleMakerProjectState.PARSED, BundleMakerProjectState.OPENED);

		if (_additionalInfoStore == null) {
			return Activator.getDefault().getPersistentDependencyStore(this);
		}

		return _additionalInfoStore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_project == null) ? 0 : _project.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BundleMakerProject other = (BundleMakerProject) obj;
		if (_project == null) {
			if (other._project != null)
				return false;
		} else if (!_project.equals(other._project))
			return false;
		return true;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceStandin
	 * @param map
	 */
	private void setupResourceStandin(ResourceStandin resourceStandin,
			Map<Resource, Resource> map) {

		// TODO
		// System.out
		// .println(resourceStandin.getFileBasedContentId() + " : "
		// + resourceStandin.getRoot() + " : "
		// + resourceStandin.getPath());

		Resource resource = map.get(new ResourceKey(resourceStandin
				.getContentId(), resourceStandin.getRoot(), resourceStandin
				.getPath()));

		if (resource == null) {
			// comment: we can use a dummy StringCache here...
			resource = new Resource(resourceStandin.getContentId(),
					resourceStandin.getRoot(), resourceStandin.getPath(),
					DEFAULT_STRING_CACHE);
		}

		resourceStandin.setResource(resource);

		// set the opposite
		// TODO: MOVE
		resource.setResourceStandin(resourceStandin);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 * @throws CoreException
	 */
	private ModifiableBundleMakerProjectDescription loadProjectDescription()
			throws CoreException {

		org.eclipse.emf.ecore.resource.Resource resource = getBundleMakerProjectDescriptionURI(PROJECT_DESCRIPTION_NAME);

		try {
			resource.load(null);
		} catch (IOException e) {
			// TODO: MSG
			throw new CoreException(new Status(IStatus.ERROR,
					BundleMakerCore.BUNDLE_ID, e.getMessage()));
		}

		ModifiableBundleMakerProjectDescription projectDescription = (ModifiableBundleMakerProjectDescription) resource
				.getContents().get(0);

		_projectState = BundleMakerProjectState.CREATED;

		return projectDescription;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param project
	 * @return
	 */
	private org.eclipse.emf.ecore.resource.Resource getBundleMakerProjectDescriptionURI(
			String name) {

		//
		URI uri = URI.createPlatformResourceURI(getProject().getFullPath()
				.append(BUNDLEMAKER_DIRECTORY_NAME).append(name).toString(),
				true);

		// return a new XMLResourceImpl
		return new XMLResourceImpl(uri);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param state
	 * @throws CoreException
	 */
	private void assertState(BundleMakerProjectState... state)
			throws CoreException {

		// check the states
		for (BundleMakerProjectState aState : state) {
			if (aState != null && aState.equals(getState())) {
				return;
			}
		}

		// throw new exception
		throw new CoreException(
				new Status(
						IStatus.ERROR,
						BundleMakerCore.BUNDLE_ID,
						String.format(
								"BundleMakerProject must be in on of the following states: '%s', but current state is '%s'.",
								Arrays.asList(state), getState())));
	}
}
