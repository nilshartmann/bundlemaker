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
package org.bundlemaker.core.osgi.exporter.helper;

import java.io.File;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public abstract class AbstractTemplateDirectoryBasedExporterHelper extends AbstractExporterHelper {

  /** the root directory for all templates */
  private File _templateRootDirectory;

  /** the module template directory */
  private File _moduleTemplateDirectory;

  /**
   * @param modularizedSystem
   * @param module
   * @param context
   */
  public AbstractTemplateDirectoryBasedExporterHelper(IModularizedSystem modularizedSystem, IResourceModule module,
      IModuleExporterContext context) {

    this(modularizedSystem, module, context, null);
  }

  /**
   * @param modularizedSystem
   * @param module
   * @param context
   * @param templateRootDirectory
   */
  public AbstractTemplateDirectoryBasedExporterHelper(IModularizedSystem modularizedSystem, IResourceModule module,
      IModuleExporterContext context, File templateRootDirectory) {

    //
    super(modularizedSystem, module, context);

    //
    if (templateRootDirectory != null) {

      // assert directory
      Assert.isTrue(templateRootDirectory.isDirectory(),
          String.format("Template directory '%s' has to be a directory.", templateRootDirectory.getAbsolutePath()));

      //
      _templateRootDirectory = templateRootDirectory;

      // 'create' the project template directory
      _moduleTemplateDirectory = createModuleTemplateDirectory();
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final boolean hasTemplateRootDirectory() {
    return _templateRootDirectory != null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final boolean hasModuleTemplateDirectory() {
    return _moduleTemplateDirectory != null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final File getTemplateRootDirectory() {
    return _templateRootDirectory;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final File getModuleTemplateDirectory() {
    return _moduleTemplateDirectory;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected File createModuleTemplateDirectory() {

    //
    File result = new File(_templateRootDirectory, getModule().getModuleIdentifier().toString());

    if (result.exists()) {
      return result;
    }

    //
    result = new File(_templateRootDirectory, getModule().getModuleIdentifier().getName());

    if (result.exists()) {
      return result;
    }

    //
    return null;
  }
}
