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
package org.bundlemaker.core.ui.view.navigator;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration.ResourcePresentation;
import org.bundlemaker.core.projectdescription.ContentType;
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

  private static ArtifactModelConfiguration DEFAULT_CONFIGURATION;

  private final static String               PREFS_PREFIX               = "org.bundlemaker.core.ui.prefs.artifactModelConfiguration.";

  private final static String               PREF_AGGREGATE_INNER_TYPES = PREFS_PREFIX + "aggregateInnerTypes";

  private final static String               PREF_RESOURCE_PRESENTATION = PREFS_PREFIX + "resourcePresentation";

  private final static String               PREF_CONTENT_TYPE          = PREFS_PREFIX + "contentType";

  private final static String               PREF_VIRTUAL_MODULE        = PREFS_PREFIX + "virtualModuleForMissingTypes";

  private final static String               PREF_HIERARCHICAL_PACKAGES = PREFS_PREFIX + "hierarchicalPackages";

  /**
   * The {@link IPreferenceStore} used to load and store settings
   */
  private final IPreferenceStore            _store;

  /**
   * The managed configuration object
   */
  private ArtifactModelConfiguration        _configuration;

  private synchronized static ArtifactModelConfiguration getDefaultArtifactModelConfiguration() {
    if (DEFAULT_CONFIGURATION == null) {
      ArtifactModelConfiguration configuration = new ArtifactModelConfiguration();
      configuration.setAggregateInnerTypes(true);
      configuration.setResourcePresentation(ResourcePresentation.ONLY_NON_TYPE_RESOURCES);
      DEFAULT_CONFIGURATION = configuration;
    }

    return DEFAULT_CONFIGURATION;
  }

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
  public ArtifactModelConfiguration getArtifactModelConfiguration() {
    if (_configuration == null) {
      // Get instance pre-filled with default settings
      _configuration = getDefaultArtifactModelConfiguration();
      try {
        // override default settings with stored preferences
        if (_store.contains(PREF_AGGREGATE_INNER_TYPES)) {
          _configuration.setAggregateInnerTypes(_store.getBoolean(PREF_AGGREGATE_INNER_TYPES));
        }
        if (_store.contains(PREF_CONTENT_TYPE)) {
          _configuration.setContentType(ContentType.valueOf(_store.getString(PREF_CONTENT_TYPE)));
        }
        if (_store.contains(PREF_HIERARCHICAL_PACKAGES)) {
          _configuration.setHierarchicalPackages(_store.getBoolean(PREF_HIERARCHICAL_PACKAGES));
        }
        if (_store.contains(PREF_RESOURCE_PRESENTATION)) {
          _configuration.setResourcePresentation(ResourcePresentation.valueOf(_store
              .getString(PREF_RESOURCE_PRESENTATION)));
        }
        if (_store.contains(PREF_VIRTUAL_MODULE)) {
          _configuration.setIncludeVirtualModuleForMissingTypes(_store.getBoolean(PREF_VIRTUAL_MODULE));
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    // return the configuration
    return _configuration;

  }

  /**
   * Writes the current configuraton to the preference store
   * 
   * <p>
   * This method is not part of the API and should not be invoked by clients
   * 
   * @param configuration
   */
  public void store() {
    _store.setValue(PREF_AGGREGATE_INNER_TYPES, _configuration.isAggregateInnerTypes());
    _store.setValue(PREF_CONTENT_TYPE, _configuration.getContentType().toString());
    _store.setValue(PREF_RESOURCE_PRESENTATION, _configuration.getResourcePresentation().toString());
    _store.setValue(PREF_VIRTUAL_MODULE, _configuration.isIncludeVirtualModuleForMissingTypes());
    _store.setValue(PREF_HIERARCHICAL_PACKAGES, _configuration.isHierarchicalPackages());
  }

}
