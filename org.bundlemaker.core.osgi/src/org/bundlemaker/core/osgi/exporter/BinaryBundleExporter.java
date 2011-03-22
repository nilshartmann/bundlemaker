/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.osgi.exporter;

import org.bundlemaker.core.osgi.internal.manifest.DroolsBasedBundleManifestCreator;
import org.bundlemaker.core.osgi.internal.manifest.ExportPackagePreferences;
import org.bundlemaker.core.osgi.internal.manifest.PackageWiringPreferences;
import org.bundlemaker.core.osgi.manifest.DependencyStyle;
import org.bundlemaker.core.osgi.manifest.IBundleManifestCreator;
import org.eclipse.core.runtime.CoreException;

import com.springsource.bundlor.util.BundleManifestUtils;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class BinaryBundleExporter extends AbstractJarFileBundleExporter {

  /** - */
  private DependencyStyle                  _dependencyStyle         = DependencyStyle.PREFER_IMPORT_PACKAGE;

  /** - */
  private boolean                          _useOriginalOSGiManifest = true;

  /** - */
  private DroolsBasedBundleManifestCreator _manifestCreator;

  /**
   * <p>
   * Creates a new instance of type {@link BinaryBundleExporter}.
   * </p>
   */
  public BinaryBundleExporter() {

    //
    _manifestCreator = new DroolsBasedBundleManifestCreator();
  }

  /**
   * <p>
   * </p>
   * 
   * @param useRequireBundle
   */
  public void setDependencyStyle(DependencyStyle dependencyStyle) {
    _dependencyStyle = dependencyStyle;
  }

  /**
   * <p>
   * </p>
   * 
   * @param useOriginalOSGiManifest
   */
  public void setUseOriginalOSGiManifest(boolean useOriginalOSGiManifest) {
    _useOriginalOSGiManifest = useOriginalOSGiManifest;
  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @param modularizedSystem
  // * @param module
  // * @param context
  // * @return
  // * @throws Exception
  // */
  // protected ManifestContents createManifest() throws CoreException {
  //
  // // create the manifest
  // IBundleManifestCreator creator = new DroolsBasedBundleManifestCreator();
  //
  // // set
  // creator.setDependencyStyle(_dependencyStyle);
  // creator.setUseOriginalOSGiManifest(_useOriginalOSGiManifest);
  //
  // // create manifest
  // return creator.createManifest(getCurrentModularizedSystem(),
  // getCurrentModule(), getCurrentContext(), sgetCurrentManifestTemplate());
  // }

  /**
   * {@inheritDoc}
   */
  protected ManifestContents createManifest() throws CoreException {
    return _manifestCreator.createManifest(getCurrentModularizedSystem(), getCurrentModule(),
        BundleManifestUtils.createBundleManifest(getCurrentManifestTemplate()),
        BundleManifestUtils.createBundleManifest(getOriginalManifest()), new ExportPackagePreferences(),
        new PackageWiringPreferences());
  }
}
