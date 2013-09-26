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
package org.bundlemaker.core.spi.parser;

import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * A parser factory is responsible for creating project parser.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IParserFactory {

  /**
   * <p>
   * This method is called immediately after a {@link IParserFactory} has been created.
   * </p>
   */
  void initialize();

  /**
   * <p>
   * This method is called before a {@link IParserFactory} will be destroyed.
   * </p>
   */
  void dispose();

  // /**
  // * <p>
  // * Initializes the {@link IParserFactory} for the specified project. If the {@link IProjectDescription} of an
  // * {@link IProjectDescriptionAwareBundleMakerProject} has changed, the
  // * {@link IProjectDescriptionAwareBundleMakerProject} <b>always</b> will be re-initialized.
  // * </p>
  // *
  // * @param bundleMakerProject
  // */
  // void initialize(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) throws CoreException;

  /**
   * <p>
   * Creates a new instance of type {@link IParser} for a given {@link IProjectDescriptionAwareBundleMakerProject}.
   * </p>
   * 
   * @return the newly created {@link IParser}
   * @throws CoreException
   */
  IParser createParser() throws CoreException;

  // /**
  // * <p>
  // * Disposes the {@link IParserFactory} for the specified project.
  // * </p>
  // *
  // * @param bundleMakerProject
  // */
  // void dispose(IProjectDescriptionAwareBundleMakerProject bundleMakerProject);

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  static abstract class Adapter implements IParserFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
    }
  }
}
