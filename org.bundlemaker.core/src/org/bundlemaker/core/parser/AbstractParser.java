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
package org.bundlemaker.core.parser;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IResourceKey;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractParser implements IParser {

  /**
   * {@inheritDoc}
   */
  @Override
  public void parseBundleMakerProjectStart(IBundleMakerProject bundleMakerProject) {
    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IProblem> parse(IFileBasedContent content, List<IDirectory> directoryList, IResourceCache cache,
      IProgressMonitor progressMonitor) throws CoreException {

    List<IProblem> _errors = new LinkedList<IProblem>();

    // iterate over the directories and parse the directory fragments
    for (IDirectory directory : directoryList) {

      // TODO Support for ParserType.SOURCE_AND_BINARY
      List<IDirectoryFragment> directoryFragments = getParserType().equals(ParserType.BINARY) ? directory
          .getBinaryDirectoryFragments() : directory.getSourceDirectoryFragments();

      for (IDirectoryFragment directoryFragment : directoryFragments) {

        // finally: parse the class files
        for (IResourceKey resourceKey : directoryFragment.getResourceKeys()) {

          if (canParse(resourceKey)) {

            // parse the class file
            parseResource(resourceKey, content, cache);
          }
          //
          progressMonitor.worked(1);
        }
      }
    }

    //
    return _errors;
  }

  @Override
  public List<IProblem> parseResources(IFileBasedContent content, List<IResource> resources, IResourceCache cache,
      IProgressMonitor _progressMonitor) throws CoreException {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parseBundleMakerProjectStop(IBundleMakerProject bundleMakerProject) {
    // ignore
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceKey
   * @param cache
   */
  protected abstract void parseResource(IResourceKey resourceKey, IFileBasedContent content, IResourceCache cache);

  /**
   * <p>
   * </p>
   * 
   * @param resourceKey
   * @return
   */
  protected abstract boolean canParse(IResourceKey resourceKey);
}
