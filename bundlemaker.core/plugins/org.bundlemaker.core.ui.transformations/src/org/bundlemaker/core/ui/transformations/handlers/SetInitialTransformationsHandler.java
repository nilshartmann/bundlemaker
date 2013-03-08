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
package org.bundlemaker.core.ui.transformations.handlers;

import org.bundlemaker.core.transformations.script.config.ITransformationScriptConfigManager;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.preferences.IWorkingCopyManager;
import org.eclipse.ui.preferences.WorkingCopyManager;
import org.osgi.service.prefs.BackingStoreException;

/**
 * TODO: This is a workaround only. Remove with UI to configure transformation scripts.
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class SetInitialTransformationsHandler extends AbstractHandler implements IHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {


    ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
    // List<IType> selectedTypes = AbstractBundleMakerHandler.getSelectedObject(currentSelection,
    // IType.class);
    //
    // if (selectedTypes.isEmpty()) {
    // return null;
    // }
    //
    // // build comma-delimited list of type names
    // StringBuilder builder = new StringBuilder();
    // Iterator<IType> iterator = selectedTypes.iterator();
    //
    // while (true) {
    // builder.append(iterator.next().getFullyQualifiedName());
    //
    // if (!iterator.hasNext()) {
    // break;
    // }
    //
    // builder.append(',');
    // }
    IType selectedType;
    try {
      selectedType = TransformationScriptHandlerUtil.getTransformationScript(event, currentSelection);
    } catch (Exception ex) {
      throw new ExecutionException("Error while executing command: " + ex, ex);
    }
    if (selectedType == null) {
      return null;
    }

    // save preferences
    IWorkingCopyManager workingCopyManager = new WorkingCopyManager();

    IScopeContext scopeContext = new ProjectScope(selectedType.getJavaProject().getProject());

    setStoredValue(ITransformationScriptConfigManager.PREF_KEY, scopeContext, selectedType.getFullyQualifiedName(),
        workingCopyManager);
    try {
      workingCopyManager.applyChanges();
    } catch (BackingStoreException e) {
      e.printStackTrace();
    }

    return null;
  }

  private IEclipsePreferences getNode(IScopeContext context, IWorkingCopyManager manager) {
    IEclipsePreferences node = context.getNode(ITransformationScriptConfigManager.PREF_QUALIFIER);
    if (manager != null) {
      return manager.getWorkingCopy(node);
    }
    return node;
  }

  public void setStoredValue(String key, IScopeContext context, String value, IWorkingCopyManager manager) {
    if (value != null) {
      getNode(context, manager).put(key, value);
    } else {
      getNode(context, manager).remove(key);
    }
  }

}
