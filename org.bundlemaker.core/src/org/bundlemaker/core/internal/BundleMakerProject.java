package org.bundlemaker.core.internal;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.internal.parser.ProjectParser;
import org.bundlemaker.core.model.internal.projectdescription.EFileBasedContent;
import org.bundlemaker.core.model.internal.projectdescription.EProjectDescription;
import org.bundlemaker.core.model.internal.projectdescription.EResourceContent;
import org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionFactory;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.ModularizedSystem;
import org.bundlemaker.core.parser.IParserFactory;
import org.bundlemaker.core.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.FileBasedContent;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IResourceContent;
import org.bundlemaker.core.projectdescription.ResourceContent;
import org.bundlemaker.core.resource.Reference;
import org.bundlemaker.core.resource.Resource;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.resource.ResourceStandin;
import org.bundlemaker.core.resource.Type;
import org.bundlemaker.core.store.IDependencyStore;
import org.bundlemaker.core.transformation.BasicProjectContentTransformation;
import org.bundlemaker.core.transformation.ITransformation;
import org.bundlemaker.core.util.ProgressMonitor;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

/**
 * <p>
 * Implementation of the interface {@link IBundleMakerProject}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProject implements IBundleMakerProject {

	/** the associated eclipse project (the bundle make project) */
	private IProject _project;

	/** the bundle maker project description */
	private BundleMakerProjectDescription _projectDescription;

	/** the associated info store */
	private IDependencyStore _additionalInfoStore;

	/** the state the project is in */
	private BundleMakerProjectState _projectState;

	/** the project description working copies */
	private Map<String, ModularizedSystem> _modifiableModualizedSystemWorkingCopies;

	/** - */
	private IProgressMonitor _currentProgressMonitor;

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

		// step 1: map to emf model
		EProjectDescription eDescription = ProjectdescriptionFactory.eINSTANCE
				.createEProjectDescription();

		eDescription.setCurrentId(_projectDescription.getCurrentId());
		eDescription.setJre(_projectDescription.getJRE());

		for (FileBasedContent content : _projectDescription
				.getModifiableFileBasedContent()) {

			EFileBasedContent eFileBasedContent = ProjectdescriptionFactory.eINSTANCE
					.createEFileBasedContent();
			eDescription.getFileBasedContent().add(eFileBasedContent);

			eFileBasedContent.setId(content.getId());
			eFileBasedContent.setName(content.getName());
			eFileBasedContent.setVersion(content.getVersion());

			for (IPath path : content.getBinaryPaths()) {
				eFileBasedContent.getBinaryPathNames().add(path.toString());
			}

			if (content.isResourceContent()) {

				IResourceContent resourceContent = content.getResourceContent();

				EResourceContent eResourceContent = ProjectdescriptionFactory.eINSTANCE
						.createEResourceContent();
				eFileBasedContent.setResourceContent(eResourceContent);

				eResourceContent.setAnalyzeSourceResources(resourceContent
						.isAnalyzeSourceResources());

				for (IPath path : resourceContent.getSourcePaths()) {
					eResourceContent.getSourcePathNames().add(path.toString());
				}
			}

		}

		// step 2: save
		org.eclipse.emf.ecore.resource.Resource resource = getBundleMakerProjectDescriptionURI(PROJECT_DESCRIPTION_NAME);
		resource.getContents().add(eDescription);
		try {
			resource.save(null);
		} catch (IOException e) {
			// TODO: MSG
			throw new CoreException(new Status(IStatus.ERROR,
					BundleMakerCore.BUNDLE_ID, e.getMessage()));
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

		List<Resource> resources = dependencyStore.getResources();

		Map<Resource, Resource> map = new HashMap<Resource, Resource>();

		ProgressMonitor monitor = new ProgressMonitor();

		// TODO
		monitor.beginTask("Opening database ", resources.size());

		for (Resource resource : resources) {
			map.put(resource, resource);

			// create copies of references
			Set<Reference> references = new HashSet<Reference>();
			for (Reference reference : resource.getModifiableReferences()) {
				Reference newReference = new Reference(reference);
				references.add(newReference);
				newReference.setResource(resource);
			}
			resource.getModifiableReferences().clear();
			resource.getModifiableReferences().addAll(references);

			// set monitor
			monitor.worked(1);
		}

		monitor.done();

		List<FileBasedContent> fileBasedContents = _projectDescription
				.getModifiableFileBasedContent();

		// TODO
		for (FileBasedContent fileBasedContent : fileBasedContents) {

			Map<String, ResourceStandin> typeToResourceStandin = new HashMap<String, ResourceStandin>();

			if (fileBasedContent.isResourceContent()) {

				for (ResourceStandin resourceStandin : ((ResourceContent) fileBasedContent
						.getResourceContent()).getModifiableBinaryResources()) {

					setupResourceStandin(resourceStandin, map, false);
					Assert.isNotNull(resourceStandin.getResource());
				}

				for (ResourceStandin resourceStandin : ((ResourceContent) fileBasedContent
						.getResourceContent()).getModifiableSourceResources()) {

					setupResourceStandin(resourceStandin, map, true);
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
	@Override
	public void createModularizedSystemWorkingCopy(String name)
			throws CoreException {

		// assert
		assertState(BundleMakerProjectState.OPENED);

		// TODO: WORKING COPY!!!
		// IBundleMakerProjectDescription projectDescriptionWorkingCopy =
		// (IBundleMakerProjectDescription) DeepCopy
		// .copy(_projectDescription);

		IBundleMakerProjectDescription projectDescriptionWorkingCopy = _projectDescription;

		// TODO
		// create the modularized system
		ModularizedSystem modularizedSystem = new ModularizedSystem(name,
				(IBundleMakerProjectDescription) projectDescriptionWorkingCopy);

		// create the default transformation
		ITransformation basicContentTransformation = new BasicProjectContentTransformation();
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
	public BundleMakerProjectDescription getProjectDescription() {
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
			Map<Resource, Resource> map, boolean isSource) {

		// get the associated resource
		Resource resource = map.get(new ResourceKey(resourceStandin
				.getContentId(), resourceStandin.getRoot(), resourceStandin
				.getPath()));

		// create empty resource if no resource was stored in the database
		if (resource == null) {
			resource = new Resource(resourceStandin.getContentId(),
					resourceStandin.getRoot(), resourceStandin.getPath());
		}

		// associate resource and resource stand-in...
		resourceStandin.setResource(resource);
		// ... and set the opposite
		resource.setResourceStandin(resourceStandin);

		// set the references
		Set<Reference> resourceReferences = new HashSet<Reference>();
		for (Reference reference : resource.getModifiableReferences()) {
			Reference newReference = new Reference(reference);
			newReference.setResource(resource);
			resourceReferences.add(newReference);
		}
		resource.getModifiableReferences().clear();
		resource.getModifiableReferences().addAll(resourceReferences);

		// set the type-back-references
		for (Type type : resource.getModifiableContainedTypes()) {

			if (isSource) {
				type.setSourceResource(resource);
			} else {
				type.setBinaryResource(resource);
			}

			// set the references
			Set<Reference> typeReferences = new HashSet<Reference>();
			for (Reference reference : type.getModifiableReferences()) {
				Reference newReference = new Reference(reference);
				newReference.setType(type);
				typeReferences.add(newReference);
			}
			type.getModifiableReferences().clear();
			type.getModifiableReferences().addAll(typeReferences);
		}

	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 * @throws CoreException
	 */
	private BundleMakerProjectDescription loadProjectDescription()
			throws CoreException {

		org.eclipse.emf.ecore.resource.Resource resource = getBundleMakerProjectDescriptionURI(PROJECT_DESCRIPTION_NAME);

		try {
			resource.load(null);
		} catch (IOException e) {
			// TODO: MSG
			throw new CoreException(new Status(IStatus.ERROR,
					BundleMakerCore.BUNDLE_ID, e.getMessage()));
		}

		EProjectDescription projectDescription = (EProjectDescription) resource
				.getContents().get(0);

		BundleMakerProjectDescription result = new BundleMakerProjectDescription();
		result.setCurrentId(projectDescription.getCurrentId());
		result.setJre(projectDescription.getJre());

		for (EFileBasedContent eFileBasedContent : projectDescription
				.getFileBasedContent()) {

			FileBasedContent fileBasedContent = new FileBasedContent();
			result.getModifiableFileBasedContent().add(fileBasedContent);

			fileBasedContent.setId(eFileBasedContent.getId());
			fileBasedContent.setName(eFileBasedContent.getName());
			fileBasedContent.setVersion(eFileBasedContent.getVersion());

			for (String path : eFileBasedContent.getBinaryPathNames()) {
				fileBasedContent.getModifiableBinaryPaths().add(new Path(path));
			}

			if (eFileBasedContent.getResourceContent() != null) {

				ResourceContent resourceContent = new ResourceContent();
				fileBasedContent.setResourceContent(resourceContent);

				resourceContent.setAnalyzeSourceResources(eFileBasedContent
						.getResourceContent().isAnalyzeSourceResources());

				for (String path : eFileBasedContent.getResourceContent()
						.getSourcePathNames()) {

					resourceContent.getModifiableSourcePaths().add(
							new Path(path));
				}
			}
		}

		_projectState = BundleMakerProjectState.CREATED;

		return result;
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
