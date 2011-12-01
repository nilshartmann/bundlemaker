package org.bundlemaker.core.osgi.exporter;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;

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
public interface IManifestContentsInterceptor {

  /**
   * <p>
   * </p>
   * 
   * @param manifestContents
   */
  void manipulateManifestContents(ManifestContents manifestContents, IModularizedSystem modularizedSystem,
      IModule module);
}
