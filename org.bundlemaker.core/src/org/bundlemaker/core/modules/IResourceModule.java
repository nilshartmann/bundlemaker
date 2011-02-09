package org.bundlemaker.core.modules;

import java.util.List;
import java.util.Map;

import org.bundlemaker.core.projectdescription.ContentType;

public interface IResourceModule extends IResourceContainer, ITypeModule {

	IResourceContainer getSelfResourceContainer();

	Map<String, ? extends IResourceContainer> getContainedResourceContainers();

	List<String> getDuplicateResources(ContentType contentType);

	boolean hasDuplicateResources(ContentType contentType);

	//
	// boolean isSourceModule();
}