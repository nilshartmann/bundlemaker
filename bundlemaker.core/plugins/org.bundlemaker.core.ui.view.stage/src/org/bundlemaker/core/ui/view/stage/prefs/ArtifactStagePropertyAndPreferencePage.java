/*******************************************************************************
 * Copyright (c) 2013 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.ui.view.stage.prefs;

import org.bundlemaker.core.ui.preferences.AbstractPropertyAndPreferencesPage;
import org.bundlemaker.core.ui.preferences.ConfigurationBlock;
import org.bundlemaker.core.ui.view.stage.internal.Activator;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactStagePropertyAndPreferencePage extends AbstractPropertyAndPreferencesPage {

  @Override
  public String getStoreIdentifier() {
    return Activator.PLUGIN_ID;
  }

  @Override
  protected String getPreferencePageID() {
    return "org.bundlemaker.core.ui.stage.preferences";
  }

  @Override
  protected String getPropertyPageID() {
    return "org.bundlemaker.core.ui.stage.properties";
  }

  @Override
  protected ConfigurationBlock createPreferenceContent(Composite composite) {
    return new ArtifactStagePreferencesConfigurationBlock(composite, this);
  }

}
