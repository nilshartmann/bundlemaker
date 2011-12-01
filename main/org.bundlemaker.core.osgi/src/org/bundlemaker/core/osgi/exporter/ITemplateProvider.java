package org.bundlemaker.core.osgi.exporter;

import java.util.Set;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IContentProvider;

import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ITemplateProvider {

  /**
   * <p>
   * </p>
   * 
   * @param module
   * @param modularizedSystem
   * @param context
   * @return
   */
  ManifestContents getManifestTemplate(IResourceModule module, IModularizedSystem modularizedSystem,
      IModuleExporterContext context);

  /**
   * <p>
   * </p>
   * 
   * @param currentModule
   * @param currentModularizedSystem
   * @param currentContext
   * @return
   */
  Set<IContentProvider> getAdditionalResources(IResourceModule currentModule,
      IModularizedSystem currentModularizedSystem, IModuleExporterContext currentContext);
}
