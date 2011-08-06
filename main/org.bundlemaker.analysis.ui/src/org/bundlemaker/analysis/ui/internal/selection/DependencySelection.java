/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.analysis.ui.internal.selection;

import java.util.Collections;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.ui.selection.IArtifactSelection;
import org.bundlemaker.analysis.ui.selection.IDependencySelection;
import org.eclipse.core.runtime.Assert;

/**
 * Implementation of {@link IArtifactSelection}
 * 
 * @author Nils Hartmann
 * 
 */
public class DependencySelection implements IDependencySelection {

  /**
   * The provider that provides this selection
   */
  private final String            _providerId;

  /**
   * the selected artifacts
   */
  private final List<IDependency> _selectedDependencies;

  public DependencySelection(String providerId, List<IDependency> selectedDependencies) {
    Assert.isNotNull(providerId, "The parameter 'providerId' must not be null");
    Assert.isNotNull(selectedDependencies, "The parameter 'selectedDependencies' must not be null");

    _providerId = providerId;
    _selectedDependencies = Collections.unmodifiableList(selectedDependencies);
  }

  @Override
  public String getProviderId() {
    return _providerId;
  }

  @Override
  public List<IDependency> getSelectedDependencies() {
    return _selectedDependencies;
  }

}
