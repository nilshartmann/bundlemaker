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

import java.util.List;

import org.bundlemaker.core.jdt.internal.ExtensionRegistryTracker;
import org.bundlemaker.core.jdt.parser.IJdtSourceParserHook;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.spi.parser.AbstractParser;
import org.bundlemaker.core.spi.parser.IParser;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractHookAwareJdtParser extends AbstractParser implements IParser {

  /** - */
  private ExtensionRegistryTracker<IJdtSourceParserHook> _hookRegistry;

  /** - */
  private List<IJdtSourceParserHook>                     _currentHooks;

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @throws CoreException
   */
  public AbstractHookAwareJdtParser(ExtensionRegistryTracker<IJdtSourceParserHook> hookRegistry) {

    Assert.isNotNull(hookRegistry);

    //
    _hookRegistry = hookRegistry;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parseBundleMakerProjectStart(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) {

    // initialize current hooks
    _currentHooks = _hookRegistry.getExtensionObjects();

    // notify 'start'
    for (IJdtSourceParserHook sourceParserHook : _currentHooks) {
      sourceParserHook.parseBundleMakerProjectStart(bundleMakerProject);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parseBundleMakerProjectStop(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) {

    // notify 'stop'
    for (IJdtSourceParserHook sourceParserHook : _currentHooks) {
      sourceParserHook.parseBundleMakerProjectStop(bundleMakerProject);
    }

    //
    _currentHooks = null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param iCompilationUnit
   * @param compilationUnit
   */
  protected final void callSourceParserHooks(IProjectContentResource resourceKey, CompilationUnit compilationUnit) {

    //
    Assert.isNotNull(_currentHooks);

    //
    for (IJdtSourceParserHook hook : _currentHooks) {

      //
      hook.analyzeCompilationUnit(resourceKey, compilationUnit);
    }
  }
}
