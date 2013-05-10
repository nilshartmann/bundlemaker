package org.bundlemaker.core.internal.projectdescription.api;

import org.bundlemaker.core.internal.projectdescription.IResourceStandin;
import org.bundlemaker.core.projectdescription.IProjectDescription;

public interface IInternalProjectDescription extends IProjectDescription {

  void fireProjectDescriptionChangedEvent();

  void fireProjectDescriptionRecomputedEvent();

  void addBinaryResource(IResourceStandin resourceStandin);

  void addSourceResource(IResourceStandin resourceStandin);

}
