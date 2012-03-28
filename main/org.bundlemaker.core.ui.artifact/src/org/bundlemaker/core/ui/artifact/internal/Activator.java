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
package org.bundlemaker.core.ui.artifact.internal;

import java.net.MalformedURLException;
import java.net.URL;

import org.bundlemaker.core.ui.artifact.configuration.ArtifactModelConfigurationProvider;
import org.bundlemaker.core.ui.artifact.configuration.IArtifactModelConfigurationProvider;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  // The plug-in ID
  public static final String                  PLUGIN_ID = "org.bundlemaker.core.ui"; //$NON-NLS-1$

  // The shared instance
  private static Activator                    plugin;

  private IArtifactModelConfigurationProvider _artifactModelConfigurationProvider;

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

    _artifactModelConfigurationProvider = new ArtifactModelConfigurationProvider(getPreferenceStore());
    context.registerService(IArtifactModelConfigurationProvider.class.getName(), _artifactModelConfigurationProvider,
        null);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    plugin = null;

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

  public String getFile(String path) {
    Bundle bundle = Platform.getBundle(PLUGIN_ID);
    Object file = FileLocator.find(bundle, new Path(path), null);
    if (file != null) {
      return file.toString();
    } else {
      return null;
    }
  }

  public Image getIcon(String icon) {
    ImageRegistry registry = getImageRegistry();
    Image image = registry.get(icon);

    if (image != null) {
      return image;
    }
    URL url = null;
    try {
      url = new URL(getFile("icons/" + icon));
    } catch (MalformedURLException e) {
    }

    ImageDescriptor myImage = ImageDescriptor.createFromURL(url);
    image = myImage.createImage();
    getImageRegistry().put(icon, image);

    return image;
  }

  /**
   * @return the {@link IArtifactModelConfigurationProvider}. Never null
   */
  public IArtifactModelConfigurationProvider getArtifactModelConfigurationProvider() {
    return _artifactModelConfigurationProvider;
  }

}
