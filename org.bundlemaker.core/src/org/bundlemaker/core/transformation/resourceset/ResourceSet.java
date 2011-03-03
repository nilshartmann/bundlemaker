package org.bundlemaker.core.transformation.resourceset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.types.selectors.SelectorUtils;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.util.FileUtils;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceSet {

	/** - */
	private IModuleIdentifier _moduleIdentifier;

	/** - */
	private List<String> _includes;

	/** - */
	private List<String> _excludes;

	/**
	 * <p>
	 * Creates a new instance of type {@link ResourceSet}.
	 * </p>
	 */
	public ResourceSet() {
		_includes = new ArrayList<String>();
		_excludes = new ArrayList<String>();
	}

	public IModuleIdentifier getModuleIdentifier() {
		return _moduleIdentifier;
	}

	public void setModuleIdentifier(IModuleIdentifier moduleIdentifier) {
		_moduleIdentifier = moduleIdentifier;
	}

	public List<String> getIncludes() {
		return _includes;
	}

	public List<String> getExcludes() {
		return _excludes;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceSet
	 * @param resourceModule
	 * @param contentType
	 * @return
	 */
	public List<IResource> getMatchingResources(
			IResourceModule resourceModule, ContentType contentType) {

		IModuleIdentifier moduleIdentifier = _moduleIdentifier;

		if (!(resourceModule.getModuleIdentifier().getName()
				.equals(moduleIdentifier.getName()) && resourceModule
				.getModuleIdentifier().getVersion()
				.equals(moduleIdentifier.getVersion()))) {

			return Collections.emptyList();
		}

		List<IResource> result = new ArrayList<IResource>();

		//
		Set<IResource> resourceStandins = resourceModule
				.getResources(contentType);

		for (IResource resourceStandin : resourceStandins) {

			if (isIncluded(resourceStandin)) {
				result.add(resourceStandin);
			}
		}

		// TODO: Contained Containers!

		return result;
	}

	private boolean isIncluded(IResource resourceStandin) {

		boolean included = false;

		for (String include : _includes) {
			if (SelectorUtils.matchPath(FileUtils.normalize(include),
					FileUtils.normalize(resourceStandin.getPath()))) {
				included = true;
				break;
			}
		}

		if (!included) {
			return false;
		}

		for (String exclude : _excludes) {
			if (SelectorUtils.matchPath(FileUtils.normalize(exclude),
					FileUtils.normalize(resourceStandin.getPath()))) {
				return false;
			}
		}

		return true;

	}
}
