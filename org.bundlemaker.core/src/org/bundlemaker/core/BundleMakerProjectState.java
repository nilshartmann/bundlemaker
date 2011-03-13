package org.bundlemaker.core;

/**
 * <p>
 * </p>
 * <ul>
 * <li><b><i>NEW:</i></b> The project has been created, but no project description has been set up yet.</li>
 * <li><b><i>INITIALIZED:</i></b> The project description has been set up.</li>
 * <li><b><i>PARSED:</i></b> The project has been parsed. Note that the parsing of a project has to be started manually
 * by the user as it's a long-running operation.</li>
 * <li><b><i>OPENED:</i></b> The project is opened and ready to use.</li>
 * </ul>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This enum is not intended to be extended by clients.
 */
public enum BundleMakerProjectState {

  /** the CREATED state */
  CREATED,

  /** the INITIALIZED state */
  INITIALIZED,

  /** the PARSED state */
  PARSED,

  /** the OPENED state */
  OPENED;
}
