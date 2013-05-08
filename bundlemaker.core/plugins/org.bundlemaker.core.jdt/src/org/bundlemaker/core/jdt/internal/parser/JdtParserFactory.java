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
package org.bundlemaker.core.jdt.internal.parser;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.jdt.parser.CoreParserJdt;
import org.bundlemaker.core.jdt.parser.IJdtSourceParserHook;
import org.bundlemaker.core.parser.IParser;
import org.bundlemaker.core.parser.IParserFactory;
import org.bundlemaker.core.util.ExtensionRegistryTracker;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtParserFactory extends IParserFactory.Adapter {

  /** - */
  private ExtensionRegistryTracker<IJdtSourceParserHook> _hookRegistry;

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize() {
    _hookRegistry = new ExtensionRegistryTracker<IJdtSourceParserHook>(CoreParserJdt.EXTENSION_POINT_ID);
    _hookRegistry.initialize();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {
    _hookRegistry.dispose();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(IBundleMakerProject bundleMakerProject) throws CoreException {

    // create or get the java project
    if (!JdtProjectHelper.hasAssociatedJavaProject(bundleMakerProject)) {
      JdtProjectHelper.newAssociatedJavaProject(bundleMakerProject);
    }

    JdtProjectHelper.setupAssociatedJavaProject(bundleMakerProject);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose(IBundleMakerProject bundleMakerProject) {
    JdtProjectHelper.deleteAssociatedProjectIfNecessary(bundleMakerProject.getProject());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IParser createParser(IBundleMakerProject bundleMakerProject) throws CoreException {
    return new JdtParser(bundleMakerProject, _hookRegistry);
  }
}
