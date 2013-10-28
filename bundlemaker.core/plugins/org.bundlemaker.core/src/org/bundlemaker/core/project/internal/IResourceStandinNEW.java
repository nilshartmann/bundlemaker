package org.bundlemaker.core.project.internal;

import org.bundlemaker.core.project.IProjectContentResource;

public interface IResourceStandinNEW extends IProjectContentResource {

  IResourceStandinAwareProjectContentResource getResource();

  void setResource(IResourceStandinAwareProjectContentResource resource);

  void setAnalyzeReferences(boolean analyzeReferences);
}
