package org.bundlemaker.core.modules.modifiable;

import java.util.Set;

import org.bundlemaker.core.modules.IResourceContainer;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;

public interface IModifiableResourceContainer extends IResourceContainer {

  Set<IResource> getModifiableResourcesSet(ContentType binary);

}
