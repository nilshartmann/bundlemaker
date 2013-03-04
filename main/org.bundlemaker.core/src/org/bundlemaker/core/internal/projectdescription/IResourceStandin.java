package org.bundlemaker.core.internal.projectdescription;

import org.bundlemaker.core.resource.IResource;

/**
 * <p>
 * A resource standin represents a resource that should be analyzed.
 * </p>
 * <p>
 * {@link IResourceStandin IResourceStandins} can only be created within the context of a {@link ProjectContent}
 * by calling {@link ProjectContent#createNewResourceStandin(String, String, String, ContentType)}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IResourceStandin extends IResource {
  // empty tag interface
}