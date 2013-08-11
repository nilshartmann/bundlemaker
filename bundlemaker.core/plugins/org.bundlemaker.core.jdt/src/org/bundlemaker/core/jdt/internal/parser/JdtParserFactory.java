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

import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.spi.parser.IParser;
import org.bundlemaker.core.spi.parser.IParserFactory;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtParserFactory extends IParserFactory.Adapter {

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) throws CoreException {

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
  public void dispose(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) {
    JdtProjectHelper.deleteAssociatedProjectIfNecessary(bundleMakerProject.getProject());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IParser createParser() throws CoreException {
    return new JdtParser();
  }
}
