package org.bundlemaker.core.project.internal;

import org.bundlemaker.core.project.IProjectContentResource;

public interface IResourceStandinNEW extends IProjectContentResource {

  IProjectContentResource getResource();

  void setResource(IProjectContentResource resource);

  void setAnalyzeReferences(boolean analyzeReferences);
}
