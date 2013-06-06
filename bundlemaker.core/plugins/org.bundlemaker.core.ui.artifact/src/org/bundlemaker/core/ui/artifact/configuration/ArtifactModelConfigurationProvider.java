/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.artifact.configuration;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.common.ResourceType;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Implementation of {@link IArtifactModelConfigurationProvider}.
 * 
 * <p>
 * This implementation loads and stores the configuration via the {@link IPreferenceStore}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactModelConfigurationProvider implements IArtifactModelConfigurationProvider {

  private final static String        PREFS_PREFIX               = "org.bundlemaker.core.ui.prefs.artifactModelConfiguration.";

  private final static String        PREF_CONTENT_TYPE          = PREFS_PREFIX + "contentType";

  private final static String        PREF_VIRTUAL_MODULE        = PREFS_PREFIX + "virtualModuleForMissingTypes";

  private final static String        PREF_HIERARCHICAL_PACKAGES = PREFS_PREFIX + "hierarchicalPackages";

  /**
   * The {@link IPreferenceStore} used to load and store settings
   */
  private final IPreferenceStore     _store;

  /** - */
  private AnalysisModelConfiguration _analysisModelConfiguration;

  /**
   * @param preferenceStore
   */
  public ArtifactModelConfigurationProvider(IPreferenceStore preferenceStore) {
    _store = preferenceStore;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.view.navigator.IArtifactModelConfigurationProvider#getCurrentArtifactModelConfiguration()
   */
  @Override
  public AnalysisModelConfiguration getArtifactModelConfiguration() {

    //

    if (_analysisModelConfiguration == null) {

      ResourceType contentType = ResourceType.SOURCE;
      boolean hierarchical = false;
      boolean includeVirtualModuleForMissingTypes = false;

      try {

        // override default settings with stored preferences
        if (_store.contains(PREF_CONTENT_TYPE)) {
          contentType = ResourceType.valueOf(_store.getString(PREF_CONTENT_TYPE));
        }
        if (_store.contains(PREF_HIERARCHICAL_PACKAGES)) {
          hierarchical = _store.getBoolean(PREF_HIERARCHICAL_PACKAGES);
        }
        if (_store.contains(PREF_VIRTUAL_MODULE)) {
          includeVirtualModuleForMissingTypes = _store.getBoolean(PREF_VIRTUAL_MODULE);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }

      _analysisModelConfiguration = new AnalysisModelConfiguration(hierarchical, contentType,
          includeVirtualModuleForMissingTypes);
    }

    // return the configuration
    return _analysisModelConfiguration;

  }

  /**
   * <p>
   * Writes the current configuraton to the preference store
   * </p>
   * 
   * <p>
   * This method is not part of the API and should not be invoked by clients
   * </p>
   * 
   * @param includeVirtualModuleForMissingTypes
   * @param contentType
   * @param hierarchical
   */
  public void store(boolean hierarchical, ResourceType contentType, boolean includeVirtualModuleForMissingTypes) {

    //
    _analysisModelConfiguration = null;

    // Store Boolean values as Strings, otherwise they were never stored, in case
    // the default value in DEFAULT_CONFIGURATION is 'false'
    _store.setValue(PREF_CONTENT_TYPE, contentType.toString());
    _store.setValue(PREF_HIERARCHICAL_PACKAGES,
        String.valueOf(
            hierarchical));
    _store.setValue(PREF_VIRTUAL_MODULE, String.valueOf(includeVirtualModuleForMissingTypes));
  }
}
