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
package org.bundlemaker.analysis.ui.handlers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.ui.internal.Activator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Abstract base class for BundleMaker-based command handlers.
 * <p>
 * This class provides some convenience methods for own handler implementations
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractBundleMakerHandler extends AbstractHandler implements IHandler {
  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {

    ISelection selection = HandlerUtil.getCurrentSelection(event);
    if (selection != null) {
      // Invoke execution method
      try {
        execute(event, selection);
      } catch (Exception ex) {
        reportError(Activator.PLUGIN_ID, "Error while executing command: " + ex, ex);
        throw new ExecutionException("Error while executing command: " + ex, ex);
      }
    }

    // execute() methods always must return null
    return null;
  }

  /**
   * Is called when the handler is executed
   * 
   * @param event
   *          the ExecutionEvent. Never null
   * @param selection
   *          the selection. Never null
   * @throws Exception
   */
  protected abstract void execute(ExecutionEvent event, ISelection selection) throws Exception;

  /**
   * Report an error via Eclipse Log service
   * 
   * @param pluginId
   * @param message
   * @param ex
   */
  protected void reportError(String pluginId, String message, Throwable ex) {
    Status errorStatus = new Status(IStatus.ERROR, pluginId, message, ex);
    Activator.getDefault().getLog().log(errorStatus);
  }

  /**
   * Returns all elements in the given {@link ISelection} that are of the specified type
   * 
   * <p>
   * Selected objects in the specified selection that are <em>not</em> instances of the specified type are ignored.
   * 
   * @param <T>
   * @param selection
   *          The object containing selected objects
   * @param type
   *          the expected type of the result objects
   * @return
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static <T> List<T> getSelectedObject(ISelection selection, Class<T> type) {
    final List<T> result = new LinkedList<T>();
    if (selection instanceof IStructuredSelection) {
      IStructuredSelection structuredSelection = (IStructuredSelection) selection;
      Iterator iterator = structuredSelection.iterator();
      while (iterator.hasNext()) {
        Object element = iterator.next();
        if (type.isInstance(element)) {
          result.add((T) element);
        }
      }
    }
    return result;
  }

}
