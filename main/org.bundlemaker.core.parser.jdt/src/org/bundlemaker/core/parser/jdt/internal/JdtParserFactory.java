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
package org.bundlemaker.core.parser.jdt.internal;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.parser.IParser;
import org.bundlemaker.core.parser.IParserFactory;
import org.bundlemaker.core.parser.jdt.CoreParserJdt;
import org.bundlemaker.core.parser.jdt.IJdtSourceParserHook;
import org.bundlemaker.core.util.ExtensionRegistryTracker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtParserFactory implements IParserFactory {

  /** - */
  private IResourceChangeListener                        _resourceChangeListener;

  /** - */
  private ExtensionRegistryTracker<IJdtSourceParserHook> _hookRegistry;

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize() {
    _resourceChangeListener = new DeleteAssociatedProjectChangeListener();
    ResourcesPlugin.getWorkspace().addResourceChangeListener(_resourceChangeListener,
        IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE);

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
  public IParser createParser(IBundleMakerProject bundleMakerProject, boolean parseIndirectReferences)
      throws CoreException {
    return new JdtParser(bundleMakerProject, _hookRegistry, parseIndirectReferences);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose(IBundleMakerProject bundleMakerProject) {

  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private boolean createAssociatedJavaProject(IBundleMakerProject bundleMakerProject) {

    //
    if (!JdtProjectHelper.hasAssociatedJavaProject(bundleMakerProject)) {
      return true;
    }

    //
    IProject project = JdtProjectHelper.getAssociatedJavaProjectAsProject(bundleMakerProject);

    //
    IResource resource = bundleMakerProject.getProject().findMember(BundleMakerCore.PROJECT_DESCRIPTION_PATH);

    //
    return resource.getLocalTimeStamp() > project.getLocalTimeStamp();
  }
}
