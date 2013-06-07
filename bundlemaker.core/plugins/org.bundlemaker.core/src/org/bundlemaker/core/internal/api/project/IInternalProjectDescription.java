package org.bundlemaker.core.internal.api.project;

import org.bundlemaker.core.internal.api.resource.IResourceStandin;
import org.bundlemaker.core.project.IProjectDescription;

public interface IInternalProjectDescription extends IProjectDescription {

  void fireProjectDescriptionChangedEvent();

  void fireProjectDescriptionRecomputedEvent();

  void addBinaryResource(IResourceStandin resourceStandin);

  void addSourceResource(IResourceStandin resourceStandin);

}
