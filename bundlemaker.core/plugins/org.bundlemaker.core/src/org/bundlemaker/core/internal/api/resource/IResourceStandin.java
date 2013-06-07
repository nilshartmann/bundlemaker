package org.bundlemaker.core.internal.api.resource;

import org.bundlemaker.core.internal.projectdescription.ProjectContentEntry;
import org.bundlemaker.core.resource.IModuleResource;

/**
 * <p>
 * A resource standin represents a resource that should be analyzed.
 * </p>
 * <p>
 * {@link IResourceStandin IResourceStandins} can only be created within the context of a {@link ProjectContentEntry}
 * by calling {@link ProjectContentEntry#createNewResourceStandin(String, String, String, ContentType)}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IResourceStandin extends IModuleResource {
  // empty tag interface
}