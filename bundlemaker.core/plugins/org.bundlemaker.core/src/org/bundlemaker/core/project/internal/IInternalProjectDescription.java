package org.bundlemaker.core.project.internal;

import org.bundlemaker.core.project.IProjectDescription;

public interface IInternalProjectDescription extends IProjectDescription {

  void fireProjectDescriptionChangedEvent();

  void fireProjectDescriptionRecomputedEvent();

  void addBinaryResource(IResourceStandinNEW resourceStandin);

  void addSourceResource(IResourceStandinNEW resourceStandin);

  void removeBinaryResource(IResourceStandinNEW resourceStandin);

  void removeSourceResource(IResourceStandinNEW resourceStandin);
}
