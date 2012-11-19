/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.analysis.selectors;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;

/**
 * Selects modules by its names. Names can be specified using <tt>**</tt> as placeholder (Ant-style)
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ModuleSelector extends AbstractPatternBasedSelector {

  public ModuleSelector(IBundleMakerArtifact artifact, String includePattern, String excludePattern) {
    super(artifact, includePattern, excludePattern);
  }

  /**
   * @param artifact
   * @param includePattern
   */
  public ModuleSelector(IBundleMakerArtifact artifact, String includePattern) {
    super(artifact, includePattern);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.IArtifactSelector#getBundleMakerArtifacts()
   */
  @Override
  public List<? extends IBundleMakerArtifact> getBundleMakerArtifacts() {
    final List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();

    getBundleMakerArtifact().accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {

        //
        String moduleName = moduleArtifact.getQualifiedName();

        if (isIncluded(moduleName) && !isExcluded(moduleName))
        {
          result.add(moduleArtifact);
        }

        //
        return true;
      }
    });

    return result;
  }

}
