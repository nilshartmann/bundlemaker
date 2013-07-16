package org.bundlemaker.core.spi.modext;

import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IAnalysisModelContext {

  IAnalysisModelConfiguration getConfiguration();

  IModularizedSystem getModularizedSystem();

  IResourceArtifact getOrCreateResource(IModuleResource resource);

  IPackageArtifact getOrCreatePackage(String moduleName, String packageName);

  IPackageArtifact getOrCreatePackage(IModule module, String packageName);

  IModuleArtifact getOrCreateModuleArtifact(IModule module);

  IModuleArtifact getOrCreateModuleArtifact(String moduleName);
}
