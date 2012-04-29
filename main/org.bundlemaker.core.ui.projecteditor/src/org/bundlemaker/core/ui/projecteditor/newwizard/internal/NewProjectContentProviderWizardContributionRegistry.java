/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.projecteditor.newwizard.internal;

import java.util.LinkedHashSet;
import java.util.Set;

import org.bundlemaker.core.ui.projecteditor.jdt.wizard.JdtProjectContentProviderWizardContribution;
import org.bundlemaker.core.ui.projecteditor.provider.NewProjectContentProviderWizardContribution;

/**
 * TODO Extension Point or Service Registry
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class NewProjectContentProviderWizardContributionRegistry {

  private final Set<NewProjectContentProviderWizardContribution> _registry = new LinkedHashSet<NewProjectContentProviderWizardContribution>();

  public NewProjectContentProviderWizardContributionRegistry() {
    _registry.add(new JdtProjectContentProviderWizardContribution());
    // _registry.add(new)
  }

  public Set<NewProjectContentProviderWizardContribution> getRegisteredWizardContributions() {
    return _registry;
  }

}
