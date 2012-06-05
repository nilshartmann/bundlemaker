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
package org.bundlemaker.core.ui.handler.exporter;

import java.util.Vector;

import org.bundlemaker.core.ui.internal.Activator;
import org.eclipse.jface.dialogs.IDialogSettings;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @see Copied from org.eclipse.team.internal.ui.wizards.PsfStore.class
 */
public abstract class ConfigurationStore {
  // Most recently used filename is first in the array.
  // Least recently used filename is at the end of the list.
  // When the list overflows, items drop off the end.
  private static final int       HISTORY_LENGTH = 10;

  private static final String    STORE_SECTION  = "ImportPSFDialog"; //$NON-NLS-1$

  private static IDialogSettings _section;

  protected abstract String getPreviousTag();

  protected abstract String getListTag();

  protected String getPrevious() {
    IDialogSettings section = getSettingsSection();
    String retval = section.get(getPreviousTag());
    if (retval == null) {
      retval = ""; //$NON-NLS-1$
    }
    return retval;
  }

  public String[] getHistory() {
    IDialogSettings section = getSettingsSection();
    String[] arr = section.getArray(getListTag());
    if (arr == null) {
      arr = new String[0];
    }
    return arr;
  }

  public void remember(String filename) {
    Vector<String> filenames = createVector(getHistory());
    if (filenames.contains(filename)) {
      // The item is in the list. Remove it and add it back at the
      // beginning. If it already was at the beginning this will be a
      // waste of time, but it's not even measurable so I don't care.
      filenames.remove(filename);
    }
    // Most recently used filename goes to the beginning of the list
    filenames.add(0, filename);

    // Forget any overflowing items
    while (filenames.size() > HISTORY_LENGTH) {
      filenames.remove(HISTORY_LENGTH);
    }

    // Make it an array
    String[] arr = filenames.toArray(new String[filenames.size()]);

    IDialogSettings section = getSettingsSection();
    section.put(getListTag(), arr);
    section.put(getPreviousTag(), filename);
  }

  private Vector<String> createVector(String[] arr) {
    Vector<String> v = new Vector<String>();
    for (int ix = 0; ix < arr.length; ++ix) {
      v.add(ix, arr[ix]);
    }
    return v;
  }

  private IDialogSettings getSettingsSection() {
    if (_section != null)
      return _section;

    IDialogSettings settings = Activator.getDefault().getDialogSettings();
    _section = settings.getSection(getStoreSection());
    if (_section != null)
      return _section;

    _section = settings.addNewSection(getStoreSection());
    return _section;
  }

  protected abstract String getStoreSection();
}
