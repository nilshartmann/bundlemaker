package org.bundlemaker.core.osgi.internal.manifest;

import org.bundlemaker.core.osgi.manifest.IExportPackagePreferences;

public class ExportPackagePreferences implements IExportPackagePreferences {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean noUses() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean noExportPackageVersion() {
    return false;
  }
}
