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
package org.bundlemaker.core.ui.commands;

import java.util.List;

import org.bundlemaker.analysis.ui.handlers.AbstractBundleMakerHandler;
import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.ui.Activator;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class RunTransformationScriptHandler extends AbstractBundleMakerHandler {

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.commands.AbstractBundleMakerHandler#execute(org.eclipse.core.commands.ExecutionEvent
   * , org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected void execute(ExecutionEvent event, ISelection selection) throws Exception {

    IFile scriptFile = null;

    if (selection instanceof ITextSelection) {
      // ITextSelection textSelection = (ITextSelection) selection;
      IEditorPart editor = HandlerUtil.getActiveEditor(event);
      if (editor != null) {
        IEditorInput editorInput = editor.getEditorInput();
        scriptFile = (IFile) editorInput.getAdapter(IFile.class);
      }
    } else {
      List<IFile> files = getSelectedObject(selection, IFile.class);
      if (files.size() > 0) {
        scriptFile = files.get(0);
      }
    }

    if (scriptFile == null) {
      return;
    }

    // Select the bundlemaker project
    IBundleMakerProject bundleMakerProject = selectBundleMakerProject();

    if (bundleMakerProject == null) {
      return;
    }

    String moduleName = TransformHandler.getModuleName(scriptFile);
    TransformHandler.transform(bundleMakerProject, moduleName, scriptFile);

  }

  /**
   * @return
   */
  private IBundleMakerProject selectBundleMakerProject() throws Exception {
    ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(new Shell(), new WorkbenchLabelProvider(),
        new WorkbenchContentProvider());
    dialog.setHelpAvailable(false);
    dialog.setTitle("Choose bundlemaker project");
    dialog.setMessage("Choose the bundlemaker project that your transformation script should be applied on");

    // set the filter that filters bundlemaker projects
    dialog.addFilter(new BundleMakerProjectFilter());

    // set the validator that checks that only IProject instances can be selected
    dialog.setValidator(new ISelectionStatusValidator() {

      private IStatus fgErrorStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "");

      private IStatus fgOKStatus    = new Status(IStatus.OK, Activator.PLUGIN_ID, "");

      @Override
      public IStatus validate(Object[] selection) {

        for (Object object : selection) {
          if (!(object instanceof IProject)) {
            return fgErrorStatus;
          }
        }
        return fgOKStatus;
      }
    });
    dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
    dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
    dialog.setAllowMultiple(false);

    // open the dialog and return the selected IFile
    if (dialog.open() == Window.OK) {
      Object firstResult = dialog.getFirstResult();
      if (firstResult == null) {
        return null;
      }
      IProject selectedProject = (IProject) firstResult;
      return BundleMakerCore.getBundleMakerProject(selectedProject, null);
    }
    return null;

  }

  class BundleMakerProjectFilter extends ViewerFilter {

    public boolean select(Viewer viewer, Object parent, Object element) {

      System.out.println("element: " + element);

      if (element instanceof IProject) {
        IProject project = (IProject) element;
        if (!project.isOpen()) {
          return false;
        }

        try {
          if (project.hasNature(BundleMakerCore.NATURE_ID)) {
            IBundleMakerProject bundleMakerProject = BundleMakerCore.getBundleMakerProject(project, null);
            return (bundleMakerProject.getState() == BundleMakerProjectState.READY);
          }
        } catch (CoreException e) {
          reportError(Activator.PLUGIN_ID, e.toString(), e);
        }
      }
      return false;
    }
  }

}
