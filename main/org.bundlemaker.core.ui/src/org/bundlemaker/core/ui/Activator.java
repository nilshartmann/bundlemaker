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
package org.bundlemaker.core.ui;

import org.bundlemaker.core.ui.artifact.configuration.IArtifactModelConfigurationProvider;
import org.bundlemaker.core.ui.editor.adapter.ProjectDescriptionAdapterFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  // The plug-in ID
  public static final String PLUGIN_ID = "org.bundlemaker.core.ui"; //$NON-NLS-1$

  // The shared instance
  private static Activator   plugin;

  private BundleContext      _bundleContext;

  /**
   * The constructor
   */
  public Activator() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    plugin = this;
    _bundleContext = context;

    ProjectDescriptionAdapterFactory.register();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    plugin = null;
    _bundleContext = null;

    ProjectDescriptionAdapterFactory.unregister();
    super.stop(context);
  }

  /**
   * Returns the shared instance
   * 
   * @return the shared instance
   */
  public static Activator getDefault() {
    return plugin;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IArtifactModelConfigurationProvider getArtifactModelConfigurationProvider() {

    //
    ServiceReference serviceReference = _bundleContext.getServiceReference(IArtifactModelConfigurationProvider.class
        .getName());

    //
    if (serviceReference != null) {

      //
      IArtifactModelConfigurationProvider provider = (IArtifactModelConfigurationProvider) _bundleContext
          .getService(serviceReference);

      //
      return provider;
    }

    //
    return null;
  }
}
